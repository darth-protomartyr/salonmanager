package salonmanager;

import java.awt.BorderLayout;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.Font;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import salonmanager.utilidades.UtilidadesGraficasStatics;

public class StaticsManager extends FrameFull {

    DAOConfig daoC = new DAOConfig();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesUpdate utiliUpd = new UtilidadesUpdate();
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
    ArrayList<Table> tabs = new ArrayList<Table>();
    ArrayList<ItemSale> iSales = new ArrayList<ItemSale>();
    ArrayList<Workshift> workshifts = new ArrayList<Workshift>();
    ArrayList<String> captions = null;
    Manager manager = null;
    PieChart chartOrder = null;
    XYChart chartCurveSell = null;
    CategoryChart chartItemsSelled = null;
    PieChart chartCaptionPie = null;

    double numTabs = 0;
    double numBar = 0;
    double numDeli = 0;
//    HashMap<Integer, Integer> countItems = null;
    HashMap<String, Double> countCapt = null;
    JPanel chartPanelByOrder = new JPanel();
    JPanel chartPanelSellCurve = new JPanel();
    JPanel chartPanelItemsCaption = new JPanel();

    JTextField fieldTotal = null;
    JTextField fieldPromTab = null;
    JTextField fieldTimeTab = null;
    JLabel labelPeriod = null;
    JPanel panelStatsBySell = new JPanel();

    JLabel labelCaption0 = null;
    JLabel labelCaption1 = null;
    JLabel labelCaption2 = null;
    JLabel labelCaption3 = null;
    JLabel labelCaption4 = null;
    JLabel labelCaption5 = null;

    
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
        panelStatsBySell.setLayout(null);
        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 14);
        panelStatsBySell.setBackground(bluLg);
        panelPpal.add(panelStatsBySell);

        JLabel labelStats = utiliGraf.labelTitleBacker1("Período de consulta:");
        labelStats.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelStats);

        JButtonMetalBlu butStatsPeriod = utiliGraf.button1("ESTABLECER", anchoUnit * 2, altoUnit * 5, anchoUnit * 13);
        butStatsPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                openSelectorPeriod();
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butStatsPeriod);

        labelPeriod = utiliGraf.labelTitleBacker2("");
        labelPeriod.setBounds(anchoUnit * 2, altoUnit * 12, anchoUnit * 16, altoUnit * 6);
        panelStatsBySell.add(labelPeriod);

        JButtonMetalBlu butViewAllItemSales = utiliGraf.button2("Ver Ventas", anchoUnit * 2, altoUnit * 19, anchoUnit * 13);
        butViewAllItemSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openItemSaleViewer();
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllItemSales);

        JButtonMetalBlu butViewAllTabs = utiliGraf.button2("Ver Mesas", anchoUnit * 2, altoUnit * 24, anchoUnit * 13);
        butViewAllTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openTabViewer();
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllTabs);

        JLabel labelTotalTitle = utiliGraf.labelTitleBacker2("Facturación período:");
        labelTotalTitle.setBounds(anchoUnit * 2, altoUnit * 29, anchoUnit * 13, altoUnit * 3);
        panelStatsBySell.add(labelTotalTitle);

        fieldTotal = new JTextField();
        fieldTotal.setBackground(bluLg);
        Font f = new Font("Arial", Font.BOLD, 35);
        fieldTotal.setBorder(null);
        fieldTotal.setFont(f);
        fieldTotal.setBounds(anchoUnit * 0, altoUnit * 32, anchoUnit * 17, altoUnit * 5);
        fieldTotal.setHorizontalAlignment(SwingConstants.CENTER);
        panelStatsBySell.add(fieldTotal);

        JLabel labelPromTab = utiliGraf.labelTitleBacker2("Prom. gasto por orden:");
        labelPromTab.setBounds(anchoUnit * 2, altoUnit * 38, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelPromTab);

        fieldPromTab = new JTextField();
        fieldPromTab.setBackground(bluLg);
        fieldPromTab.setBorder(null);
        fieldPromTab.setFont(f);
        fieldPromTab.setBounds(anchoUnit * 0, altoUnit * 41, anchoUnit * 17, altoUnit * 5);
        fieldPromTab.setHorizontalAlignment(SwingConstants.CENTER);
        panelStatsBySell.add(fieldPromTab);

        JLabel labelTimeTab = utiliGraf.labelTitleBacker2("Prom. tiempo por mesa:");
        labelTimeTab.setBounds(anchoUnit * 2, altoUnit * 47, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelTimeTab);

        fieldTimeTab = new JTextField();
        fieldTimeTab.setBackground(bluLg);
        fieldTimeTab.setBorder(null);
        Font f1 = new Font("Arial", Font.BOLD, 20);
        fieldTimeTab.setFont(f1);
        fieldTimeTab.setBounds(anchoUnit * 0, altoUnit * 50, anchoUnit * 17, altoUnit * 4);
        fieldTimeTab.setHorizontalAlignment(SwingConstants.CENTER);
        panelStatsBySell.add(fieldTimeTab);

//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
//PANEL ORDER KIND--------------------------------------------------------------        
        JPanel panelOrder = new JPanel();
        panelOrder.setBackground(bluLg);
        panelOrder.setLayout(null);
        panelOrder.setBounds(anchoUnit * 21, altoUnit * 10, anchoUnit * 32, altoUnit * 37);
        panelPpal.add(panelOrder);

        JLabel labelOrderKind = utiliGraf.labelTitleBacker2("Volumen de venta por tipo de orden");
        labelOrderKind.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelOrder.add(labelOrderKind);

        chartPanelByOrder.setLayout(new BorderLayout());
        chartPanelByOrder.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 30, altoUnit * 30);
        panelOrder.add(chartPanelByOrder);

//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
//PANEL WORKSHIFT SELL CURVE----------------------------------------------------        
        JPanel panelSellCurve = new JPanel();
        panelSellCurve.setBackground(bluLg);
        panelSellCurve.setLayout(null);
        panelSellCurve.setBounds(anchoUnit * 54, altoUnit * 10, anchoUnit * 49, altoUnit * 37);
        panelPpal.add(panelSellCurve);

        JLabel labelSellCurve = utiliGraf.labelTitleBacker2("Ventas por turno");
        labelSellCurve.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelSellCurve.add(labelSellCurve);

        chartPanelSellCurve.setLayout(new BorderLayout());
        chartPanelSellCurve.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 47, altoUnit * 30);
        panelSellCurve.add(chartPanelSellCurve);

//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
//PANEL ITEM STATICs-------------------------------------------------------------        
        JPanel panelItemsStatics = new JPanel();
        panelItemsStatics.setLayout(null);
        panelItemsStatics.setBackground(bluLg);
        panelItemsStatics.setBounds(anchoUnit * 21, altoUnit * 49, anchoUnit * 40, altoUnit * 45);
        panelPpal.add(panelItemsStatics);

        JButtonMetalBlu butItemSells = utiliGraf.button2("VENTA ITEMS", anchoUnit * 1, altoUnit * 4, anchoUnit * 11);
        butItemSells.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openItemsSelledViewer(1);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemSells);
        
        JButtonMetalBlu butItemPriceEvol = utiliGraf.button2("VAR PRECIO x ITEM", anchoUnit * 13, altoUnit * 4, anchoUnit * 13);
        butItemPriceEvol .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openItemsSelledViewer(2);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemPriceEvol);

        JButtonMetalBlu butItemQuantEvol = utiliGraf.button2("VOL VENTA x ITEM", anchoUnit * 27, altoUnit * 4, anchoUnit * 12);
        butItemQuantEvol .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openItemsSelledViewer(3);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemQuantEvol);        
                
        JLabel labelItemsStatics = utiliGraf.labelTitleBacker2("Estadísticas de Items");
        labelItemsStatics.setBounds(anchoUnit * 1, altoUnit * 0, anchoUnit * 30, altoUnit * 3);
        panelItemsStatics.add(labelItemsStatics);

        JPanel panelItemsCaption = new JPanel();
        panelItemsCaption.setLayout(null);
        panelItemsCaption.setBackground(narUlg);
        panelItemsCaption.setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 38, altoUnit * 34);
        panelItemsStatics.add(panelItemsCaption);

        JLabel labelItemsCaption = utiliGraf.labelTitleBacker2("Venta de Items por rubro");
        labelItemsCaption.setBounds(anchoUnit * 1, altoUnit * 0, anchoUnit * 40, altoUnit * 3);
        panelItemsCaption.add(labelItemsCaption);

        labelCaption0 = utiliGraf.labelTitleBacker3("");
        labelCaption0.setBounds(anchoUnit * 25, altoUnit * 5, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption0);

        labelCaption1 = utiliGraf.labelTitleBacker3("");
        labelCaption1.setBounds(anchoUnit * 25, altoUnit * 10, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption1);

        labelCaption2 = utiliGraf.labelTitleBacker3("");
        labelCaption2.setBounds(anchoUnit * 25, altoUnit * 15, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption2);        

        labelCaption3 = utiliGraf.labelTitleBacker3("");
        labelCaption3.setBounds(anchoUnit * 25, altoUnit * 20, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption3);

        labelCaption4 = utiliGraf.labelTitleBacker3("");
        labelCaption4.setBounds(anchoUnit * 25, altoUnit * 25, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption4);

        labelCaption5 = utiliGraf.labelTitleBacker3("");
        labelCaption5.setBounds(anchoUnit * 25, altoUnit * 30, anchoUnit * 20, altoUnit * 3);
        panelItemsCaption.add(labelCaption5);
        
        chartPanelItemsCaption.setLayout(new BorderLayout());
        chartPanelItemsCaption.setBounds(anchoUnit * 1, altoUnit * 4, anchoUnit * 23, altoUnit * 29);
        chartPanelItemsCaption.setBackground(bluSt);
        panelItemsCaption.add(chartPanelItemsCaption);
        
        


//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
//PANEL WAITER STATIC-----------------------------------------------------------        
        JPanel panelWaiterStatics = new JPanel();
        panelWaiterStatics.setBackground(bluLg);
        panelWaiterStatics.setLayout(null);
        panelWaiterStatics.setBounds(anchoUnit * 62, altoUnit * 49, anchoUnit * 41, altoUnit * 45);
        panelPpal.add(panelWaiterStatics);

        JLabel labelWaiterStatics = utiliGraf.labelTitleBacker2("Estadísticas de Mozos");
        labelWaiterStatics.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelWaiterStatics.add(labelWaiterStatics);
        
        JPanel panelWaiterSells = new JPanel();
        panelWaiterSells.setLayout(null);
        panelWaiterSells.setBackground(narUlg);
        panelWaiterSells.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 38, altoUnit * 34);
        panelItemsStatics.add(panelWaiterSells);

        
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
    public void openSelectorPeriod() {
        new StaticsSelectorPeriod(this);
    }

    private void openItemSaleViewer() throws Exception {
        if (iSales != null) {
            new ItemSaleViewer(this);
            setEnabled(false);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    private void openTabViewer() throws Exception {
        if (tabs != null) {
            new TabViewer(tabs);
            setEnabled(false);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    private void updater() throws Exception {
        double tot = 0;
        double promTab = 0;
        long totTime = 0;
        int tabInt = 0;
        countCapt = new HashMap<>();
        ArrayList<Integer> idSales = new ArrayList<Integer>();
        ArrayList<Integer> cantSales = new ArrayList<Integer>();
//        idSales.add(iSales.get(0).getItemSaleId());

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
        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String formattedTotal = df.format(tot);
        fieldTotal.setText("$" + formattedTotal);

        promTab = tot / tabs.size();
        String formattedPromTab = df.format(promTab);
        fieldPromTab.setText("$" + formattedPromTab);

        long timeTab = totTime / tabInt;
        LocalTime time = toLongHAndM(totTime);
        fieldTimeTab.setText(time.getHour() + " horas, " + time.getMinute() + " min.");

        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 55);

        //Sells bykind of order
        chartOrder = utiliGrafStats.chartOrderBacker(this);
        chartPanelByOrder.removeAll();
        chartPanelByOrder.add(new XChartPanel<>(chartOrder));
        chartPanelByOrder.revalidate();
        chartPanelByOrder.repaint();

        //Curve by mount volume
        chartCurveSell = utiliGrafStats.chartCurveBacker(this);
        chartPanelSellCurve.removeAll();
        chartPanelSellCurve.add(new XChartPanel<>(chartCurveSell));
        chartPanelSellCurve.revalidate();
        chartPanelSellCurve.repaint();

        //Volume by item Capition
        
        chartCaptionPie = utiliGrafStats.chartCaptionBacker(countCapt, this);
        chartPanelItemsCaption.removeAll();
        chartPanelItemsCaption.add(new XChartPanel<>(chartCaptionPie));
        chartPanelItemsCaption.revalidate();
        chartPanelItemsCaption.repaint();
    }

    private void openItemsSelledViewer(int i) throws Exception {
        new StatsViewer(this, i);
    }

    public void setTabs(ArrayList tables) {
        tabs = tables;
    }

    public ArrayList<Table> getTabs() {
        return tabs;
    }

    public void setItemsSale(ArrayList iSals) {
        iSales = iSals;
    }

    public ArrayList<ItemSale> getItemsales() {
        return iSales;
    }

    void setWorkshifts(ArrayList<Workshift> workshifts) throws Exception {
        this.workshifts = workshifts;
        updater();
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

    public PieChart getChartOrder() {
        return chartOrder;
    }

    public void setChartOrder(PieChart chartOrder) {
        this.chartOrder = chartOrder;
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

    public JPanel getChartPanelByOrder() {
        return chartPanelByOrder;
    }

    public void setChartPanelByOrder(JPanel chartPanelByOrder) {
        this.chartPanelByOrder = chartPanelByOrder;
    }

    public XYChart getChartCurveSell() {
        return chartCurveSell;
    }

    public void setChartCurveSell(XYChart chartCurveSell) {
        this.chartCurveSell = chartCurveSell;
    }

    public JPanel getChartPanelSellCurve() {
        return chartPanelSellCurve;
    }

    public void setChartPanelSellCurve(JPanel chartPanelSellCurve) {
        this.chartPanelSellCurve = chartPanelSellCurve;
    }

    public CategoryChart getChartItemsSelled() {
        return chartItemsSelled;
    }

    public void setChartItemsSelled(CategoryChart chartItemsSelled) {
        this.chartItemsSelled = chartItemsSelled;
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

    private LocalTime toLongHAndM(long totTime) {
        long secs = totTime / 1000;
        long hours = secs / 3600;
        long minutes = (secs % 3600) / 60;
        LocalTime time = LocalTime.of((int) hours, (int) minutes);

        return time;
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
    
    

}
