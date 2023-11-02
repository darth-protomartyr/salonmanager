package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
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
    Salon sal = null;

    public void closeWorkshift(Workshift ws, Salon sal1) throws Exception {
        sal = sal1;
        actualWs = ws;
        tabsWs = st.workshiftTableslistComplete(actualWs);
        boolean partialEnd = openTableVerificate();

        if (partialEnd == true) {
            Thread.sleep(10); // Espera durante 10 milisegundos (1 segundo) para no pisarse con la marca temporal del cierre del turno anterior
            tabsWsAux = new ArrayList<Table>();
            partialWs = new Workshift();
            partialWs.setCashier(null);
            partialWs.setOpenShift(new Timestamp(new Date().getTime()));
            partialWs.setCloseShift(null);
            partialWs.setStateWorkshift(true);
            partialWs.setTotalMountShiftCash(0);
            partialWs.setTotalMountShiftElectronic(0);
            partialWs.setTotalMountShift(0);
            partialWs.setTotalMountShiftReal(0);
            partialWs.setErrorMountShift(0);
            partialWs.setErrorMountShiftReal(0);
        }

        for (int i = 0; i < tabsWs.size(); i++) {
            Table tab = tabsWs.get(i);
            if (tab.getPartialPayed().size() > 0 || tab.getAuxiliarPartialPayedNoDiscount().size() > 0) { //Divide a la mesa para cerrar lo pagado y que el resto pase al proximo turno 
                //crea mesa para nuevo turno
                int num = tab.getNum();
                String pos = tab.getPos();
                boolean bill = tab.isBill();
                ArrayList<ItemCarta> order = tab.getOrder();
                User waiter = tab.getWaiter();
                int discount = tab.getDiscount();
                double total = tab.getTotal();
                Table tabNewWs = new Table(num, pos, bill, order, waiter, discount, total);
                tabsWsAux.add(tabNewWs);
                daoT.guardarTable(tabNewWs);
                for (ItemCarta ic : tab.getPartialPayed()) {
                    daoI.eliminarItemPayedTable(ic, tabNewWs);
                }
                for (ItemCarta ic : tab.getOrder()) {
                    daoI.guardarItemOrderTable(ic, tabNewWs);
                }
                tabsWsAux.add(tabNewWs);
                tab.setOpen(false);
                tab.setOrder(tab.getPartialPayed());
                tab.setPartialPayed(null);
                tab.setTotal(ss.countBill(tab));
                daoT.updateTableTotal(tab);
            }

            mount += tab.getTotal();
            mountError += tab.getError();
            mountCash += tab.getAmountCash();
            mountElectronic += tab.getAmountElectronic();
        }

        actualWs.setTotalMountShift(mount);
        actualWs.setErrorMountShift(mountError);
        actualWs.setTotalMountShiftCash(mountCash);
        actualWs.setTotalMountShiftElectronic(mountElectronic);
        daoW.updateWorkshiftTotal(actualWs);
        daoW.updateWorkshiftError(actualWs);
        daoW.updateWorkshiftCash(actualWs);
        daoW.updateWorkshiftElectronic(actualWs);

        if (partialEnd == true) {
            daoW.guardarWorkshift(partialWs);
            for (Table tab : tabsWsAux) {
                daoT.guardarTable(tab);
            }
        }

        sal.workshiftConclude(actualWs, tabsWs, partialEnd);
    }

    private boolean openTableVerificate() {
        boolean openTable = false;
        for (int i = 0; i < tabsWs.size(); i++) {
            if (tabsWs.get(i).isOpen() == true) {
                openTable = true;
            }
        }
        return openTable;
    }
}
