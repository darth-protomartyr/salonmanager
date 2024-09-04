package salonmanager.servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import salonmanager.LandingFrame;
import salonmanager.Salon;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServicioWorkshift {

    DAOConfig daoC = new DAOConfig();
    DAOItemcard daoI = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
    DAOWorkshift daoW = new DAOWorkshift();
    ServicioTable st = new ServicioTable();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveWorkshift(Workshift actualWs, Workshift newWs, ArrayList<Table> actualTabs, ArrayList<Table> newTabs, ArrayList<Table> toEraseTabs, ArrayList<Table> toUpdTabs, Salon salon) throws Exception {
        boolean isTabs = false;
        if (actualTabs.size() + newTabs.size() + toUpdTabs.size() > 0) {
            isTabs = true;
        }
        daoW.updateWorkshiftCash(actualWs);
        daoW.updateWorkshiftElectronic(actualWs);
        daoW.updateWorkshiftTabs(actualWs);
        daoW.updateWorkshiftMountWs(actualWs);
        daoW.updateWorkshiftError(actualWs);
        daoW.updateWorkshiftErrorWs(actualWs);
        daoW.updateWorkshiftClose(actualWs, isTabs);
        daoW.updateWorkshiftState(actualWs);
        daoW.updateWorkshiftComment(actualWs);
        daoC.updateCfgActOpenWs(false);
        daoC.updateCfgActOpenIdWs(0);
        daoC.updateCfgActModTabs(new ArrayList<String>());
        ArrayList<Integer>indexes =  new  ArrayList<>();
        indexes.add(0);
        indexes.add(0);
        daoC.updateIndexes(indexes, true);
        if (newWs != null) {
            daoW.saveWorkshift(newWs);
            int id = daoW.askWorshiftActualId();
            daoC.updateCfgActOpenWs(true);
            daoC.updateCfgActOpenIdWs(id);
            salon.getCfgAct().setOpenWs(true);
            salon.getCfgAct().setOpenIdWs(newWs.getId());
            for (Table t : toEraseTabs) {

                if (t.getOrder().size() > 0) {
                    daoI.downActiveItemOrderTableAll(t);
                }

                if (t.getGifts().size() > 0) {
                    daoI.downActiveItemGiftTableAll(t);
                }

                if (t.getPartialPayed().size() > 0) {
                    daoI.downActiveItemPayedTableAll(t);
                }

                if (t.getPartialPayedND().size() > 0) {
                    daoI.downActiveItemPayedNDTableAll(t);
                }
                daoT.downActiveTable(t);
            }

            if (toUpdTabs.size() > 0) {
                for (int i = 0; i < toUpdTabs.size(); i++) {
                    Table t = toUpdTabs.get(i);
                    daoI.downActiveItemOrderTableAll(t);
                    daoI.downActiveItemGiftTableAll(t);
                    daoI.downActiveItemPayedTableAll(t);
                    daoI.downActiveItemPayedNDTableAll(t);
                    daoT.updateComments(t);
                    daoT.updateTableTotal(t);
                    if (t.getOrder().size() > 0) {
                        for (Itemcard ic : t.getOrder()) {
                            daoI.upActiveItemOrderTable(ic, t);
                        }
                    }

                    if (t.getGifts().size() > 0) {
                        for (Itemcard ic : t.getGifts()) {
                            daoI.upActiveItemGiftTable(t, ic);
                        }
                    }
                    daoT.updateTableOpen(t);
                    daoT.updateToPay(t);
                }
            }

            for (Table t : newTabs) {
                st.saveTableCompleteChangeWs(t, salon);
            }
            utiliMsg.cargaCloseWsPending();
            SalonManager.frameCloser();
            System.exit(0);
        } else {
            utiliMsg.cargaCloseWs();
        }
    }
}
