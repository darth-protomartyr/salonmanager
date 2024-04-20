package salonmanager.servicios;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.persistencia.DAOWorkshift;

public class ServiceStatics {

    DAOWorkshift daoW = new DAOWorkshift();
    DAOItemSale daoIS = new DAOItemSale();

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
}
