package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.MoneyFlow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOMoneyFlow extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveMoneyFlow(MoneyFlow cf) throws Exception {
        try { 
            String sql1 = "INSERT INTO workshift_flows(workshift_flow_kind, workshift_flow_m_k, workshift_flow_amount, workshift_flow_comment, workshift_flow_time, workshift_id, workshift_flow_active)"
                    + "VALUES(" + cf.isMoneyFwKind() + ", " + cf.isMoneyFwMoneyKind() + ", " + cf.getMoneyFwAmount() + ", '" + cf.getMoneyFwComment() + "', '" + cf.getMoneyFwTime() + "', " + cf.getMoneyFwWsId() + ", " + cf.isMoneyFwActive() + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.cargaMoneyFlowSuccess();
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

    public MoneyFlow askMFbyId(int id) throws Exception {
        MoneyFlow cf = new MoneyFlow();
        try {
            String sql = "SELECT * FROM workshift_flows WHERE workshift_flow_id = " + id + " AND workshift_flow_active = " + true + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cf.setId(resultado.getInt(1));
                cf.setMoneyFwKind(resultado.getBoolean(2));
                cf.setMoneyFwMoneyKind(resultado.getBoolean(3));
                cf.setMoneyFwAmount(resultado.getDouble(4));
                cf.setMoneyFwComment(resultado.getString(5));
                cf.setMoneyFwTime(resultado.getTimestamp(6));
                cf.setMoneyFwWsId(resultado.getInt(7));
                cf.setMoneyFwActive(resultado.getBoolean(8));
            }
            return cf;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
    
    public ArrayList<MoneyFlow> askMFByWorkshift(int wsId) throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<MoneyFlow> cfList = new ArrayList<>();
        try {
            String sql = "SELECT workshift_flow_id FROM workshift_flows WHERE workshift_id = " + wsId + " AND workshift_flow_active = " + true + ";";
            System.out.println(sql);
            consultarBase(sql);
           
            while (resultado.next()) {
                int i = resultado.getInt(1);
                ids.add(i);
            }
            
            for (int i = 0; i < ids.size(); i++) {
                MoneyFlow cf = askMFbyId(ids.get(i));
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
