/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.WorkshiftEndPanel;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonTable;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.graphics.JButtonBarr;
import salonmanager.entidades.graphics.JButtonDelivery;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class ServicioSalon {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOItemcard daoIC = new DAOItemcard();
    DAOWorkshift daoW = new DAOWorkshift();
    DAODelivery daoD = new DAODelivery();
    DAOConfig daoC = new DAOConfig();
    ServicioItemMonitor sim = new ServicioItemMonitor();
    ServicioTable st = new ServicioTable();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Salon salon = null;

    public ArrayList<Integer> salonConfigValues(Integer tab, int anchoPane, int alturaPane) {
        ArrayList<Integer> configValues = new ArrayList<Integer>();
        int fontSize = 0;
        int wUnit = 0;
        int hUnit = 0;
        int rows = 0;
        int col = 0;

        if (tab == 12) {
            fontSize = 48;
            wUnit = (anchoPane) / 21;
            hUnit = (alturaPane) / 16;
            rows = 3;
            col = 4;
        } else if (tab == 24) {
            fontSize = 36;
            wUnit = (anchoPane) / 31;
            hUnit = (alturaPane) / 21;
            rows = 4;
            col = 6;
        } else if (tab == 35) {
            fontSize = 24;
            wUnit = (anchoPane) / 36;
            hUnit = (alturaPane) / 26;
            rows = 5;
            col = 7;
        } else if (tab == 48) {
            fontSize = 16;
            wUnit = (anchoPane) / 41;
            hUnit = (alturaPane) / 31;
            rows = 6;
            col = 8;
        }

        configValues.add(fontSize);
        configValues.add(wUnit);
        configValues.add(hUnit);
        configValues.add(rows);
        configValues.add(col);

        return configValues;
    }

    public int itemRepeat(Itemcard ic, ArrayList<Itemcard> itemsTableAux) {
        int rep = -1;
        for (int i = 0; i < itemsTableAux.size(); i++) {
            if (ic.equals(itemsTableAux.get(i))) {
                rep = i;
            }
        };
        return rep;
    }

    public ArrayList<Itemcard> itemTableLesser(ArrayList<Itemcard> items, Itemcard ic) {
        ArrayList<Itemcard> itemsLesser = new ArrayList<Itemcard>(items);
        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            if (itemsLesser.get(i).getId() == ic.getId()) {
                index = i;
            }
        }
        itemsLesser.remove(index);
        return itemsLesser;
    }

//Resumen de Cuenta Total
    public double countBill(Table tableAux) {
        double bill = 0;
        int discount = tableAux.getDiscount();
        ArrayList<Itemcard> itemsTable = tableAux.getOrder();

        for (int i = 0; i < itemsTable.size(); i++) {
            if (itemsTable.get(i).isActiveItem()) {
                bill = bill + (itemsTable.get(i).getPrice());
            }
        }

        if (discount > 0) {
            bill = bill - Math.round(bill * discount / 100);
        }

//      bill += tableAux.getErrorMod;
        return bill;
    }

//resumen de cuenta abonada en pago parcial  
    public double partialBillPayed(Table tableAux) {
        double partial = 0;
        int discount = tableAux.getDiscount();
        ArrayList<Itemcard> itemsPartial = new ArrayList<Itemcard>(tableAux.getPartialPayed());
        for (int i = 0; i < itemsPartial.size(); i++) {
            partial += itemsPartial.get(i).getPrice();
        }

        double disc = (double) discount;
        partial = partial * (1 - disc / 100);

        ArrayList<Itemcard> itemsPartialAux = new ArrayList<Itemcard>(tableAux.getPartialPayedND());
        double partialND = 0;
        for (int i = 0; i < itemsPartialAux.size(); i++) {
            partialND += itemsPartialAux.get(i).getPrice();
        }

        partial += partialND /* + tableAux.getErrorMod*/;
        return partial;
    }

//Resumen de cuenta aplicada a ventana de pago parcial
    public double billPartial(ArrayList<Itemcard> items, int discount) {
        double bill = 0;
        for (Itemcard ic : items) {
            bill += ic.getPrice();
        }
        double disc = discount;
        bill = bill * (1 - disc / 100);
        return bill;
    }

    public void createTable(Salon sal, Table tableAux) throws Exception {
        daoT.saveTable(tableAux);
        daoU.saveWaiterTable(tableAux);
    }

    public ArrayList<Itemcard> itemDeployer(Itemcard ic, int num) {
        ArrayList<Itemcard> al = new ArrayList<Itemcard>();
        int count = 0;
        while (count < num) {
            al.add(ic);
            count += 1;
        }
        return al;
    }

    public void addItemOrder(Salon sal, Table tableAux, ArrayList<Itemcard> arrayAux, boolean indiBool) throws Exception {
        for (int i = 0; i < arrayAux.size(); i++) {
            daoIC.saveItemOrderTable(arrayAux.get(i), tableAux);
            sim.createItemMonitor(sal, tableAux, arrayAux.get(i), indiBool);
        }
    }

    public boolean openJBTButtonsTester(ArrayList<JButtonTable> tableButtons, ArrayList<JButtonBarr> barrButtons, ArrayList<JButtonDelivery> deliButtons) {
        boolean close = true;
        for (JButtonTable jbt : tableButtons) {
            if (jbt.isOpenJBT() == true) {
                close = false;
            }
        }

        for (JButtonBarr jbb : barrButtons) {
            if (jbb.isOpenJBB() == true) {
                close = false;
            }
        }

        for (JButtonDelivery jbd : deliButtons) {
            if (jbd.isOpenJBD() == true) {
                close = false;
            }
        }

        return close;
    }

    public void endWorkshift(Salon salon, boolean errorWs) throws Exception {
        salon.setEnabled(true);
        if (salon.getBarrButtons().size() > 0) {
            if (salon.getBarrButtons().get(0).isOpenJBB()) {
                salon.getBarrButtons().get(0).setOpenJBB(false);
            }
        }

        if (salon.getDeliButtons().size() > 0) {
            if (salon.getDeliButtons().get(0).isOpenJBD()) {
                salon.getDeliButtons().get(0).setOpenJBD(false);
            }
        }

        if (openJBTButtonsTester(salon.getTableButtons(), salon.getBarrButtons(), salon.getDeliButtons()) == true || errorWs == false) {
            boolean confirm2 = utiliMsg.cargaConfirmarCierreTurno(salon.getUser().getName(), salon.getUser().getLastName());
            if (confirm2 == true) {
                closeWorkshift(salon, salon.getWorkshiftNow(), null, null, null, null, null, errorWs);
                salon.setEnabled(false);
            }
        } else {
            boolean confirm3 = utiliMsg.cargaConfirmarCambioTurno(salon.getUser());
            if (confirm3 == true) {
                inconcludeWsCutter(salon, salon.getWorkshiftNow(), errorWs);
                salon.setEnabled(false);
            }
        }
    }

    //CLOSE Workshift
    public void closeWorkshift(Salon salon, Workshift actWs, Workshift nWs, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> ersdTabs, ArrayList<Table> updTabs, boolean errorWs) throws Exception {
        Workshift actualWs = actWs;
        Workshift newWs = nWs;
        ArrayList<Table> actualTabs = actTabs;
        ArrayList<Table> upTabs = nTabs;
        ArrayList<Table> downTabs = ersdTabs;
        ArrayList<Table> toUpdTabs = updTabs;
        if (salon.getDeliButtons().size() > 0) {
            Delivery deli = salon.getDeliButtons().get(0).getDelivery();
            if (deli.getTab() == null) {
                daoD.updateDownAct(deli);
            }
        }

        if (nWs == null) {
            actualWs = setWsEnd(actualWs);
            actualTabs = st.workshiftTableslistComplete(actualWs);
        }

        if (actualTabs.size() == 0) {
            daoW.updateWorkshiftState(actualWs);
            daoC.updateCfgActOpenWs(false);
            daoC.updateCfgActOpenIdWs(0);
            utiliMsg.workshiftEmpty();
            salon.setWorkshiftNow(null);
            salon.getLabelWorkshift().setText("Turno no iniciado.");
            salon.getButInitWorkshift().setText("ABRIR TURNO");
        } else {
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
            new WorkshiftEndPanel(salon, actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs, errorWs);
        }
    }

    public void inconcludeWsCutter(Salon sal, Workshift ws, boolean errorWs) throws InterruptedException, Exception {
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
                    double total1 = countBill(tabNewWs);
                    tabNewWs.setTotal(total1);
                    tabNewWs.setComments("Nueva mesa con elementos no pagados del turno anterior");

                    tab.setOpen(false);
                    tab.setToPay(false);
                    tab.setGifts(new ArrayList<Itemcard>());
                    tab.setOrder(tab.getPartialPayed());
                    tab.setPartialPayedND(tab.getPartialPayedND());
                    double total2 = countBill(tab);
                    tab.setTotal(total2);
                    tab.setPartialPayed(new ArrayList<Itemcard>());
                    tab.setPartialPayedND(new ArrayList<Itemcard>());
                    tab.setComments(tab.getComments() + "<br>Los elementos no pagados fueron enviados al siguiente turno");
                    tab.setActiveTable(true);

                    toUpdTabs.add(tab);
                    upTabs.add(tabNewWs);
                } else {

                    Thread.sleep(10);
                    Table tabNewWs = new Table(tab.getNum(), tab.getPos(), bill, activeTable, orderNew, waiter, discount, total, comments);
                    tabNewWs.setOrder(tab.getOrder());
                    tabNewWs.setGifts(tab.getGifts());
                    total = countBill(tabNewWs);
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
        closeWorkshift(sal, actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs, errorWs);
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

