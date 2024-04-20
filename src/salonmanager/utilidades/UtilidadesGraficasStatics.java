package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import salonmanager.StaticsManager;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOUser;
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

    DAOItemcard daoI = new DAOItemcard();
    DAOUser daoU = new DAOUser();
    Utilidades utili = new Utilidades();

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
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

        // Crear datos de ventas
        ArrayList<Timestamp> turnos = new ArrayList<Timestamp>();
        ArrayList<Double> sales = new ArrayList<>();
        ArrayList<Workshift> wsS = statsM.getWorkshift();

        for (Workshift ws : wsS) {
            turnos.add(ws.getOpenWs());
            sales.add(ws.getTotalMountWs());
        }

        // Crear el gráfico de líneas
        XYChart chart = new XYChartBuilder()
                .width(anchoUnit * 40)
                .height(altoUnit * 40)
                .title("")
                .xAxisTitle("Turnos")
                .yAxisTitle("Ventas")
                .build();

        // Agregar series de datos al gráfico
        chart.addSeries("Ventas", turnos, sales);

        return chart;
    }

    public CategoryChart chartItemsBacker(HashMap<Integer, Integer> countItems, int q) throws Exception {
        ArrayList<Integer> itemsId = new ArrayList<>(countItems.keySet());
        ArrayList<Integer> cants = new ArrayList<>(countItems.values());

        ArrayList<String> itemNames = new ArrayList<String>();
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
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAxisTitlesVisible(false);

        chart.addSeries("Items más vendidos", itemNames, cants);

        return chart;
    }

    public PieChart chartCaptionBacker(HashMap<String, Double> hashMap, StaticsManager statsM) {
        PieChart chartCaption = new PieChartBuilder().width(anchoUnit * 20).height(altoUnit * 20).build();

        chartCaption.getStyler().setLegendVisible(true);
        chartCaption.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        ArrayList<String> captions = new ArrayList<>(hashMap.keySet());
        ArrayList<Double> amounts = new ArrayList<>(hashMap.values());

        statsM.getLabelCaption0().setText(captions.get(0) + ": $" + amounts.get(0));
        statsM.getLabelCaption1().setText(captions.get(1) + ": $" + amounts.get(1));
        statsM.getLabelCaption2().setText(captions.get(2) + ": $" + amounts.get(2));
        statsM.getLabelCaption3().setText(captions.get(3) + ": $" + amounts.get(3));
        statsM.getLabelCaption4().setText(captions.get(4) + ": $" + amounts.get(4));
        statsM.getLabelCaption5().setText(captions.get(5) + ": $" + amounts.get(5));

        for (int i = 0; i < hashMap.size(); i++) {
            chartCaption.addSeries(captions.get(i), amounts.get(i));
        }

        return chartCaption;
    }

    public PieChart chartWSellBacker(HashMap<String, Double> hashMap1, StaticsManager statsM) throws Exception {
        PieChart chartWaiter = new PieChartBuilder().width(anchoUnit * 20).height(altoUnit * 20).build();

        chartWaiter.getStyler().setLegendVisible(true);
        chartWaiter.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        ArrayList<String> waitersAux = new ArrayList<>(hashMap1.keySet());
        ArrayList<String> waiters = new ArrayList<String>();

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
        
        countWWs = sStats.orderHsSI(countWWs);
        ArrayList<String> waitersId = new ArrayList<>(countWWs.keySet());
        ArrayList<Integer> wss = new ArrayList<>(countWWs.values());

        ArrayList<String> waiterNames = new ArrayList<String>();
        for (String n : waitersId) {
            String name = daoU.getUserNameById(n);
            
            if (name.length() > 10) {
                name = utili.reduxSt(name, 2);
            }
            waiterNames.add(name);
        }
        
        // Crear el gráfico
        CategoryChart chart = new CategoryChartBuilder()
                .xAxisTitle("Items")
                .yAxisTitle("Ventas")
                .height(50 * altoUnit)
                .build();
        chart.getStyler().setYAxisDecimalPattern("#");
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
//        chart.getStyler().setPlotMargin(60); // Establecer el margen del área de trazado
        chart.getStyler().setAvailableSpaceFill(0.8);
       
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");
        waiterNames.add("Raul Cas");

        wss.add(7);
        wss.add(9);
        wss.add(10);
        wss.add(23);
        wss.add(37);
        wss.add(6);
        wss.add(23);
        wss.add(10);
        wss.add(23);
        wss.add(10);

        
        chart.addSeries("Turnos por mozo", waiterNames, wss);

        return chart;
    }
}
