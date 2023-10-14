package salonmanager.servicios;

import java.util.ArrayList;
import java.util.Collections;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Table;

public class ServicioTable {

//    public Table orderTable(Table table) {
//        Table tableAux = table;
//        ArrayList<ItemCarta> iT = table.getOrder();
//        ArrayList<ItemCarta> iTAux = new ArrayList<ItemCarta>();
//        ArrayList<Integer> byId = new ArrayList<Integer>();
//        for (ItemCarta ic : iT) {
//            int i = ic.getId();
//            byId.add(i);
//        }
//
//        Collections.sort(byId);
//
//        for (int e = 0; e < byId.size(); e++) {
//            for (int y = 0; y < iT.size(); y++) {
//                int counter = 0;
//                if (byId.get(e) == iT.get(y).getId()) {
//                    while (counter < 1) {
//                        iTAux.add(iT.get(y));
//                        counter += 1;
//                    }
//                }
//            }
//        }
//        tableAux.setOrder(iTAux);
//        return tableAux;
//    }

    public int giftCounter(ArrayList<ItemCarta> gifts, ItemCarta ic) {
        int units = 0;
        for (int i = 0; i < gifts.size(); i++) {
            if (gifts.get(i).getId() == ic.getId()) {
                units++;
            }
        }
        return units;
    }

    public String listarGifts(ArrayList<ItemCarta> gifts) {
        String txt = "";
        txt = txt + "Obsequios:\n";
        if (gifts.size() > 0) {
            for (int i = 0; i < gifts.size(); i++) {
                ItemCarta ic = gifts.get(i);
                txt += ic.getName() + "\n";
            }
        }
        return txt;
    }

//    public ArrayList<ItemCarta> orderItemComplete(Table tab) {
//        ArrayList<ItemCarta> orderDeploy = new ArrayList<ItemCarta>();
//        for (int i = 0; i < tab.getOrder().size(); i++) {
//            int repeat = 1;
//            while ( repeat <=  tab.getUnits().get(i)) {
//                orderDeploy.add(tab.getOrder().get(i));
//                repeat += 1;
//            }
//        }
//        return orderDeploy;
//    }
    public int itemUnitsBacker(ArrayList<ItemCarta> items, ItemCarta ic) {
        int units = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == ic.getId()) {
                units += 1;
            }
        }
        return units;
    }
}
