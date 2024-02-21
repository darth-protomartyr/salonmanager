package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;

public class ServicioTable {
    DAODelivery daoD = new DAODelivery();
    DAOTable daoT = new DAOTable();
    DAOItemcard daoI = new DAOItemcard();
    DAOUser daoU = new DAOUser();

    public int giftCounter(ArrayList<Itemcard> gifts, Itemcard ic) {
        int units = 0;
        for (int i = 0; i < gifts.size(); i++) {
            if (gifts.get(i).getId() == ic.getId()) {
                units++;
            }
        }
        return units;
    }

    public String listarGifts(ArrayList<Itemcard> gifts) {
        String txt = "";
        txt = txt + "Obsequios:\n";
        if (gifts.size() > 0) {
            for (int i = 0; i < gifts.size(); i++) {
                Itemcard ic = gifts.get(i);
                txt += ic.getName() + "\n";
            }
        }
        return txt;
    }

    public int itemUnitsBacker(ArrayList<Itemcard> items, Itemcard ic) {
        int units = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == ic.getId()) {
                units += 1;
            }
        }
        return units;
    }

    public ArrayList<Table> workshiftTableslistComplete(Workshift ws) throws Exception {
        ArrayList<Table> wsTabs = new ArrayList<Table>();
        ArrayList<Table> workshiftTabs = daoT.listarTablesByTimestamp(ws);
        for (Table tab : workshiftTabs) {
            tab.setOrder(daoI.listarItemcardOrder(tab.getId()));
            tab.setGifts(daoI.listarItemcardGifts(tab.getId()));
            tab.setPartialPayed(daoI.listarItemcardPartialPayed(tab.getId()));
            tab.setPartialPayedND(daoI.listarItemcardPartialPayedND(tab.getId()));
            tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
            wsTabs.add(tab);
        }
        return wsTabs;
    }

    public Table tableItemsByTab(Table table) throws Exception {
        Table tab = table;
        tab.setOrder(daoI.listarItemcardOrder(tab.getId()));
        tab.setGifts(daoI.listarItemcardGifts(tab.getId()));
        tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
        return tab;
    }

    public void saveTableCompleteChangeWs(Table tab, Salon salon) throws Exception {
        daoT.saveTable(tab);
        daoU.saveWaiterTable(tab);
        if (tab.getPos().equals("delivery")) {
            String deli = salon.getJbdAux().getDelivery().getId();
            daoD.updateDeliveryTable(tab.getId(), deli);
        }

        for (int i = 0; i < tab.getOrder().size(); i++) {
            daoI.saveItemOrderTable(tab.getOrder().get(i), tab);
        }

        for (int i = 0; i < tab.getGifts().size(); i++) {
            daoI.saveItemGiftTable(tab.getGifts().get(i), tab);
        }
    }

    public Table getCompleteTableById(String id) throws Exception {
        Table tab = daoT.getTableById(id);
        tab.setOrder(daoI.listarItemcardOrder(tab.getId()));
        tab.setGifts(daoI.listarItemcardGifts(tab.getId()));
        tab.setPartialPayed(daoI.listarItemcardPartialPayed(tab.getId()));
        tab.setPartialPayedND(daoI.listarItemcardPartialPayedND(tab.getId()));
        tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
        return tab;
    }
}