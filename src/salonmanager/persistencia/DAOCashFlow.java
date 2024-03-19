package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.CashFlow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOCashFlow extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveCashFlow(CashFlow cf) throws Exception {
        try { 
            String sql1 = "INSERT INTO workshift_flows(workshift_flow_kind, workshift_flow_m_k, workshift_flow_amount, workshift_flow_comment, workshift_flow_time, workshift_id, workshift_flow_active)"
                    + "VALUES(" + cf.isCashFwKind() + ", " + cf.isCashFwMoneyKind() + ", " + cf.getCashFwAmount() + ", '" + cf.getCashFwComment() + "', '" + cf.getCashFwTime() + "', " + cf.getCashFwWsId() + ", " + cf.isCashFwActive() + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.cargaCashFlowSuccess();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public CashFlow askCFbyId(int id) throws Exception {
        CashFlow cf = new CashFlow();
        try {
            String sql = "SELECT * FROM workshift_flows WHERE workshift_flow_id = " + id + " AND workshift_flow_active = " + true + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cf.setId(resultado.getInt(1));
                cf.setCashFwKind(resultado.getBoolean(2));
                cf.setCashFwMoneyKind(resultado.getBoolean(3));
                cf.setCashFwAmount(resultado.getDouble(4));
                cf.setCashFwComment(resultado.getString(5));
                cf.setCashFwTime(resultado.getTimestamp(6));
                cf.setCashFwWsId(resultado.getInt(7));
                cf.setCashFwActive(resultado.getBoolean(8));
            }
            return cf;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
    
    public ArrayList<CashFlow> askCfByWorkshift(int wsId) throws Exception {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<CashFlow> cfList = new ArrayList<CashFlow>();
        try {
            String sql = "SELECT workshift_flow_id FROM workshift_flows WHERE workshift_id = " + wsId + " AND workshift_flow_active = " + true + ";";
            System.out.println(sql);
            consultarBase(sql);
           
            while (resultado.next()) {
                int i = resultado.getInt(1);
                ids.add(i);
            }
            
            for (int i = 0; i < ids.size(); i++) {
                CashFlow cf = askCFbyId(ids.get(i));
                cfList.add(cf);
            }
           
            return cfList;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}
