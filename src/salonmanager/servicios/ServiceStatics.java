package salonmanager.servicios;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import salonmanager.ItemSaleViewer;
import salonmanager.StaticsManager;
import salonmanager.StaticsSelectorPeriod;
import salonmanager.StatsItemViewer;
import salonmanager.StatsWaiterViewer;
import salonmanager.TabViewer;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServiceStatics {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public static HashMap<Integer, Integer> orderHsII(HashMap<Integer, Integer> hashMap) {
        List<Map.Entry<Integer, Integer>> lista = new LinkedList<>(hashMap.entrySet());

        Collections.sort(lista, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue()); // Ordenar de mayor a menor
            }
        });

        HashMap<Integer, Integer> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entrada : lista) {
            hashMapOrdenado.put(entrada.getKey(), entrada.getValue());
        }
        return hashMapOrdenado;
    }

    public HashMap<String, Integer> orderHsSI(HashMap<String, Integer> countWWs) {
        List<Map.Entry<String, Integer>> lista = new LinkedList<>(countWWs.entrySet());

        Collections.sort(lista, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue()); // Ordenar de mayor a menor
            }
        });

        HashMap<String, Integer> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entrada : lista) {
            hashMapOrdenado.put(entrada.getKey(), entrada.getValue());
        }
        return hashMapOrdenado;
    }

    public static HashMap<Integer, Integer> orderHsIIDownToUp(HashMap<Integer, Integer> hashMap) {
        List<Map.Entry<Integer, Integer>> lista = new LinkedList<>(hashMap.entrySet());

        Collections.sort(lista, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey()); // Ordenar de mayor a menor
            }
        });

        HashMap<Integer, Integer> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entrada : lista) {
            hashMapOrdenado.put(entrada.getKey(), entrada.getValue());
        }
        return hashMapOrdenado;
    }

    public static HashMap<Integer, Double> orderHsIDDownToUp(HashMap<Integer, Double> hashMap) {
        List<Map.Entry<Integer, Double>> lista = new LinkedList<>(hashMap.entrySet());

        Collections.sort(lista, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o1.getKey().compareTo(o2.getKey()); // Ordenar de mayor a menor
            }
        });

        HashMap<Integer, Double> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entrada : lista) {
            hashMapOrdenado.put(entrada.getKey(), entrada.getValue());
        }
        return hashMapOrdenado;
    }

    public void openSelectorPeriod(StaticsManager statsM) {
        new StaticsSelectorPeriod(statsM);
    }

    public void openItemSaleViewer(StaticsManager statsM) throws Exception {
        if (statsM.getISales() != null) {
            new ItemSaleViewer(statsM);
            statsM.setEnabled(false);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    public void openTabViewer(StaticsManager statsM) throws Exception {
        if (statsM.getTabs() != null) {
            new TabViewer(statsM.getTabs(), statsM);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    public void openItemsSelledViewer(int i, StaticsManager statsM) throws Exception {
        new StatsItemViewer(statsM, i, statsM.getPeriod());
    }

    public void openWSellsViewer(int i, StaticsManager statsM) throws Exception {
        new StatsWaiterViewer(statsM, i, statsM.getPeriod());
    }
}