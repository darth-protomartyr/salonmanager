package salonmanager.servicios;

import java.util.ArrayList;
import java.util.Collections;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Table;

public class ServicioTable {
    
    public Table orderTable(Table table) {
        Table tableAux = table;
        ArrayList<ItemCarta> iT = table.getOrder();
        ArrayList<ItemCarta> iTAux = new ArrayList<ItemCarta>();
        ArrayList<Integer> units = table.getUnits();
        ArrayList<Integer> unitsAux = new ArrayList<Integer>();
        ArrayList<Integer> byId = new ArrayList<Integer>();
        for (ItemCarta ic  : iT ) {
            int i = ic.getId();
            byId.add(i);
        }

        Collections.sort(byId);
        
        for (int e = 0; e < byId.size(); e++) {
            for (int y = 0; y < iT.size(); y++) {
                if (byId.get(e) == iT.get(y).getId()) {
                    iTAux.add(iT.get(y));
                    unitsAux.add(units.get(y));
                }
            }
        }
        tableAux.setOrder(iTAux);
        tableAux.setUnits(unitsAux);     
        return tableAux;
    }

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
        
        
        
    
}
