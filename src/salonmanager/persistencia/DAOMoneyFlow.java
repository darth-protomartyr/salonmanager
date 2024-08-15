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
            String sql1 = "INSERT INTO money_flows( money_flow_id, money_flow_kind, money_flow_m_k, money_flow_amount, money_flow_comment, money_flow_date, money_flow_ws_id, money_flow_active)"
                    + "VALUES('" + SalonManager.encryptInt(cf.getId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwKind()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwMoneyKind()) + "', '" + SalonManager.encryptDouble(cf.getMoneyFwAmount()) + "', '" + SalonManager.encrypt(cf.getMoneyFwComment()) + "', '" + cf.getMoneyFwDate() + "', '" + SalonManager.encryptInt(cf.getMoneyFwWsId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwActive()) + "');";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.cargaMoneyFlowSuccess();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public MoneyFlow askMFbyId(int id) throws Exception {
        MoneyFlow cf = new MoneyFlow();
        try {
            String sql = "SELECT * FROM money_flows WHERE money_flow_id = '" + SalonManager.encryptInt(id) + "' AND money_flow_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cf.setId(SalonManager.decryptInt(resultado.getString(1)));
                cf.setMoneyFwKind(SalonManager.decryptBoolean(resultado.getString(2)));
                cf.setMoneyFwMoneyKind(SalonManager.decryptBoolean(resultado.getString(3)));
                cf.setMoneyFwAmount(SalonManager.decryptDouble(resultado.getString(4)));
                cf.setMoneyFwComment(SalonManager.decrypt(resultado.getString(5)));
                cf.setMoneyFwDate(resultado.getTimestamp(6));
                cf.setMoneyFwWsId(SalonManager.decryptInt(resultado.getString(7)));
                cf.setMoneyFwActive(SalonManager.decryptBoolean(resultado.getString(8)));
            }
            return cf;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public ArrayList<MoneyFlow> askMFByWorkshift(int wsId) throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<MoneyFlow> cfList = new ArrayList<>();
        try {
            String sql = "SELECT money_flow_id FROM money_flows WHERE money_flow_ws_id = '" + SalonManager.encryptInt(wsId) + "' AND money_flo_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
           
            while (resultado.next()) {
                int i = SalonManager.decryptInt(resultado.getString(1));
                ids.add(i);
            }
            
            for (int i = 0; i < ids.size(); i++) {
                MoneyFlow cf = askMFbyId(ids.get(i));
                cfList.add(cf);
            }
            
            return cfList;
        } catch (Exception e) {
            throw e;
        }
    }

    
    public int getMoneyFlowId() throws Exception {
        try {       
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM money_flows;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
            return id;
        } catch (Exception e) {
            throw e;
        }
    }
}