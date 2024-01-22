package salonmanager;

import salonmanager.entidades.graphics.JButtonDeliverySee;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.config.Config;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.graphics.FrameFullManager;
import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonBarr;
import salonmanager.entidades.graphics.JButtonDelivery;
import salonmanager.entidades.graphics.JButtonTable;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.servicios.ServicioCloseWorkshift;
import salonmanager.servicios.ServicioItemMonitor;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    SalonManager sm = new SalonManager();
    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();
    DAOItemcard daoI = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAODelivery daoD = new DAODelivery();
    ServicioSalon ss = new ServicioSalon();
    ServicioTable st = new ServicioTable();
    ServicioItemMonitor sim = new ServicioItemMonitor();

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color narUlgX = new Color(255, 255, 210);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);
    ArrayList<String> configSalon = new ArrayList<String>(); //Configuración de l salón
    int anchoPane = (anchoFrame * 7 / 10);
    int alturaPane = (alturaFrame * 7 / 10);
    int totalTable = 0;
    int numBut = 1;
    int rowsButtons = 0;
    int colButtons = 0;
    int fontSizeTable = 0;
    int wUnit = 0;
    int hUnit = 0;
    boolean tabsBut = true;

    int f1 = anchoUnit * 3;
    Font font1 = new Font("Arial", Font.BOLD, f1);
    int f2 = (int) Math.round(anchoUnit * 2.3);
    Font font2 = new Font("Arial", Font.BOLD, f2);
    int f3 = (int) Math.round(anchoUnit * 1.6);
    Font font3 = new Font("Arial", Font.BOLD, f2);
    JScrollPane scrPaneBarr = new JScrollPane();
    JPanel panelBarrBut = new JPanel();
    JPanel panelDeliBut = new JPanel();
    JPanel panelDeliContainer = new JPanel();

    ArrayList<Integer> tableNum = new ArrayList<Integer>(); // número de mesa
    ArrayList<String> tablePan = new ArrayList<String>(); // Nombre del sector
    ArrayList<String> tablePanCh = new ArrayList<String>(); // Primer Char del Nombre del sector

    ArrayList<User> waiters = new ArrayList<User>(); // mozos
    ArrayList<Itemcard> itemsDB = new ArrayList<Itemcard>(); // Items Completos
    ArrayList<Itemcard> itemsTableAux = new ArrayList<Itemcard>();//items a cobrar de la mesa
    ArrayList<Itemcard> itemsGift = new ArrayList<Itemcard>(); //items obsequiados
    ArrayList<Itemcard> itemsPartialPaid = new ArrayList<Itemcard>(); // items cobrados por pago parcial
    ArrayList<Itemcard> itemsPartialPaidNoDiscount = new ArrayList<Itemcard>(); // items cobrados anted de aplicar descuento
    ArrayList<Itemcard> itemsError = new ArrayList<Itemcard>();
    ArrayList<ItemMonitor> itemsMntr = new ArrayList<ItemMonitor>();
    User user = null;
    User waiterAux = null; // mozo actual
    Table tableAux = null; // mesa actual
    Workshift workshiftNow = null;
    Delivery deliOrderAux = null;

    int discount = 0; //porcentaje de descuento
    double priceCorrection = 0; //correccion debido a modificación de precio de un item que se encontraba pago;
    double amoutnCash = 0; //dinero en billete
    double amountElectronic = 0; //dinero electrónico
    double total = 0; // total a pagar(pago parcial restado)
    double error = 0; // dinero faltante a pagar;

    //Botonera
    ArrayList<JPanel> panelsPane = new ArrayList<JPanel>();
    ArrayList<JButtonTable> tableButtons = new ArrayList<JButtonTable>();
    ArrayList<JButtonBarr> barrButtons = new ArrayList<JButtonBarr>();
    ArrayList<JButtonDelivery> deliButtons = new ArrayList<JButtonDelivery>();
    ArrayList<JButtonDeliverySee> deliButtonsSees = new ArrayList<JButtonDeliverySee>();
    ScheduledExecutorService scheduler = null;
    boolean loopBreaker = false;

    JButton butBarrDeli = null;
    JPanel panelBDButtons = new JPanel();
    JButtonBarr jbbAux = null;
    JButtonDelivery jbdAux = null;
    JButtonDeliverySee jbdSAux = null;

    JPanel panelA = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jbtAux = null; //boton de mesa actual
    ServicioCloseWorkshift sw = new ServicioCloseWorkshift();

    //Menu Lateral
    int rowsItems = 0; //nro filas de la tabla
    int colItems = 3; //nro columnas de las tablas
    String col1 = "Uni.";
    String col2 = "Items";
    String col3 = "Total $";
    boolean indiBool = false;

    String[] colNames = {col1, col2, col3};
    String[][] data = new String[rowsItems][colItems];
    JComboBox comboItems = new JComboBox();
    JCheckBox checkBoxIndic = new JCheckBox();
    JSpinner spinnerUnitsItem = new JSpinner();
    JScrollPane scrollPaneItems = new JScrollPane();
    JTable jTableItems = new JTable();
    JButton butCloseTable = new JButton();
    JPanel panelCuenta = new JPanel();
    JPanel panelFlotante = new JPanel();
    JLabel labelTotalParcial = new JLabel();
    JLabel labelOrder = new JLabel();
    JLabel labelCuenta = new JLabel();
    JLabel labelWaiter = new JLabel();
    JLabel labelTip = new JLabel();
    JLabel labelTotal = new JLabel();
    JLabel labelPartialPay = new JLabel();
    JButton butInitWorkshift = new JButton();
    JButton butPartialPay = new JButton();
    JPanel panelPartial = new JPanel();

    Salon sal = null;
    Manager manager = null;

    public Salon(ArrayList<Table> tables, Manager man) throws Exception {
        manager = man;
        sm.addFrame(this);
        user = man.getUser();
        setTitle("Salón Manager");
        sal = this;
        itemsDB = daoI.listarItemsCard();
        waiters = daoU.listUserByRol("MOZO");
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);

        Config cfg = daoC.consultarConfig();
        totalTable = cfg.getTotalTable();
        tableNum = cfg.getTableNum();
        tablePan = cfg.getTablePan();
        tablePanCh = cfg.getTablePanCh();

        JPanel panelActual = new JPanel();
        panelActual.setBounds(anchoUnit * 11, altoUnit * 3, anchoUnit * 17, altoUnit * 14);
        panelActual.setBackground(bluLg);
        panelActual.setLayout(null);
        panelPpal.add(panelActual);

        JLabel labelUser = utiliGraf.labelTitleBacker3("Usuario: " + user.getName().toUpperCase() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
        labelUser.setBounds(altoUnit, altoUnit, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelUser);

        JLabel labelSession = utiliGraf.labelTitleBacker3("");

        Timestamp timeInitSes = new Timestamp(new Date().getTime());
        if (timeInitSes != null) {
            labelSession.setText("Inicio Sesion: " + utili.friendlyDate(timeInitSes));
        } else {
            labelSession.setText("Sesion no iniciada.");
        }
        labelSession.setBounds(altoUnit, altoUnit * 4, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelSession);

        JLabel labelWorkshift = utiliGraf.labelTitleBacker3("");
        if (workshiftNow != null) {
            Timestamp timeInitWork = workshiftNow.getWsOpen();
            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(timeInitWork));
        } else {
            labelWorkshift.setText("Turno no iniciado.");
        }
        labelWorkshift.setBounds(altoUnit, altoUnit * 7, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelWorkshift);

        butInitWorkshift = utiliGraf.button2("Abrir Turno", anchoUnit * 3, altoUnit * 9, anchoUnit * 11);
        butInitWorkshift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (workshiftNow == null) {
                        boolean confirm1 = utiliMsg.cargaConfirmarInicioTurno(user.getName(), user.getLastName());
                        if (confirm1 == true) {
                            workshiftNow = new Workshift(user);
                            daoW.saveWorkshift(workshiftNow);
                            sm.workshiftBacker(workshiftNow);
                            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(workshiftNow.getWsOpen()));
                            butInitWorkshift.setText("CERRAR TURNO");
                        }
                    } else {
                        endWorkshift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelActual.add(butInitWorkshift);

//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------
//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------
//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------
//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------      
//BARR / DELI
        JPanel panelBarrDeli = new JPanel();
        panelBarrDeli.setBounds(anchoUnit * 52, altoUnit * 3, anchoUnit * 25, altoUnit * 14);
        panelBarrDeli.setBackground(narLg);
        panelBarrDeli.setLayout(null);
        panelPpal.add(panelBarrDeli);

        butBarrDeli = new JButton();
        butBarrDeli.setBackground(narUlg);
        butBarrDeli.setBounds(anchoUnit * 1, altoUnit * 2, anchoUnit * 23, altoUnit * 10);
        butBarrDeli.setBorder(null);
        butBarrDeli.setFont(font1);
        butBarrDeli.setText("Barra - Delivery");
        butBarrDeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (workshiftNow != null) {
                        butPanelTurn();
                    } else {
                        utiliMsg.errorWorkshift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBarrDeli.add(butBarrDeli);

        JPanel panelMonitor = new JPanel();
        panelMonitor.setBounds(anchoUnit * 29, altoUnit * 3, anchoUnit * 22, altoUnit * 14);
        panelMonitor.setBackground(narLg);
        panelMonitor.setLayout(null);
        panelPpal.add(panelMonitor);

        JButton butMonitor = new JButton();
        butMonitor.setBackground(narUlg);
        butMonitor.setBounds(anchoUnit * 1, altoUnit * 2, anchoUnit * 20, altoUnit * 10);
        butMonitor.setBorder(null);
        butMonitor.setFont(font1);
        butMonitor.setText("Seguimiento");
        butMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (workshiftNow != null) {
                        new Monitor(sal);
                    } else {
                        utiliMsg.errorWorkshift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelMonitor.add(butMonitor);

        panelBDButtons = new JPanel();
        panelBDButtons.setLayout(null);
        panelBDButtons.setBackground(narLg);
        panelBDButtons.setBounds(anchoUnit, anchoUnit, anchoPane, (alturaPane + altoUnit * 3));
        panelBDButtons.setVisible(false);
        panelA.add(panelBDButtons);

//Panel Barra-----------------------------------------------------------        
        JPanel panelBarr = returnPanelBarr();
        panelBDButtons.add(panelBarr);

//Panel Delivery--------------------------------------------------------        
        JPanel panelDeli = returnPanelDeli();
        panelBDButtons.add(panelDeli);

//Delivery
//        JButton butClosePanelBarrDeli = utiliGraf.button2("Cerrar", anchoUnit * 26, alturaPane - altoUnit * 2, anchoUnit * 20);
//        butClosePanelBarrDeli.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                try {
//                    closePanelBarrDeli();
//                } catch (Exception ex) {
//                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        panelBDButtons.add(butClosePanelBarrDeli);
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
        panelA.setLayout(null);
        panelA.setBounds(20, altoUnit * 18, (anchoPane + anchoUnit * 2), (alturaPane + altoUnit * 7));
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);

        for (int i = 0; i < tableNum.size(); i++) {
            JPanel panelB = new JPanel();
            panelB.setBackground(narLg);
            panelB.setLayout(null);
            ArrayList<Integer> configValues = ss.salonConfigValues(tableNum.get(i), anchoPane, alturaPane);
            fontSizeTable = configValues.get(0);
            wUnit = configValues.get(1);
            hUnit = configValues.get(2);
            rowsButtons = configValues.get(3);
            colButtons = configValues.get(4);

            for (int y = 0; y < rowsButtons; y++) {
                for (int z = 0; z < colButtons; z++) {
                    JButtonTable jbt = new JButtonTable(tablePanCh.get(i), numBut, wUnit * (5 * z + 1), hUnit * (5 * y + 1), (wUnit * 4), (hUnit * 4));
                    font1 = new Font("Arial", Font.BOLD, fontSizeTable);
                    jbt.setFont(font1);
                    jbt.setText(jbt.getText());
                    jbt.setBackground(narUlg);
                    jbt.setBorder(new EmptyBorder(10, 10, 10, 10));
                    jbt.setBounds(jbt.getMarginW(), jbt.getMarginH(), jbt.getWidth(), jbt.getHeight());
                    numBut += 1;
                    tableButtons.add(jbt);
                    panelB.add(jbt);
                }
            }
            panelB.setBounds(wUnit, hUnit, anchoPane, alturaPane + hUnit);
            panelsPane.add(panelB);
        }

        for (int i = 0; i < panelsPane.size(); i++) {
            tabbedPane.addTab(tablePan.get(i), panelsPane.get(i));
        }

        tabbedPane.setBounds(anchoUnit, anchoUnit, anchoPane, (alturaPane + altoUnit * 3));
        panelA.add(tabbedPane);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (workshiftNow != null) {
                    if (jbtAux != null) {
                        jbtAux.setBorder(null);
                        if (jbtAux.isOpenJBT() == true) {
                            try {
                                resetTableValues();
                            } catch (Exception ex) {
                                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    JButtonTable butClicked = (JButtonTable) e.getSource();
                    for (int i = 0; i < tableButtons.size(); i++) {
                        if (tableButtons.get(i).getNum() == butClicked.getNum()) {
                            jbtAux = tableButtons.get(i);
                        }
                    }
                    try {
                        resetTableValues();
                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jbtAux.setBorder(new LineBorder(bluSt, 8));

                    if (jbtAux.isOpenJBT() == false) {
                        try {
                            setWaiter(0);
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tableAux = new Table(jbtAux.getNum(), jbtAux.getPos(), waiterAux);
                        tableAux.setOpen(true);
                        String nameT = tableAux.getPos() + tableAux.getNum();
                        labelOrder.setText("MESA:" + nameT);
                        tableAux.setOrder(new ArrayList<Itemcard>());
                        try {
                            jButExtSetter();
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        tableAux = jbtAux.getTable();
                        tableFullerProp();
                    }
                } else {
                    utiliMsg.errorWorkshift();
                }
            }
        };

        for (int i = 0; i < tableButtons.size(); i++) {
            jbtAux = tableButtons.get(i);
            jbtAux.addActionListener(actionListener);
        }

// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(bluLg);
        panelLateral.setLayout(null);
        panelLateral.setBounds((anchoFrame * 7 / 10) + anchoUnit * 5, altoUnit * 3, anchoFrame - (anchoPane + anchoUnit * 7), (alturaPane + altoUnit * 22));
        panelPpal.add(panelLateral);

        JPanel panelTable = new JPanel();
        panelTable.setLayout(null);
        panelTable.setBackground(narLg);
        panelTable.setBounds(anchoUnit, anchoUnit, anchoFrame - (anchoPane + anchoUnit * 9), altoUnit * 89);
        panelLateral.add(panelTable);
        labelOrder = utiliGraf.labelTitleBackerA2b("MESA: --");
        labelOrder.setBounds(altoUnit, altoUnit, anchoUnit * 23, altoUnit * 6);
        panelTable.add(labelOrder);

        labelWaiter = utiliGraf.labelTitleBackerA4("Mozo: --");
        labelWaiter.setBounds(altoUnit, altoUnit * 7, anchoUnit * 20, 30);
        panelTable.add(labelWaiter);

        JPanel panelSelItem = new JPanel();
        panelSelItem.setLayout(null);
        panelSelItem.setBounds(6, altoUnit * 12, anchoFrame - (anchoPane + anchoUnit * 10), altoUnit * 16);
        panelSelItem.setBackground(bluLg);
        panelTable.add(panelSelItem);

        JButton butCaptionBebidas = utiliGraf.button3("Bebidas", 5, 5, 55);
        butCaptionBebidas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Bebidas");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionBebidas);

        JButton butCaptionPlatos = utiliGraf.button3("Platos", 65, 5, 55);
        butCaptionPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Platos");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionPlatos);

        JButton butCaptionCafeteria = utiliGraf.button3("Cafetería", 125, 5, 80);
        butCaptionCafeteria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Cafeteria");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionCafeteria);

        JButton butCaptionOtros = utiliGraf.button3("Otros", 210, 5, 65);
        butCaptionOtros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Otros");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionOtros);

        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        comboItems.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 11, altoUnit * 4);
        comboItems.setSelectedIndex(itemsDB.size());
        panelSelItem.add(comboItems);

        JLabel labelUnitsItem = utiliGraf.labelTitleBacker3("Unidades");
        labelUnitsItem.setBounds(anchoUnit * 13, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelSelItem.add(labelUnitsItem);
        spinnerUnitsItem = utiliGraf.spinnerBack(anchoUnit * 18, altoUnit * 5, anchoUnit * 3, altoUnit * 4, spinnerUnitsItem);
        panelSelItem.add(spinnerUnitsItem);

//Boton Ingreso Item
        JButton butSelItem = utiliGraf.button2("Ingresar item", anchoUnit * 1, altoUnit * 11, anchoUnit * 10);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (waiterAux == null) {
                        utiliMsg.errorWaiterNull();
                        resetTableValues();
                    } else {
                        if (tableAux.isBill() == false) {
                            String item = (String) comboItems.getSelectedItem();
                            if (!item.equals("")) {
                                butSelItemActionPerformed(item);
                            } else {
                                utiliMsg.errorSeleccion();
                            }
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butSelItem);

        JLabel labelAddIndication = utiliGraf.labelTitleBacker3("Indicación item");
        labelAddIndication.setBounds(anchoUnit * 12, altoUnit * 12, anchoUnit * 8, altoUnit * 3);
        panelSelItem.add(labelAddIndication);

        checkBoxIndic.setBounds(anchoUnit * 20 - 5, altoUnit * 12, altoUnit * 3, altoUnit * 3);
        checkBoxIndic.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    indiBool = true;
                } else {
                    indiBool = false;
                }
            }
        });
        panelSelItem.add(checkBoxIndic);

        scrollPaneItems = scrollItemsBack(altoUnit, altoUnit * 28, anchoUnit * 21 + altoUnit, altoUnit * 30);
        panelTable.add(scrollPaneItems);

//Generar Modificación Tabla        
        jTableItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = jTableItems.getSelectedRow();
                char[] pass = utiliMsg.requestMod();
                boolean perm = utili.requiredPerm(pass);
                if (perm) {
                    if (filaSeleccionada <= rowsItems) {
                        itemCorrector();
                        setTableItems();
                    }
                }
            }
        });

//Boton Obsequio
        JButton butGift = utiliGraf.button2("Obsequio", altoUnit, altoUnit * 59, anchoUnit * 11);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            char[] pass = utiliMsg.requestMod();
                            boolean perm = utili.requiredPerm(pass);
                            if (perm) {
                                gifter();
                            }
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butGift);

//Boton Descuento
        JButton butDiscount = utiliGraf.button2("Descuento", altoUnit * 2 + anchoUnit * 11, altoUnit * 59, anchoUnit * 10);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (discount == 0) {
                            if (tableAux.isBill() == false) {
                                char[] pass = utiliMsg.requestMod();
                                boolean perm = utili.requiredPerm(pass);
                                if (perm) {
                                    discounter();
                                }
                            } else {
                                utiliMsg.errorBillSend();
                            }
                        } else {
                            utiliMsg.errorDiscountRepeat();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butDiscount);

//Boton Pago Parcial
        panelPartial.setLayout(null);
        panelPartial.setBounds(altoUnit, altoUnit * 64, anchoUnit * 21 + altoUnit, altoUnit * 5);
        panelPartial.setBackground(bluLg);
        panelTable.add(panelPartial);

        butPartialPay = utiliGraf.button3("PAGO PARCIAL", altoUnit, altoUnit * 1, anchoUnit * 8);
        butPartialPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() == 0) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        partialPayer();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPartial.add(butPartialPay);

        labelPartialPay.setText("Pagado: $0.0");
        labelPartialPay.setBounds(anchoUnit * 10, altoUnit, anchoUnit * 10, altoUnit * 3);
        panelPartial.add(labelPartialPay);

//Boton Pago Final
        butCloseTable = utiliGraf.button1("CERRAR ORDEN", altoUnit, altoUnit * 70, anchoUnit * 13 + altoUnit);
        panelTable.add(butCloseTable);
        butCloseTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            resetTableFull();
                        } else {
                            if (tableAux.isBill() == false) {
                                boolean confirm = utiliMsg.cargaConfirmarCierre();
                                if (confirm) {
                                    tableClose();
                                }
                            } else {
                                moneyKind(sal, true, null, false, total);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//Boton Error
        JButton butErrorTable = utiliGraf.button1("ERROR", altoUnit + anchoUnit * 14, altoUnit * 70, anchoUnit * 7 + altoUnit);
        butErrorTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux.isBill() == false) {
                        utiliMsg.errorBillUnsend();
                    } else {
                        errorTaker();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butErrorTable);

        panelCuenta.setLayout(null);
        panelCuenta.setBounds(altoUnit, altoUnit * 78, anchoUnit * 21 + altoUnit, altoUnit * 10);
        panelCuenta.setBackground(narUlg);
        panelTable.add(panelCuenta);

        labelTotalParcial = utiliGraf.labelTitleBacker2("Parcial $:");
        labelTotalParcial.setBounds(5, altoUnit * 2, anchoUnit * 6, altoUnit * 3);
        panelCuenta.add(labelTotalParcial);

        labelCuenta = utiliGraf.labelTitleBackerA3("00,0");
        labelCuenta.setBounds(anchoUnit * 7, altoUnit * 2, anchoUnit * 15, altoUnit * 4);
        labelCuenta.setBackground(viol);
        panelCuenta.add(labelCuenta);

        labelTip = utiliGraf.labelTitleBacker3("Prop.: $ 00,0");
        labelTip.setBounds(5, altoUnit * 7, anchoUnit * 10, altoUnit * 2);
        panelCuenta.add(labelTip);

        labelTotal = utiliGraf.labelTitleBacker3("Total: $ 00,0");
        labelTotal.setBounds(anchoUnit * 11, altoUnit * 7, anchoUnit * 10, altoUnit * 2);
        panelCuenta.add(labelTotal);

//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
//    public static void main(String[] args) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    ArrayList<Table> tabs = new ArrayList<Table>();
//                    new Salon(null);
//                } catch (Exception ex) {
//                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------    
//Seleccionar Mozo
    public void setWaiter(int i) throws Exception {
        WaiterSelector ws = new WaiterSelector(this, i);
        ws.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ws.setAlwaysOnTop(true);
        setEnabled(false);
    }

    void waiterBacker(User waiter) {
        waiterAux = waiter;
        labelWaiter.setText("Mozo: " + waiterAux.getName() + " " + utili.strShorter(waiterAux.getLastName(), 2).toUpperCase());
        tableAux.setWaiter(waiterAux);
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Listar Items por etiqueta
    private void itemCaptionBack(String capt) {
        ArrayList<Itemcard> aic = new ArrayList<Itemcard>();
        for (Itemcard ic : itemsDB) {
            if (ic.getCaption().toLowerCase().equals(capt.toLowerCase())) {
                aic.add(ic);
            }
        }
        comboItems.setModel(utili.itemsComboModelReturn(aic));
    }

//------------------------------------------------------------------------------------------------------------------
//Ingreso de items
    private void butSelItemActionPerformed(String item) throws Exception {
        int u = (int) spinnerUnitsItem.getValue();

        if (indiBool == true && u > 1) {
            utiliMsg.errorMultipleIndications();
            resetTableValues();
        } else {
            if (itemsTableAux.size() < 1) {
                ss.createTable(sal, tableAux);
                if (jbtAux != null) {
                    jbtAux.setOpenJBT(true);
                    jbtAux.setBackground(green);
                }

                if (jbbAux != null) {
                    jbbAux.setOpenJBB(true);
                    jbbAux.setBackground(green);
                }

                if (jbdAux != null) {
                    jbdAux.setOpenJBD(true);
                    jbdAux.setBackground(green);
                    jbdSAux.setBackground(green);
                }
            }

            Itemcard ic = null;

            ic = utili.ItemcardBacker(item, itemsDB);
            butCloseTable.setText("CERRAR CUENTA");
            int counter = 0;
            while (counter < u) {
                itemsTableAux.add(ic);
                counter += 1;
            }
            tableAux.setOrder(itemsTableAux);
            spinnerUnitsItem.setValue(1);
            total = ss.countBill(tableAux);
            tableAux.setTotal(total);
            daoT.updateTableTotal(tableAux);
            ArrayList<Itemcard> arrayAux = ss.itemDeployer(ic, u);
            ss.addItemOrder(sal, tableAux, arrayAux, indiBool);
            jButExtSetter();
            labelCuenta.setText("$ " + total);
            comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
            comboItems.setSelectedIndex(itemsDB.size());
            setTableItems();
        }
    }

//------------------------------------------------------------------------------------------------------------------
//Scroll de la Tabla
    private JScrollPane scrollItemsBack(int marginW, int marginH, int anchoPane, int alturaPane) {
        setTableItems();
        JScrollPane scrollPane = new JScrollPane(jTableItems);
        scrollPane.setPreferredSize(new Dimension(anchoPane, alturaPane));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(marginW, marginH, anchoPane, alturaPane);
        return scrollPane;
    }

//------------------------------------------------------------------------------------------------------------------
//Tabla de Items
    private void setTableItems() {
        HashSet<Itemcard> itemsSet = new HashSet<Itemcard>(itemsTableAux);
        ArrayList<Itemcard> itemsAux = new ArrayList<Itemcard>(itemsSet);

        HashSet<Itemcard> partialSet = new HashSet<Itemcard>(itemsPartialPaid);
        ArrayList<Itemcard> partials = new ArrayList<Itemcard>(partialSet);

        HashSet<Itemcard> partialSetND = new HashSet<Itemcard>(itemsPartialPaidNoDiscount);
        ArrayList<Itemcard> partialsND = new ArrayList<Itemcard>(partialSetND);

        HashSet<Itemcard> giftSet = new HashSet<Itemcard>(itemsGift);
        ArrayList<Itemcard> gifts = new ArrayList<Itemcard>(giftSet);

        HashSet<Itemcard> totalSet = new HashSet<Itemcard>(itemsAux);
        ArrayList<Itemcard> totalItems = new ArrayList<Itemcard>(totalSet);

        totalItems.addAll(partials);
        totalItems.addAll(partialsND);
        totalItems.addAll(gifts);

        rowsItems = totalItems.size();

        int aux = 0;
        if (discount > 0) {
            aux += 1;
        }

        if (priceCorrection > 0) {
            aux += 1;
        }

        data = new String[rowsItems + aux][colItems];

        int intAux = itemsAux.size();
        int intPartial = itemsAux.size() + partials.size();
        int intPartialND = itemsAux.size() + partials.size() + partialsND.size();

        double disc = (double) discount / 100;

        for (int i = 0; i < rowsItems; i++) {
            Itemcard ic = totalItems.get(i);

            if (i < intAux) {
                int u = st.itemUnitsBacker(itemsTableAux, ic);
                data[i][0] = " " + u;
                data[i][1] = " " + ic.getName();
                data[i][2] = " " + ic.getPrice() * u * (1 - disc);
            }

            if (partials.size() > 0 && i >= intAux && i < intPartial) {
                int u = st.itemUnitsBacker(itemsPartialPaid, ic);
                data[i][0] = " " + u;
                data[i][1] = " PAG." + ic.getName();
                data[i][2] = "PAGADO";
            }

            if (partialsND.size() > 0 && i >= intPartial && i < intPartialND) {
                int u = st.itemUnitsBacker(itemsPartialPaidNoDiscount, ic);
                data[i][0] = " " + u;
                data[i][1] = " PAG*." + ic.getName();
                data[i][2] = "PAGADO";
            }

            if (i >= intPartialND) {
                int u = st.itemUnitsBacker(itemsGift, ic);
                data[i][0] = " " + u;
                data[i][1] = " OBS." + ic.getName();
                data[i][2] = " 0";
            }
        }

        if (priceCorrection > 0) {
            if (discount > 0) {
                data[rowsItems - 1][1] = "Corrección precio mod:";
                data[rowsItems - 1][2] = priceCorrection + "";
            } else {
                data[rowsItems][1] = "Corrección precio mod:";
                data[rowsItems][2] = priceCorrection + "";
            }
        }

        if (discount > 0) {
            data[rowsItems][1] = "DESCUENTO: " + discount + "%";
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        jTableItems.setModel(tableModel);
        jTableItems.setDefaultEditor(Object.class, null);

        JTableHeader header = jTableItems.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(bluSt);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        jTableItems.setFont(cellFont);
        jTableItems.setRowHeight(25);
        jTableItems.setBackground(narUlg);

        int c = (anchoPane - 50) / 8;
        TableColumn column1 = jTableItems.getColumnModel().getColumn(0);
        column1.setPreferredWidth(c);
        TableColumn column2 = jTableItems.getColumnModel().getColumn(1);
        column2.setPreferredWidth(c * 5);
        TableColumn column3 = jTableItems.getColumnModel().getColumn(2);
        column3.setPreferredWidth(c * 2);
    }

//-----------------------------------------------------OBSEQUIO-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
    private void gifter() {
        itemsTableAux = tableAux.getOrder();
        GiftSelector gs = new GiftSelector(this);
        gs.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        gs.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
    public void giftBacker(Itemcard ic) throws Exception {
        itemsGift.add(ic);
        tableAux.setGifts(itemsGift);
        itemsTableAux = ss.itemTableLesser(tableAux.getOrder(), ic);
        tableAux.setOrder(itemsTableAux);
        utiliMsg.cargaGift(ic.getName());
        setTableItems();
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoI.downActiveItemOrderTable(ic, tableAux);
        daoI.saveItemGiftTable(ic, tableAux);
        daoT.updateTableTotal(tableAux);
        jButExtSetter();
        labelCuenta.setText(total + "");
        setEnabled(true);
    }

//-------------------------------------------------PAGO DESCUENTO---------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
    private void discounter() {
        if (itemsPartialPaid.size() > 0) {
            itemsPartialPaidNoDiscount = itemsPartialPaid;
            itemsPartialPaid = new ArrayList<Itemcard>();
            tableAux.setPartialPayedND(itemsPartialPaidNoDiscount);
            tableAux.setPartialPayed(itemsPartialPaid);
        }
        BillDiscounter bd = new BillDiscounter(this);
        bd.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        bd.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
    public void discountBacker(int disc) throws Exception {
        discount = disc;
        tableAux.setDiscount(disc);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        daoT.updateTableDiscount(tableAux);
        jButExtSetter();
        labelCuenta.setText(total + "");
        setTableItems();
        setEnabled(true);
    }

//-------------------------------------------------PAGO PARCIAL-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------ 
//Ingreso a pago parcial
    private void partialPayer() {
        PartialPayer pp = new PartialPayer(this);
        pp.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        pp.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//pago parcial de cuenta fraccionada
    void partialPayTaker(ArrayList<Itemcard> itemsPayed) throws Exception {
        jbtAux.setBackground(viol);
        for (int i = 0; i < itemsPayed.size(); i++) {
            itemsTableAux = ss.itemTableLesser(itemsTableAux, itemsPayed.get(i));
        }
        itemsPartialPaid.addAll(itemsPayed);
        tableAux.setPartialPayed(itemsPartialPaid);
        tableAux.setOrder(itemsTableAux);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        tableAux.setToPay(true);
        daoT.updateToPay(tableAux);
        for (Itemcard ic : itemsPayed) {
            daoI.saveItemPayedTable(ic, tableAux);
            daoI.downActiveItemOrderTable(ic, tableAux);
        }
        labelCuenta.setText(total + "");
        double payed = ss.partialBillPayed(tableAux);
        labelPartialPay.setText("Pagado: $" + (payed));
        jButExtSetter();
        setTableItems();
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------ 
//pago completo de cuenta fraccionada
    void totalPayTaker(ArrayList<Itemcard> itemsPayed) throws Exception {
        itemsPartialPaid.addAll(itemsPayed);
        tableAux.setPartialPayed(itemsPartialPaid);
        itemsTableAux = itemsPartialPaid;
        itemsPartialPaid = new ArrayList<Itemcard>();
        tableAux.setOrder(itemsTableAux);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        tableAux.setOpen(false);
        daoT.updateTableTotal(tableAux);
        tableAux.setToPay(false);
        daoT.updateToPay(tableAux);
        for (Itemcard ic : itemsPayed) {
            daoI.saveItemPayedTable(ic, tableAux);
            daoI.downActiveItemOrderTable(ic, tableAux);
        }
        labelCuenta.setText(total + "");
        setEnabled(true);
    }

//----------------------------------------------CIERRE Y PAGO DE CUENTA---------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//Cierre de cuenta
    private void tableClose() throws Exception {
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        tableAux.setBill(true);
        daoT.updateTableBill(tableAux);
        jButExtSetter();
        labelTotalParcial.setText("Total $:");
        labelCuenta.setText("" + total);
        labelTip.setText("Prop.: " + Math.round(total * 0.1));
        double tot = total + Math.round(total * 0.1);
        labelTotal.setText("Total: " + tot);

        if (jbtAux != null) {
            jbtAux.setBackground(red);
        }

        if (jbbAux != null) {
            jbbAux.setBackground(red);
        }

        if (jbdAux != null) {
            jbdAux.setBackground(red);
            jbdSAux.setBackground(red);
        }

        butCloseTable.setText("CONFIRMAR PAGO");
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
    public void moneyKind(Salon sal, boolean end, ArrayList<Itemcard> itemsPayed, boolean toPay, double amountToPay) {
        tableAux.setToPay(toPay);
        MoneyType mt = new MoneyType(sal, end, itemsPayed, amountToPay);
        mt.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        mt.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//Pago de cuenta y cierre de mesa
    private void tablePaid() throws Exception {
        jButExtSetter();
        resetTableFull();
        setTableItems();
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
    public void amountsTypes(ArrayList<Double> amounts, boolean endex, ArrayList<Itemcard> itemsPayed, String comments) throws Exception {
        double amountC = amounts.get(0);
        double amountE = amounts.get(1);
        tableAux.setAmountCash(amountC);
        tableAux.setAmountElectronic(amountE);
        tableAux.setTotal(total);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        tableAux.setComments(comments);
        if (tableAux.isToPay() == false) {
            tableAux.setOpen(false);
        }
        daoT.updateTableMountCash(tableAux);
        daoT.updateTableMountElectronic(tableAux);
        daoT.updateTableOpen(tableAux);
        daoT.updateComments(tableAux);
        if (itemsPayed != null) {
            if (endex == true) {
                totalPayTaker(itemsPayed);
            } else {
                partialPayTaker(itemsPayed);
            }
        }

        if (endex == true) {
            itemsMntr = sim.downOpenIMon(itemsMntr, tableAux);
            tablePaid();
        }
        setEnabled(true);
    }

//----------------------------------------------------ERROR---------------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Ingreso a error
    private void errorTaker() {
        ErrorTableCount etc = new ErrorTableCount(this);
        etc.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        etc.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//Monto faltante por cash
    public void errorMountBacker(double errorBack, String cause) throws Exception {
        error = errorBack;
        tableAux.setError(error);
        daoT.updateError(tableAux);
        tableAux.setComments(cause);
        daoT.updateComments(tableAux);
        utiliMsg.cargaError();
        if (itemsPartialPaid.size() > 0) {
            itemsTableAux = itemsPartialPaid;
            tableAux.setOrder(itemsTableAux);
            itemsPartialPaid = new ArrayList<Itemcard>();
            tableAux.setPartialPayed(itemsPartialPaid);
            total = ss.countBill(tableAux);
            tableAux.setTotal(total);
            daoT.updateTableTotal(tableAux);
        } else {
            total = total - error;
        }
        tableAux.setTotal(total);
        tablePaid();
        setEnabled(true);
    }

//------------------------------------------------Corrector deItems-------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Corregir
    private void itemCorrector() {
        CorrectorItem ci = new CorrectorItem(this);
        ci.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ci.setAlwaysOnTop(true);
        setEnabled(false);
    }

//Corregir
    public void correctItems(Itemcard ic, int num) throws Exception {
        switch (num) {
            case 1:
                itemsTableAux = ss.itemTableLesser(itemsTableAux, ic);
                tableAux.setOrder(itemsTableAux);
                daoI.downActiveItemOrderTable(ic, tableAux);
                break;
            case 2:
                itemsGift = ss.itemTableLesser(itemsGift, ic);
                tableAux.setGifts(itemsGift);
                itemsTableAux.add(ic);
                tableAux.setOrder(itemsTableAux);
                daoI.downActiveItemGiftTable(ic, tableAux);
                daoI.upActiveItemOrderTable(ic, tableAux);
                break;
            case 3:
                itemsPartialPaid = ss.itemTableLesser(itemsPartialPaid, ic);
                tableAux.setPartialPayed(itemsPartialPaid);
                itemsTableAux.add(ic);
                tableAux.setOrder(itemsTableAux);
                daoI.downActiveItemPayedTable(ic, tableAux);
                daoI.upActiveItemOrderTable(ic, tableAux);
                break;
        }
        jButExtSetter();
        setTableItems();
        setEnabled(true);
    }

//----------------------------------------------Finalización Turno--------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
    private void endWorkshift() throws Exception {
        setEnabled(true);
        if (ss.openJBTButtonsTester(tableButtons) == true) {
            boolean confirm2 = utiliMsg.cargaConfirmarCierreTurno(user.getName(), user.getLastName());
            if (confirm2 == true) {
                sw.closeWorkshift(sal, workshiftNow, null, null, null, null, null);
                setEnabled(false);
            }
        } else {
            boolean confirm3 = utiliMsg.cargaConfirmarCambioTurno(user);
            if (confirm3 == true) {
                sw.inconcludeWsCutter(sal, workshiftNow);
                setEnabled(false);
            }
        }
    }

    public void saveWorkshift(Workshift actWs, Workshift nWs, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> eraseTabs, ArrayList<Table> updTabs) throws Exception {
        boolean isTabs = false;
        if (actTabs.size() > 0 || nTabs.size() > 0 || updTabs.size() > 0) {
            isTabs = true;
        }

        daoW.updateWorkshiftCash(actWs);
        daoW.updateWorkshiftClose(actWs, isTabs);
        daoW.updateWorkshiftElectronic(actWs);
        daoW.updateWorkshiftError(actWs);
        daoW.updateWorkshiftErrorReal(actWs);
        daoW.updateWorkshiftMountReal(actWs);
        daoW.updateWorkshiftState(actWs);
        daoW.updateWorkshiftTotal(actWs);
        if (nWs != null) {
            daoW.saveWorkshift(nWs);
            for (Table t : eraseTabs) {

                if (t.getOrder().size() > 0) {
                    daoI.downActiveItemOrderTableAll(t);
                }

                if (t.getGifts().size() > 0) {
                    daoI.downActiveItemGiftTableAll(t);
                }

                if (t.getPartialPayed().size() > 0) {
                    daoI.downActiveItemPayedTableAll(t);
                }

                if (t.getPartialPayedND().size() > 0) {
                    daoI.downActiveItemPayedNDTableAll(t);
                }
                daoT.downActiveTable(t);
            }

            if (updTabs.size() > 0) {
                for (int i = 0; i < updTabs.size(); i++) {
                    Table t = updTabs.get(i);
                    daoI.downActiveItemOrderTableAll(t);
                    daoI.downActiveItemGiftTableAll(t);
                    daoI.downActiveItemPayedTableAll(t);
                    daoI.downActiveItemPayedNDTableAll(t);
                    daoT.updateComments(t);
                    daoT.updateTableTotal(t);
                    if (t.getOrder().size() > 0) {
                        for (Itemcard ic : t.getOrder()) {
                            daoI.upActiveItemOrderTable(ic, t);
                        }
                    }

                    if (t.getGifts().size() > 0) {
                        for (Itemcard ic : t.getGifts()) {
                            daoI.upActiveItemGiftTable(t, ic);
                        }
                    }

                    daoT.updateTableOpen(t);
                    daoT.updateToPay(t);
                }
            }

            for (Table t : nTabs) {
                st.saveTableCompleteChangeWs(t);
            }
        }
        dispose();
    }

//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
    private void butPanelTurn() throws Exception {
        tableAux = null;
        if (tabsBut == true) {
            if (jbtAux != null) {
                jbtAux.setBorder(null);
                jbtAux = null;
            }
            resetTableValues();
            tabbedPane.setVisible(false);
            panelBDButtons.setVisible(true);
            panelPartial.setBackground(narLg);
            butPartialPay.setEnabled(false);
            labelPartialPay.setText("DESHABILITADO");
            butBarrDeli.setText("Mesas");
            tabsBut = false;
        } else {
            if (jbbAux != null) {
                jbbAux.setBorder(new LineBorder(narLg, 8));
                tableAux = null;
                jbbAux = null;
            }

            if (jbdAux != null) {
                jbdAux.setBorder(new LineBorder(narLg, 8));
                jbdSAux.setBorder(new LineBorder(narLg, 8));
                tableAux = null;
                jbdAux = null;
                jbdSAux = null;
            }
            tabbedPane.setVisible(true);
            panelBDButtons.setVisible(false);
            panelPartial.setBackground(bluLg);
            butPartialPay.setEnabled(true);
            resetTableValues();
            butBarrDeli.setText("Barra - Delivery");
            tabsBut = true;

        }
    }

    private void closePanelBarrDel() throws Exception {

    }

    // BARR--------------------------------------
    // BARR--------------------------------------
    private JPanel returnPanelBarr() {
        JPanel panelBarr = new JPanel();
        panelBarr.setLayout(null);
        panelBarr.setBackground(bluLg);
        panelBarr.setBounds(anchoUnit * 2, altoUnit, (anchoPane / 2) - anchoUnit * 3, alturaPane);

        JLabel labelBP = utiliGraf.labelTitleBackerA4("Barra");
        labelBP.setBounds(anchoUnit, altoUnit, anchoUnit * 12, altoUnit * 4);
        panelBarr.add(labelBP);

        JButton butCreateBarr = utiliGraf.button1("Crear pedido Barra", anchoUnit * 7, altoUnit * 6, anchoUnit * 20);
        butCreateBarr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createBarr();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBarr.add(butCreateBarr);

        panelBarrBut.setBackground(narLg);
        panelBarrBut.setLayout(new GridLayout(0, 1, anchoUnit * 5, altoUnit * 5));
        scrPaneBarr = new JScrollPane(panelBarrBut);
        scrPaneBarr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPaneBarr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrPaneBarr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPaneBarr.setBounds(anchoUnit, altoUnit * 15, anchoUnit * 32, altoUnit * 55);

//                scrPaneBarr.setPreferredSize(new Dimension(anchoUnit * 32, altoUnit * 55));
//        scrPaneBarr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrPaneBarr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelBarr.add(scrPaneBarr);

        return panelBarr;
    }

    private void createBarr() throws Exception {
        boolean testN = false;
        boolean emptyButton = false;
        if (barrButtons.size() > 0) {
            testN = true;
        }

        if (testN) {
            Table tab = barrButtons.get(0).getTable();
            if (barrButtons.get(0).isOpenJBB() == false && tab.isBill() == false) {
                emptyButton = true;
                utiliMsg.erroNotNewButton();
            }
        }

        if (emptyButton == false) {
            int num = barrButtons.size() + 1;
            JButtonBarr newJBB = new JButtonBarr(num);
            Table newTable = new Table(newJBB.getNum(), newJBB.getPos(), user);
            newJBB.setTable(newTable);
            barrButtons.add(0, newJBB);
            barrButUpdater();
            resetTableValues();
        }
    }

    private void barrButUpdater() {
        for (int i = 0; i < barrButtons.size(); i++) {
            JButtonBarr butSelBarr = barrButtons.get(i);
            butSelBarr.setBackground(narUlg);
            butSelBarr.setBorder(new LineBorder(narLg, 8));

            butSelBarr.setFont(font2);
            butSelBarr.setText("Barra pedido " + butSelBarr.getNum());
            butSelBarr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {

                        if (scheduler != null) {
                            scheduler.shutdown();
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        } else {
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        }

                        if (!loopBreaker) {
                            selectBarr(ae);
                            loopBreaker = true;
                            Runnable duty = () -> {
                                loopBreaker = false;
                            };
                            long timeWait = 1000; // en segundos
                            scheduler.schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            if (barrButtons.get(i).isOpenJBB()) {
                barrButtons.get(i).setBackground(green);
            }

            if (barrButtons.get(i).getTable().isBill() == true) {
                barrButtons.get(i).setBackground(red);
            }

            if (barrButtons.get(i).isOpenJBB() == false && barrButtons.get(i).getTable().isBill() == true) {
                barrButtons.get(i).setBackground(narUlgX);
                barrButtons.get(i).setEnabled(false);
                barrButtons.get(i).setText("Barra " + "P" + jbbAux.getTable().getNum() + " Cerrado");
            }

            panelBarrBut.add(butSelBarr);
        }
        revalidate();
        repaint();
    }

    private void selectBarr(ActionEvent e) {
        if (jbtAux != null) {
            jbtAux.setBorder(null);
            jbtAux = null;
        }

        if (jbdAux != null) {
            jbdAux.setBorder(new LineBorder(narLg, 8));
            jbdAux = null;
            jbdSAux.setBorder(new LineBorder(narLg, 8));
            jbdSAux = null;
        }

        if (jbbAux != null) {
            jbbAux.setBorder(new LineBorder(narLg, 8));
            jbbAux = null;
        }

        JButtonBarr butClicked = (JButtonBarr) e.getSource();
        for (int i = 0; i < barrButtons.size(); i++) {
            if (barrButtons.get(i).getNum() == butClicked.getNum()) {
                jbbAux = barrButtons.get(i);
            }
        }
        try {
            resetTableValues();
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        jbbAux.setBorder(new LineBorder(bluSt, 8));

        if (jbbAux.isOpenJBB() == false) {
            tableAux = jbbAux.getTable();
            waiterAux = user;
            tableAux.setWaiter(user);
            tableAux.setOpen(true);
            labelOrder.setText("BARRA: B" + tableAux.getNum());
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
            tableAux.setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter();
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableAux = jbbAux.getTable();
            tableFullerProp();
        }
    }

    // DELIVERY----------------------------------
    // DELIVERY----------------------------------
    private JPanel returnPanelDeli() {
        JPanel panelDeli = new JPanel();
        panelDeli.setLayout(null);
        panelDeli.setBackground(bluLg);
        panelDeli.setBounds(anchoPane / 2 + anchoUnit, altoUnit, (anchoPane / 2) - anchoUnit * 3, alturaPane);

        JLabel labelDP = utiliGraf.labelTitleBackerA4("Delivery");
        labelDP.setBounds(anchoUnit, altoUnit, anchoUnit * 12, altoUnit * 4);
        panelDeli.add(labelDP);

        JButton butCreateDeli = utiliGraf.button1("Crear pedido delivery", anchoUnit * 7, altoUnit * 6, anchoUnit * 20);
        butCreateDeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createDelivery();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelDeli.add(butCreateDeli);

        panelDeliContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelDeliContainer.setBackground(bluLg);
        panelDeliContainer.setBounds(0, altoUnit * 14, (anchoPane / 2) - anchoUnit * 3, (alturaPane - altoUnit * 13));
        panelDeli.add(panelDeliContainer);

        panelDeliContainerSetter();

        return panelDeli;
    }

    private void createDelivery() throws Exception {
        boolean testN = false;
        boolean emptyButton = false;
        int deliBq = deliButtons.size();
        if (deliBq > 0) {
            testN = true;
        }

        //To not create new buttons if is empty 
        if (testN) {
            Table tab = deliButtons.get(0).getTable();
            if (deliButtons.get(0).isOpenJBD() == false && tab.isBill() == false) {
                emptyButton = true;
                utiliMsg.erroNotNewButton();
            }
        }

        if (emptyButton == false) {
            innDeliveryData();
        }
    }

    private void panelDeliContainerSetter() {
        panelDeliContainer.removeAll();
        int height = altoUnit * ((deliButtons.size() * 10) + 1);
        panelDeliBut.setLayout(null);
        if (height < altoUnit * 52) {
            height = altoUnit * 52;
        }

        panelDeliBut.setPreferredSize(new Dimension(anchoUnit * 32, height));
        panelDeliBut.setBackground(narLg);

        for (int i = 0; i < deliButtons.size(); i++) {
            deliButtons.get(i).setBounds(anchoUnit, altoUnit * ((i * 10) + 1), anchoUnit * 23, altoUnit * 10);
            panelDeliBut.add(deliButtons.get(i));

            //Test
            deliButtonsSees.get(i).setBounds(anchoUnit * 24, altoUnit * ((i * 10) + 1), anchoUnit * 6, altoUnit * 10);
            panelDeliBut.add(deliButtonsSees.get(i));
        }

        JScrollPane scrPaneDeli = new JScrollPane(panelDeliBut);
        scrPaneDeli.setPreferredSize(new Dimension(anchoUnit * 32, altoUnit * 55));
        scrPaneDeli.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPaneDeli.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelDeliContainer.add(scrPaneDeli);
    }

    void getDeliOrder(Delivery dOrder) throws Exception {
        deliOrderAux = dOrder;
        daoD.saveDelivery(deliOrderAux);
        int num = deliButtons.size() + 1;
        JButtonDelivery newJBD = new JButtonDelivery(num);
        Table newTable = new Table(newJBD.getNum(), newJBD.getPos(), user);
        newJBD.setTable(newTable);
        deliButtons.add(0, newJBD);

        JButtonDeliverySee butSee = new JButtonDeliverySee(num, dOrder);
        deliButtonsSees.add(0, butSee);

        panelDeliBut.repaint();

        panelDeliContainerSetter();
        deliButUpdater();
        resetTableValues();
    }

    void setDeliOrder(Delivery dOrder) throws Exception {
        deliOrderAux = dOrder;
        daoD.updateDelivery(deliOrderAux);
        deliButUpdater();
        resetTableValues();
    }

    private void deliButUpdater() {
        for (int i = 0; i < deliButtons.size(); i++) {
            JButtonDelivery butSelDelivery = deliButtons.get(i);
            butSelDelivery.setBackground(narUlg);
            butSelDelivery.setBorder(new LineBorder(narLg, 8));
            butSelDelivery.setFont(font2);
            butSelDelivery.setText("Delivery pedido " + butSelDelivery.getNum());
            butSelDelivery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (scheduler != null) {
                            scheduler.shutdown();
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        } else {
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        }

                        if (!loopBreaker) {
                            selectDeli(ae);
                            if (!loopBreaker) {
                                selectDeli(ae);
                                loopBreaker = true;
                                Runnable duty = () -> {
                                    loopBreaker = false;
                                };
                                long timeWait = 1000; // en segundos
                                scheduler.schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );

            //Test
            JButtonDeliverySee butSee = deliButtonsSees.get(i);

            butSee.setBackground(narUlg);

            butSee.setBorder(
                    new LineBorder(narLg, 8));
            butSee.setFont(font3);

            butSee.setText(
                    "ver");
            butSee.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae
                ) {
                    try {
                        if (scheduler != null) {
                            scheduler.shutdown();
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        } else {
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                        }

                        if (!loopBreaker) {
                            selectDeliSee(ae);
                            loopBreaker = true;
                            Runnable duty = () -> {
                                loopBreaker = false;
                            };
                            long timeWait = 1000; // en segundos
                            scheduler.schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            );

            if (deliButtons.get(i)
                    .isOpenJBD()) {
                deliButtons.get(i).setBackground(green);
                deliButtonsSees.get(i).setBackground(green);
            }

            if (deliButtons.get(i)
                    .getTable().isBill() == true) {
                deliButtons.get(i).setBackground(red);
                //Test
                deliButtonsSees.get(i).setBackground(red);
            }

            if (deliButtons.get(i)
                    .isOpenJBD() == false && deliButtons.get(i).getTable().isBill() == true) {
                deliButtons.get(i).setBackground(narUlgX);
                deliButtons.get(i).setEnabled(false);
                //Test
                deliButtonsSees.get(i).setBackground(narUlgX);
                deliButtonsSees.get(i).setEnabled(false);
            }

            panelDeliBut.add(butSelDelivery);
        }
        revalidate();
        repaint();
    }

    private void innDeliveryData() throws Exception {
        new DeliveryTemplate(sal, null);
        setEnabled(false);
    }

    private void selectDeli(ActionEvent e) {
        if (jbtAux != null) {
            jbtAux.setBorder(null);
            jbtAux = null;
        }
        if (jbdAux != null) {
            jbdAux.setBorder(new LineBorder(narLg, 8));
            jbdAux = null;
            jbdSAux.setBorder(new LineBorder(narLg, 8));
            jbdSAux = null;
        }

        if (jbbAux != null) {
            jbbAux.setBorder(new LineBorder(narLg, 8));
            jbbAux = null;
        }

        JButtonDelivery butClicked = (JButtonDelivery) e.getSource();
        for (int i = 0; i < deliButtons.size(); i++) {
            if (deliButtons.get(i).getNum() == butClicked.getNum()) {
                jbdAux = deliButtons.get(i);
                jbdSAux = deliButtonsSees.get(i);
            }
        }
        try {
            resetTableValues();
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        jbdAux.setBorder(new LineBorder(bluSt, 8));
        jbdSAux.setBorder(new LineBorder(bluSt, 8));

        if (jbdAux.isOpenJBD() == false) {
            tableAux = jbdAux.getTable();
            waiterAux = user;
            tableAux.setWaiter(user);
            tableAux.setOpen(true);
            labelOrder.setText("DELIV.: D" + tableAux.getNum());
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
            tableAux.setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter();
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableAux = jbdAux.getTable();
            tableFullerProp();
        }
    }

    private void selectDeliSee(ActionEvent e) throws Exception {

        if (jbtAux != null) {
            jbtAux.setBorder(null);
            jbtAux = null;
        }

        if (jbdAux != null) {
            jbdAux.setBorder(new LineBorder(narLg, 8));
            jbdAux = null;
            jbdSAux.setBorder(new LineBorder(narLg, 8));
            jbdSAux = null;
        }

        if (jbbAux != null) {
            jbbAux.setBorder(new LineBorder(narLg, 8));
            jbbAux = null;
        }

        JButtonDeliverySee butClicked = (JButtonDeliverySee) e.getSource();
        for (int i = 0; i < deliButtonsSees.size(); i++) {
            if (deliButtonsSees.get(i).getNumDeli() == butClicked.getNumDeli()) {
                jbdAux = deliButtons.get(i);
                jbdSAux = deliButtonsSees.get(i);
                Delivery deli = jbdSAux.getDelivery();
                if (jbdSAux.getDelivery().getConsumer() != null) {
                    new DeliveryTemplate(sal, deli);
                } else {
                    utiliMsg.errorNullDeli();
                }
            }
        }
        try {
            resetTableValues();
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        jbdAux.setBorder(new LineBorder(bluSt, 8));
        jbdSAux.setBorder(new LineBorder(bluSt, 8));

        if (jbdAux.isOpenJBD() == false) {
            tableAux = jbdAux.getTable();
            waiterAux = user;
            tableAux.setWaiter(user);
            tableAux.setOpen(true);
            labelOrder.setText("DELIV.: D" + tableAux.getNum());
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
            tableAux.setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter();
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableAux = jbdAux.getTable();
            tableFullerProp();
        }

    }

//--------------------------------------------------FUNCIONES-------------------------------------------------------
//--------------------------------------------------GENERALES-------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
    public void enableSalon() {
        setEnabled(true);
    }

    public Table getTable() {
        return tableAux;
    }

//------------------------------------------------------------------------------------------------------------------
    double getTotal() {
        return total;
    }

//------------------------------------------------------------------------------------------------------------------
    double totalBacker() {
        return total;
    }

//------------------------------------------------------------------------------------------------------------------
    public ArrayList<Itemcard> getItemsTableAux() {
        return itemsTableAux;
    }

//------------------------------------------------------------------------------------------------------------------
    private void jButExtSetter() throws Exception {
        if (jbtAux != null) {
            jbtAux.setTable(tableAux);
            for (int i = 0; i < tableButtons.size(); i++) {
                if (tableButtons.get(i).getNum() == jbtAux.getNum()) {
                    tableButtons.set(i, jbtAux);
                }
            }
        }

        if (jbbAux != null) {
            jbbAux.setTable(tableAux);
            for (int i = 0; i < barrButtons.size(); i++) {
                if (barrButtons.get(i).getNum() == jbbAux.getNum()) {
                    barrButtons.set(i, jbbAux);
                }
            }
        }

        if (jbdAux != null) {
            jbdAux.setTable(tableAux);
            for (int i = 0; i < deliButtons.size(); i++) {
                if (deliButtons.get(i).getNum() == jbdAux.getNum()) {
                    deliButtons.set(i, jbdAux);
                }
            }
        }
    }

//------------------------------------------------------------------------------------------------------------------
    private void tableFullerProp() {
        itemsTableAux = tableAux.getOrder();
        itemsGift = tableAux.getGifts();
        itemsPartialPaid = tableAux.getPartialPayed();
        itemsPartialPaidNoDiscount = tableAux.getPartialPayedND();
        waiterAux = tableAux.getWaiter();
        discount = tableAux.getDiscount();
        priceCorrection = tableAux.getPriceCorrection();
        total = tableAux.getTotal();
        error = tableAux.getError();
        setTableItems();
        if (itemsPartialPaid.size() > 0) {
            double payed = ss.partialBillPayed(tableAux);
            labelPartialPay.setText("Pagado: $" + (payed));
        }

        if (tableAux.isBill() == true) {
            labelTotalParcial.setText("Total $:");
            labelTip.setText("Prop.: " + Math.round(total * 0.1));
            double tot = Math.round(total * 0.1) + total;
            labelTotal.setText("Total: " + tot);
        } else {
            labelTotalParcial.setText("Parcial $:");
        }

        if (jbtAux != null) {
            labelWaiter.setText("Mozo: " + waiterAux.getName() + " " + utili.strShorter(waiterAux.getLastName(), 2).toUpperCase());
            String nameT = tableAux.getPos() + tableAux.getNum();
            labelOrder.setText("MESA:" + nameT);
            if (jbtAux.isOpenJBT() == false) {
                butCloseTable.setText("CERRAR ORDEN");
            } else {
                if (tableAux.isBill() == true) {
                    butCloseTable.setText("CONFIRMAR PAGO");
                } else {
                    butCloseTable.setText("CERRAR CUENTA");
                }
            }
        }

        if (jbbAux != null) {
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
            labelOrder.setText("BARRA: B" + tableAux.getNum());
        }

        if (jbdAux != null) {
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2).toUpperCase());
            labelOrder.setText("DELIV.: D" + tableAux.getNum());
        }

        labelCuenta.setText(total + "");
    }

//------------------------------------------------------------------------------------------------------------------
    private void resetTableFull() throws Exception {
        if (jbtAux != null) {
            jbtAux.setBackground(narUlg);
            jbtAux.setTable(null);
            jbtAux.setOpenJBT(false);
        }

        if (jbbAux != null) {
            if (jbbAux.isOpenJBB() == false && tableAux.isBill() == false) {
                int barrBIndex = -1;
                for (int i = 0; i < barrButtons.size(); i++) {
                    if (jbbAux.getNum() == barrButtons.get(i).getNum()) {
                        barrBIndex = i;
//                        jbbAux.setVisible(false);
                    }
                }
                barrButtons.remove(barrBIndex);
                panelBarrBut.removeAll();
                barrButUpdater();
            } else {
                jbbAux.setBackground(narUlgX);
                jbbAux.setEnabled(false);
                jbbAux.setOpenJBB(false);
                jbbAux.setText("B" + jbbAux.getTable().getNum() + " Cerrado");
            }
        }

        if (jbdAux != null) {
            if (jbdAux.isOpenJBD() == false && tableAux.isBill() == false) {
                int deliBIndex = -1;
                for (int i = 0; i < deliButtons.size(); i++) {
                    if (jbdAux.getNum() == deliButtons.get(i).getNum()) {
                        deliBIndex = i;
                    }
                }
                deliButtons.remove(deliBIndex);
                panelDeliBut.removeAll();
                deliButUpdater();
            } else {
                jbdAux.setBackground(narUlgX);
                jbdSAux.setBackground(narUlgX);
                jbdAux.setEnabled(false);
                jbdSAux.setEnabled(false);
                jbdAux.setOpenJBD(false);
                jbdAux.setText("D" + jbdAux.getTable().getNum() + " Cerrado");
            }
        }
        jButExtSetter();
        resetTableValues();

        utiliMsg.cargaTableErase();
    }

    private void resetTableValues() throws Exception {
        itemsTableAux = new ArrayList<Itemcard>();//items a cobrar de la mesa
        itemsGift = new ArrayList<Itemcard>(); //items obsequiados
        itemsPartialPaid = new ArrayList<Itemcard>(); // items cobrados por pago parcial
        itemsPartialPaidNoDiscount = new ArrayList<Itemcard>(); // items cobrados anted de aplicar descuento
        waiterAux = null;
        tableAux = null;
        total = 0;
        error = 0;
        discount = 0;
        priceCorrection = 0;
        setTableItems();
        checkBoxIndic.setSelected(false);
        spinnerUnitsItem.setValue(1);
//        comboItems.setSelectedIndex(itemsDB.size());
        labelTotalParcial.setText("Parcial $:");
        labelCuenta.setText("0.00");
        labelTip.setText("Prop: $0.00");
        labelTotal.setText("Total: $0.00");
        labelPartialPay.setText("Pagado: $0.00");
        labelOrder.setText("MESA:--");
        labelWaiter.setText("Mozo: --");
        butCloseTable.setText("CERRAR ORDEN");
        if (jbtAux != null) {
            jbtAux.setBorder(null);
            if (jbtAux.isOpenJBT() == true) {
                if (itemsTableAux.size() > 0) {
                    if (tableAux.isBill() == true) {
                        butCloseTable.setText("CONFIRMAR PAGO");
                    } else {
                        butCloseTable.setText("CERRAR CUENTA");
                    }
                }
            }
        }
    }

    public ArrayList<ItemMonitor> getItemsMonitor() {
        return itemsMntr;
    }

    void setItemsMnr(ArrayList<ItemMonitor> newItemsMntr) {
        itemsMntr = newItemsMntr;
    }

    public void addItemMonitorList(ItemMonitor im) {
        itemsMntr.add(im);
    }

    public User getUser() {
        return user;
    }

    public void setSalonEnabled() {
        setEnabled(true);
    }
}
