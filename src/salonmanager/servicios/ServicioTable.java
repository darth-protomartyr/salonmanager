package salonmanager.servicios;

import java.util.ArrayList;
import java.util.Collections;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Table;
import salonmanager.entidades.Workshift;
import salonmanager.persistencia.DAOItemCarta;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;

public class ServicioTable {

    DAOTable daoT = new DAOTable();
    DAOItemCarta daoI = new DAOItemCarta();
    DAOUser daoU = new DAOUser();


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


    public int itemUnitsBacker(ArrayList<ItemCarta> items, ItemCarta ic) {
        int units = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == ic.getId()) {
                units += 1;
            }
        }
        return units;
    }

    ArrayList<Table> workshiftTableslistComplete(Workshift ws) throws Exception {
        ArrayList<Table> wsTabs = new ArrayList<Table>();
        ArrayList<Table> workshiftTabs = daoT.listarTablesByTimestamp(ws);
        for (Table tab : workshiftTabs) {
            tab.setOrder(daoI.listarItemCartaOrder(tab.getId()));
            tab.setGifts(daoI.listarItemCartaGifts(tab.getId()));
            tab.setPartialPayed(daoI.listarItemCartaPartialPayed(tab.getId()));
            tab.setAuxiliarPartialPayedNoDiscount(daoI.listarItemCartaPartialPayedND(tab.getId()));
            tab.setErrorItems(daoI.listarItemCartaError(tab.getId()));
            tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
            wsTabs.add(tab);
//            tab.setAuxiliarPartialPayedNoDiscount(daoI.listarItemCartaPartialPayedNoDiscount(tab.getId()));
//            tab.setErrorItems(daoI.listarItemCartaErrorItems(tab.getId()));
//            tab.setWaiter(tab.getWaiter());
        }
        return wsTabs;
    }
}
