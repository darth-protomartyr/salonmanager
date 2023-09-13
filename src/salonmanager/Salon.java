package salonmanager;

import salonmanager.persistencia.DAOConfig;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import salonmanager.entidades.FrameFullManager;
import salonmanager.entidades.JButtonTable;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesSalon;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesSalon utiliSal = new UtilidadesSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    SalonManager sm = new SalonManager();
    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();

    ServicioSalon ss = new ServicioSalon();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    int anchoPane = (anchoFrame * 7 / 10);
    int alturaPane = (alturaFrame * 7 / 10);
    int totalTable = 0;
    int numBut = 1;

    int rows = 0;
    int col = 0;
    int fontSizeTable = 0;
    int wUnit = 0;
    int hUnit = 0;
    ArrayList<Table> openTables = new ArrayList<Table>();
    ArrayList<String> configSalon = new ArrayList<String>();
    ArrayList<Integer> tableNum = new ArrayList<Integer>();
    ArrayList<String> tablePan = new ArrayList<String>();
    ArrayList<String> tablePanCh = new ArrayList<String>();
    ArrayList<JButtonTable> tableButtons = new ArrayList<JButtonTable>();
    ArrayList<User> waiters = new ArrayList<User>();
    User waiterAux = null;

    ArrayList<JPanel> panelsPane = new ArrayList<JPanel>();
    JPanel panelA = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jBTAux = new JButtonTable();
    JComboBox comboWaiters = new JComboBox();
    JLabel labelWaiter = new JLabel();

    public Salon() throws Exception {
        sm.addFrame(this);
        setTitle("Salón Manager");
        waiters = daoU.listWaiter();
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);

//        Config cfg = daoC.consultarConfig();
//        totalTable = cfg.getTotalTable();
//        tableNum = cfg.getTableNum();
//        tablePan = cfg.getTablePan();
//        tablePanCh = cfg.getTablePanCh();
        tableNum.add(12);
        tablePan.add("Salón");
        tablePanCh.add("s");

        panelA.setLayout(null);
        panelA.setBackground(narLg);
        panelPpal.add(panelA);
//------------------------------------------------------------------------------------------------------------

        for (int i = 0; i < tableNum.size(); i++) {
            JPanel panelB = new JPanel();
            panelB.setBackground(viol);
            panelB.setLayout(null);
            ArrayList<Integer> configValues = utiliSal.salonConfigValues(tableNum.get(i), anchoPane, alturaPane);
            fontSizeTable = configValues.get(0);
            wUnit = configValues.get(1);
            hUnit = configValues.get(2);
            rows = configValues.get(3);
            col = configValues.get(4);

            for (int y = 0; y < rows; y++) {
                for (int z = 0; z < col; z++) {
                    JButtonTable jbt = new JButtonTable(tablePanCh.get(i), numBut, wUnit * (5 * z + 1), hUnit * (5 * y + 1), (wUnit * 4), (hUnit * 4));
                    Font font = new Font("Arial", Font.BOLD, fontSizeTable);
                    jbt.setFont(font);
                    jbt.setText(jbt.getText());
                    jbt.setBackground(narUlg);
                    jbt.setBorder(new EmptyBorder(10, 10, 10, 10));
                    jbt.setBounds(jbt.getMarginW(), jbt.getMarginH(), jbt.getWidth(), jbt.getHeight());
                    numBut += 1;
                    tableButtons.add(jbt);
                    panelB.add(jbt);
                }
            }
            panelB.setBounds(wUnit, hUnit, anchoPane, alturaPane);
            panelsPane.add(panelB);
        }

        for (int i = 0; i < panelsPane.size(); i++) {
            tabbedPane.addTab(tablePan.get(i), panelsPane.get(i));
        }

        panelA.setBounds(20, 100, (anchoPane + wUnit), (alturaPane + hUnit * 2));
        tabbedPane.setBounds(wUnit / 2, hUnit / 2, (anchoPane), (alturaPane + hUnit));
        panelA.add(tabbedPane);

// lateral------------------------------------------------------------------------------------------------------
        comboWaiters.setModel(utili.userComboModelReturn(waiters));
        comboWaiters.setBounds(anchoFrame - 250, 100, 190, 40);
        panelPpal.add(comboWaiters);
        JButton butSelWaiter = utiliGraf.button2("Seleccione un Mozo", anchoFrame - 250, 150, 190);
        butSelWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    getWaiter();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelWaiter);
        labelWaiter.setBounds(anchoFrame - 250, 240, 190, 40);
        panelPpal.add(labelWaiter);

//----------------------------------------------------------------------------------------------------------------
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButtonTable botonClicado = (JButtonTable) e.getSource();
                if (botonClicado.getTable() == null) {
                    if (waiterAux == null) {
                        utiliMsg.errorWaiterNull();
                    } else {
                        botonClicado.tableButtonActionPerformed(botonClicado, waiterAux);
                        botonClicado.setBackground(green);
                        waiterAux = null;
                        labelWaiter.setText("");
                    }
                } else {
                    

                }
            }
        };

        for (int i = 0; i < tableButtons.size(); i++) {
            jBTAux = tableButtons.get(i);
            jBTAux.addActionListener(actionListener);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Salon();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

//    private void tableButtonActionPerformed() throws Exception {
//        if (waiterAux == null) {
//            
//        } else {
//            if (jBTAux.getTable() == null) {
//                Table table = new Table(jBTAux.getNum(), jBTAux.getPos(), waiterAux);
//                jBTAux.setTable(table);
//            }
//        }
//    }
    public void getWaiter() {
        String selection = (String) comboWaiters.getSelectedItem();
        waiterAux = utili.userSelReturn(selection, waiters);
        labelWaiter.setText(waiterAux.getNombre());
    }
}
