package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.MoneyFlow;
import salonmanager.entidades.bussiness.Register;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOMoneyFlow extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAORegister daoReg = new DAORegister();
    
    public void saveMoneyFlow(MoneyFlow cf) throws Exception {
        try {
            String sql1 = "INSERT INTO money_flows( money_flow_id, money_flow_kind, money_flow_m_k, money_flow_amount, money_flow_comment, money_flow_date, money_flow_ws_id, money_flow_active)"
                    + "VALUES('" + SalonManager.encryptInt(cf.getId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwKind()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwMoneyKind()) + "', '" + SalonManager.encryptDouble(cf.getMoneyFwAmount()) + "', '" + SalonManager.encrypt(cf.getMoneyFwComment()) + "', '" + SalonManager.encryptTs(cf.getMoneyFwDate()) + "', '" + SalonManager.encryptInt(cf.getMoneyFwWsId()) + "', '" + SalonManager.encryptBoolean(cf.isMoneyFwActive()) + "');";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.successMoneyFlow(null);
            
            String inOut = "Salida: ";
            if (cf.isMoneyFwKind()) {
                inOut = "Ingreso: ";
            }
            
            Register rgo = new Register(new Timestamp(new Date().getTime()), SalonManager.getUserId(), "Flujo de Caja", "", 1, inOut + cf.getMoneyFwAmount() + "");
            daoReg.saveRegister(rgo);
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
                cf.setMoneyFwDate(SalonManager.decryptTs(resultado.getString(6)));
                cf.setMoneyFwWsId(SalonManager.decryptInt(resultado.getString(7)));
                cf.setMoneyFwActive(SalonManager.decryptBoolean(resultado.getString(8)));
            }
            return cf;
        } catch (Exception e) {
            throw e;
        }  finally {
            desconectarBase();
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
        }  finally {
            desconectarBase();
        }
    }

    
    public int getMoneyFlowId() throws Exception {
        try {   
            ArrayList<Integer> ids = new ArrayList<>();
            int max = 1;
            int id = 0;
            String sql = "SELECT money_flow_id FROM money_flows;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                id = SalonManager.decryptInt(resultado.getString(1));
                ids.add(id);
            }
            int max2 = 0;
            
            if (ids.size() > 0) {
                max2 = Collections.max(ids);
            }
            
            max =max + max2;
            return max;            
        } catch (Exception e) {
            throw e;
        }  finally {
            desconectarBase();
        }
    }
}