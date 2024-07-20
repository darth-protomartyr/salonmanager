package salonmanager.servicios;

import java.sql.Timestamp;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.MoneyFlow;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOMoneyFlow;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServiceMoneyFlow {
    DAOWorkshift daoW = new DAOWorkshift();
    DAOMoneyFlow daoCF = new DAOMoneyFlow();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    
    public void moneyFlowMod(boolean moneyKind,double amount, String comment, Workshift ws) throws Exception {
        Timestamp ts = utili.updateTmestamp(ws.getOpenWs(), -5);
        int wsId = ws.getId();
        MoneyFlow mf = new MoneyFlow(true, moneyKind, amount, comment, ts, wsId);
        int id = daoCF.getMoneyFlowId();
        mf.setId(id);
        daoCF.saveMoneyFlow(mf);
    }
    
    
    public void moneyFlowAdd(int flowKind, boolean moneyKind, double moneyFlow, String comment, Salon salon) throws Exception {
        boolean kind = true;
        if (flowKind == 2) {
            kind = false;
        }
        int wsId = salon.getWorkshiftNow().getId();
        MoneyFlow mf = new MoneyFlow(kind, moneyKind, moneyFlow, comment, wsId);
        int id = daoCF.getMoneyFlowId();
        mf.setId(id);
        if (kind == false) { //substr
            if (moneyKind == false) {
                if (salon.getMoneyFlowElec() - moneyFlow < 0) {
                    utiliMsg.errorLackOfFunds();
                } else {
                    salon.setMoneyFlowElec(salon.getMoneyFlowElec() - moneyFlow);
                    salon.getWorkshiftNow().setMoneyFlowWsElec(salon.getMoneyFlowElec());
                    daoW.updateWorkshiftMoneyFlowElec(salon.getWorkshiftNow());
                    daoCF.saveMoneyFlow(mf);
                }
            } else {
                if (salon.getMoneyFlowCash() - moneyFlow < 0) {
                    utiliMsg.errorLackOfFunds();
                } else {
                    salon.setMoneyFlowCash(salon.getMoneyFlowCash() - moneyFlow);
                    salon.getWorkshiftNow().setMoneyFlowWsCash(salon.getMoneyFlowCash());
                    daoW.updateWorkshiftMoneyFlowCash(salon.getWorkshiftNow());
                    daoCF.saveMoneyFlow(mf);
                }
            }
        } else {
            if (moneyKind == false) {
                salon.setMoneyFlowElec(salon.getMoneyFlowElec() + moneyFlow);
                salon.getWorkshiftNow().setMoneyFlowWsElec(salon.getMoneyFlowElec());
                daoW.updateWorkshiftMoneyFlowElec(salon.getWorkshiftNow());
                daoCF.saveMoneyFlow(mf);
            } else {
                salon.setMoneyFlowCash(salon.getMoneyFlowCash() + moneyFlow);
                salon.getWorkshiftNow().setMoneyFlowWsCash(salon.getMoneyFlowCash());
                daoW.updateWorkshiftMoneyFlowCash(salon.getWorkshiftNow());
                daoCF.saveMoneyFlow(mf);
            }
        }
    }
}