package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOConfig extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveConfigGeneral(int totalTab, ArrayList<Integer> numTab, ArrayList<String> strPan, ArrayList<String> chartPan, boolean wsActive, int wsId, boolean sessionOpen, int sessionOpenId, Timestamp lastSession, boolean cfgActive) throws Exception {
        try {
            deleteConfigGeneral();
            String nums = utili.arrayIntToStr(numTab);
            String pans = utili.arrayStrToStr(strPan);
            String charts = utili.arrayStrToStr(chartPan);
            String sql1
                    = "INSERT INTO config_general(config_table_total, config_table_num_panes, config_table_name_panes, config_table_chart_panes, config_open_ws, config_open_ws_id, config_open_session, config_open_session_id, config_last_session_time, config_active))"
                    + "VALUES('" + totalTab + "', '" + nums + "', '" + pans + "', '" + charts + "', " + wsActive + ", " + wsId + ", " + sessionOpen + ", " + sessionOpenId + ", " + lastSession + ", " + cfgActive + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    private void deleteConfigGeneral() throws Exception {
        try {
            String sql1 = "DELETE FROM config_general;";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public ConfigGeneral askConfigGeneral() throws Exception {
        String sql = "SELECT * FROM config_general;";
        consultarBase(sql);
        ConfigGeneral cfnGen = new ConfigGeneral();
        while (resultado.next()) {
            cfnGen.setTotalTable(resultado.getInt(1));
            cfnGen.setTableNum(utili.strToArrayInt(resultado.getString(2)));
            cfnGen.setTablePan(utili.strToArrayStr(resultado.getString(3)));
            cfnGen.setTablePanCh(utili.strToArrayStr(resultado.getString(4)));
            cfnGen.setActiveConfig(resultado.getBoolean(5));
        }
        desconectarBase();
        return cfnGen;
    }

    public ConfigActual askConfigActual() throws Exception {
        String sql = "SELECT * FROM config_actual;";
        consultarBase(sql);
        ConfigActual cfnAct = new ConfigActual();
        while (resultado.next()) {
            cfnAct.setOpenWs(resultado.getBoolean(1));
            cfnAct.setOpenIdWs(resultado.getInt(2));
            cfnAct.setOpenSession(resultado.getBoolean(3));
            cfnAct.setOpenIdSession(resultado.getInt(4));
            cfnAct.setLastSessionOpen(resultado.getTimestamp(5));
            cfnAct.setArrayDeferWs(utili.strToArrayInt(resultado.getString(6)));
        }
        desconectarBase();
        return cfnAct;
    }

//    public void upOpenSession() {
//        
//    }
//
//    public void upOpenSessionId(Session sess) {
//        
//    }
    public void updateCfgActOpenWs(boolean bool) throws Exception {
        try {
            String sql1 = "UPDATE config_actual SET config_open_ws = " + bool + ";";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateCfgActOpenIdWs(int idWs) throws Exception {
        try {
            String sql1 = "UPDATE config_actual SET config_open_ws_id = '" + idWs + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateCfgActOpenSession(boolean bool) throws Exception {
        try {
            String sql1 = "UPDATE config_actual SET config_open_session = " + bool + ";";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateCfgActOpenSessionId(int idSess) throws Exception {
        try {
            String sql1 = "UPDATE config_actual SET config_open_session_id = '" + idSess + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateCfgActLastSessionOpen(Timestamp ts) throws Exception {
        try {
            String sql1 = "UPDATE config_actual SET config_last_session_time = '" + ts + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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
    
    
    public void updateCfgAct(ArrayList<Integer> arrayDeferWs) throws Exception {
            String stArray =  utili.arrayIntToStr(arrayDeferWs);
        try {
            String sql1 = "UPDATE config_actual SET congif_defer_close_ws = '" + stArray + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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
    
    

//    public int askOpenIdSession(Timestamp openSession) throws Exception {
//        try {
//            String sql = "SELECT  FROM sessions WHERE session_open = '" + openSession + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            int id = 0;
//            while (resultado.next()) {
//                id = resultado.getInt(1);
//            }
//            return id;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }
}
