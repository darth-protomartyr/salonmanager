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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
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
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class WorkshiftEndPanel extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
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
    JTextArea textArea = new JTextArea();

    Salon salon = null;
    ConfigActual cfgAct = null;
    Workshift actualWs = null;
    Workshift newWs = null;
    ArrayList<Table> actualTabs = new ArrayList<>();
    ArrayList<Table> newTabs = new ArrayList<>();
    ArrayList<Table> toEraseTabs = new ArrayList<>();
    ArrayList<Table> toUpdTabs = new ArrayList<>();
    ArrayList<String> actualTabsSt = new ArrayList<>();

    double total = 0; //amount billing
    double cash = 0;
    double electronic = 0;
    double cashFlow = 0;
    double electronicFlow = 0;
    double error = 0;
    double cashComplete = 0; //cash + electronic + cFlow + eFlow
    boolean errorWsUser = false;
    Manager manager = null;

    public WorkshiftEndPanel(Salon sal, Manager man, Workshift ws1, Workshift ws2, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> toErsdTabs, ArrayList<Table> updTabs, boolean errorW) throws Exception {
        manager = man;
        if (sal != null) {
            cfgAct = sal.getCfgAct();
        } else {
            cfgAct = daoC.askConfigActual();
        }

        actualWs = ws1;
        newWs = ws2;
        actualTabs = filterClose(actTabs);
        errorWsUser = errorW;

        if (nTabs == null) {
            newTabs = new ArrayList<>();
        } else {
            newTabs = nTabs;
        }

        if (toErsdTabs == null) {
            toEraseTabs = new ArrayList<>();
        } else {
            toEraseTabs = toErsdTabs;
        }

        if (updTabs == null) {
            toUpdTabs = new ArrayList<>();
        } else {
            toUpdTabs = updTabs;
        }

        salon = sal;
        sm.addFrame(this);
        User cashier = actualWs.getCashierWs();
        setTitle("Cierre de turno");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, frame.getWidth(), altoUnit * 6);
        panelPpal.add(panelLabel);

        String nomCashier = cashier.getName();
        String pronomCashier = cashier.getLastName();

        String nomCashierEnd = manager.getUser().getName();
        String pronomCashierEnd = manager.getUser().getLastName();

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("CIERRE DE TURNO");
        panelLabel.add(labelTit);

        JLabel labelCashier = utiliGraf.labelTitleBacker2W("Titular caja: " + nomCashier.toUpperCase() + " " + pronomCashier.toUpperCase());
        labelCashier.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 30, altoUnit * 3);
        panelPpal.add(labelCashier);

        if (salon == null) {
            JLabel labelCashierEnd = utiliGraf.labelTitleBacker2W("Titular cierre caja: " + nomCashierEnd.toUpperCase() + " " + pronomCashierEnd.toUpperCase());
            labelCashierEnd.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 30, altoUnit * 3);
            panelPpal.add(labelCashierEnd);
        }

        JLabel labelOpen = utiliGraf.labelTitleBacker2W("Inicio: " + utili.friendlyDate2(actualWs.getOpenWs()));
        labelOpen.setBounds(anchoUnit * 35, altoUnit * 7, anchoUnit * 18, altoUnit * 3);
        panelPpal.add(labelOpen);

        JLabel labelClose = utiliGraf.labelTitleBacker2W("Cierre: " + utili.friendlyDate2(actualWs.getCloseWs()));
        labelClose.setBounds(anchoUnit * 35, altoUnit * 10, anchoUnit * 18, altoUnit * 3);
        panelPpal.add(labelClose);

        //Panel elements
        JPanel panelMounts = new JPanel();
        panelMounts.setLayout(null);
        panelMounts.setBounds(anchoUnit * 3, altoUnit * 14, anchoUnit * 45, altoUnit * 77);
        panelMounts.setBackground(bluLg);
        panelPpal.add(panelMounts);

        total = actualWs.getTotalMountWs();
        cash = actualWs.getTotalMountCashWs();
        cashFlow = actualWs.getCashFlowWsCash();
        electronic = actualWs.getTotalMountElectronicWs();
        electronicFlow = actualWs.getCashFlowWsElec();
        error = actualWs.getErrorMountWs();

        //Facturación
        JPanel panelTotalFact = new JPanel();
        panelTotalFact.setLayout(new BoxLayout(panelTotalFact, BoxLayout.X_AXIS));
        panelTotalFact.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 43, altoUnit * 7);
        panelMounts.add(panelTotalFact);

        JLabel labelTotalFact1 = utiliGraf.labelTitleBackerA3("Facturación:");
        labelTotalFact1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTotalFact.add(labelTotalFact1);
        panelTotalFact.add(Box.createHorizontalGlue());

        JLabel labelTotalFact2 = utiliGraf.labelTitleBackerA3("$" + total);
        labelTotalFact2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelTotalFact.add(labelTotalFact2);

        //Efectivo    
        //Efectivo    
        JPanel panelCash = new JPanel();
        panelCash.setLayout(null);
        panelCash.setBounds(anchoUnit * 1, altoUnit * 9, anchoUnit * 21, altoUnit * 20);
        panelMounts.add(panelCash);

        //Fact Cash
        JPanel panelCashSell = new JPanel();
        panelCashSell.setLayout(new BoxLayout(panelCashSell, BoxLayout.X_AXIS));
        panelCashSell.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 19, altoUnit * 2);
        panelCash.add(panelCashSell);

        JLabel labelTotalCash1 = utiliGraf.labelTitleBacker3("Flujo Venta Efectivo:");
        panelCashSell.add(labelTotalCash1);
        panelCashSell.add(Box.createHorizontalGlue());

        JLabel labelTotalCash2 = utiliGraf.labelTitleBacker3("$" + cash);
        labelTotalCash2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelCashSell.add(labelTotalCash2);

        //Inn Cash
        JPanel panelCashInn = new JPanel();
        panelCashInn.setLayout(new BoxLayout(panelCashInn, BoxLayout.X_AXIS));

        panelCashInn.setBounds(anchoUnit * 1, altoUnit * 4, anchoUnit * 19, altoUnit * 2);
        panelCash.add(panelCashInn);

        JLabel labelCashInn1 = utiliGraf.labelTitleBacker3("Flujo Auxiliar Efectivo:");
        panelCashInn.add(labelCashInn1);
        panelCashInn.add(Box.createHorizontalGlue());

        JLabel labelCashInn2 = utiliGraf.labelTitleBacker3("$" + cashFlow);
        labelCashInn2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelCashInn.add(labelCashInn2);

        //Total Efec
        JLabel labelCashTotal1 = utiliGraf.labelTitleBacker1("TOTAL EFECTIVO:");
        labelCashTotal1.setBounds(anchoUnit, altoUnit * 8, anchoUnit * 19, altoUnit * 3);
        panelCash.add(labelCashTotal1);

        double totalCash = cash + cashFlow;

        JLabel labelCashTotal2 = utiliGraf.labelTitleBackerA3("$" + (totalCash));
        labelCashTotal2.setHorizontalAlignment(SwingConstants.CENTER);
        labelCashTotal2.setBounds(anchoUnit, altoUnit * 13, anchoUnit * 19, altoUnit * 5);
        panelCash.add(labelCashTotal2);

        //Transferencia         
        //Transferencia 
        JPanel panelElec = new JPanel();
        panelElec.setLayout(null);
        panelElec.setBounds(anchoUnit * 23, altoUnit * 9, anchoUnit * 21, altoUnit * 20);
        panelMounts.add(panelElec);

        //Fact Elec
        JPanel panelElecSell = new JPanel();
        panelElecSell.setLayout(new BoxLayout(panelElecSell, BoxLayout.X_AXIS));
        panelElecSell.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 19, altoUnit * 2);
        panelElec.add(panelElecSell);

        JLabel labelTotalElec1 = utiliGraf.labelTitleBacker3("Flujo Ventas por Transf.:");
        panelElecSell.add(labelTotalElec1);
        panelElecSell.add(Box.createHorizontalGlue());

        JLabel labelTotalElec2 = utiliGraf.labelTitleBacker3("$" + electronic);
        labelTotalElec2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelElecSell.add(labelTotalElec2);

        //Inn Electronic
        JPanel panelElecInn = new JPanel();
        panelElecInn.setLayout(new BoxLayout(panelElecInn, BoxLayout.X_AXIS));

        panelElecInn.setBounds(anchoUnit * 1, altoUnit * 4, anchoUnit * 19, altoUnit * 2);
        panelElec.add(panelElecInn);

        JLabel labelElecInn1 = utiliGraf.labelTitleBacker3("Flujo Auxiliar Transf.:");
        panelElecInn.add(labelElecInn1);
        panelElecInn.add(Box.createHorizontalGlue());

        JLabel labelElecInn2 = utiliGraf.labelTitleBacker3("$" + electronicFlow);
        labelElecInn2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelElecInn.add(labelElecInn2);

        //Total Electr
        JLabel labelElecTotal1 = utiliGraf.labelTitleBacker1("TOTAL ELECTRÓNICO:");
        labelElecTotal1.setBounds(anchoUnit, altoUnit * 8, anchoUnit * 19, altoUnit * 3);
        panelElec.add(labelElecTotal1);

        double totalElec = electronic + electronicFlow;

        JLabel labelElecTotal2 = utiliGraf.labelTitleBackerA3("$" + totalElec);
        labelElecTotal2.setHorizontalAlignment(SwingConstants.CENTER);
        labelElecTotal2.setBounds(anchoUnit, altoUnit * 13, anchoUnit * 19, altoUnit * 5);
        panelElec.add(labelElecTotal2);

        //Error
        JPanel panelError = new JPanel();
        panelError.setLayout(new BoxLayout(panelError, BoxLayout.X_AXIS));

        panelError.setBounds(anchoUnit * 1, altoUnit * 30, anchoUnit * 43, altoUnit * 7);
        panelMounts.add(panelError);

        JLabel labelError1 = utiliGraf.labelTitleBackerA3("Error:");
        labelError1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelError.add(labelError1);
        panelError.add(Box.createHorizontalGlue());

        JLabel labelError2 = utiliGraf.labelTitleBackerA3("$ " + error);
        labelError2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelError.add(labelError2);

        //Total
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new BoxLayout(panelTotal, BoxLayout.X_AXIS));
        panelTotal.setBounds(anchoUnit * 1, altoUnit * 38, anchoUnit * 43, altoUnit * 7);
        panelMounts.add(panelTotal);

        JLabel labelTotal1 = utiliGraf.labelTitleBackerA3("Total Caja:");
        labelTotal1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTotal.add(labelTotal1);
        panelTotal.add(Box.createHorizontalGlue());

        cashComplete = electronic + cash + cashFlow + electronicFlow;
        JLabel labelTotal2 = utiliGraf.labelTitleBackerA3("$" + cashComplete);
        labelTotal2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelTotal.add(labelTotal2);

        //Total
        JPanel panelRealAmount = new JPanel();
        panelRealAmount.setBackground(narUlg);
        panelRealAmount.setBounds(anchoUnit * 1, altoUnit * 46, anchoUnit * 43, altoUnit * 16);
        panelRealAmount.setLayout(null);
        panelMounts.add(panelRealAmount);

        JLabel labelInMount = utiliGraf.labelTitleBacker2("<html>Ingrese el total del efectivo <br>y de las transferencias:</html>");
        labelInMount.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 18, altoUnit * 6);
        panelRealAmount.add(labelInMount);

        JLabel label$ = utiliGraf.labelTitleBackerA3("$");
        label$.setBounds(anchoUnit * 1, altoUnit * 8, anchoUnit * 2, altoUnit * 7);
        panelRealAmount.add(label$);

        fieldFinalAmount.setBounds(anchoUnit * 3, altoUnit * 8, anchoUnit * 14, altoUnit * 7);
        fieldFinalAmount.setFont(new Font("Arial", Font.BOLD, 35));
        panelRealAmount.add(fieldFinalAmount);

        JLabel labelComment = utiliGraf.labelTitleBacker2("Ingrese un comentario:");
        labelComment.setBounds(anchoUnit * 18, altoUnit * 1, anchoUnit * 14, altoUnit * 3);
        panelRealAmount.add(labelComment);

        textArea.setRows(3);
        textArea.setColumns(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textArea.setFont(newFont);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(anchoUnit * 18, altoUnit * 5, anchoUnit * 14, altoUnit * 10);
        panelRealAmount.add(scrollPane);

        JButtonMetalBlu buttonConfirm = utiliGraf.button1("Confirmar", anchoUnit * 33, altoUnit * 7, anchoUnit * 9);
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

        //Consultas
        JPanel panelAsk = new JPanel();
        panelAsk.setLayout(null);
        panelAsk.setBounds(anchoUnit * 1, altoUnit * 63, anchoUnit * 43, altoUnit * 13);
        panelMounts.add(panelAsk);
        //ConsultaMesa
        JPanel panelAskTab = new JPanel();
        panelAskTab.setLayout(null);
        panelAskTab.setBackground(bluLg);
        panelAskTab.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 41, altoUnit * 5);
        panelAsk.add(panelAskTab);

        JLabel labelTabs = utiliGraf.labelTitleBacker1("Consultar Mesa");
        labelTabs.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 12, altoUnit * 3);
        panelAskTab.add(labelTabs);

        actualTabsSt = tabsIdToAsk(actualTabs);
        actualTabsSt = utili.tabsEasyReader(actualTabsSt);
        comboTabs.setModel(utili.categoryComboModelReturn(actualTabsSt));
        comboTabs.setBounds(anchoUnit * 15, altoUnit * 1, anchoUnit * 15, altoUnit * 3);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setFont(new Font("Arial", Font.PLAIN, 50));
        comboTabs.setRenderer(renderer);
        panelAskTab.add(comboTabs);
        JButtonMetalBlu butSelTab = utiliGraf.button3("Elija mesa", anchoUnit * 31, altoUnit * 1, anchoUnit * 9);
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
        panelAskTab.add(butSelTab);

        JPanel panelAskCashFlow = new JPanel();
        panelAskCashFlow.setLayout(null);
        panelAskCashFlow.setBackground(bluLg);
        panelAskCashFlow.setBounds(anchoUnit * 1, altoUnit * 7, anchoUnit * 41, altoUnit * 5);
        panelAsk.add(panelAskCashFlow);

        JLabel labelCashFlow = utiliGraf.labelTitleBacker1("Consultar Movimientos de Caja");
        labelCashFlow.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 24, altoUnit * 3);
        panelAskCashFlow.add(labelCashFlow);

        JButtonMetalBlu butSeeCashFlow = utiliGraf.button3("Ver Listado", anchoUnit * 31, altoUnit * 1, anchoUnit * 9);
        butSeeCashFlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butSeeCashFlowAction();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelAskCashFlow.add(butSeeCashFlow);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirm = utiliMsg.cargaConfirmRealWsMount();
                if (confirm) {
                    if (salon != null) {
                        salon.setEnabled(true);
                    }
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                boolean confirm = utiliMsg.cargaConfirmRealWsMount();
                if (confirm) {
                    if (salon != null) {
                        salon.setEnabled(true);
                    }
                    dispose();
                }
            }
        });
    }

    private void confirmRealAmount() throws Exception {
        String real = fieldFinalAmount.getText();
        String comment = "";
        if (errorWsUser) {

            comment = "El turno fue iniciado por " + actualWs.getCashierWs().getName() + " " + actualWs.getCashierWs().getLastName() + " y fue finalizado por "
                    + manager.getUser().getName() + " " + manager.getUser().getLastName() + ".\n";

            String comment1 = textArea.getText();
            comment += comment1;
        } else {
            comment = textArea.getText();
        }
        try {
            double realAmount = parseDouble(real);
            double realError = (realAmount - cashComplete) * (-1);
            realError = utili.round2Dec(realError);
            boolean confirm = false;
            if (realError > 0) {
                if (!comment.equals("")) {
                    confirm = utiliMsg.cargaConfirmarFacturacion(realAmount, realError);
                } else {
                    utiliMsg.errorCommentNull();
                }
            } else {
                confirm = utiliMsg.cargaConfirmarFacturacion(realAmount, realError);
            }

            if (confirm) {
                actualWs.setTotalMountRealWs(realAmount);
                actualWs.setErrorMountRealWs(realError + error);
                actualWs.setCommentWs(comment);
                if (realError < 0) {
                    realError = realError * (-1);
                } else if (realError > 0) {
                    realError = 0;
                }
                sw.saveWorkshift(actualWs, newWs, actualTabs, newTabs, toEraseTabs, toUpdTabs, salon);
                if (salon != null) {
                    salon.getCfgAct().setOpenIdWs(0);
                    salon.getCfgAct().setOpenWs(false);
                    utiliGrafSal.resetWsValues(salon);
                    salon.setEnabled(true);
                }
                dispose();
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            fieldFinalAmount.setText("");
        }
    }

    public void getTab() throws Exception {
        String id1 = (String) comboTabs.getSelectedItem();
        Table tab = new Table();

        for (int i = 0; i < actualTabs.size(); i++) {
            if (actualTabsSt.get(i).equals(id1)) {
                tab = actualTabs.get(i);
            }
        }
        if (!id1.equals("")) {
            new TableResumePanel(null, tab, 0, null);
        } else {
            utiliMsg.errorTableResume();
        }
    }

    private ArrayList<Table> filterClose(ArrayList<Table> actTabs) {
        ArrayList<Table> closeTabs = new ArrayList<>();
        for (Table t : actTabs) {
            if (t.isActiveTable() && t.isOpen() == false) {
                closeTabs.add(t);
            }
        }
        return closeTabs;
    }

    private void buttonDeferWsCloseAction() throws Exception {
        ArrayList<String> deferWsArray = cfgAct.getArrayDeferWs();
        daoC.updateCfgActDeferWs(deferWsArray);
        utiliMsg.cargaWsDefer();
    }

    private void butSeeCashFlowAction() throws Exception {
        new CashFlowViewer(this, salon);
        setEnabled(false);

    }

    public void setEnableWEP() {
        setEnabled(true);
    }

    private ArrayList<String> tabsIdToAsk(ArrayList<Table> actualTabs) {
        ArrayList<String> tabsId = new ArrayList<>();
        for (Table tab : actualTabs) {
            tabsId.add(tab.getId());
        }
        return tabsId;
    }
}
