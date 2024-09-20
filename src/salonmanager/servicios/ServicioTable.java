package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServicioTable {

    DAODelivery daoD = new DAODelivery();
    DAOTable daoT = new DAOTable();
    DAOItemcard daoI = new DAOItemcard();
    DAOUser daoU = new DAOUser();
    DAOConfig daoC = new DAOConfig();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

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
            workshiftTabsOld = listTablesByTs(ws.getOpenDateWs(), ws.getCloseDateWs(), false);
        } else if (opt == 2) {
            workshiftTabsOld = listTablesByTs(ws.getOpenDateWs(), ws.getCloseDateWs(), true);
        }

        for (Table tab : workshiftTabsOld) {
            String id = tab.getId();
            tab.setOrder(daoI.listarItemcardOrder(id));
            tab.setGifts(daoI.listarItemcardGifts(id));
            tab.setPartialPayed(daoI.listarItemcardPartialPayed(id));
            tab.setPartialPayedND(daoI.listarItemcardPartialPayedND(id));
            tab.setWaiter(daoU.getWaiterByTable(id));
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

    public void saveTableCompleteChangeWs(Table tab, Salon salon, Timestamp wsNewTs, Delivery delivery) throws Exception {
        ArrayList<Integer> indexes = daoC.askIndexes();
        if (tab.getPos().equals("delivery")) {
            int i = indexes.get(1) + 1;
            tab.setNum(i);
            indexes.set(1, i);
            daoC.updateIndexes(indexes, false);
            tab.setWaiter(salon.getUser());
            Delivery newDeli = new Delivery(delivery.getConsumer().getPhone(), delivery.getDeliUser().getId());
            newDeli.setTab(tab);
            daoD.saveDelivery(newDeli);
        }

        if (tab.getPos().equals("barra")) {
            int i = indexes.get(0) + 1;
            tab.setNum(i);
            indexes.set(0, i);
            daoC.updateIndexes(indexes, false);
            tab.setWaiter(salon.getUser());
        }

        boolean done = daoT.saveTable(tab, wsNewTs);
        if (done) {
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
        } else {
            utiliMsg.errorSaveTable();
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

    public ArrayList<Table> listTablesByTs(Timestamp ts1, Timestamp ts2, boolean open) throws Exception {
        if (ts2 == null) {
            ts2 = new Timestamp(new Date().getTime());
        }

        ArrayList<Timestamp> tabsTs = new ArrayList<>();
        ArrayList<Timestamp> tabsTsBarr = new ArrayList<>();
        ArrayList<Timestamp> tabsTsDeli = new ArrayList<>();
        if (!open) {
            tabsTs = daoT.listarTabsTsActive();
        } else {
            tabsTs = daoT.listarTabsTsOpen();
            tabsTsBarr = daoT.listarTabsTsActiveBarr();
            tabsTsDeli = daoT.listarTabsTsActiveDeli();
        }

        tabsTs = utili.tsFilter(tabsTs, ts1, ts2);
        tabsTsBarr = utili.tsFilter(tabsTsBarr, ts1, ts2);
        tabsTsDeli = utili.tsFilter(tabsTsDeli, ts1, ts2);

        ArrayList<Table> tabs = new ArrayList<>();
        for (int i = 0; i < tabsTs.size(); i++) {
            Table tab = daoT.getTableByTs(tabsTs.get(i));
            if (open) {
                if (!tab.getPos().equals("barra") && !tab.getPos().equals("delivery")) {
                    tabs.add(tab);
                }
            } else {
                tabs.add(tab);
            }
        }

        if (open) {
            for (int i = 0; i < tabsTsBarr.size(); i++) {
                Table tab = daoT.getTableByTs(tabsTsBarr.get(i));
                tabs.add(tab);
            }

            for (int i = 0; i < tabsTsDeli.size(); i++) {
                Table tab = daoT.getTableByTs(tabsTsDeli.get(i));
                tabs.add(tab);
            }
        }

        return tabs;
    }

    public int maxBarrTab(Workshift ws) throws Exception {
        ArrayList<Table> tabs = listTablesByTs(ws.getOpenDateWs(), ws.getCloseDateWs(), false);
        ArrayList<Integer> ints = new ArrayList<>();
        for (Table t : tabs) {
            ints.add(t.getNum());
        }
        int max = Collections.max(ints);
        return max;
    }
}
