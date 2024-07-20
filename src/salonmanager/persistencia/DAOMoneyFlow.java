package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.MoneyFlow;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOMoneyFlow extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveMoneyFlow(MoneyFlow cf) throws Exception {
        try { 
            String sql1 = "INSERT INTO workshift_flows( workshift_flow_id, workshift_flow_kind, workshift_flow_m_k, workshift_flow_amount, workshift_flow_comment, workshift_flow_time, workshift_id, workshift_flow_active)"
                    + "VALUES('" + SalonManager.encryptInteger(cf.getId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwKind()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwMoneyKind()) + "', '" + SalonManager.encryptDouble(cf.getMoneyFwAmount()) + "', '" + SalonManager.encrypt(cf.getMoneyFwComment()) + "', '" + SalonManager.encryptTs(cf.getMoneyFwTime()) + "', '" + SalonManager.encryptInteger(cf.getMoneyFwWsId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwActive()) + "');";
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
            String sql = "SELECT * FROM workshift_flows WHERE workshift_flow_id = '" + SalonManager.encryptInteger(id) + "' AND workshift_flow_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cf.setId(SalonManager.decryptInteger(resultado.getString(1)));
                cf.setMoneyFwKind(SalonManager.decryptBoolean(resultado.getString(2)));
                cf.setMoneyFwMoneyKind(SalonManager.decryptBoolean(resultado.getString(3)));
                cf.setMoneyFwAmount(SalonManager.decryptDouble(resultado.getString(4)));
                cf.setMoneyFwComment(SalonManager.decrypt(resultado.getString(5)));
                String create1 = resultado.getString(6);
                if (create1 == null) {
                    create1 = "";
                }
                cf.setMoneyFwTime(SalonManager.decryptTs(create1));
                cf.setMoneyFwWsId(SalonManager.decryptInteger(resultado.getString(7)));
                cf.setMoneyFwActive(SalonManager.decryptBoolean(resultado.getString(8)));
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
            String sql = "SELECT workshift_flow_id FROM workshift_flows WHERE workshift_id = '" + SalonManager.encryptInteger(wsId) + "' AND workshift_flow_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
           
            while (resultado.next()) {
                int i = SalonManager.decryptInteger(resultado.getString(1));
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

    
    public int getMoneyFlowId() throws Exception {
        try {       
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM workshift_flows;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}