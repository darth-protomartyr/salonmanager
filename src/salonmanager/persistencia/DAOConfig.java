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

    public void saveConfigGeneral(int totalTab, ArrayList<Integer> numTab, ArrayList<String> strPan, ArrayList<String> chartPan, boolean cfgActive) throws Exception {
        try {
            deleteConfigGeneral();
            String nums = utili.arrayIntToStr(numTab);
            String pans = utili.arrayStrToStr(strPan);
            String charts = utili.arrayStrToStr(chartPan);
            String sql1
                    = "INSERT INTO config_general(config_table_total, config_table_num_panes, config_table_name_panes, config_table_chart_panes, config_active))"
                    + "VALUES('" + totalTab + "', '" + nums + "', '" + pans + "', '" + charts + "', " + cfgActive + ");";
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
            cfnAct.setArrayDeferWs(utili.strToArrayInt(resultado.getString(3)));
        }
        desconectarBase();
        return cfnAct;
    }

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
    
    
    public void updateCfgActDeferWs(ArrayList<Integer> arrayDeferWs) throws Exception {
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
}
