package salonmanager;

import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesUpdate;
import org.knowm.xchart.*;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServiceStatics;
import salonmanager.utilidades.UtilidadesGraficasStatics;

public class StaticsManager extends FrameFull {

    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesUpdate utiliUpd = new UtilidadesUpdate();
    ServiceStatics sStats = new ServiceStatics();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color narUlgX = new Color(255, 255, 210);
    Color narLg = new Color(252, 203, 5);

    SalonManager sm = new SalonManager();
    private User userIn = null;
    ArrayList<Table> tabs = new ArrayList<>();
    ArrayList<ItemSale> iSales = new ArrayList<>();
    ArrayList<Workshift> workshifts = new ArrayList<>();
    ArrayList<String> captions = null;
    Manager manager = null;
    PieChart chartOrder = null;
    XYChart chartCurveSell = null;
    CategoryChart chartItemsSelled = null;
    PieChart chartCaptionPie = null;

    String period = "";
    double numTabs = 0;
    double numBar = 0;
    double numDeli = 0;
    HashMap<String, Double> countWSells = null;
    HashMap<String, Integer> countWWs = null;
    HashMap<String, Double> countCapt = null;
    JPanel panelChartByOrder = new JPanel();
    JPanel panelChartSellCurve = new JPanel();
    JPanel panelChartCaption = new JPanel();
    JPanel panelStatsBySell = new JPanel();

    JTextField fieldTotal = null;
    JTextField fieldPromTab = null;
    JTextField fieldTimeTab = null;
    JLabel labelPeriod = null;

    JLabel labelCaption0 = null;
    JLabel labelCaption1 = null;
    JLabel labelCaption2 = null;
    JLabel labelCaption3 = null;
    JLabel labelCaption4 = null;
    JLabel labelCaption5 = null;

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
        captions = cfgGen.getTableItemCaptions();

        JLabel labelStatics = utiliGraf.labelTitleBackerA3W("Estadísticas");
        labelStatics.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelStatics);

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
        JPanel panelOrder = utiliGrafStats.panelOrderBacker(this);
        panelOrder.setLayout(null);
        panelOrder.setBounds(anchoUnit * 21, altoUnit * 10, anchoUnit * 32, altoUnit * 37);
        panelOrder.setBackground(bluLg);
        panelPpal.add(panelOrder);

//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
        JPanel panelSellCurve = utiliGrafStats.panelSellCurveBacker(this);
        panelSellCurve.setLayout(null);
        panelSellCurve.setBounds(anchoUnit * 54, altoUnit * 10, anchoUnit * 49, altoUnit * 37);
        panelSellCurve.setBackground(bluLg);
        panelPpal.add(panelSellCurve);

//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
        JPanel panelItemsCaption = utiliGrafStats.panelItemsStaticsBacker(this);
        panelItemsCaption.setLayout(null);
        panelItemsCaption.setBounds(anchoUnit * 21, altoUnit * 49, anchoUnit * 40, altoUnit * 45);
        panelItemsCaption.setBackground(bluLg);
        panelPpal.add(panelItemsCaption);

//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
        JPanel panelWaiterStatics = utiliGrafStats.panelWaiterStaticsBacker(this);
        panelWaiterStatics.setBackground(bluLg);
        panelWaiterStatics.setLayout(null);
        panelWaiterStatics.setBounds(anchoUnit * 62, altoUnit * 49, anchoUnit * 41, altoUnit * 45);
        panelPpal.add(panelWaiterStatics);

//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
//EXTRAS------------------------------------------------------------------------        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
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
    private void updater(StaticsManager statsM) throws Exception {
        double tot = 0;
        double promTab = 0;
        long totTime = 0;
        int tabInt = 0;
        countCapt = new HashMap<>();
        countWSells = new HashMap<String, Double>();
        countWWs = new HashMap<String, Integer>();
        ArrayList<String> waiterIdsDB = daoU.listarUserByRol("MOZO");
        ArrayList<String> waiterIds = new ArrayList<>();
        ArrayList<Integer> idSales = new ArrayList<>();
        ArrayList<Integer> cantSales = new ArrayList<>();
        ArrayList<HashSet<Integer>> listHS = new ArrayList<>();
        for (Table tab : tabs) {
            tot += tab.getTotal();
            if (tab.getPos().equals("barra")) {
                numBar += tab.getTotal();
            } else if (tab.getPos().equals("delivery")) {
                numDeli += tab.getTotal();
            } else {
                numTabs += tab.getTotal();
            }

            long time1 = tab.getOpenTime().getTime();
            long time2 = tab.getCloseTime().getTime();

            // Calcular la diferencia en milisegundos
            long differenceInMillis = time2 - time1;
            if (differenceInMillis < 28800000 && !tab.getPos().equals("barra") && !tab.getPos().equals("delivery")) {
                totTime += differenceInMillis;
                tabInt += 1;
            }
        }

        for (int i = 0; i < captions.size(); i++) {
            countCapt.put(captions.get(i), 0.0);
        }

        for (ItemSale is : iSales) {
            for (Map.Entry<String, Double> entry : countCapt.entrySet()) {
                String key = entry.getKey();
                double newValue = 0;
                double value = entry.getValue();
                if (key.equals(is.getItemSaleCaption())) {
                    newValue = value + is.getItemSalePrice();
                    entry.setValue(newValue);
                }
            }

            for (int i = 0; i < waiterIdsDB.size(); i++) {
                String id1 = waiterIdsDB.get(i);
                String Id2 = is.getItemSaleWaiterId();
                if (id1.equals(Id2)) {
                    waiterIds.add(is.getItemSaleWaiterId());
                    HashSet<Integer> counterWs = new HashSet<Integer>();
                    listHS.add(counterWs);
                }
            }
        }

        ArrayList<String> ids = new ArrayList<>(waiterIds);
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            countWSells.put(id, 0.0);
            countWWs.put(id, 0);
        }

        for (ItemSale is : iSales) {
            for (Map.Entry<String, Double> entry : countWSells.entrySet()) {
                String key = entry.getKey();
                if (key.equals(is.getItemSaleWaiterId())) {
                    double value = entry.getValue();
                    double newValue = value + is.getItemSalePrice();
                    entry.setValue(newValue);
                }
            }

            int counter = 0;
            for (Map.Entry<String, Integer> entry : countWWs.entrySet()) {
                String key = entry.getKey();
                if (key.equals(is.getItemSaleWaiterId())) {
                    listHS.get(counter).add(is.getItemSaleWorkshiftId());
                    int newValue = listHS.get(counter).size();
                    entry.setValue(newValue);
                }
                counter += 1;
            }
        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String formattedTotal = df.format(tot);
        fieldTotal.setText("$" + formattedTotal);

        promTab = tot / tabs.size();
        String formattedPromTab = df.format(promTab);
        fieldPromTab.setText("$" + formattedPromTab);

        long timeTab = totTime / tabInt;
        LocalTime time = utili.toLongHAndM(timeTab);
        fieldTimeTab.setText(time.getHour() + " horas, " + time.getMinute() + " min.");

        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 84);

        //Sells bykind of order
        chartOrder = utiliGrafStats.chartOrderBacker(this);
        panelChartByOrder.removeAll();
        panelChartByOrder.add(new XChartPanel<>(chartOrder));
        panelChartByOrder.revalidate();
        panelChartByOrder.repaint();

//        Curve by mount volume
        chartCurveSell = utiliGrafStats.chartCurveBacker(this);
        panelChartSellCurve.removeAll();
        panelChartSellCurve.add(new XChartPanel<>(chartCurveSell));
        panelChartSellCurve.revalidate();
        panelChartSellCurve.repaint();

        //Volume by item Caption
        chartCaptionPie = utiliGrafStats.chartCaptionBacker(countCapt, this);
        panelChartCaption.removeAll();
        panelChartCaption.add(new XChartPanel<>(chartCaptionPie));
        panelChartCaption.revalidate();
        panelChartCaption.repaint();

        //TOP Waiters Sell
        ArrayList<String> waitersSell1 = new ArrayList<>(countWSells.keySet());
        ArrayList<String> waitersSell2 = new ArrayList<>();
        ArrayList<Double> amounts1 = new ArrayList<>(countWSells.values());
        ArrayList<Double> amounts2 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (i < waitersSell1.size()) {
                String name = daoU.getUserNameById(waitersSell1.get(i));
                waitersSell2.add(name);
                amounts2.add(amounts1.get(i));
            } else {
                waitersSell2.add("--");
                amounts2.add(0.0);
            }
        }

        //TOP Waiters Workshift
        ArrayList<String> waitersWs1 = new ArrayList<>(countWWs.keySet());
        ArrayList<String> waitersWs2 = new ArrayList<>();
        ArrayList<Integer> wss1 = new ArrayList<>(countWWs.values());
        ArrayList<Integer> wss2 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (i < waitersWs1.size()) {
                String name = daoU.getUserNameById(waitersWs1.get(i));
                waitersWs2.add(name);
                wss2.add(wss1.get(i));
            } else {
                waitersWs2.add("--");
                wss2.add(0);
            }
        }

        labelWaiter1.setText("1- " + waitersSell2.get(0) + ": $" + amounts2.get(0));
        labelWaiter2.setText("2- " + waitersSell2.get(1) + ": $" + amounts2.get(1));
        labelWaiter3.setText("3- " + waitersSell2.get(2) + ": $" + amounts2.get(2));
        labelWaiter4.setText("4- " + waitersSell2.get(3) + ": $" + amounts2.get(3));
        labelWaiter5.setText("5- " + waitersSell2.get(4) + ": $" + amounts2.get(4));

        labelWaiter6.setText("1- " + waitersWs2.get(0) + ": " + wss2.get(0) + " turnos.");
        labelWaiter7.setText("2- " + waitersWs2.get(1) + ": " + wss2.get(1) + " turnos.");
        labelWaiter8.setText("3- " + waitersWs2.get(2) + ": " + wss2.get(2) + " turnos.");
        labelWaiter9.setText("4- " + waitersWs2.get(3) + ": " + wss2.get(3) + " turnos.");
        labelWaiter10.setText("5- " + waitersWs2.get(4) + ": " + wss2.get(4) + " turnos.");
    }


    
    //Paneles
    //Paneles
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

    public JPanel getPanelItemsCaption() {
        return panelChartCaption;
    }

    public void setPanelItemsCaption(JPanel panelItemsCaption) {
        this.panelChartCaption = panelItemsCaption;
    }

    public JPanel getPanelStatsBySell() {
        return panelStatsBySell;
    }

    public void setPanelStatsBySell(JPanel panelStatsBySell) {
        this.panelStatsBySell = panelStatsBySell;
    }

    public JPanel getPanelChartCaption() {
        return panelChartCaption;
    }

    public void setPanelChartCaption(JPanel panelChartCaption) {
        this.panelChartCaption = panelChartCaption;
    }
    
    //Charts
    //Charts
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

    public PieChart getChartCaptionPie() {
        return chartCaptionPie;
    }

    public void setChartCaptionPie(PieChart chartCaptionPie) {
        this.chartCaptionPie = chartCaptionPie;
    }
    
    public void setTabs(ArrayList tables) {
        tabs = tables;
    }

    public ArrayList<Table> getTabs() {
        return tabs;
    }

    public void setItemsSale(ArrayList iSs) {
        iSales = iSs;
    }

    public ArrayList<ItemSale> getItemsales() {
        return iSales;
    }

    public void setWorkshifts(ArrayList<Workshift> workshifts) throws Exception {
        this.workshifts = workshifts;
        updater(this);
    }

    public ArrayList<Workshift> getWorkshift() {
        return workshifts;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<ItemSale> getiSales() {
        return iSales;
    }

    public void setiSales(ArrayList<ItemSale> iSales) {
        this.iSales = iSales;
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

    public JLabel getLabelCaption0() {
        return labelCaption0;
    }

    public void setLabelCaption0(JLabel labelCaption0) {
        this.labelCaption0 = labelCaption0;
    }

    public JLabel getLabelCaption1() {
        return labelCaption1;
    }

    public void setLabelCaption1(JLabel labelCaption1) {
        this.labelCaption1 = labelCaption1;
    }

    public JLabel getLabelCaption2() {
        return labelCaption2;
    }

    public void setLabelCaption2(JLabel labelCaption2) {
        this.labelCaption2 = labelCaption2;
    }

    public JLabel getLabelCaption3() {
        return labelCaption3;
    }

    public void setLabelCaption3(JLabel labelCaption3) {
        this.labelCaption3 = labelCaption3;
    }

    public JLabel getLabelCaption4() {
        return labelCaption4;
    }

    public void setLabelCaption4(JLabel labelCaption4) {
        this.labelCaption4 = labelCaption4;
    }

    public JLabel getLabelCaption5() {
        return labelCaption5;
    }

    public void setLabelCaption5(JLabel labelCaption5) {
        this.labelCaption5 = labelCaption5;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public JTextField getFieldTotal() {
        return fieldTotal;
    }

    public void setFieldTotal(JTextField fieldTotal) {
        this.fieldTotal = fieldTotal;
    }

    public HashMap<String, Double> getCountCapt() {
        return countCapt;
    }

    public void setCountCapt(HashMap<String, Double> countCapt) {
        this.countCapt = countCapt;
    }

    public ArrayList<String> getCaptions() {
        return captions;
    }

    public void setCaptions(ArrayList<String> captions) {
        this.captions = captions;
    }
    
    
    
}
