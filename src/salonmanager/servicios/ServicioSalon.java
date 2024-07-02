package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Manager;
import salonmanager.Salon;
import salonmanager.TableAdder;
import salonmanager.TabsToEnd;
import salonmanager.WorkshiftEndPanel;
import salonmanager.entidades.bussiness.CashFlow;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonTable;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.JButtonBarr;
import salonmanager.entidades.graphics.JButtonDelivery;
import salonmanager.persistencia.DAOCashFlow;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServicioSalon {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOItemcard daoIC = new DAOItemcard();
    DAOWorkshift daoW = new DAOWorkshift();
    DAODelivery daoD = new DAODelivery();
    DAOConfig daoC = new DAOConfig();
    DAOCashFlow daoCF = new DAOCashFlow();
    ServicioItemMonitor sim = new ServicioItemMonitor();
    ServicioItemSale sis = new ServicioItemSale();
    ServicioTable st = new ServicioTable();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Salon salon = null;

    public ArrayList<Integer> salonConfigValues(Integer total, Integer tab, int anchoPane, int alturaPane) {
        ArrayList<Integer> configValues = new ArrayList<>();

        int fontSize = 0;
        int wUnit = 0;
        int hUnit = 0;
        int rows = 0;
        int col = 0;

        if (tab == 12) {
            fontSize = 65;
            wUnit = (anchoPane) / 21;
            hUnit = (alturaPane) / 16;
            rows = 3;
            col = 4;
        } else if (tab == 24) {
            if (total > 100) {
                fontSize = 40;
            } else {
                fontSize = 50;
            }
            wUnit = (anchoPane) / 31;
            hUnit = (alturaPane) / 21;
            rows = 4;
            col = 6;
        } else if (tab == 35) {
            if (total > 100) {
                fontSize = 35;
            } else {
                fontSize = 45;
            }
            wUnit = (anchoPane) / 36;
            hUnit = (alturaPane) / 26;
            rows = 5;
            col = 7;
        } else if (tab == 48) {
            if (total > 100) {
                fontSize = 30;
            } else {
                fontSize = 35;
            }
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
        }
        return rep;
    }

    public ArrayList<Itemcard> itemTableLesser(ArrayList<Itemcard> items, Itemcard ic) {
        ArrayList<Itemcard> itemsLesser = new ArrayList<>(items);
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
    public double countBill(Table tableAux, Salon sal) {
        double bill = 0;
        int discount = tableAux.getDiscount();
        ArrayList<Itemcard> itemsTable = tableAux.getOrder();

        for (int i = 0; i < itemsTable.size(); i++) {
            if (itemsTable.get(i).isActiveItem()) {
                bill = bill + utili.priceMod(itemsTable.get(i), sal);
            }
        }

        if (discount > 0) {
            bill = bill - Math.round(bill * discount / 100);
        }

        return bill;
    }

//resumen de cuenta abonada en pago parcial  
    public double partialBillPayed(Table tableAux, Salon sal) {
        double partial = 0;
        int discount = tableAux.getDiscount();
        ArrayList<Itemcard> itemsPartial = new ArrayList<>(tableAux.getPartialPayed());
        for (int i = 0; i < itemsPartial.size(); i++) {
            partial += utili.priceMod(itemsPartial.get(i), sal);
        }

        double disc = (double) discount;
        partial = partial * (1 - disc / 100);

        ArrayList<Itemcard> itemsPartialAux = new ArrayList<>(tableAux.getPartialPayedND());
        double partialND = 0;
        for (int i = 0; i < itemsPartialAux.size(); i++) {
            partialND += utili.priceMod(itemsPartialAux.get(i), sal);
        }

        partial += partialND /* + tableAux.getErrorMod*/;
        return partial;
    }

//Resumen de cuenta aplicada a ventana de pago parcial
    public double billPartial(ArrayList<Itemcard> items, int discount, Salon sal) {
        double bill = 0;
        for (Itemcard ic : items) {
            bill += utili.priceMod(ic, sal);
        }
        double disc = discount;
        bill = bill * (1 - disc / 100);
        return bill;
    }

    public void createTable(Salon sal, Table tableAux) throws Exception {
        daoT.saveTable(tableAux, sal.getWorkshiftNow().getOpenWs());
        daoU.saveWaiterTable(tableAux);
    }

    public ArrayList<Itemcard> itemDeployer(Itemcard ic, int num) {
        ArrayList<Itemcard> al = new ArrayList<>();
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

    //GIFT---------------------------------------------------------------------------------------------
    //GIFT---------------------------------------------------------------------------------------------
    //GIFT---------------------------------------------------------------------------------------------
    //GIFT---------------------------------------------------------------------------------------------
    public void giftBacker(Itemcard ic, Salon salon) throws Exception {
        salon.getItemsGift().add(ic);
        salon.getTableAux().setGifts(salon.getItemsGift());
        salon.setItemsTableAux(itemTableLesser(salon.getTableAux().getOrder(), ic));
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        utiliMsg.cargaGift(ic.getName());
        salon.setTotal(countBill(salon.getTableAux(), salon));
        salon.getTableAux().setTotal(salon.getTotal());
        daoIC.downActiveItemOrderTable(ic, salon.getTableAux());
        daoIC.saveItemGiftTable(ic, salon.getTableAux());
        daoT.updateTableTotal(salon.getTableAux());
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        salon.setEnabled(true);
    }

    //DISCOUNT-----------------------------------------------------------------------------------------
    //DISCOUNT-----------------------------------------------------------------------------------------
    //DISCOUNT-----------------------------------------------------------------------------------------
    //DISCOUNT-----------------------------------------------------------------------------------------
    public void discountBacker(int disc, Salon salon) throws Exception {
        salon.setDiscount(disc);
        salon.getTableAux().setDiscount(disc);
        salon.setTotal(countBill(salon.getTableAux(), salon));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());
        daoT.updateTableDiscount(salon.getTableAux());
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        salon.setEnabled(true);
    }

    //ERROR--------------------------------------------------------------------------------------------
    //ERROR--------------------------------------------------------------------------------------------
    //ERROR--------------------------------------------------------------------------------------------
    //ERROR--------------------------------------------------------------------------------------------
    public void errorMountBacker(double errorBack, String cause, Salon salon, double cash, double elec) throws Exception {
        if (salon.getItemsPartialPaid().size() > 0) {
            salon.getItemsTableAux().addAll(salon.getItemsPartialPaid());
            salon.getTableAux().setOrder(salon.getItemsTableAux());
            salon.setItemsPartialPaid(new ArrayList<>());
            salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
            daoIC.downActiveItemPayedTableAll(salon.getTableAux());
            daoIC.upActiveItemOrderTableAll(salon.getTableAux());
            salon.getTableAux().setToPay(false);
            daoT.updateToPay(salon.getTableAux());
        }

        salon.getTableAux().setAmountCash(salon.getTableAux().getAmountCash() + cash);
        daoT.updateTableMountCash(salon.getTableAux());

        salon.getTableAux().setAmountElectronic(salon.getAmountElectronic() + elec);
        daoT.updateTableMountElectronic(salon.getTableAux());

        salon.setTotal(countBill(salon.getTableAux(), salon));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());

        salon.setError(errorBack);
        salon.getTableAux().setError(salon.getError());
        daoT.updateError(salon.getTableAux());

        salon.getTableAux().setComments(cause);
        daoT.updateComments(salon.getTableAux());

        salon.getTableAux().setCloseTime(new Timestamp(new Date().getTime()));
        daoT.updateCloseTime(salon.getTableAux());

        salon.getTableAux().setOpen(false);
        daoT.updateTableOpen(salon.getTableAux());

        sis.createItemSale(salon);

        ArrayList<String> deferTabs = salon.getCfgAct().getArrayDeferWs();
        deferTabs.add(salon.getTableAux().getId());
        daoC.updateCfgActDeferWs(deferTabs);

        utiliMsg.cargaError();
        salon.setEnabled(true);
    }

    //CORRECTOR----------------------------------------------------------------------------------------
    //CORRECTOR----------------------------------------------------------------------------------------
    //CORRECTOR----------------------------------------------------------------------------------------
    //CORRECTOR----------------------------------------------------------------------------------------    
    public void correctItems(Itemcard ic, int num, Salon salon) throws Exception {
        switch (num) {
            case 1:
                salon.setItemsTableAux(itemTableLesser(salon.getItemsTableAux(), ic));
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoIC.downActiveItemOrderTable(ic, salon.getTableAux());
                break;
            case 2:
                salon.setItemsGift(itemTableLesser(salon.getItemsGift(), ic));
                salon.getTableAux().setGifts(salon.getItemsGift());
                salon.getItemsTableAux().add(ic);
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoIC.downActiveItemGiftTable(ic, salon.getTableAux());
                daoIC.upActiveItemOrderTable(ic, salon.getTableAux());
                break;
            case 3:
                salon.setItemsPartialPaid(itemTableLesser(salon.getItemsPartialPaid(), ic));
                salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
                salon.getItemsTableAux().add(ic);
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoIC.downActiveItemPayedTable(ic, salon.getTableAux());
                daoIC.upActiveItemOrderTable(ic, salon.getTableAux());
                break;
        }
        salon.setEnabled(true);
    }

    //PARTIAL PAY--------------------------------------------------------------------------------------
    //PARTIAL PAY--------------------------------------------------------------------------------------
    //PARTIAL PAY--------------------------------------------------------------------------------------
    //PARTIAL PAY--------------------------------------------------------------------------------------
    public void partialPayTaker(ArrayList<Itemcard> itemsPayed, Salon salon) throws Exception {
        for (int i = 0; i < itemsPayed.size(); i++) {
            salon.setItemsTableAux(itemTableLesser(salon.getItemsTableAux(), itemsPayed.get(i)));
        }
        salon.getItemsPartialPaid().addAll(itemsPayed);
        salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        salon.setTotal(countBill(salon.getTableAux(), salon));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());
        salon.getTableAux().setToPay(true);
        daoT.updateToPay(salon.getTableAux());
        for (Itemcard ic : itemsPayed) {
            daoIC.saveItemPayedTable(ic, salon.getTableAux());
            daoIC.downActiveItemOrderTable(ic, salon.getTableAux());
        }
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        double payed = partialBillPayed(salon.getTableAux(), salon);
        salon.getLabelPartialPay().setText("Pagado: $" + (payed));
        salon.setEnabled(true);
    }

    public void totalPayTaker(ArrayList<Itemcard> itemsPayed, Salon salon) throws Exception {
        salon.getItemsTableAux().addAll(salon.getItemsPartialPaid());
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        salon.setItemsPartialPaid(new ArrayList<>());
        salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
        sis.createItemSale(salon);
        daoIC.downActiveItemPayedTableAll(salon.getTableAux());
        daoIC.upActiveItemOrderTableAll(salon.getTableAux());

        salon.setTotal(countBill(salon.getTableAux(), salon));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());

        salon.getTableAux().setOpen(false);
        daoT.updateTableOpen(salon.getTableAux());
        salon.getTableAux().setToPay(false);
        daoT.updateToPay(salon.getTableAux());
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        salon.setEnabled(true);
    }

    //CLOSE WORKSHIFT----------------------------------------------------------------------------------
    //CLOSE WORKSHIFT----------------------------------------------------------------------------------
    //CLOSE WORKSHIFT----------------------------------------------------------------------------------
    //CLOSE WORKSHIFT----------------------------------------------------------------------------------
    public void endWorkshift(Salon salon, Manager manager, boolean errorWs) throws Exception { //errorWs shows 
//        salon.setEnabled(true);
        if (salon != null) {
            if (salon.getBarrButtons().size() > 0) {
                if (salon.getBarrButtons().get(0).isOpenJBB() && salon.getBarrButtons().get(0).getTable().getTotal() == 0) {
                    salon.getBarrButtons().get(0).setOpenJBB(false);
                }
            }

            if (salon.getDeliButtons().size() > 0 && salon.getDeliButtons().get(0).getTable().getTotal() == 0) {
                if (salon.getDeliButtons().get(0).isOpenJBD()) {
                    salon.getDeliButtons().get(0).setOpenJBD(false);
                }
            }
        }

        if (errorWs == false) {
            if (openJBTButtonsTester(salon.getTableButtons(), salon.getBarrButtons(), salon.getDeliButtons()) == false) {
                boolean confirm1 = utiliMsg.cargaConfirmarCambioTurno(salon.getUser());
                if (confirm1 == true) {
                    salon.setEnabled(false);
                    inconcludeWsCutter(salon, salon.getWorkshiftNow(), errorWs);
                }
            } else {
                boolean confirm2 = utiliMsg.cargaConfirmarCierreTurno(salon.getUser().getName(), salon.getUser().getLastName());
                if (confirm2 == true) {
                    salon.setEnabled(false);
                    closeWorkshift(salon, salon.getManager(), salon.getWorkshiftNow(), null, null, null, null, null, errorWs, 0);
                }
            }
        } else {
            ConfigActual cfgAct = daoC.askConfigActual();
            Workshift ws = daoW.askWorshiftById(cfgAct.getOpenIdWs());
            User cashier = daoU.getCashierByWorkshift(ws.getId());
            ws.setCashierWs(cashier);
            ArrayList<Table> prevTabs = st.workshiftTableslistComplete(ws, 2);
            utiliMsg.errorDiferentCashier();
            boolean confirm3 = utiliMsg.cargaConfirmarNuevoTurno();
            if (confirm3) {
                //Cerrar turno abierto por otro usuario y abrir uno nuevo
                closeWorkshift(null, manager, ws, null, null, null, null, null, errorWs, 1);
            } else {
                if (prevTabs.size() > 0) {
                    //Mesas abiertas del turno anterior y abrir uno nuevo
                    boolean confirm5 = utiliMsg.cargaConfirmarOpenTabsOldWs();
                    if (confirm5) {
                        new TabsToEnd(manager, ws, errorWs);
//                        boolean confirm4 = utiliMsg.cargaConfirmAddTables();
//                        if (confirm4) {
//                            new TableAdder(ws, manager);
//                        } else {
//                            closeWorkshift(null, manager, ws, null, null, null, null, null, errorWs, 2);
//                        }
                    }
                } else {
                    boolean confirm6 = utiliMsg.cargaConfirmarCierreTurnoError();
                    if (confirm6 == true) {
                        boolean confirm4 = utiliMsg.cargaConfirmAddTables();
                        if (confirm4) {
                            new TableAdder(ws, manager, null);
                        } else {
                            closeWorkshift(null, manager, ws, null, null, null, null, null, errorWs, 2);
                        }                                                
                    }
                }
            }
        }
    }

    public void closeWorkshift(Salon salon, Manager manager, Workshift actWs, Workshift nWs, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> ersdTabs, ArrayList<Table> updTabs, boolean errorWs, int error) throws Exception {
        if (errorWs) {
            if (error == 1) {
                actWs.setCloseWs(new Timestamp(new Date().getTime()));
                daoW.updateWorkshiftClose(actWs, false);
                actWs.setStateWs(false);
                daoW.updateWorkshiftState(actWs);
                actWs.setCommentWs(actWs.getCommentWs() + "El turno fue cerrado para continuar con el próximo, a la espera de ser completado por el Administrador.<br>");
                daoW.updateWorkshiftComment(actWs);
                actWs.setError(true);
                daoW.updateWorkshiftErrorBool(actWs);
                utiliMsg.cargaErrorWs();
                daoC.updateCfgActOpenIdWs(0);
                daoC.updateCfgActOpenWs(false);
            } else if (error == 2) {
                workshiftConclusive(salon, manager, actWs, nWs, actTabs, nTabs, ersdTabs, updTabs, errorWs);
            }
        } else {
            workshiftConclusive(salon, salon.getManager(), actWs, nWs, actTabs, nTabs, ersdTabs, updTabs, errorWs);
        }
    }

    public void inconcludeWsCutter(Salon sal, Workshift ws, boolean errorWs) throws InterruptedException, Exception {
        Workshift newWs = new Workshift();
        Workshift actualWs = ws;
        ArrayList<Table> actualTabs = null;
        ArrayList<Table> upTabs = new ArrayList<>();
        ArrayList<Table> downTabs = new ArrayList<>();
        ArrayList<Table> toUpdTabs = new ArrayList<>();
        actualWs = setWsEnd(ws);
        actualTabs = st.workshiftTableslistComplete(actualWs, 1);
        Thread.sleep(100);
        newWs.setCashierWs(null);
        newWs.setOpenWs(new Timestamp(new Date().getTime()));
        newWs.setCloseWs(null);
        newWs.setStateWs(true);
        newWs.setTotalMountCashWs(0);
        newWs.setTotalMountElectronicWs(0);
        newWs.setTotalMountWs(0);
        newWs.setTotalMountRealWs(0);
        newWs.setErrorMountWs(0);
        newWs.setErrorMountRealWs(0);
        newWs.setCommentWs(newWs.getCommentWs() + "Turno creado a partir de mesas no cerradas del turno anterior.<br>");
        for (int i = 0; i < actualTabs.size(); i++) {
            Table tab = actualTabs.get(i);
            if (tab.isOpen() == true) {
                boolean bill = tab.isBill();
                boolean activeTable = tab.isActiveTable();
                User waiter = tab.getWaiter();
                int discount = tab.getDiscount();
                double total = 0;
                ArrayList<Itemcard> orderNew = new ArrayList<>();
                String comments = tab.getComments();
                if (tab.isToPay()) {
                    Thread.sleep(10);
                    orderNew = tab.getOrder();
                    Table tabNewWs = new Table(tab.getNum(), tab.getPos(), bill, activeTable, orderNew, waiter, discount, total, comments);
                    tabNewWs.setGifts(tab.getGifts());
                    double total1 = countBill(tabNewWs, sal);
                    tabNewWs.setTotal(total1);
                    tabNewWs.setComments(tabNewWs.getComments() + "Nueva mesa con elementos no pagados del turno anterior.<br>");

                    tab.setOpen(false);
                    tab.setToPay(false);
                    tab.setGifts(new ArrayList<>());
                    tab.setOrder(tab.getPartialPayed());
                    tab.setPartialPayedND(tab.getPartialPayedND());
                    double total2 = countBill(tab, sal);
                    tab.setTotal(total2);
                    tab.setCloseTime(new Timestamp(new Date().getTime()));
                    tab.setPartialPayed(new ArrayList<>());
                    tab.setPartialPayedND(new ArrayList<>());
                    tab.setComments(tab.getComments() + "Los elementos no pagados fueron enviados al siguiente turno.<br>");
                    tab.setActiveTable(true);

                    toUpdTabs.add(tab);
                    upTabs.add(tabNewWs);
                } else {
                    Thread.sleep(10);
                    Table tabNewWs = new Table(tab.getNum(), tab.getPos(), bill, activeTable, orderNew, waiter, discount, total, comments);
                    tabNewWs.setOrder(tab.getOrder());
                    tabNewWs.setGifts(tab.getGifts());
                    total = countBill(tabNewWs, sal);
                    tabNewWs.setTotal(total);
                    tabNewWs.setComments(tabNewWs.getComments() + "La mesa fue dividida por cambio de turno.<br>");
                    tab.setActiveTable(false);
                    upTabs.add(tabNewWs);
                    downTabs.add(tab);
                }
            }
        }
        Thread.sleep(100);
        Timestamp close = new Timestamp(new Date().getTime());
        newWs.setCloseWs(close);
        newWs.setCloseWs(null);
        closeWorkshift(sal, sal.getManager(), actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs, errorWs, 0);
    }

    private Workshift setWsEnd(Workshift ws) throws Exception {
        Workshift newWs = ws;
        int id = daoW.findId(newWs.getOpenWs());
        newWs.setId(id);
        newWs.setCloseWs(new Timestamp(new Date().getTime()));
        newWs.setStateWs(false);
        return newWs;
    }

    public void cashFlowAdd(int flowKind, boolean moneyKind, double cashFlow, String comment, Salon salon) throws Exception {
        boolean kind = true;
        if (flowKind == 2) {
            kind = false;
        }
        int wsId = salon.getWorkshiftNow().getId();

        CashFlow cf = new CashFlow(kind, moneyKind, cashFlow, comment, wsId);
        if (kind == false) { //substr
            if (moneyKind == false) {
                if (salon.getCashFlowElec() - cashFlow < 0) {
                    utiliMsg.errorLackOfFunds();
                } else {
                    salon.setCashFlowElec(salon.getCashFlowElec() - cashFlow);
                    salon.getWorkshiftNow().setCashFlowWsElec(salon.getCashFlowElec());
                    daoW.updateWorkshiftCashFlowElec(salon.getWorkshiftNow());
                    daoCF.saveCashFlow(cf);
                }
            } else {
                if (salon.getCashFlowCash() - cashFlow < 0) {
                    utiliMsg.errorLackOfFunds();
                } else {
                    salon.setCashFlowCash(salon.getCashFlowCash() - cashFlow);
                    salon.getWorkshiftNow().setCashFlowWsCash(salon.getCashFlowCash());
                    daoW.updateWorkshiftCashFlowCash(salon.getWorkshiftNow());
                    daoCF.saveCashFlow(cf);
                }
            }
        } else {
            if (moneyKind == false) {
                salon.setCashFlowElec(salon.getCashFlowElec() + cashFlow);
                salon.getWorkshiftNow().setCashFlowWsElec(salon.getCashFlowElec());
                daoW.updateWorkshiftCashFlowElec(salon.getWorkshiftNow());
                daoCF.saveCashFlow(cf);
            } else {
                salon.setCashFlowCash(salon.getCashFlowCash() + cashFlow);
                salon.getWorkshiftNow().setCashFlowWsCash(salon.getCashFlowCash());
                daoW.updateWorkshiftCashFlowCash(salon.getWorkshiftNow());
                daoCF.saveCashFlow(cf);
            }
        }
    }

    private void workshiftConclusive(Salon salon, Manager manager, Workshift actWs, Workshift nWs, ArrayList<Table> actTabs, ArrayList<Table> nTabs, ArrayList<Table> ersdTabs, ArrayList<Table> updTabs, boolean errorWs) throws Exception {
        Workshift actualWs = actWs;
        Workshift newWs = nWs;
        ArrayList<Table> actualTabs = actTabs;
        ArrayList<Table> upTabs = nTabs;
        ArrayList<Table> downTabs = ersdTabs;
        ArrayList<Table> toUpdTabs = updTabs;
        if (salon != null) {
            if (salon.getDeliButtons().size() > 0) {
                Delivery deli = salon.getDeliButtons().get(0).getDelivery();
                if (deli.getTab() == null) {
                    daoD.updateDownAct(deli);
                }
            }
        }

        if (newWs == null) {
            actualWs = setWsEnd(actualWs);
            actualTabs = st.workshiftTableslistComplete(actualWs, 1);
        }

        if (actualTabs.size() == 0) {
            if (salon != null) {
                boolean confirm = utiliMsg.cargaWorkshiftEmpty();
                if (confirm) {
                    daoW.updateWorkshiftState(actualWs);
                    daoW.downWorkshiftActive(actualWs);
                    daoC.updateCfgActOpenWs(false);
                    daoC.updateCfgActOpenIdWs(0);
                    salon.setWorkshiftNow(null);
                    salon.getLabelWorkshift().setText("Turno no iniciado.");
                    salon.getButInitWorkshift().setText("ABRIR TURNO");
                    salon.setEnabled(true);
                    salon.dispose();
                } else {
                    salon.setEnabled(true);
                }
            }
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
            actualWs.setTotalMountWs(mount);
            actualWs.setErrorMountWs(mountError);
            actualWs.setTotalMountCashWs(mountCash);
            actualWs.setTotalMountElectronicWs(mountElectronic);
            new WorkshiftEndPanel(salon, manager, actualWs, newWs, actualTabs, upTabs, downTabs, toUpdTabs, errorWs);
            if (salon != null) {
                salon.setEnabled(false);
            }
        }
    }
}
