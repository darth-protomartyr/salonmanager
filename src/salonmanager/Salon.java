package salonmanager;

import salonmanager.entidades.graphics.JButtonDeliverySee;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
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
import salonmanager.entidades.config.ConfigGeneral;
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
    JPanel panelCount = new JPanel();
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

        ConfigGeneral cfgGen = daoC.askConfigGeneral();
        totalTable = cfgGen.getTotalTable();
        tableNum = cfgGen.getTableNum();
        tablePan = cfgGen.getTablePan();
        tablePanCh = cfgGen.getTablePanCh();

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
//Panel SelItem---------------------------------------------------------
//Panel Count---------------------------------------------------------
        
        panelCount = utiliGrafSal.returnPanelCount(sal);
        panelTable.add(panelCount);

//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }  
  

    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    //GETTER && SETTER----------------------------------------------
    public ArrayList<ItemMonitor> getItemsMnr() {
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

    public void getUser(User user) {
        this.user = user;
    }    
    
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

    public JPanel getPanelCount() {
        return panelCount;
    }

    public void setPanelCount(JPanel panelCount) {
        this.panelCount = panelCount;
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
