package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.Config;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOConfig extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void guardarConfig(int totalTab, ArrayList<Integer> numTab, ArrayList<String> strPan, ArrayList<String> chartPan) throws Exception {
        try {
            deleteConfig();
            String nums = utili.arrayIntToStr(numTab);
            String pans = utili.arrayStrToStr(strPan);
            String charts = utili.arrayStrToStr(chartPan);
            String sql1
                    = "INSERT INTO config(config_table_total, config_table_numPanes, confug_table_namePanes, config_table_chartPanes)"
                    + "VALUES('" + totalTab + "', '" + nums + "', '" + pans + "', '" + charts + "');";
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

    private void deleteConfig() throws Exception {
        try {
            String sql1 = "DELETE FROM config;";
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
    
    public Config consultarConfig() throws Exception {
        String sql = "SELECT * FROM config;";
        consultarBase(sql);
        Config cfn = new Config();
        while (resultado.next()) {
            cfn.setTotalTable(resultado.getInt(1));
            cfn.setTableNum(utili.strToArrayInt(resultado.getString(2)));
            cfn.setTablePan(utili.strToArrayStr(resultado.getString(3)));
            cfn.setTablePanCh(utili.strToArrayStr(resultado.getString(4)));
        }
        desconectarBase();
        return cfn;
    }
    
    
    
    
}
