package salonmanager;

import salonmanager.entidades.graphics.PanelPpal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import org.knowm.xchart.*;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.persistencia.DAOConfig;
import salonmanager.utilidades.UtilidadesGraficasStatics;

public class StaticsManager extends FrameFull {

    DAOConfig daoC = new DAOConfig();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Color bluLg = new Color(194, 242, 206);

    ArrayList<Table> tabs = new ArrayList<>();
    ArrayList<ItemSale> iSales = new ArrayList<>();
    ArrayList<Workshift> workshifts = new ArrayList<>();
    ArrayList<String> categories = null;
    Manager manager = null;
    PieChart chartOrder = null;
    XYChart chartCurveSell = null;
    CategoryChart chartItemsSelled = null;
    PieChart chartCategoryPie = null;

    String period = "";
    double numTabs = 0;
    double numBar = 0;
    double numDeli = 0;
    HashMap<String, Double> countWSells = null;
    HashMap<String, Integer> countWWs = null;
    HashMap<String, Double> countCat = null;
    JPanel panelChartByOrder = new JPanel();
    JPanel panelChartSellCurve = new JPanel();
    JPanel panelChartCategory = new JPanel();
    JPanel panelStatsBySell = new JPanel();

    JPanel panelOrder = null;
    JPanel panelSellCurve = null;
    JPanel panelItemsCategory = null;
    JPanel panelWaiterStatics = null;

    
    JTextField fieldTotal = null;
    JTextField fieldErrorTab = null;
    JTextField fieldErrorWs = null;
    JTextField fieldTotalReal = null;

    JTextField fieldPromTab = null;
    JTextField fieldTimeTab = null;
    JLabel labelPeriod = null;

    JLabel labelCategory0 = null;
    JLabel labelCategory1 = null;
    JLabel labelCategory2 = null;
    JLabel labelCategory3 = null;
    JLabel labelCategory4 = null;
    JLabel labelCategory5 = null;

    JLabel labelWaiter1 = null;
    JLabel labelWaiter2 = null;
    JLabel labelWaiter3 = null;
    JLabel labelWaiter4 = null;
    JLabel labelWaiter5 = null;
    JLabel labelWaiter6 = null;
    JLabel labelWaiter7 = null;
    JLabel labelWaiter8 = null;
    JLabel labelWaiter9 = null;
    JLabel labelWaiter10 = null;

    public StaticsManager(Manager man) throws Exception {
        setTitle("Administrador");
        manager = man;
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();

        ConfigGeneral cfgGen = daoC.askConfigGeneral();
        categories = cfgGen.getTableItemCategories();

        JLabel labelStatics = utiliGraf.labelTitleBackerA3W("Estadísticas");
        labelStatics.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelStatics);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

//PANEL LATERAL IZQ-------------------------------------------------------------
//PANEL LATERAL IZQ-------------------------------------------------------------        
//PANEL LATERAL IZQ-------------------------------------------------------------        
//PANEL LATERAL IZQ-------------------------------------------------------------
        panelStatsBySell = utiliGrafStats.panelLateralBacker(this);
        panelStatsBySell.setLayout(null);
        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 39);
        panelStatsBySell.setBackground(bluLg);
        panelPpal.add(panelStatsBySell);
        
//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
        panelOrder = utiliGrafStats.panelOrderBacker(this);
        panelOrder.setLayout(null);
        panelOrder.setBounds(anchoUnit * 21, altoUnit * 10, anchoUnit * 32, altoUnit * 37);
        panelOrder.setBackground(bluLg);
        panelOrder.setVisible(false);
        panelPpal.add(panelOrder);

//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
        panelSellCurve = utiliGrafStats.panelSellCurveBacker(this);
        panelSellCurve.setLayout(null);
        panelSellCurve.setBounds(anchoUnit * 54, altoUnit * 10, anchoUnit * 49, altoUnit * 37);
        panelSellCurve.setBackground(bluLg);
        panelSellCurve.setVisible(false);
        panelPpal.add(panelSellCurve);

//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
        panelItemsCategory = utiliGrafStats.panelItemsStaticsBacker(this);
        panelItemsCategory.setLayout(null);
        panelItemsCategory.setBounds(anchoUnit * 21, altoUnit * 49, anchoUnit * 40, altoUnit * 45);
        panelItemsCategory.setBackground(bluLg);
        panelItemsCategory.setVisible(false);
        panelPpal.add(panelItemsCategory);

//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
        panelWaiterStatics = utiliGrafStats.panelWaiterStaticsBacker(this);
        panelWaiterStatics.setBackground(bluLg);
        panelWaiterStatics.setLayout(null);
        panelWaiterStatics.setBounds(anchoUnit * 62, altoUnit * 49, anchoUnit * 41, altoUnit * 45);
        panelWaiterStatics.setVisible(false);
        panelPpal.add(panelWaiterStatics);

//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
    }

//FUNCTIONS---------------------------------------------------------------------        
//FUNCTIONS---------------------------------------------------------------------        
//FUNCTIONS---------------------------------------------------------------------        
//FUNCTIONS---------------------------------------------------------------------        
//FUNCTIONS---------------------------------------------------------------------        
//FUNCTIONS---------------------------------------------------------------------        

    
    //Paneles
    //Paneles

    public ArrayList<Table> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<Table> tabs) {
        this.tabs = tabs;
    }

    public ArrayList<ItemSale> getISales() {
        return iSales;
    }

    public void setISales(ArrayList<ItemSale> iSales) {
        this.iSales = iSales;
    }

    public ArrayList<Workshift> getWorkshifts() {
        return workshifts;
    }

    public void setWorkshifts(ArrayList<Workshift> workshifts) {
        this.workshifts = workshifts;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public PieChart getChartOrder() {
        return chartOrder;
    }

    public void setChartOrder(PieChart chartOrder) {
        this.chartOrder = chartOrder;
    }

    public XYChart getChartCurveSell() {
        return chartCurveSell;
    }

    public void setChartCurveSell(XYChart chartCurveSell) {
        this.chartCurveSell = chartCurveSell;
    }

    public CategoryChart getChartItemsSelled() {
        return chartItemsSelled;
    }

    public void setChartItemsSelled(CategoryChart chartItemsSelled) {
        this.chartItemsSelled = chartItemsSelled;
    }

    public PieChart getChartCategoryPie() {
        return chartCategoryPie;
    }

    public void setChartCategoryPie(PieChart chartCategoryPie) {
        this.chartCategoryPie = chartCategoryPie;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getNumTabs() {
        return numTabs;
    }

    public void setNumTabs(double numTabs) {
        this.numTabs = numTabs;
    }

    public double getNumBar() {
        return numBar;
    }

    public void setNumBar(double numBar) {
        this.numBar = numBar;
    }

    public double getNumDeli() {
        return numDeli;
    }

    public void setNumDeli(double numDeli) {
        this.numDeli = numDeli;
    }

    public HashMap<String, Double> getCountWSells() {
        return countWSells;
    }

    public void setCountWSells(HashMap<String, Double> countWSells) {
        this.countWSells = countWSells;
    }

    public HashMap<String, Integer> getCountWWs() {
        return countWWs;
    }

    public void setCountWWs(HashMap<String, Integer> countWWs) {
        this.countWWs = countWWs;
    }

    public HashMap<String, Double> getCountCat() {
        return countCat;
    }

    public void setCountCat(HashMap<String, Double> countCat) {
        this.countCat = countCat;
    }

    public JPanel getPanelChartByOrder() {
        return panelChartByOrder;
    }

    public void setPanelChartByOrder(JPanel panelChartByOrder) {
        this.panelChartByOrder = panelChartByOrder;
    }

    public JPanel getPanelChartSellCurve() {
        return panelChartSellCurve;
    }

    public void setPanelChartSellCurve(JPanel panelChartSellCurve) {
        this.panelChartSellCurve = panelChartSellCurve;
    }

    public JPanel getPanelChartCategory() {
        return panelChartCategory;
    }

    public void setPanelChartCategory(JPanel panelChartCategory) {
        this.panelChartCategory = panelChartCategory;
    }

    public JPanel getPanelStatsBySell() {
        return panelStatsBySell;
    }

    public void setPanelStatsBySell(JPanel panelStatsBySell) {
        this.panelStatsBySell = panelStatsBySell;
    }

    public JPanel getPanelOrder() {
        return panelOrder;
    }

    public void setPanelOrder(JPanel panelOrder) {
        this.panelOrder = panelOrder;
    }

    public JPanel getPanelSellCurve() {
        return panelSellCurve;
    }

    public void setPanelSellCurve(JPanel panelSellCurve) {
        this.panelSellCurve = panelSellCurve;
    }

    public JPanel getPanelItemsCategory() {
        return panelItemsCategory;
    }

    public void setPanelItemsCategory(JPanel panelItemsCategory) {
        this.panelItemsCategory = panelItemsCategory;
    }

    public JPanel getPanelWaiterStatics() {
        return panelWaiterStatics;
    }

    public void setPanelWaiterStatics(JPanel panelWaiterStatics) {
        this.panelWaiterStatics = panelWaiterStatics;
    }

    public JTextField getFieldTotal() {
        return fieldTotal;
    }

    public void setFieldTotal(JTextField fieldTotal) {
        this.fieldTotal = fieldTotal;
    }

    public JTextField getFieldErrorTab() {
        return fieldErrorTab;
    }

    public void setFieldErrorTab(JTextField fieldErrorTab) {
        this.fieldErrorTab = fieldErrorTab;
    }

    public JTextField getFieldErrorWs() {
        return fieldErrorWs;
    }

    public void setFieldErrorWs(JTextField fieldErrorWs) {
        this.fieldErrorWs = fieldErrorWs;
    }

    public JTextField getFieldTotalReal() {
        return fieldTotalReal;
    }

    public void setFieldTotalReal(JTextField fieldTotalReal) {
        this.fieldTotalReal = fieldTotalReal;
    }

    public JTextField getFieldPromTab() {
        return fieldPromTab;
    }

    public void setFieldPromTab(JTextField fieldPromTab) {
        this.fieldPromTab = fieldPromTab;
    }

    public JTextField getFieldTimeTab() {
        return fieldTimeTab;
    }

    public void setFieldTimeTab(JTextField fieldTimeTab) {
        this.fieldTimeTab = fieldTimeTab;
    }

    public JLabel getLabelPeriod() {
        return labelPeriod;
    }

    public void setLabelPeriod(JLabel labelPeriod) {
        this.labelPeriod = labelPeriod;
    }

    public JLabel getLabelCategory0() {
        return labelCategory0;
    }

    public void setLabelCategory0(JLabel labelCategory0) {
        this.labelCategory0 = labelCategory0;
    }

    public JLabel getLabelCategory1() {
        return labelCategory1;
    }

    public void setLabelCategory1(JLabel labelCategory1) {
        this.labelCategory1 = labelCategory1;
    }

    public JLabel getLabelCategory2() {
        return labelCategory2;
    }

    public void setLabelCategory2(JLabel labelCategory2) {
        this.labelCategory2 = labelCategory2;
    }

    public JLabel getLabelCategory3() {
        return labelCategory3;
    }

    public void setLabelCategory3(JLabel labelCategory3) {
        this.labelCategory3 = labelCategory3;
    }

    public JLabel getLabelCategory4() {
        return labelCategory4;
    }

    public void setLabelCategory4(JLabel labelCategory4) {
        this.labelCategory4 = labelCategory4;
    }

    public JLabel getLabelCategory5() {
        return labelCategory5;
    }

    public void setLabelCategory5(JLabel labelCategory5) {
        this.labelCategory5 = labelCategory5;
    }

    public JLabel getLabelWaiter1() {
        return labelWaiter1;
    }

    public void setLabelWaiter1(JLabel labelWaiter1) {
        this.labelWaiter1 = labelWaiter1;
    }

    public JLabel getLabelWaiter2() {
        return labelWaiter2;
    }

    public void setLabelWaiter2(JLabel labelWaiter2) {
        this.labelWaiter2 = labelWaiter2;
    }

    public JLabel getLabelWaiter3() {
        return labelWaiter3;
    }

    public void setLabelWaiter3(JLabel labelWaiter3) {
        this.labelWaiter3 = labelWaiter3;
    }

    public JLabel getLabelWaiter4() {
        return labelWaiter4;
    }

    public void setLabelWaiter4(JLabel labelWaiter4) {
        this.labelWaiter4 = labelWaiter4;
    }

    public JLabel getLabelWaiter5() {
        return labelWaiter5;
    }

    public void setLabelWaiter5(JLabel labelWaiter5) {
        this.labelWaiter5 = labelWaiter5;
    }

    public JLabel getLabelWaiter6() {
        return labelWaiter6;
    }

    public void setLabelWaiter6(JLabel labelWaiter6) {
        this.labelWaiter6 = labelWaiter6;
    }

    public JLabel getLabelWaiter7() {
        return labelWaiter7;
    }

    public void setLabelWaiter7(JLabel labelWaiter7) {
        this.labelWaiter7 = labelWaiter7;
    }

    public JLabel getLabelWaiter8() {
        return labelWaiter8;
    }

    public void setLabelWaiter8(JLabel labelWaiter8) {
        this.labelWaiter8 = labelWaiter8;
    }

    public JLabel getLabelWaiter9() {
        return labelWaiter9;
    }

    public void setLabelWaiter9(JLabel labelWaiter9) {
        this.labelWaiter9 = labelWaiter9;
    }

    public JLabel getLabelWaiter10() {
        return labelWaiter10;
    }

    public void setLabelWaiter10(JLabel labelWaiter10) {
        this.labelWaiter10 = labelWaiter10;
    }
    
    
    
    
}