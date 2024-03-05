package salonmanager;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.servicios.ServicioWorkshift;

public class WorkshiftEndPanel extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOConfig daoC = new DAOConfig();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();
    ServicioWorkshift sw = new ServicioWorkshift();
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    JComboBox comboTabs = new JComboBox();
    JTextField fieldFinalAmount = new JTextField();

    Salon salon = null;
    Workshift actualWs = null;
    Workshift newWs = null;
    ArrayList<Table> actualTabs = new ArrayList<Table>();
    ArrayList<Table> newTabs = new ArrayList<Table>();
    ArrayList<Table> toEraseTabs = new ArrayList<Table>();
    ArrayList<Table> toUpdTabs = new ArrayList<Table>();
    double total = 0;
    double cash = 0;
    double electronic = 0;
    double error = 0;

    public WorkshiftEndPanel(Salon sal, Workshift ws1, Workshift ws2, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> toErsdTabs, ArrayList<Table> updTabs, boolean errorWs) throws Exception {
        actualWs = ws1;
        newWs = ws2;
        actualTabs = filterClose(actTabs);
        
        if (nTabs == null) {
            newTabs = new ArrayList<Table>();
        } else {
            newTabs = nTabs;
        }

        if (toErsdTabs == null) {
            toEraseTabs = new ArrayList<Table>();
        } else {
            toEraseTabs = toErsdTabs;
        }

        if (updTabs == null) {
            toUpdTabs = new ArrayList<Table>();
        } else {
            toUpdTabs = updTabs;
        }

        salon = sal;
        sm.addFrame(this);
        User cashier = actualWs.getWsCashier();
        setTitle("Cierre de turno");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);
        String nomCashier = cashier.getName();
        String pronomCashier = cashier.getLastName();
        String tsInit = actualWs.getWsOpen() + "";
        String tsClose = actualWs.getWsClose() + "";

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("CIERRE DE TURNO");
        panelLabel.add(labelTit);

        JLabel labelCashier = utiliGraf.labelTitleBacker2W("Titular caja: " + nomCashier.toUpperCase() + " " + pronomCashier.toUpperCase());
        labelCashier.setBounds(50, 50, 300, 25);
        panelPpal.add(labelCashier);

        JLabel labelOpen = utiliGraf.labelTitleBacker2W("Inicio: " + tsInit);
        labelOpen.setBounds(380, 50, 300, 25);
        panelPpal.add(labelOpen);

        JLabel labelClose = utiliGraf.labelTitleBacker2W("Cierre: " + tsClose);
        labelClose.setBounds(380, 70, 300, 25);
        panelPpal.add(labelClose);

        JPanel panelMounts = new JPanel();
        panelMounts.setLayout(null);
        panelMounts.setBounds(80, 105, 510, 300);
        panelMounts.setBackground(bluLg);
        panelPpal.add(panelMounts);

        total = actualWs.getWsTotalMount();
        cash = actualWs.getWsTotalMountCash();
        electronic = actualWs.getWsTotalMountElectronic();
        error = actualWs.getWsErrorMount();

        //Facturación
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new BoxLayout(panelTotal, BoxLayout.X_AXIS));
        panelTotal.setBounds(20, 20, 470, 50);
        panelMounts.add(panelTotal);

        JLabel labelTotal1 = utiliGraf.labelTitleBackerA3("Facturación:");
        panelTotal.add(labelTotal1);

        panelTotal.add(Box.createHorizontalGlue());

        JLabel labelTotal2 = utiliGraf.labelTitleBackerA3("$ " + total);
        labelTotal2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelTotal.add(labelTotal2);

        //Efectivo
        JPanel panelCash = new JPanel();
        panelCash.setLayout(new BoxLayout(panelCash, BoxLayout.X_AXIS));
        panelCash.setBounds(20, 90, 470, 50);
        panelMounts.add(panelCash);

        JLabel labelCash1 = utiliGraf.labelTitleBackerA3("Efectivo:");
        panelCash.add(labelCash1);

        panelCash.add(Box.createHorizontalGlue());

        JLabel labelCash2 = utiliGraf.labelTitleBackerA3("$ " + cash);
        labelCash2.setAlignmentX(Component.RIGHT_ALIGNMENT); // Alinear a la derecha
        panelCash.add(labelCash2);

        //Electronic
        JPanel panelElectronic = new JPanel();
        panelElectronic.setLayout(new BoxLayout(panelElectronic, BoxLayout.X_AXIS));
        panelElectronic.setBounds(20, 160, 470, 50);
        panelMounts.add(panelElectronic);

        JLabel labelElectronic1 = utiliGraf.labelTitleBackerA3("Transferencia:");
        panelElectronic.add(labelElectronic1);

        panelElectronic.add(Box.createHorizontalGlue());

        JLabel labelElectronic2 = utiliGraf.labelTitleBackerA3("$ " + electronic);
        labelElectronic2.setAlignmentX(Component.RIGHT_ALIGNMENT); // Alinear a la derecha
        panelElectronic.add(labelElectronic2);

        //Error
        JPanel panelError = new JPanel();
        panelError.setLayout(new BoxLayout(panelError, BoxLayout.X_AXIS));
        panelError.setBounds(20, 230, 470, 50);
        panelMounts.add(panelError);

        JLabel labelError1 = utiliGraf.labelTitleBackerA3("Error:");
        panelError.add(labelError1);

        panelError.add(Box.createHorizontalGlue());

        JLabel labelError2 = utiliGraf.labelTitleBackerA3("$ " + error);
        labelError2.setAlignmentX(Component.RIGHT_ALIGNMENT); // Alinear a la derecha
        panelError.add(labelError2);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                utiliMsg.confirmRealWsMount();
                salon.setEnabled(true);
                dispose();
            }
        });

        JPanel panelRealAmount = new JPanel();
        panelRealAmount.setBounds(80, 405, 510, 70);
        panelRealAmount.setLayout(null);
        panelPpal.add(panelRealAmount);

        JLabel labelInMount = utiliGraf.labelTitleBacker2("Ingrese la suma total del efectivo y de las transferencias:");
        labelInMount.setBounds(40, 5, 500, 20);
        panelRealAmount.add(labelInMount);

        fieldFinalAmount.setBounds(100, 35, 150, 30);
        fieldFinalAmount.setFont(new Font("Arial", Font.PLAIN, 20));
        panelRealAmount.add(fieldFinalAmount);
        JButton buttonConfirm = utiliGraf.button2("Confirmar", 270, 35, 120);
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    confirmRealAmount();
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelRealAmount.add(buttonConfirm);
        
        JButton buttonDeferWsClose = utiliGraf.button2("Arbritrar", 330, 35, 120);
        buttonDeferWsClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    buttonDeferWsCloseAction();
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        if (errorWs) {
            panelRealAmount.add(buttonConfirm);
            utiliMsg.cargaAdvertNoData();
        }
        

        JPanel panelTabs = new JPanel();
        panelTabs.setLayout(null);
        panelTabs.setBounds(80, 515, 510, 120);
        panelTabs.setBackground(bluLg);
        panelPpal.add(panelTabs);

        JLabel labelTabs = utiliGraf.labelTitleBacker1("Consultar Mesa");
        labelTabs.setBounds(50, 10, 200, 25);
        panelTabs.add(labelTabs);
        
        comboTabs.setModel(utili.tableComboModelReturn(actualTabs));
        comboTabs.setBounds(50, 50, 200, 30);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setFont(new Font("Arial", Font.PLAIN, 50));
        comboTabs.setRenderer(renderer);
        panelTabs.add(comboTabs);
        JButton butSelTab = utiliGraf.button3("Elija mesa", 280, 50, 150);
        butSelTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    getTab();                    
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTabs.add(butSelTab);
    }

    private void confirmRealAmount() throws Exception {
        String real = fieldFinalAmount.getText();
        try {
            double realAmount = parseDouble(real);
            double realError = realAmount - total;
            boolean confirm = utiliMsg.cargaConfirmarFacturacion(realAmount, realError);
            if (confirm) {
                realAmount = parseDouble(real);
                actualWs.setWsTotalMountReal(realAmount);
                actualWs.setWsErrorMountReal(realError);
                if (realError < 0) {
                    realError = realError * (-1);
                } else if (realError > 0) {
                    realError = 0;
                }
                sw.saveWorkshift(actualWs, newWs, actualTabs, newTabs, toEraseTabs, toUpdTabs, salon);
                dispose();
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            fieldFinalAmount.setText("");
        }
    }
    
    public void getTab() throws Exception {
        String selection = (String) comboTabs.getSelectedItem();
        Table tab = new Table();
        for (Table t : actualTabs) {
            if (t.getId().equals(selection)) {
                tab = t;
            }
        }
        if (!selection.equals("")) {
            new TableResumePanel(tab);
        } else {
            utiliMsg.errorTableResume();
        }
    }

    private ArrayList<Table> filterClose(ArrayList<Table> actTabs) {
        ArrayList<Table> closeTabs = new ArrayList<Table>();
        for (Table t : actTabs) {
            if (t.isActiveTable() && t.isOpen() == false) {
                closeTabs.add(t);
            }
        }
        return closeTabs;
    }
    
    private void buttonDeferWsCloseAction() throws Exception {
        ConfigActual cfgGen = daoC.askConfigActual();
        ArrayList<Integer> deferWsArray = cfgGen.getArrayDeferWs();
        deferWsArray.add(actualWs.getWsId());
        deferWsArray.add(actualWs.getWsId());
        daoC.updateCfgActDeferWs(deferWsArray);
        utiliMsg.cargaWsDefer();
    }
}
