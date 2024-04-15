package salonmanager.utilidades;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import salonmanager.StaticsManager;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOItemcard;

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

    Utilidades utili = new Utilidades();

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
                itemName = utili.reduxSt(itemName);
            }
            itemNames.add(itemName);
        }

        // Crear el gráfico
        CategoryChart chart = new CategoryChartBuilder()
                .xAxisTitle("Items")
                .yAxisTitle("Ventas")                
                .build();
        chart.getStyler().setYAxisMax(q*1.1); // Establece el valor máximo del eje y (vertical) a 100
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
}
