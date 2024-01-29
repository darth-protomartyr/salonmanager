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
import salonmanager.servicios.ServicioItemMonitor;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasSalon;
import salonmanager.utilidades.UtilidadesMensajes;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
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
    JPanel panelA = new JPanel();

    JButtonBarr jbbAux = null;
    JButtonDelivery jbdAux = null;
    JButtonDeliverySee jbdSAux = null;

    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jbtAux = null; //boton de mesa actual
//    ServicioCloseWorkshift sw = new ServicioCloseWorkshift();

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

//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------
//PANEL STATE----------------------------------------------------------------------------------------------------------
//PANEL STATE----------------------------------------------------------------------------------------------------------        
        JPanel panelActual = utiliGrafSal.panelActualBacker(sal);
        panelPpal.add(panelActual);

//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------
//PANEL BARR_DELIVERY--------------------------------------------------------------------------------------------------      
//BARR / DELI
        JPanel panelBarrDeli = utiliGrafSal.panelBarrDeliBacker(sal);
        panelPpal.add(panelBarrDeli);

//PANEL MONITOR--------------------------------------------------------------------------------------------------------
//PANEL MONITOR--------------------------------------------------------------------------------------------------------
        JPanel panelMonitor = utiliGrafSal.panelMonitor(sal);
        panelPpal.add(panelMonitor);

//PANELES CONTENEDORES BOTONES 1--------------------------------------------------------------------------------------
//PANELES CONTENEDORES BOTONES 1--------------------------------------------------------------------------------------
        panelA = new JPanel();
        panelA.setLayout(null);
        panelA.setBounds(20, altoUnit * 18, (anchoPane + anchoUnit * 2), (alturaPane + altoUnit * 7));
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);
        panelBDButtons = new JPanel();
        panelBDButtons.setLayout(null);
        panelBDButtons.setBackground(narLg);
        panelBDButtons.setBounds(anchoUnit, anchoUnit, anchoPane, (alturaPane + altoUnit * 3));
        panelBDButtons.setVisible(false);
        panelA.add(panelBDButtons);

//Panel Barra-----------------------------------------------------------
        JPanel panelBarr = utiliGrafSal.returnPanelBarr(sal);
        panelBDButtons.add(panelBarr);

//Panel Delivery--------------------------------------------------------        
        JPanel panelDeli = utiliGrafSal.returnPanelDeli(sal);
        panelBDButtons.add(panelDeli);

//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
        utiliGrafSal.returnTabbedPanes(sal);

// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(bluLg);
        panelLateral.setLayout(null);
        panelLateral.setBounds((anchoFrame * 7 / 10) + anchoUnit * 5, altoUnit * 3, anchoFrame - (anchoPane + anchoUnit * 7), (alturaPane + altoUnit * 22));
        panelPpal.add(panelLateral);

//Panel Table-----------------------------------------------------------
        JPanel panelTable = utiliGrafSal.returnPanelTable(sal);
        panelLateral.add(panelTable);

//Panel SelItem---------------------------------------------------------
        JPanel panelSelItem = utiliGrafSal.returnPanelSelItem(sal);
        panelTable.add(panelSelItem);

//Tabla con corrector--------------------------------------------------- 
        scrollPaneItems = utiliGrafSal.scrollItemsBack(altoUnit, altoUnit * 28, anchoUnit * 21 + altoUnit, altoUnit * 30, sal);
        panelTable.add(scrollPaneItems);
        utiliGrafSal.tableCarrector(sal);

//Boton Obsequio--------------------------------------------------------
        JButton butGift = utiliGraf.button2("Obsequio", altoUnit, altoUnit * 59, anchoUnit * 11);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    utiliGrafSal.actionButtonGift(sal);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butGift);

//Boton Descuento-------------------------------------------------------
        JButton butDiscount = utiliGraf.button2("Descuento", altoUnit * 2 + anchoUnit * 11, altoUnit * 59, anchoUnit * 10);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    utiliGrafSal.actionButtonDiscount(sal);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butDiscount);

//Boton Pago Parcial----------------------------------------------------
        panelPartial = utiliGrafSal.returnPanelPartial(sal);
        panelTable.add(panelPartial);

//Boton Pago Final
        butCloseTable = utiliGraf.button1("CERRAR ORDEN", altoUnit, altoUnit * 70, anchoUnit * 13 + altoUnit);
        panelTable.add(butCloseTable);
        butCloseTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    utiliGrafSal.actionButtonCloseTable(sal);

                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            utiliGrafSal.resetTableFull(sal);
                        } else {
                            if (tableAux.isBill() == false) {
                                boolean confirm = utiliMsg.cargaConfirmarCierre();
                                if (confirm) {
                                    utiliGrafSal.tableClose(sal);
                                }
                            } else {
                                utiliGrafSal.moneyKind(sal, true, null, false, total);
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
                        utiliGrafSal.errorTaker(sal);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butErrorTable);
        
        panelCuenta = utiliGrafSal.returnPanelCount(sal);
        panelTable.add(panelCuenta);

//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------    

//------------------------------------------------------------------------------------------------------------------
//Listar Items por etiqueta
//------------------------------------------------------------------------------------------------------------------
//Ingreso de items
//------------------------------------------------------------------------------------------------------------------
//Scroll de la Tabla
//------------------------------------------------------------------------------------------------------------------
//Tabla de Items
//    private void setTableItems() {
//        HashSet<Itemcard> itemsSet = new HashSet<Itemcard>(itemsTableAux);
//        ArrayList<Itemcard> itemsAux = new ArrayList<Itemcard>(itemsSet);
//
//        HashSet<Itemcard> partialSet = new HashSet<Itemcard>(itemsPartialPaid);
//        ArrayList<Itemcard> partials = new ArrayList<Itemcard>(partialSet);
//
//        HashSet<Itemcard> partialSetND = new HashSet<Itemcard>(itemsPartialPaidNoDiscount);
//        ArrayList<Itemcard> partialsND = new ArrayList<Itemcard>(partialSetND);
//
//        HashSet<Itemcard> giftSet = new HashSet<Itemcard>(itemsGift);
//        ArrayList<Itemcard> gifts = new ArrayList<Itemcard>(giftSet);
//
//        HashSet<Itemcard> totalSet = new HashSet<Itemcard>(itemsAux);
//        ArrayList<Itemcard> totalItems = new ArrayList<Itemcard>(totalSet);
//
//        totalItems.addAll(partials);
//        totalItems.addAll(partialsND);
//        totalItems.addAll(gifts);
//
//        rowsItems = totalItems.size();
//
//        int aux = 0;
//        if (discount > 0) {
//            aux += 1;
//        }
//
//        if (priceCorrection > 0) {
//            aux += 1;
//        }
//
//        data = new String[rowsItems + aux][colItems];
//
//        int intAux = itemsAux.size();
//        int intPartial = itemsAux.size() + partials.size();
//        int intPartialND = itemsAux.size() + partials.size() + partialsND.size();
//
//        double disc = (double) discount / 100;
//
//        for (int i = 0; i < rowsItems; i++) {
//            Itemcard ic = totalItems.get(i);
//
//            if (i < intAux) {
//                int u = st.itemUnitsBacker(itemsTableAux, ic);
//                data[i][0] = " " + u;
//                data[i][1] = " " + ic.getName();
//                data[i][2] = " " + ic.getPrice() * u * (1 - disc);
//            }
//
//            if (partials.size() > 0 && i >= intAux && i < intPartial) {
//                int u = st.itemUnitsBacker(itemsPartialPaid, ic);
//                data[i][0] = " " + u;
//                data[i][1] = " PAG." + ic.getName();
//                data[i][2] = "PAGADO";
//            }
//
//            if (partialsND.size() > 0 && i >= intPartial && i < intPartialND) {
//                int u = st.itemUnitsBacker(itemsPartialPaidNoDiscount, ic);
//                data[i][0] = " " + u;
//                data[i][1] = " PAG*." + ic.getName();
//                data[i][2] = "PAGADO";
//            }
//
//            if (i >= intPartialND) {
//                int u = st.itemUnitsBacker(itemsGift, ic);
//                data[i][0] = " " + u;
//                data[i][1] = " OBS." + ic.getName();
//                data[i][2] = " 0";
//            }
//        }
//
//        if (priceCorrection > 0) {
//            if (discount > 0) {
//                data[rowsItems - 1][1] = "Corrección precio mod:";
//                data[rowsItems - 1][2] = priceCorrection + "";
//            } else {
//                data[rowsItems][1] = "Corrección precio mod:";
//                data[rowsItems][2] = priceCorrection + "";
//            }
//        }
//
//        if (discount > 0) {
//            data[rowsItems][1] = "DESCUENTO: " + discount + "%";
//        }
//
//        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
//        jTableItems.setModel(tableModel);
//        jTableItems.setDefaultEditor(Object.class, null);
//
//        JTableHeader header = jTableItems.getTableHeader();
//        header.setFont(new Font("Arial", Font.BOLD, 16));
//        header.setBackground(bluSt);
//
//        Font cellFont = new Font("Arial", Font.BOLD, 14);
//        jTableItems.setFont(cellFont);
//        jTableItems.setRowHeight(25);
//        jTableItems.setBackground(narUlg);
//
//        int c = (anchoPane - 50) / 8;
//        TableColumn column1 = jTableItems.getColumnModel().getColumn(0);
//        column1.setPreferredWidth(c);
//        TableColumn column2 = jTableItems.getColumnModel().getColumn(1);
//        column2.setPreferredWidth(c * 5);
//        TableColumn column3 = jTableItems.getColumnModel().getColumn(2);
//        column3.setPreferredWidth(c * 2);
//    }
//-----------------------------------------------------OBSEQUIO-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//    public void giftBacker(Itemcard ic) throws Exception {
//        itemsGift.add(ic);
//        tableAux.setGifts(itemsGift);
//        itemsTableAux = ss.itemTableLesser(tableAux.getOrder(), ic);
//        tableAux.setOrder(itemsTableAux);
//        utiliMsg.cargaGift(ic.getName());
//        utiliGrafSal.setTableItems(sal);
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        daoI.downActiveItemOrderTable(ic, tableAux);
//        daoI.saveItemGiftTable(ic, tableAux);
//        daoT.updateTableTotal(tableAux);
//        utiliGrafSal.jButExtSetter(sal);
//        labelCuenta.setText(total + "");
//        setEnabled(true);
//    }

//-------------------------------------------------PAGO DESCUENTO---------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//    public void discountBacker(int disc) throws Exception {
//        discount = disc;
//        tableAux.setDiscount(disc);
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        daoT.updateTableTotal(tableAux);
//        daoT.updateTableDiscount(tableAux);
//        utiliGrafSal.jButExtSetter(sal);
//        labelCuenta.setText(total + "");
//        utiliGrafSal.setTableItems(sal);
//        setEnabled(true);
//    }

//-------------------------------------------------PAGO PARCIAL-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------ 
//------------------------------------------------------------------------------------------------------------------
//pago parcial de cuenta fraccionada
//    void partialPayTaker(ArrayList<Itemcard> itemsPayed) throws Exception {
//        jbtAux.setBackground(viol);
//        for (int i = 0; i < itemsPayed.size(); i++) {
//            itemsTableAux = ss.itemTableLesser(itemsTableAux, itemsPayed.get(i));
//        }
//        itemsPartialPaid.addAll(itemsPayed);
//        tableAux.setPartialPayed(itemsPartialPaid);
//        tableAux.setOrder(itemsTableAux);
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        daoT.updateTableTotal(tableAux);
//        tableAux.setToPay(true);
//        daoT.updateToPay(tableAux);
//        for (Itemcard ic : itemsPayed) {
//            daoI.saveItemPayedTable(ic, tableAux);
//            daoI.downActiveItemOrderTable(ic, tableAux);
//        }
//        labelCuenta.setText(total + "");
//        double payed = ss.partialBillPayed(tableAux);
//        labelPartialPay.setText("Pagado: $" + (payed));
//        utiliGrafSal.jButExtSetter(sal);
//        utiliGrafSal.setTableItems(sal);
//        setEnabled(true);
//    }

//------------------------------------------------------------------------------------------------------------------ 
//pago completo de cuenta fraccionada
//    void totalPayTaker(ArrayList<Itemcard> itemsPayed) throws Exception {
//        itemsPartialPaid.addAll(itemsPayed);
//        tableAux.setPartialPayed(itemsPartialPaid);
//        itemsTableAux = itemsPartialPaid;
//        itemsPartialPaid = new ArrayList<Itemcard>();
//        tableAux.setOrder(itemsTableAux);
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        tableAux.setOpen(false);
//        daoT.updateTableTotal(tableAux);
//        tableAux.setToPay(false);
//        daoT.updateToPay(tableAux);
//        for (Itemcard ic : itemsPayed) {
//            daoI.saveItemPayedTable(ic, tableAux);
//            daoI.downActiveItemOrderTable(ic, tableAux);
//        }
//        labelCuenta.setText(total + "");
//        setEnabled(true);
//    }

//----------------------------------------------CIERRE Y PAGO DE CUENTA---------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//Cierre de cuenta
//    private void tableClose() throws Exception {
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        daoT.updateTableTotal(tableAux);
//        tableAux.setBill(true);
//        daoT.updateTableBill(tableAux);
//        utiliGrafSal.jButExtSetter(sal);
//        labelTotalParcial.setText("Total $:");
//        labelCuenta.setText("" + total);
//        labelTip.setText("Prop.: " + Math.round(total * 0.1));
//        double tot = total + Math.round(total * 0.1);
//        labelTotal.setText("Total: " + tot);
//
//        if (jbtAux != null) {
//            jbtAux.setBackground(red);
//        }
//
//        if (jbbAux != null) {
//            jbbAux.setBackground(red);
//        }
//
//        if (jbdAux != null) {
//            jbdAux.setBackground(red);
//            jbdSAux.setBackground(red);
//        }
//
//        butCloseTable.setText("CONFIRMAR PAGO");
//        setEnabled(true);
//    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
//    public void moneyKind(Salon sal, boolean end, ArrayList<Itemcard> itemsPayed, boolean toPay, double amountToPay) {
//        tableAux.setToPay(toPay);
//        MoneyType mt = new MoneyType(sal, end, itemsPayed, amountToPay);
//        mt.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
//        mt.setAlwaysOnTop(true);
//        setEnabled(false);
//    }
//
////------------------------------------------------------------------------------------------------------------------
////Pago de cuenta y cierre de mesa
//    private void tablePaid() throws Exception {
//        utiliGrafSal.jButExtSetter(sal);
//        utiliGrafSal.resetTableFull(sal);
//        utiliGrafSal.setTableItems(sal);
//        setEnabled(true);
//    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
//    public void amountsTypes(ArrayList<Double> amounts, boolean endex, ArrayList<Itemcard> itemsPayed, String comments) throws Exception {
//        double amountC = amounts.get(0);
//        double amountE = amounts.get(1);
//        tableAux.setAmountCash(amountC);
//        tableAux.setAmountElectronic(amountE);
//        tableAux.setTotal(total);
//        total = ss.countBill(tableAux);
//        tableAux.setTotal(total);
//        tableAux.setComments(comments);
//        if (tableAux.isToPay() == false) {
//            tableAux.setOpen(false);
//        }
//        daoT.updateTableMountCash(tableAux);
//        daoT.updateTableMountElectronic(tableAux);
//        daoT.updateTableOpen(tableAux);
//        daoT.updateComments(tableAux);
//        if (itemsPayed != null) {
//            if (endex == true) {
//                utiliGrafSal.totalPayTaker(itemsPayed, sal);
//            } else {
//                utiliGrafSal.partialPayTaker(itemsPayed,sal);
//            }
//        }
//
//        if (endex == true) {
//            itemsMntr = sim.downOpenIMon(itemsMntr, tableAux);
//            utiliGrafSal.tablePaid(sal);
//        }
//        setEnabled(true);
//    }
//
////----------------------------------------------------ERROR---------------------------------------------------------    
////------------------------------------------------------------------------------------------------------------------
////Ingreso a error
//    private void errorTaker() {
//        ErrorTableCount etc = new ErrorTableCount(this);
//        etc.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
//        etc.setAlwaysOnTop(true);
//        setEnabled(false);
//    }
//
////------------------------------------------------------------------------------------------------------------------
////Monto faltante por cash
//    public void errorMountBacker(double errorBack, String cause) throws Exception {
//        error = errorBack;
//        tableAux.setError(error);
//        daoT.updateError(tableAux);
//        tableAux.setComments(cause);
//        daoT.updateComments(tableAux);
//        utiliMsg.cargaError();
//        if (itemsPartialPaid.size() > 0) {
//            itemsTableAux = itemsPartialPaid;
//            tableAux.setOrder(itemsTableAux);
//            itemsPartialPaid = new ArrayList<Itemcard>();
//            tableAux.setPartialPayed(itemsPartialPaid);
//            total = ss.countBill(tableAux);
//            tableAux.setTotal(total);
//            daoT.updateTableTotal(tableAux);
//        } else {
//            total = total - error;
//        }
//        tableAux.setTotal(total);
//        utiliGrafSal.tablePaid(sal);
//        setEnabled(true);
//    }

//------------------------------------------------Corrector deItems-------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Corregir
//Corregir
//    public void correctItems(Itemcard ic, int num) throws Exception {
//        switch (num) {
//            case 1:
//                itemsTableAux = ss.itemTableLesser(itemsTableAux, ic);
//                tableAux.setOrder(itemsTableAux);
//                daoI.downActiveItemOrderTable(ic, tableAux);
//                break;
//            case 2:
//                itemsGift = ss.itemTableLesser(itemsGift, ic);
//                tableAux.setGifts(itemsGift);
//                itemsTableAux.add(ic);
//                tableAux.setOrder(itemsTableAux);
//                daoI.downActiveItemGiftTable(ic, tableAux);
//                daoI.upActiveItemOrderTable(ic, tableAux);
//                break;
//            case 3:
//                itemsPartialPaid = ss.itemTableLesser(itemsPartialPaid, ic);
//                tableAux.setPartialPayed(itemsPartialPaid);
//                itemsTableAux.add(ic);
//                tableAux.setOrder(itemsTableAux);
//                daoI.downActiveItemPayedTable(ic, tableAux);
//                daoI.upActiveItemOrderTable(ic, tableAux);
//                break;
//        }
//        utiliGrafSal.jButExtSetter(sal);
//        utiliGrafSal.setTableItems(sal);
//        setEnabled(true);
//    }

//----------------------------------------------Finalización Turno--------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//    public void saveWorkshift(Workshift actualWs, Workshift newWs, ArrayList<Table> actualTabs, ArrayList<Table> newTabs, ArrayList<Table> toEraseTabs, ArrayList<Table> toUpdTabs) throws Exception {
//        boolean isTabs = false;
//
//        if (actualTabs.size() + newTabs.size() + toUpdTabs.size() > 0) {
//            isTabs = true;
//        }
//
//        daoW.updateWorkshiftCash(actualWs);
//        daoW.updateWorkshiftClose(actualWs, isTabs);
//        daoW.updateWorkshiftElectronic(actualWs);
//        daoW.updateWorkshiftError(actualWs);
//        daoW.updateWorkshiftErrorReal(actualWs);
//        daoW.updateWorkshiftMountReal(actualWs);
//        daoW.updateWorkshiftState(actualWs);
//        daoW.updateWorkshiftTotal(actualWs);
//        if (newWs != null) {
//            daoW.saveWorkshift(newWs);
//            for (Table t : toEraseTabs) {
//
//                if (t.getOrder().size() > 0) {
//                    daoI.downActiveItemOrderTableAll(t);
//                }
//
//                if (t.getGifts().size() > 0) {
//                    daoI.downActiveItemGiftTableAll(t);
//                }
//
//                if (t.getPartialPayed().size() > 0) {
//                    daoI.downActiveItemPayedTableAll(t);
//                }
//
//                if (t.getPartialPayedND().size() > 0) {
//                    daoI.downActiveItemPayedNDTableAll(t);
//                }
//                daoT.downActiveTable(t);
//            }
//
//            if (toUpdTabs.size() > 0) {
//                for (int i = 0; i < toUpdTabs.size(); i++) {
//                    Table t = toUpdTabs.get(i);
//                    daoI.downActiveItemOrderTableAll(t);
//                    daoI.downActiveItemGiftTableAll(t);
//                    daoI.downActiveItemPayedTableAll(t);
//                    daoI.downActiveItemPayedNDTableAll(t);
//                    daoT.updateComments(t);
//                    daoT.updateTableTotal(t);
//                    if (t.getOrder().size() > 0) {
//                        for (Itemcard ic : t.getOrder()) {
//                            daoI.upActiveItemOrderTable(ic, t);
//                        }
//                    }
//
//                    if (t.getGifts().size() > 0) {
//                        for (Itemcard ic : t.getGifts()) {
//                            daoI.upActiveItemGiftTable(t, ic);
//                        }
//                    }
//
//                    daoT.updateTableOpen(t);
//                    daoT.updateToPay(t);
//                }
//            }
//
//            for (Table t : newTabs) {
//                st.saveTableCompleteChangeWs(t);
//            }
//        }
//        dispose();
//    }

//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
//Panel BARR-DELI---------------------------------------------------------------
    // BARR--------------------------------------
    // BARR--------------------------------------
    // DELIVERY----------------------------------
    // DELIVERY----------------------------------
//    void getDeliOrder(Delivery dOrder) throws Exception {
//        deliOrderAux = dOrder;
//        daoD.saveDelivery(deliOrderAux);
//        int num = deliButtons.size() + 1;
//        JButtonDelivery newJBD = new JButtonDelivery(num);
//        Table newTable = new Table(newJBD.getNum(), newJBD.getPos(), user);
//        newJBD.setTable(newTable);
//        deliButtons.add(0, newJBD);
//
//        JButtonDeliverySee butSee = new JButtonDeliverySee(num, dOrder);
//        deliButtonsSees.add(0, butSee);
//
//        panelDeliBut.repaint();
//
//        utiliGrafSal.panelDeliContainerSetter(sal);
//        deliButUpdater();
//        utiliGrafSal.resetTableValues(sal);
//    }

//    void setDeliOrder(Delivery dOrder) throws Exception {
//        deliOrderAux = dOrder;
//        daoD.updateDelivery(deliOrderAux);
//        deliButUpdater();
//        utiliGrafSal.resetTableValues(sal);
//    }

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
            utiliGrafSal.resetTableValues(sal);
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
                utiliGrafSal.jButExtSetter(sal);
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableAux = jbdAux.getTable();
            utiliGrafSal.tableFullerProp(sal);
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
            utiliGrafSal.resetTableValues(sal);
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
                utiliGrafSal.jButExtSetter(sal);
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableAux = jbdAux.getTable();
            utiliGrafSal.tableFullerProp(sal);
        
        }

    }

//--------------------------------------------------FUNCIONES-------------------------------------------------------
//--------------------------------------------------GENERALES-------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
    public void enableSalon() {
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
   
//------------------------------------------------------------------------------------------------------------------
    
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

    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    public Table getTableAux() {
        return tableAux;
    }

    public void setTableAux(Table table) {
        this.tableAux = table;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Itemcard> getItemsTableAux() {
        return itemsTableAux;
    }

    public void setItemsTableAux(ArrayList<Itemcard> itemsTableAux) {
        this.itemsTableAux = itemsTableAux;
    }

    public ArrayList<String> getConfigSalon() {
        return configSalon;
    }

    public void setConfigSalon(ArrayList<String> configSalon) {
        this.configSalon = configSalon;
    }

    public int getAnchoPane() {
        return anchoPane;
    }

    public void setAnchoPane(int anchoPane) {
        this.anchoPane = anchoPane;
    }

    public int getAlturaPane() {
        return alturaPane;
    }

    public void setAlturaPane(int alturaPane) {
        this.alturaPane = alturaPane;
    }

    public int getTotalTable() {
        return totalTable;
    }

    public void setTotalTable(int totalTable) {
        this.totalTable = totalTable;
    }

    public int getNumBut() {
        return numBut;
    }

    public void setNumBut(int numBut) {
        this.numBut = numBut;
    }

    public int getRowsButtons() {
        return rowsButtons;
    }

    public void setRowsButtons(int rowsButtons) {
        this.rowsButtons = rowsButtons;
    }

    public int getColButtons() {
        return colButtons;
    }

    public void setColButtons(int colButtons) {
        this.colButtons = colButtons;
    }

    public int getFontSizeTable() {
        return fontSizeTable;
    }

    public void setFontSizeTable(int fontSizeTable) {
        this.fontSizeTable = fontSizeTable;
    }

    public boolean isTabsBut() {
        return tabsBut;
    }

    public void setTabsBut(boolean tabsBut) {
        this.tabsBut = tabsBut;
    }

    public int getF1() {
        return f1;
    }

    public void setF1(int f1) {
        this.f1 = f1;
    }

    public Font getFont1() {
        return font1;
    }

    public void setFont1(Font font1) {
        this.font1 = font1;
    }

    public int getF2() {
        return f2;
    }

    public void setF2(int f2) {
        this.f2 = f2;
    }

    public Font getFont2() {
        return font2;
    }

    public void setFont2(Font font2) {
        this.font2 = font2;
    }

    public int getF3() {
        return f3;
    }

    public void setF3(int f3) {
        this.f3 = f3;
    }

    public Font getFont3() {
        return font3;
    }

    public void setFont3(Font font3) {
        this.font3 = font3;
    }

    public JScrollPane getScrPaneBarr() {
        return scrPaneBarr;
    }

    public void setScrPaneBarr(JScrollPane scrPaneBarr) {
        this.scrPaneBarr = scrPaneBarr;
    }

    public JPanel getPanelBarrBut() {
        return panelBarrBut;
    }

    public void setPanelBarrBut(JPanel panelBarrBut) {
        this.panelBarrBut = panelBarrBut;
    }

    public JPanel getPanelDeliBut() {
        return panelDeliBut;
    }

    public void setPanelDeliBut(JPanel panelDeliBut) {
        this.panelDeliBut = panelDeliBut;
    }

    public JPanel getPanelDeliContainer() {
        return panelDeliContainer;
    }

    public void setPanelDeliContainer(JPanel panelDeliContainer) {
        this.panelDeliContainer = panelDeliContainer;
    }

    public ArrayList<Integer> getTableNum() {
        return tableNum;
    }

    public void setTableNum(ArrayList<Integer> tableNum) {
        this.tableNum = tableNum;
    }

    public ArrayList<String> getTablePan() {
        return tablePan;
    }

    public void setTablePan(ArrayList<String> tablePan) {
        this.tablePan = tablePan;
    }

    public ArrayList<String> getTablePanCh() {
        return tablePanCh;
    }

    public void setTablePanCh(ArrayList<String> tablePanCh) {
        this.tablePanCh = tablePanCh;
    }

    public ArrayList<User> getWaiters() {
        return waiters;
    }

    public void setWaiters(ArrayList<User> waiters) {
        this.waiters = waiters;
    }

    public ArrayList<Itemcard> getItemsDB() {
        return itemsDB;
    }

    public void setItemsDB(ArrayList<Itemcard> itemsDB) {
        this.itemsDB = itemsDB;
    }

    public ArrayList<Itemcard> getItemsGift() {
        return itemsGift;
    }

    public void setItemsGift(ArrayList<Itemcard> itemsGift) {
        this.itemsGift = itemsGift;
    }

    public ArrayList<Itemcard> getItemsPartialPaid() {
        return itemsPartialPaid;
    }

    public void setItemsPartialPaid(ArrayList<Itemcard> itemsPartialPaid) {
        this.itemsPartialPaid = itemsPartialPaid;
    }

    public ArrayList<Itemcard> getItemsPartialPaidNoDiscount() {
        return itemsPartialPaidNoDiscount;
    }

    public void setItemsPartialPaidNoDiscount(ArrayList<Itemcard> itemsPartialPaidNoDiscount) {
        this.itemsPartialPaidNoDiscount = itemsPartialPaidNoDiscount;
    }

    public ArrayList<Itemcard> getItemsError() {
        return itemsError;
    }

    public void setItemsError(ArrayList<Itemcard> itemsError) {
        this.itemsError = itemsError;
    }

    public ArrayList<ItemMonitor> getItemsMntr() {
        return itemsMntr;
    }

    public void setItemsMntr(ArrayList<ItemMonitor> itemsMntr) {
        this.itemsMntr = itemsMntr;
    }

    public User getWaiterAux() {
        return waiterAux;
    }

    public void setWaiterAux(User waiterAux) {
        this.waiterAux = waiterAux;
    }

    public Workshift getWorkshiftNow() {
        return workshiftNow;
    }

    public void setWorkshiftNow(Workshift workshiftNow) {
        this.workshiftNow = workshiftNow;
    }

    public Delivery getDeliOrderAux() {
        return deliOrderAux;
    }

    public void setDeliOrderAux(Delivery deliOrderAux) {
        this.deliOrderAux = deliOrderAux;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPriceCorrection() {
        return priceCorrection;
    }

    public void setPriceCorrection(double priceCorrection) {
        this.priceCorrection = priceCorrection;
    }

    public double getAmoutnCash() {
        return amoutnCash;
    }

    public void setAmoutnCash(double amoutnCash) {
        this.amoutnCash = amoutnCash;
    }

    public double getAmountElectronic() {
        return amountElectronic;
    }

    public void setAmountElectronic(double amountElectronic) {
        this.amountElectronic = amountElectronic;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public ArrayList<JPanel> getPanelsPane() {
        return panelsPane;
    }

    public void setPanelsPane(ArrayList<JPanel> panelsPane) {
        this.panelsPane = panelsPane;
    }

    public ArrayList<JButtonTable> getTableButtons() {
        return tableButtons;
    }

    public void setTableButtons(ArrayList<JButtonTable> tableButtons) {
        this.tableButtons = tableButtons;
    }

    public ArrayList<JButtonBarr> getBarrButtons() {
        return barrButtons;
    }

    public void setBarrButtons(ArrayList<JButtonBarr> barrButtons) {
        this.barrButtons = barrButtons;
    }

    public ArrayList<JButtonDelivery> getDeliButtons() {
        return deliButtons;
    }

    public void setDeliButtons(ArrayList<JButtonDelivery> deliButtons) {
        this.deliButtons = deliButtons;
    }

    public ArrayList<JButtonDeliverySee> getDeliButtonsSees() {
        return deliButtonsSees;
    }

    public void setDeliButtonsSees(ArrayList<JButtonDeliverySee> deliButtonsSees) {
        this.deliButtonsSees = deliButtonsSees;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public boolean isLoopBreaker() {
        return loopBreaker;
    }

    public void setLoopBreaker(boolean loopBreaker) {
        this.loopBreaker = loopBreaker;
    }

    public JButton getButBarrDeli() {
        return butBarrDeli;
    }

    public void setButBarrDeli(JButton butBarrDeli) {
        this.butBarrDeli = butBarrDeli;
    }

    public JPanel getPanelBDButtons() {
        return panelBDButtons;
    }

    public void setPanelBDButtons(JPanel panelBDButtons) {
        this.panelBDButtons = panelBDButtons;
    }

    public JButtonBarr getJbbAux() {
        return jbbAux;
    }

    public void setJbbAux(JButtonBarr jbbAux) {
        this.jbbAux = jbbAux;
    }

    public JButtonDelivery getJbdAux() {
        return jbdAux;
    }

    public void setJbdAux(JButtonDelivery jbdAux) {
        this.jbdAux = jbdAux;
    }

    public JButtonDeliverySee getJbdSAux() {
        return jbdSAux;
    }

    public void setJbdSAux(JButtonDeliverySee jbdSAux) {
        this.jbdSAux = jbdSAux;
    }

    public JPanel getPanelA() {
        return panelA;
    }

    public void setPanelA(JPanel panelA) {
        this.panelA = panelA;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public JButtonTable getJbtAux() {
        return jbtAux;
    }

    public void setJbtAux(JButtonTable jbtAux) {
        this.jbtAux = jbtAux;
    }

    public int getRowsItems() {
        return rowsItems;
    }

    public void setRowsItems(int rowsItems) {
        this.rowsItems = rowsItems;
    }

    public int getColItems() {
        return colItems;
    }

    public void setColItems(int colItems) {
        this.colItems = colItems;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public boolean isIndiBool() {
        return indiBool;
    }

    public void setIndiBool(boolean indiBool) {
        this.indiBool = indiBool;
    }

    public String[] getColNames() {
        return colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public JComboBox getComboItems() {
        return comboItems;
    }

    public void setComboItems(JComboBox comboItems) {
        this.comboItems = comboItems;
    }

    public JCheckBox getCheckBoxIndic() {
        return checkBoxIndic;
    }

    public void setCheckBoxIndic(JCheckBox checkBoxIndic) {
        this.checkBoxIndic = checkBoxIndic;
    }

    public JSpinner getSpinnerUnitsItem() {
        return spinnerUnitsItem;
    }

    public void setSpinnerUnitsItem(JSpinner spinnerUnitsItem) {
        this.spinnerUnitsItem = spinnerUnitsItem;
    }

    public JScrollPane getScrollPaneItems() {
        return scrollPaneItems;
    }

    public void setScrollPaneItems(JScrollPane scrollPaneItems) {
        this.scrollPaneItems = scrollPaneItems;
    }

    public JTable getJTableItems() {
        return jTableItems;
    }

    public void setJTableItems(JTable jTableItems) {
        this.jTableItems = jTableItems;
    }

    public JButton getButCloseTable() {
        return butCloseTable;
    }

    public void setButCloseTable(JButton butCloseTable) {
        this.butCloseTable = butCloseTable;
    }

    public JPanel getPanelCuenta() {
        return panelCuenta;
    }

    public void setPanelCuenta(JPanel panelCuenta) {
        this.panelCuenta = panelCuenta;
    }

    public JPanel getPanelFlotante() {
        return panelFlotante;
    }

    public void setPanelFlotante(JPanel panelFlotante) {
        this.panelFlotante = panelFlotante;
    }

    public JLabel getLabelTotalParcial() {
        return labelTotalParcial;
    }

    public void setLabelTotalParcial(JLabel labelTotalParcial) {
        this.labelTotalParcial = labelTotalParcial;
    }

    public JLabel getLabelOrder() {
        return labelOrder;
    }

    public void setLabelOrder(JLabel labelOrder) {
        this.labelOrder = labelOrder;
    }

    public JLabel getLabelCuenta() {
        return labelCuenta;
    }

    public void setLabelCuenta(JLabel labelCuenta) {
        this.labelCuenta = labelCuenta;
    }

    public JLabel getLabelWaiter() {
        return labelWaiter;
    }

    public void setLabelWaiter(JLabel labelWaiter) {
        this.labelWaiter = labelWaiter;
    }

    public JLabel getLabelTip() {
        return labelTip;
    }

    public void setLabelTip(JLabel labelTip) {
        this.labelTip = labelTip;
    }

    public JLabel getLabelTotal() {
        return labelTotal;
    }

    public void setLabelTotal(JLabel labelTotal) {
        this.labelTotal = labelTotal;
    }

    public JLabel getLabelPartialPay() {
        return labelPartialPay;
    }

    public void setLabelPartialPay(JLabel labelPartialPay) {
        this.labelPartialPay = labelPartialPay;
    }

    public JButton getButInitWorkshift() {
        return butInitWorkshift;
    }

    public void setButInitWorkshift(JButton butInitWorkshift) {
        this.butInitWorkshift = butInitWorkshift;
    }

    public JButton getButPartialPay() {
        return butPartialPay;
    }

    public void setButPartialPay(JButton butPartialPay) {
        this.butPartialPay = butPartialPay;
    }

    public JPanel getPanelPartial() {
        return panelPartial;
    }

    public void setPanelPartial(JPanel panelPartial) {
        this.panelPartial = panelPartial;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getWUnit() {
        return wUnit;
    }

    public void setWUnit(int wUnit) {
        this.wUnit = wUnit;
    }

    public int getHUnit() {
        return hUnit;
    }

    public void setHUnit(int wUnit) {
        this.hUnit = wUnit;
    }
}
