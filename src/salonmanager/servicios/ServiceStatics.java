package salonmanager.servicios;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServiceStatics {

    DAOWorkshift daoW = new DAOWorkshift();
    DAOTable daoT = new DAOTable();
    DAOItemSale daoIs = new DAOItemSale();
    DAOItemSale daoIS = new DAOItemSale();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOUser daoU = new DAOUser();
    Utilidades utili = new Utilidades();
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;

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
        if (statsM.getiSales() != null) {
            new ItemSaleViewer(statsM);
            statsM.setEnabled(false);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    public void openTabViewer(StaticsManager statsM) throws Exception {
        if (statsM.getTabs() != null) {
            new TabViewer(statsM.getTabs());
            statsM.setEnabled(false);
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

    public void staticBacker(Timestamp timestampInit, Timestamp timestampEnd, StaticsManager statsM, int wsId) throws Exception {
        ArrayList<Workshift> wsS = new ArrayList<Workshift>();

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

        ArrayList<Timestamp> tsList = new ArrayList<Timestamp>();
        for (Workshift ws : wsS) {
            Timestamp tsOpen = ws.getOpenWs();
            Timestamp tsClose = ws.getCloseWs();
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
                statsM.getLabelPeriod().setText("<html>TURNO : " + wsS.get(0).getId() +"<br> INICIO: " + utili.friendlyDate2(wsS.get(0).getOpenWs()) + "</html>");
                statsM.setPeriod("TURNO " + wsS.get(0).getId());
            }
            statsM.setItemsSale(is);
            statsM.setTabs(tabs);
            statsM.setWorkshifts(wsS);
            statsM.setEnabled(true);
        }
    }

    public class TimestampComparator implements Comparator<Table> {

        @Override
        public int compare(Table o1, Table o2) {
            return o1.getOpenTime().compareTo(o2.getOpenTime());
        }
    }
}
