package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.WorkshiftEndPanel;
import salonmanager.entidades.Itemcard;
import salonmanager.entidades.Table;
import salonmanager.entidades.User;
import salonmanager.entidades.Workshift;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;

public class ServicioCloseWorkshift {

    DAOWorkshift daoW = new DAOWorkshift();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    Utilidades utili = new Utilidades();

    public void closeWorkshift(Salon sal1, Workshift actWs, Workshift nWs, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> ersdTabs, ArrayList<Table> updTabs) throws Exception {
        Salon sal = sal1;
        Workshift actualWs = actWs;
        Workshift newWs = nWs;
        ArrayList<Table> actualTabs = actTabs;
        ArrayList<Table> upTabs = nTabs;
        ArrayList<Table> downTabs = ersdTabs;
        ArrayList<Table> toUpdTabs = updTabs;
        if (nWs == null) {
            actualWs = setWsEnd(actualWs);
            actualTabs = st.workshiftTableslistComplete(actualWs);
        }
        double mount = 0;
        double mountError = 0;
        double mountCash = 0;
        double mountElectronic = 0;
        for (int i = 0; i < actualTabs.size(); i++) {
            if (actualTabs.get(i).isOpen() == false && actualTabs.get(i).isActiveTable() == true) {
                Table tab = actualTabs.get(i);
                mount += tab.getTotal();
                mountError += tab.getError();
                mountCash += tab.getAmountCash();
                mountElectronic += tab.getAmountElectronic();
            }
        }
        actualWs.setWsTotalMount(mount);
        actualWs.setWsErrorMount(mountError);
        actualWs.setWsTotalMountCash(mountCash);
        actualWs.setWsTotalMountElectronic(mountElectronic);
        new WorkshiftEndPanel(sal, actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs);
    }

    public void inconcludeWsCutter(Salon sal, Workshift ws) throws InterruptedException, Exception {   
        Workshift newWs = new Workshift();
        Workshift actualWs = ws;
        ArrayList<Table> actualTabs = null;
        ArrayList<Table> upTabs = new ArrayList<Table>();
        ArrayList<Table> downTabs = new ArrayList<Table>();
        ArrayList<Table> toUpdTabs = new ArrayList<Table>();
        actualWs = setWsEnd(ws);
        actualTabs = st.workshiftTableslistComplete(actualWs);
        Thread.sleep(100);
        newWs.setWsCashier(null);
        newWs.setWsOpen(new Timestamp(new Date().getTime()));
        newWs.setWsClose(null);
        newWs.setWsState(true);
        newWs.setWsTotalMountCash(0);
        newWs.setWsTotalMountElectronic(0);
        newWs.setWsTotalMount(0);
        newWs.setWsTotalMountReal(0);
        newWs.setWsErrorMount(0);
        newWs.setWsErrorMountReal(0);
        for (int i = 0; i < actualTabs.size(); i++) {
            Table tab = actualTabs.get(i);
            System.out.println(tab);
            if (tab.isOpen() == true) {
                boolean bill = tab.isBill();
                boolean activeTable = tab.isActiveTable();
                User waiter = tab.getWaiter();
                int discount = tab.getDiscount();
                double total = 0;
                ArrayList<Itemcard> orderNew = new ArrayList<Itemcard>();
                String comments = tab.getComments();
                if (tab.isToPay()) {
                    Thread.sleep(10);
                    orderNew = tab.getOrder();
                    Table tabNewWs = new Table(tab.getNum(), tab.getPos(), bill, activeTable, orderNew, waiter, discount, total, comments);
                    tabNewWs.setGifts(tab.getGifts());
                    double total1 = ss.countBill(tabNewWs);
                    tabNewWs.setTotal(total1);
                    tabNewWs.setComments("Nueva mesa con elementos no pagados del turno anterior");

                    tab.setOpen(false);
                    tab.setToPay(false);
                    tab.setGifts(new ArrayList<Itemcard>());
                    tab.setOrder(tab.getPartialPayed());
                    tab.setPartialPayedND(tab.getPartialPayedND());
                    double total2 = ss.countBill(tab);
                    tab.setTotal(total2);
                    tab.setPartialPayed(new ArrayList<Itemcard>());
                    tab.setPartialPayedND(new ArrayList<Itemcard>());
                    tab.setComments(tab.getComments() + "<br>Los elementos no pagados fueron enviados al siguiente turno");
                    tab.setActiveTable(true);
                    
                    toUpdTabs.add(tab);
                    upTabs.add(tabNewWs);
                    System.out.println(tab);
                } else {
                    
                    Thread.sleep(10);
                    Table tabNewWs = new Table(tab.getNum(), tab.getPos(), bill, activeTable, orderNew, waiter, discount, total, comments);
                    tabNewWs.setOrder(tab.getOrder());
                    tabNewWs.setGifts(tab.getGifts());
                    total = ss.countBill(tabNewWs);
                    tabNewWs.setTotal(total);
                    tabNewWs.setComments("La mesa fue dividida por cambio de turno");

                    tab.setActiveTable(false);                    
                    upTabs.add(tabNewWs);
                    downTabs.add(tab);
                }
            }   
        }
        Thread.sleep(100);
        Timestamp close = new Timestamp(new Date().getTime());
        newWs.setWsClose(close);
        newWs.setWsClose(null);
        closeWorkshift(sal, actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs);
    }

    private Workshift setWsEnd(Workshift ws) throws Exception {
        Workshift newWs = ws;
        int id = daoW.findId(newWs.getWsOpen());
        newWs.setWsId(id);
        newWs.setWsClose(new Timestamp(new Date().getTime()));
        newWs.setWsState(false);
        return newWs;
    }
}