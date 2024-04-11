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

    public CategoryChart chartItemsBacker(HashMap<Integer,Integer> countItems) throws Exception {
        HashMap<Integer,Integer> countI = orderHs(countItems);
        ArrayList<Integer> itemsId = new ArrayList<>(countI.keySet());
        ArrayList<Integer> cants = new ArrayList<>(countI.values());
        
        ArrayList<String> itemNames = new ArrayList<String>();
        for (int i : itemsId) {
            String itemName = daoI.getItemNameById(i);
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
        
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAxisTitlesVisible(false);
        
        chart.addSeries("Items más vendidos", itemNames, cants);

        return chart;
    }
    
    public static HashMap<Integer, Integer> orderHs(HashMap<Integer, Integer> hashMap) {
        // Convertir el HashMap en una lista de entradas
        List<Map.Entry<Integer, Integer>> lista = new LinkedList<>(hashMap.entrySet());

        // Ordenar la lista basada en los valores de los enteros (segundo integer)
        Collections.sort(lista, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue()); // Ordenar de mayor a menor
            }
        });

        // Crear un nuevo HashMap ordenado
        HashMap<Integer, Integer> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entrada : lista) {
            hashMapOrdenado.put(entrada.getKey(), entrada.getValue());
        }
        return hashMapOrdenado;
    }

    public PieChart chartCaptionBacker(HashMap<String, Double> hashMap) {
        PieChart chartCaption = new PieChartBuilder().width(anchoUnit * 25).height(altoUnit * 25).build();
        chartCaption.getStyler().setLegendVisible(true);
        chartCaption.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        ArrayList<String> captions = new ArrayList<>(hashMap.keySet());
        ArrayList<Double> amounts = new ArrayList<>(hashMap.values());
        
        for (int i = 0; i < hashMap.size(); i++) {
            chartCaption.addSeries(captions.get(i), amounts.get(i));
        }
        
        return chartCaption;
    }
}