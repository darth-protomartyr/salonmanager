package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;

public class ServicioTable {
    DAODelivery daoD = new DAODelivery();
    DAOTable daoT = new DAOTable();
    DAOItemcard daoI = new DAOItemcard();
    DAOUser daoU = new DAOUser();
    DAOConfig daoC = new DAOConfig();

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

    public ArrayList<Table> workshiftTableslistComplete(Workshift ws, int opt) throws Exception {
        ArrayList<Table> workshiftTabsNew = new ArrayList<>();
        ArrayList<Table> workshiftTabsOld = new ArrayList<>();

        if (opt == 1) {
            workshiftTabsOld = daoT.listarTablesByWorkshift(ws); //All tabs
        } else if (opt == 2) {
            workshiftTabsOld = daoT.listarTablesOpenByWorkshift(ws); //Open tabs
        }
        for (Table tab : workshiftTabsOld) {
            tab.setOrder(daoI.listarItemcardOrder(tab.getId()));
            tab.setGifts(daoI.listarItemcardGifts(tab.getId()));
            tab.setPartialPayed(daoI.listarItemcardPartialPayed(tab.getId()));
            tab.setPartialPayedND(daoI.listarItemcardPartialPayedND(tab.getId()));
            tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
            workshiftTabsNew.add(tab);
        }
        return workshiftTabsNew;
    }

    public Table tableItemsByTab(Table table) throws Exception {
        Table tab = table;
        tab.setOrder(daoI.listarItemcardOrder(tab.getId()));
        tab.setGifts(daoI.listarItemcardGifts(tab.getId()));
        tab.setWaiter(daoU.getWaiterByTable(tab.getId()));
        return tab;
    }

    public void saveTableCompleteChangeWs(Table tab, Salon salon) throws Exception {
        ArrayList<Integer> indexes = daoC.askIndexes();
        if (tab.getPos().equals("delivery")) {
            int i = indexes.get(1) + 1;
            tab.setNum(i);
            indexes.set(1, i);
            daoC.updateIndexes(indexes);
        }

        if (tab.getPos().equals("barra")) {
            int i = indexes.get(0) + 1;
            tab.setNum(i);
            indexes.set(0, i);
            daoC.updateIndexes(indexes);
        }        
        
        
        daoT.saveTable(tab, null);
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
    
    public ArrayList<Table> tabsBacke(ArrayList<String> tabIds) throws Exception {
        ArrayList<Table> tabs = new ArrayList<>();
        for (int i = 0; i < tabIds.size(); i++) {
            Table tab = daoT.getTableById(tabIds.get(i));
            tabs.add(tab);
        }
        return tabs;
    }
}