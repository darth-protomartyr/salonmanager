package salonmanager.utilidades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import salonmanager.StaticsManager;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServiceStatics;

/**
 */
public class UtilidadesGraficasStatics {

    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;

        DAOWorkshift daoW = new DAOWorkshift();
    DAOTable daoT = new DAOTable();
    DAOItemSale daoIs = new DAOItemSale();
    DAOUser daoU = new DAOUser();
    DAOItemCard daoI = new DAOItemCard();
    Utilidades utili = new Utilidades();

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceStatics sStats = new ServiceStatics();
    Color bluLg = new Color(194, 242, 206);
    Color narUlg = new Color(255, 255, 176);

    public PieChart chartOrderBacker(StaticsManager statsM) {
        PieChart chartOrder = new PieChartBuilder().width(anchoUnit * 25).height(altoUnit * 25).build();
        chartOrder.getStyler().setLegendVisible(true);
        chartOrder.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        chartOrder.addSeries("Ventas mesa", statsM.getNumTabs());
        chartOrder.addSeries("Ventas Barra", statsM.getNumBar());
        chartOrder.addSeries("Venta delivery", statsM.getNumDeli());

        return chartOrder;
    }

    public XYChart chartCurveBacker(StaticsManager statsM) {
        ArrayList<Timestamp> turnos = new ArrayList<>();
        ArrayList<Double> sales = new ArrayList<>();
        ArrayList<Workshift> wsS = statsM.getWorkshifts();

        for (Workshift ws : wsS) {
            turnos.add(ws.getOpenDateWs());
            sales.add(ws.getTotalMountTabs());
        }

        // Crear el gráfico de líneas
        XYChart chart = new XYChartBuilder()
                .width(anchoUnit * 40)
                .height(altoUnit * 40)
                .title("")
                .xAxisTitle("Turnos")
                .yAxisTitle("Ventas")
                .build();

        chart.setTitle("");
        chart.getStyler().setYAxisMin(0.0);
        chart.addSeries("Ventas", turnos, sales);

        return chart;
    }

    public CategoryChart chartItemsBacker(HashMap<Integer, Integer> countItems, int q) throws Exception {
        ArrayList<Integer> itemsId = new ArrayList<>(countItems.keySet());
        ArrayList<Integer> cants = new ArrayList<>(countItems.values());

        ArrayList<String> itemNames = new ArrayList<>();
        for (int i : itemsId) {
            String itemName = "--";
            if (i < 100000) {
                itemName = daoI.getItemNameById(i);
            }
            if (itemName.length() > 10) {
                itemName = utili.reduxSt(itemName, 1);
            }
            itemNames.add(itemName);
        }

        // Crear el gráfico
        CategoryChart chart = new CategoryChartBuilder()
                .xAxisTitle("Items")
                .yAxisTitle("Ventas")
                .build();
        chart.getStyler().setYAxisMax(q * 1.1); // Establece el valor máximo del eje y (vertical) a 100
        chart.getStyler().setYAxisDecimalPattern("#");
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setAxisTitlesVisible(false);

        chart.addSeries("Items más vendidos", itemNames, cants);

        return chart;
    }

    public PieChart chartCategoryBacker(HashMap<String, Double> hashMap, StaticsManager statsM) {
        PieChart chartCategory = new PieChartBuilder().width(anchoUnit * 20).height(altoUnit * 20).build();

        chartCategory.getStyler().setLegendVisible(true);
        chartCategory.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        ArrayList<String> categories = new ArrayList<>(hashMap.keySet());
        ArrayList<Double> amounts = new ArrayList<>(hashMap.values());

        statsM.getLabelCategory0().setText(categories.get(0) + "$: " + amounts.get(0));
        statsM.getLabelCategory1().setText(categories.get(1) + "$: " + amounts.get(1));
        statsM.getLabelCategory2().setText(categories.get(2) + "$: " + amounts.get(2));
        statsM.getLabelCategory3().setText(categories.get(3) + "$: " + amounts.get(3));
        statsM.getLabelCategory4().setText(categories.get(4) + "$: " + amounts.get(4));
        statsM.getLabelCategory5().setText(categories.get(5) + "$: " + amounts.get(5));

        for (int i = 0; i < hashMap.size(); i++) {
            chartCategory.addSeries(categories.get(i), amounts.get(i));
        }

        return chartCategory;
    }

    public PieChart chartWSellBacker(HashMap<String, Double> hashMap1, StaticsManager statsM) throws Exception {
        PieChart chartWaiter = new PieChartBuilder().width(anchoUnit * 20).height(altoUnit * 20).build();

        chartWaiter.getStyler().setLegendVisible(true);
        chartWaiter.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        ArrayList<String> waitersAux = new ArrayList<>(hashMap1.keySet());
        ArrayList<String> waiters = new ArrayList<>();

        for (int i = 0; i < waitersAux.size(); i++) {
            String w = daoU.getUserNameById(waitersAux.get(i));
            waiters.add(w);
        }

        ArrayList<Double> amounts = new ArrayList<>(hashMap1.values());

        for (int i = 0; i < waiters.size(); i++) {
            chartWaiter.addSeries(waiters.get(i), amounts.get(i));
        }

        return chartWaiter;
    }

    public JPanel panelWaiterBacker(StaticsManager sMan, int kind) {
        JPanel panelWaiterSells = new JPanel();
        JLabel labelWaiters = null;
        String tit = "";

        labelWaiters = utiliGraf.labelTitleBacker1("");
        labelWaiters.setBounds(anchoUnit * 1, altoUnit * 0, anchoUnit * 25, altoUnit * 4);
        panelWaiterSells.add(labelWaiters);

        if (kind == 1) {
            tit = "Top 5 Mozos x VENTA";
            labelWaiters.setText(tit);

            sMan.setLabelWaiter1(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter1().setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter1());

            sMan.setLabelWaiter2(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter2().setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter2());

            sMan.setLabelWaiter3(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter3().setBounds(anchoUnit * 1, altoUnit * 15, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter3());

            sMan.setLabelWaiter4(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter4().setBounds(anchoUnit * 1, altoUnit * 20, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter4());

            sMan.setLabelWaiter5(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter5().setBounds(anchoUnit * 1, altoUnit * 25, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter5());

        } else {
            tit = "Top 5 Mozos x TURNO";
            labelWaiters.setText(tit);

            sMan.setLabelWaiter6(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter6().setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter6());

            sMan.setLabelWaiter7(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter7().setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter7());

            sMan.setLabelWaiter8(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter8().setBounds(anchoUnit * 1, altoUnit * 15, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter8());

            sMan.setLabelWaiter9(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter9().setBounds(anchoUnit * 1, altoUnit * 20, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter9());

            sMan.setLabelWaiter10(utiliGraf.labelTitleBacker2(""));
            sMan.getLabelWaiter10().setBounds(anchoUnit * 1, altoUnit * 25, anchoUnit * 25, altoUnit * 4);
            panelWaiterSells.add(sMan.getLabelWaiter10());

        }

        return panelWaiterSells;
    }

    public CategoryChart chartWWsBacker(HashMap<String, Integer> countWWs, StaticsManager statsM) throws Exception {

        CategoryChart chart = null;
        countWWs = sStats.orderHsSI(countWWs);
        ArrayList<String> waitersId = new ArrayList<>(countWWs.keySet());
        ArrayList<Integer> wss = new ArrayList<>(countWWs.values());
        ArrayList<String> waiterNames = new ArrayList<>();

        for (String n : waitersId) {
            String name = daoU.getUserNameById(n);

            if (name.length() > 10) {
                name = utili.reduxSt(name, 2);
            }
            waiterNames.add(name);
        }

        if (waitersId.size() < 5) {
            chart = new CategoryChartBuilder()
                    .xAxisTitle("Items")
                    .yAxisTitle("Ventas")
                    .height(50 * altoUnit)
                    .width(50 * altoUnit)
                    .build();

        } else {
            chart = new CategoryChartBuilder()
                    .xAxisTitle("Items")
                    .yAxisTitle("Ventas")
                    .height(50 * altoUnit)
                    .build();
        }
        chart.getStyler().setYAxisDecimalPattern("#");
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setAvailableSpaceFill(0.8);
        chart.addSeries("Turnos por mozo", waiterNames, wss);

        return chart;
    }

    public JPanel panelLateralBacker(StaticsManager statsM) throws Exception {
        JPanel panelStatsBySell = new JPanel();

        JLabel labelAsk = utiliGraf.labelTitleBackerA3("Consulta:");
        labelAsk.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 16, altoUnit * 4);
        panelStatsBySell.add(labelAsk);

        JLabel labelStatsTime = utiliGraf.labelTitleBacker1("Por período de tpo.:");
        labelStatsTime.setBounds(anchoUnit * 1, altoUnit * 8, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelStatsTime);

        JButtonMetalBlu butStatsPeriod = utiliGraf.button1("ESTABLECER", anchoUnit * 2, altoUnit * 12, anchoUnit * 13);
        butStatsPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sStats.openSelectorPeriod(statsM);
                statsM.setEnabled(false);
            }
        });
        panelStatsBySell.add(butStatsPeriod);

        JLabel labelStatsWs = utiliGraf.labelTitleBacker1("Por turno:");
        labelStatsWs.setBounds(anchoUnit * 1, altoUnit * 21, anchoUnit * 18, altoUnit * 3);
        panelStatsBySell.add(labelStatsWs);
        
        JComboBox comboWs = new JComboBox();
        
        comboWs.setModel(wsComboBacker());
        Font font = new Font("Arial", Font.BOLD, 16);
        comboWs.setFont(font);
        comboWs.setBounds(anchoUnit * 1, altoUnit * 25, anchoUnit * 15, altoUnit * 4);
        panelStatsBySell.add(comboWs);

        JButtonMetalBlu butStatsWs = utiliGraf.button1("SELECCIONAR", anchoUnit * 2, altoUnit * 30, anchoUnit * 13);
        butStatsWs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String st = (String) comboWs.getSelectedItem();        
                try {
                    if (!st.equals("")) {
                        workshiftSelector(st, statsM);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficasStatics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelStatsBySell.add(butStatsWs);

        statsM.setLabelPeriod(utiliGraf.labelTitleBacker2(""));
        statsM.getLabelPeriod().setBounds(anchoUnit * 2, altoUnit * 39, anchoUnit * 16, altoUnit * 6);
        panelStatsBySell.add(statsM.getLabelPeriod());

        JButtonMetalBlu butViewAllItemSales = utiliGraf.button3("Ver Ventas", anchoUnit * 2, altoUnit * 46, anchoUnit * 6);
        butViewAllItemSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openItemSaleViewer(statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                statsM.setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllItemSales);

        JButtonMetalBlu butViewAllTabs = utiliGraf.button3("Ver Mesas", anchoUnit * 9, altoUnit * 46, anchoUnit * 6);
        butViewAllTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openTabViewer(statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                statsM.setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllTabs);

        JLabel labelTotalTitle = utiliGraf.labelTitleBacker2("Facturación período:");
        labelTotalTitle.setBounds(anchoUnit * 2, altoUnit * 50, anchoUnit * 13, altoUnit * 3);
        panelStatsBySell.add(labelTotalTitle);

        statsM.setFieldTotal(new JTextField());
        statsM.getFieldTotal().setBackground(bluLg);
        Font f = new Font("Arial", Font.BOLD, 35);
        statsM.getFieldTotal().setBorder(null);
        statsM.getFieldTotal().setFont(f);
        statsM.getFieldTotal().setBounds(anchoUnit * 0, altoUnit * 53, anchoUnit * 17, altoUnit * 5);
        statsM.getFieldTotal().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldTotal().setEditable(false);
        panelStatsBySell.add(statsM.getFieldTotal());
        
        JLabel labelErrorTabs = utiliGraf.labelTitleBacker3("Error Mesas Tot $:");
        labelErrorTabs.setBounds(anchoUnit * 1, altoUnit * 58, anchoUnit * 9, altoUnit * 3);
        panelStatsBySell.add(labelErrorTabs);

        statsM.setFieldErrorTab(new JTextField(""));
        statsM.getFieldErrorTab().setBackground(bluLg);
        Font f1 = new Font("Arial", Font.BOLD, 14);
        statsM.getFieldErrorTab().setBorder(null);
        statsM.getFieldErrorTab().setFont(f1);
        statsM.getFieldErrorTab().setBounds(anchoUnit * 10, altoUnit * 58, anchoUnit * 7, altoUnit * 3);
        statsM.getFieldErrorTab().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldErrorTab().setEditable(false);
        panelStatsBySell.add(statsM.getFieldErrorTab());

        JLabel labelErrorWs = utiliGraf.labelTitleBacker3("Error Turnos Tot $:");
        labelErrorWs.setBounds(anchoUnit * 1, altoUnit * 61, anchoUnit * 9, altoUnit * 2);
        panelStatsBySell.add(labelErrorWs);

        statsM.setFieldErrorWs(new JTextField(""));
        statsM.getFieldErrorWs().setBackground(bluLg);
        statsM.getFieldErrorWs().setBorder(null);
        statsM.getFieldErrorWs().setFont(f1);
        statsM.getFieldErrorWs().setBounds(anchoUnit * 10, altoUnit * 61, anchoUnit * 7, altoUnit * 2);
        statsM.getFieldErrorWs().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldErrorWs().setEditable(false);
        panelStatsBySell.add(statsM.getFieldErrorWs());

        JLabel labelTotalRealTitle = utiliGraf.labelTitleBacker2("Facturación real período:");
        labelTotalRealTitle.setBounds(anchoUnit * 1, altoUnit * 64, anchoUnit * 15, altoUnit * 3);
        panelStatsBySell.add(labelTotalRealTitle);

        statsM.setFieldTotalReal(new JTextField("9000000000"));
        statsM.getFieldTotalReal().setBackground(bluLg);
        statsM.getFieldTotalReal().setBorder(null);
        statsM.getFieldTotalReal().setFont(f);
        statsM.getFieldTotalReal().setBounds(anchoUnit * 0, altoUnit * 67, anchoUnit * 17, altoUnit * 5);
        statsM.getFieldTotalReal().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldTotalReal().setEditable(false);
        panelStatsBySell.add(statsM.getFieldTotalReal());
        
        JLabel labelPromTab = utiliGraf.labelTitleBacker2("Prom. gasto por orden:");
        labelPromTab.setBounds(anchoUnit * 2, altoUnit * 73, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelPromTab);

        statsM.setFieldPromTab(new JTextField());
        statsM.getFieldPromTab().setBackground(bluLg);
        statsM.getFieldPromTab().setBorder(null);
        statsM.getFieldPromTab().setFont(f);
        statsM.getFieldPromTab().setBounds(anchoUnit * 0, altoUnit * 76, anchoUnit * 17, altoUnit * 5);
        statsM.getFieldPromTab().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldPromTab().setEditable(false);
        panelStatsBySell.add(statsM.getFieldPromTab());

        JLabel labelTimeTab = utiliGraf.labelTitleBacker2("Prom. tiempo por mesa:");
        labelTimeTab.setBounds(anchoUnit * 2, altoUnit * 82, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelTimeTab);

        statsM.setFieldTimeTab(new JTextField());
        statsM.getFieldTimeTab().setBackground(bluLg);
        statsM.getFieldTimeTab().setBorder(null);
        Font f2 = new Font("Arial", Font.BOLD, 20);
        statsM.getFieldTimeTab().setFont(f2);
        statsM.getFieldTimeTab().setBounds(anchoUnit * 0, altoUnit * 85, anchoUnit * 17, altoUnit * 3);
        statsM.getFieldTimeTab().setHorizontalAlignment(SwingConstants.CENTER);
        statsM.getFieldTimeTab().setEditable(false);
        panelStatsBySell.add(statsM.getFieldTimeTab());

        return panelStatsBySell;
    }

    private void workshiftSelector(String st, StaticsManager statsM) throws Exception {
        int ws = 0;
        if (st.equals("ACTUAL")) {
            ws = daoW.askWorshiftActualId();
        } else {
            String[] words = st.split("\\.");
            String w = words[0];
            ws = Integer.parseInt(w);
        }

        staticBacker(null, null, statsM, ws);
    }

    public JPanel panelOrderBacker(StaticsManager statsM) {
        JPanel panelOrder = new JPanel();
        JLabel labelOrderKind = utiliGraf.labelTitleBacker2("Volumen de venta por tipo de orden");
        labelOrderKind.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelOrder.add(labelOrderKind);

        statsM.getPanelChartByOrder().setLayout(new BorderLayout());
        statsM.getPanelChartByOrder().setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 30, altoUnit * 30);
        panelOrder.add(statsM.getPanelChartByOrder());

        return panelOrder;
    }

    public JPanel panelSellCurveBacker(StaticsManager statsM) {
        JPanel panelSellCurve = new JPanel();
        JLabel labelSellCurve = utiliGraf.labelTitleBacker2("Ventas por turno");
        labelSellCurve.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelSellCurve.add(labelSellCurve);

        statsM.getPanelChartSellCurve().setLayout(new BorderLayout());
        statsM.getPanelChartSellCurve().setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 47, altoUnit * 30);
        panelSellCurve.add(statsM.getPanelChartSellCurve());

        return panelSellCurve;
    }

    public JPanel panelItemsStaticsBacker(StaticsManager statsM) {
        JPanel panelItemsStatics = new JPanel();

        JButtonMetalBlu butItemSells = utiliGraf.button2("VENTA ITEMS", anchoUnit * 1, altoUnit * 4, anchoUnit * 11);
        butItemSells.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openItemsSelledViewer(1, statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemSells);

        JButtonMetalBlu butItemQuantEvol = utiliGraf.button2("VAR VENTA x ITEM", anchoUnit * 13, altoUnit * 4, anchoUnit * 12);
        butItemQuantEvol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openItemsSelledViewer(2, statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemQuantEvol);

        JButtonMetalBlu butItemPriceEvol = utiliGraf.button2("VAR PRECIO x ITEM", anchoUnit * 26, altoUnit * 4, anchoUnit * 13);
        butItemPriceEvol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openItemsSelledViewer(3, statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItemsStatics.add(butItemPriceEvol);

        JLabel labelItemsStatics = utiliGraf.labelTitleBacker2("Estadísticas de Items");
        labelItemsStatics.setBounds(anchoUnit * 1, altoUnit * 0, anchoUnit * 30, altoUnit * 3);
        panelItemsStatics.add(labelItemsStatics);

        JPanel panelItemsCategory = new JPanel();
        panelItemsCategory.setLayout(null);
        panelItemsCategory.setBackground(narUlg);
        panelItemsCategory.setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 38, altoUnit * 34);
        panelItemsStatics.add(panelItemsCategory);

        JLabel labelItemsCategory = utiliGraf.labelTitleBacker2("Volumen Venta por categoría");
        labelItemsCategory.setBounds(anchoUnit * 1, altoUnit * 0, anchoUnit * 40, altoUnit * 4);
        panelItemsCategory.add(labelItemsCategory);

//        panelItemsCategory.setLayout(new BorderLayout());
//        statsM.getPanelItemsCategory().setBounds(anchoUnit * 1, altoUnit * 4, anchoUnit * 24, altoUnit * 29);
//        statsM.getPanelItemsCategory().setBackground(bluSt);
//        panelItemsCategory.add(statsM.getPanelItemsCategory());

        statsM.setLabelCategory0(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory0().setBounds(anchoUnit * 26, altoUnit * 5, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory0());

        statsM.setLabelCategory1(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory1().setBounds(anchoUnit * 26, altoUnit * 10, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory1());

        statsM.setLabelCategory2(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory2().setBounds(anchoUnit * 26, altoUnit * 15, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory2());

        statsM.setLabelCategory3(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory3().setBounds(anchoUnit * 26, altoUnit * 20, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory3());

        statsM.setLabelCategory4(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory4().setBounds(anchoUnit * 26, altoUnit * 25, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory4());

        statsM.setLabelCategory5(utiliGraf.labelTitleBacker3(""));
        statsM.getLabelCategory5().setBounds(anchoUnit * 26, altoUnit * 30, anchoUnit * 20, altoUnit * 3);
        panelItemsCategory.add(statsM.getLabelCategory5());

        return panelItemsStatics;
    }

    public JPanel panelWaiterStaticsBacker(StaticsManager statsM) {
        JPanel panelWaiterStatics = new JPanel();

        JLabel labelWaiterStatics = utiliGraf.labelTitleBacker2("Estadísticas de Mozos");
        labelWaiterStatics.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 30, altoUnit * 3);
        panelWaiterStatics.add(labelWaiterStatics);

        JPanel panelWaiterSells = panelWaiterBacker(statsM, 1);
        panelWaiterSells.setLayout(null);
        panelWaiterSells.setBackground(narUlg);
        panelWaiterSells.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 19, altoUnit * 30);
        panelWaiterStatics.add(panelWaiterSells);

        JPanel panelWaiterWs = panelWaiterBacker(statsM, 2);
        panelWaiterWs.setLayout(null);
        panelWaiterWs.setBackground(narUlg);
        panelWaiterWs.setBounds(anchoUnit * 21, altoUnit * 5, anchoUnit * 19, altoUnit * 30);
        panelWaiterStatics.add(panelWaiterWs);

        JButtonMetalBlu butWGlobal = utiliGraf.button2("Estadisticas globales", anchoUnit * 2, altoUnit * 38, anchoUnit * 17);
        butWGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openWSellsViewer(1, statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelWaiterStatics.add(butWGlobal);

        JButtonMetalBlu butWSingle = utiliGraf.button2("Estadisticas Individuales", anchoUnit * 22, altoUnit * 38, anchoUnit * 17);
        butWSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sStats.openWSellsViewer(2, statsM);
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelWaiterStatics.add(butWSingle);

        return panelWaiterStatics;
    }

    private DefaultComboBoxModel wsComboBacker() throws Exception {
        ArrayList<Integer> wssId = daoW.listIdWs();
        ArrayList<Timestamp> wssTs = daoW.listTsIWs();
        
        ArrayList<String> wssSt = new ArrayList<>();
        for (int i = 0; i < wssId.size(); i++) {
            String ts = utili.friendlyDate1(wssTs.get(i));
            wssSt.add(wssId.get(i) + ". " + ts);
        }

        String wsa = "";
        int actual = 0;
        actual = daoW.askWorshiftActualId();
        if (actual != 0) {
            wsa = "ACTUAL";
        }
        wssSt.add(0,wsa);
        DefaultComboBoxModel<String> modeloCombo = utili.wsComboModelReturnWNu(wssSt);
        return modeloCombo; 
    }
    
    public void staticBacker(Timestamp timestampInit, Timestamp timestampEnd, StaticsManager statsM, int wsId) throws Exception {
        ArrayList<Workshift> wsS = new ArrayList<>();

        if (wsId == 0) {
            ArrayList<Integer> wsIds = daoW.listIdByDate(timestampInit, timestampEnd);
            for (Integer id : wsIds) {
                Workshift ws = daoW.askWorshiftById(id);
                wsS.add(ws);
            }
        } else {
            Workshift ws = daoW.askWorshiftById(wsId);
            wsS.add(ws);
        }

        ArrayList<Timestamp> tsList = new ArrayList<>();
        for (Workshift ws : wsS) {
            Timestamp tsOpen = ws.getOpenDateWs();
            Timestamp tsClose = ws.getCloseDateWs();
            if (tsClose == null) {
                tsClose = new Timestamp(new Date().getTime());
            }
            tsList.add(tsOpen);
            tsList.add(tsClose);
        }

        Collections.sort(tsList, new Comparator<Timestamp>() {
            @Override
            public int compare(Timestamp t1, Timestamp t2) {
                return t1.compareTo(t2);
            }
        });

        int size = tsList.size();
        if (size == 0) {
            utiliMsg.errorNullDates();
            statsM.setEnabled(true);
        } else {
            Timestamp ts1 = tsList.get(0);
            Timestamp ts2 = tsList.get(size - 1);
            ArrayList<Table> tabs = daoT.listarTablesByDate(ts1, ts2);
            ArrayList<ItemSale> is = daoIs.listarItemSalesByDate(ts1, ts2);
            Collections.sort(tabs, new TimestampComparator());
            if (timestampInit != null && timestampEnd != null) {
                statsM.getLabelPeriod().setText("<html>LAPSO DE ANÁLISIS:<br>de " + utili.friendlyDate3(timestampInit) + " a " + utili.friendlyDate3(timestampEnd) + "</html>");
                statsM.setPeriod("de " + utili.friendlyDate3(timestampInit) + " a " + utili.friendlyDate3(timestampEnd));
            } else {
                statsM.getLabelPeriod().setText("<html>TURNO : " + wsS.get(0).getId() +"<br> INICIO: " + utili.friendlyDate2(wsS.get(0).getOpenDateWs()) + "</html>");
                statsM.setPeriod("TURNO " + wsS.get(0).getId());
            }
            statsM.setISales(is);
            statsM.setTabs(tabs);
            statsM.setWorkshifts(wsS);
            statsM.setEnabled(true);
            setPanelStatsBySell(statsM);
            updater(statsM);
        }
    }

    private void setPanelStatsBySell(StaticsManager statsM) {
        statsM.getPanelOrder().setVisible(true);
        statsM.getPanelSellCurve().setVisible(true);
        statsM.getPanelItemsCategory().setVisible(true);
        statsM.getPanelWaiterStatics().setVisible(true);
    }
    
    
    public void updater(StaticsManager statsM) throws Exception {
        double tot = 0;
//        double totReal = 0;
        double errorTab = 0;
        double errorWs = 0;
        double promTab = 0;
        long totTime = 0;
        int tabInt = 0;
        statsM.setCountCat(new HashMap<>());
        statsM.setCountWSells(new HashMap<String, Double>());
        statsM.setCountWWs(new HashMap<String, Integer>());
        ArrayList<String> waiterIdsDB = daoU.listarUserByRol("MOZO");
        ArrayList<String> waiterIds = new ArrayList<>();
//        ArrayList<Integer> idSales = new ArrayList<>();
//        ArrayList<Integer> cantSales = new ArrayList<>();
        ArrayList<HashSet<Integer>> listHS = new ArrayList<>();
        for (Table tab : statsM.getTabs()) {
            tot += tab.getTotal();
            if (tab.getPos().equals("barra")) {
                statsM.setNumBar(statsM.getNumBar() + tab.getTotal());
            } else if (tab.getPos().equals("delivery")) {
                statsM.setNumBar(statsM.getNumDeli() + tab.getTotal());
            } else {
                statsM.setNumTabs( statsM.getNumTabs() + tab.getTotal());
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
        
        for (int i = 0; i < statsM.getWorkshifts().size(); i++) {
            errorTab += statsM.getWorkshifts().get(i).getErrorMountTabs();
            errorWs += statsM.getWorkshifts().get(i).getErrorMountWs() - statsM.getWorkshifts().get(i).getErrorMountTabs();
        }

        for (int i = 0; i < statsM.getCategories().size(); i++) {
            statsM.getCountCat().put(statsM.getCategories().get(i), 0.0);
        }

        for (ItemSale is : statsM.getISales()) {
            for (Map.Entry<String, Double> entry : statsM.getCountCat().entrySet()) {
                String key = entry.getKey();
                double newValue = 0;
                double value = entry.getValue();
                if (key.equals(is.getItemSaleCategory())) {
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
            statsM.getCountWSells().put(id, 0.0);
            statsM.getCountWWs().put(id, 0);
        }

        for (ItemSale is : statsM.getISales()) {
            for (Map.Entry<String, Double> entry : statsM.getCountWSells().entrySet()) {
                String key = entry.getKey();
                if (key.equals(is.getItemSaleWaiterId())) {
                    double value = entry.getValue();
                    double newValue = value + is.getItemSalePrice();
                    entry.setValue(newValue);
                }
            }

            int counter = 0;
            for (Map.Entry<String, Integer> entry : statsM.getCountWWs().entrySet()) {
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
        statsM.getFieldTotal().setText("$" + formattedTotal);

        df.setMaximumFractionDigits(0);
        String formattedErrorTab = df.format(errorTab);
        statsM.getFieldErrorTab().setText(formattedErrorTab);

        df.setMaximumFractionDigits(0);
        String formattedErrorWs = df.format(errorWs);
        statsM.getFieldErrorWs().setText(formattedErrorWs);        
        
        df.setMaximumFractionDigits(0);
        String formattedTotalReal = df.format(tot - errorTab - errorWs);
        statsM.getFieldTotalReal().setText("$" + formattedTotalReal);         
        
        promTab = utili.round2Dec(tot / statsM.getTabs().size());
        String formattedPromTab = promTab + "";
        statsM.getFieldPromTab().setText("$" + formattedPromTab);

        long timeTab = 0;
        if (totTime > 0) {
            timeTab = totTime / tabInt;
        }
        LocalTime time = utili.toLongHAndM(timeTab);
        statsM.getFieldTimeTab().setText(time.getHour() + " horas, " + time.getMinute() + " min.");

        statsM.getPanelStatsBySell().setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 89);

        //Sells bykind of order
        statsM.setChartOrder(chartOrderBacker(statsM));
        statsM.getPanelChartByOrder().removeAll();
        statsM.getPanelChartByOrder().add(new XChartPanel<>(statsM.getChartOrder()));
        statsM.getPanelChartByOrder().revalidate();
        statsM.getPanelChartByOrder().repaint();

//        Curve by mount volume
        statsM.setChartCurveSell(chartCurveBacker(statsM));
        statsM.getPanelChartSellCurve().removeAll();
        statsM.getPanelChartSellCurve().add(new XChartPanel<>(statsM.getChartCurveSell()));
        statsM.getPanelChartSellCurve().revalidate();
        statsM.getPanelChartSellCurve().repaint();

        //Volume by item Category
        statsM.setChartCategoryPie(chartCategoryBacker(statsM.getCountCat(), statsM));
        statsM.getPanelChartCategory().removeAll();
        statsM.getPanelChartCategory().add(new XChartPanel<>(statsM.getChartCategoryPie()));
        statsM.getPanelChartCategory().revalidate();
        statsM.getPanelChartCategory().repaint();

        //TOP Waiters Sell
        ArrayList<String> waitersSell1 = new ArrayList<>(statsM.getCountWSells().keySet());
        ArrayList<String> waitersSell2 = new ArrayList<>();
        ArrayList<Double> amounts1 = new ArrayList<>(statsM.getCountWSells().values());
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
        ArrayList<String> waitersWs1 = new ArrayList<>(statsM.getCountWWs().keySet());
        ArrayList<String> waitersWs2 = new ArrayList<>();
        ArrayList<Integer> wss1 = new ArrayList<>(statsM.getCountWWs().values());
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

        statsM.getLabelWaiter1().setText("1- " + waitersSell2.get(0) + " $: " + amounts2.get(0));
        statsM.getLabelWaiter2().setText("2- " + waitersSell2.get(1) + " $: " + amounts2.get(1));
        statsM.getLabelWaiter3().setText("3- " + waitersSell2.get(2) + " $: " + amounts2.get(2));
        statsM.getLabelWaiter4().setText("4- " + waitersSell2.get(3) + " $: " + amounts2.get(3));
        statsM.getLabelWaiter5().setText("5- " + waitersSell2.get(4) + " $: " + amounts2.get(4));

        statsM.getLabelWaiter6().setText("1- " + waitersWs2.get(0) + ": " + wss2.get(0) + " turnos.");
        statsM.getLabelWaiter7().setText("2- " + waitersWs2.get(1) + ": " + wss2.get(1) + " turnos.");
        statsM.getLabelWaiter8().setText("3- " + waitersWs2.get(2) + ": " + wss2.get(2) + " turnos.");
        statsM.getLabelWaiter9().setText("4- " + waitersWs2.get(3) + ": " + wss2.get(3) + " turnos.");
        statsM.getLabelWaiter10().setText("5- " + waitersWs2.get(4) + ": " + wss2.get(4) + " turnos.");
    }
    
    public class TimestampComparator implements Comparator<Table> {
        @Override
        public int compare(Table o1, Table o2) {
            return o1.getOpenTime().compareTo(o2.getOpenTime());
        }
    }
    
    
}