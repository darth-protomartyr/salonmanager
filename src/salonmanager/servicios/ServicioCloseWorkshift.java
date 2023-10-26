package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Table;
import salonmanager.entidades.User;
import salonmanager.entidades.Workshift;
import salonmanager.persistencia.DAOItemCarta;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOWorkshift;

public class ServicioCloseWorkshift {

    DAOWorkshift daoW = new DAOWorkshift();
    DAOTable daoT = new DAOTable();
    DAOItemCarta daoI = new DAOItemCarta();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    double mountError = 0;
    double mount = 0;
    double mountCash = 0;
    double mountElectronic = 0;
    ArrayList<Table> tabsWs = new ArrayList<Table>();
    ArrayList<Table> tabsWsAux = new ArrayList<Table>();
    Workshift actualWs = null;
    Workshift partialWs = null;
    

    public Workshift closeWorkshift(Workshift ws) throws Exception {
        actualWs = ws;
        actualWs.setCloseShift(new Timestamp(new Date().getTime()));
        actualWs.setStateWorkshift(false);
//        ws.setRegisters();
        actualWs.setCloseShift(new Timestamp(new Date().getTime()));
        tabsWs = st.workshiftTableslistComplete(ws);
        boolean partialEnd = openTableVerificate();
        if (partialEnd == true) {
            tabsWsAux = new ArrayList<Table>();
            partialWs = new Workshift(); 
        }

        for (int i = 0; i < tabsWs.size(); i++) {
            Table tab = tabsWs.get(i);
            if (tab.getPartialPayed().size() > 0 || tab.getAuxiliarPartialPayedNoDiscount().size() > 0) {
                int num = tab.getNum();
                String pos = tab.getPos();
                boolean bill = tab.isBill();
                ArrayList<ItemCarta> order = tab.getOrder();
                ArrayList<ItemCarta> gifts = tab.getGifts();
                User waiter = tab.getWaiter();
                int discount = tab.getDiscount();
                double total = tab.getTotal();
                Table tabNewWs = new Table(num, pos, bill, order, gifts, waiter, discount, total);
                tabsWsAux.add(tabNewWs);
                daoT.guardarTable(tabNewWs);

                tab.setOpen(false);
                tab.setOrder(tab.getPartialPayed());
                tab.setPartialPayed(null);
                tab.setTotal(ss.countBill(tab));
                daoT.updateTableTotal(tab);
                

            } else {
                
                if (tab.isOpen() == false) {
                    mount += tab.getTotal();
                    mountError += tab.getError();
                    mountCash += tab.getAmountCash();
                    mountElectronic += tab.getAmountElectronic();    
                } else {
                    openTablesWorkshift.add(tab);
                }
            }
        }
        
        ws.setStateWorkshift(false);

        if (partialEnd == true) {
            newWsPayed = new Workshift();
            newWsPayed.setCashier(null);
            newWsPayed.setOpenShift(new Timestamp(new Date().getTime()));
            newWsPayed.setCloseShift(null);
            newWsPayed.setStateWorkshift(true);
            newWsPayed.setShiftTables(new ArrayList<Table>());
            newWsPayed.setRegisters(new ArrayList<String>());
            newWsPayed.setTotalMountShiftCash(0);
            newWsPayed.setTotalMountShiftElectronic(0);
            newWsPayed.setTotalMountShift(0);
            newWsPayed.setTotalMountShiftReal(0);
            newWsPayed.setErrorMountShift(0);
            newWsPayed.setErrorMountShiftReal(0);
        }
        return newWs;
    }

    private void tableDivide(Table tab) {

    }

    private boolean openTableVerificate() {
        boolean openTable = false;
        for (int i = 0; i <; i++) {

        }

    }
}
