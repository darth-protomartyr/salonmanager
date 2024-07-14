package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOConfig extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveConfigGeneral(int totalTab, ArrayList<Integer> numTab, ArrayList<String> strPan, ArrayList<String> strCat, ArrayList<String> chartPan, boolean cfgActive, int tip) throws Exception {
        try {
            deleteConfigGeneral();
            String nums = utili.arrayIntToStr(numTab);
            String pans = utili.arrayStrToStr(strPan);
            String cats = utili.arrayStrToStr(strCat);
            String charts = utili.arrayStrToStr(chartPan);
            
            String sql1 = "INSERT INTO config_general(config_table_total, config_table_num_panes, config_table_name_panes, config_table_name_categories, config_table_chart_panes, config_table_tip , config_active)"
                    + "VALUES('" + totalTab + "', '" + nums + "', '" + pans + "', '" + cats + "', '" + charts + "', " + tip + ", " + cfgActive + ");";
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
            cfnGen.setTableItemCategories(utili.strToArrayStr(resultado.getString(4)));
            cfnGen.setTablePanCh(utili.strToArrayStr(resultado.getString(5)));
            cfnGen.setTipPc(resultado.getInt(6));
            cfnGen.setActiveConfig(resultado.getBoolean(7));
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
            cfnAct.setArrayDeferWs(utili.strToArrayStrAlt(resultado.getString(3)));
            cfnAct.setArrayUnModTabs(utili.strToArrayStrAlt(resultado.getString(4)));
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

    public void updateCfgActDeferWs(ArrayList<String> arrayDeferWs) throws Exception {
        String stArray = utili.arrayStrToStrAlt(arrayDeferWs);
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

    public ArrayList<String> askSpaces() throws Exception {
        ArrayList<String> spaces = new ArrayList<>();
        String sql = "SELECT * FROM spaces;";
        consultarBase(sql);
        while (resultado.next()) {
            String space = resultado.getString(1);
            spaces.add(space);
        }
        desconectarBase();
        return spaces;
    }

    public ArrayList<String> askChars() throws Exception {
        ArrayList<String> chars = new ArrayList<>();
        String sql = "SELECT * FROM chars;";
        consultarBase(sql);
        while (resultado.next()) {
            String cha = resultado.getString(1);
            chars.add(cha);
        }
        desconectarBase();
        return chars;
    }

    public ArrayList<String> askCategories() throws Exception {
        ArrayList<String> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories;";
        consultarBase(sql);
        while (resultado.next()) {
            String category = resultado.getString(1);
            categories.add(category);
        }
        desconectarBase();
        return categories;
    }

    public void saveSpace(String space) throws Exception {
        try {
            String sql1 = "INSERT INTO spaces(space_name)"
                    + "VALUES('" + space + "');";
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

    public void saveCategory(String category) throws Exception {
        try {
            String sql1 = "INSERT INTO categories(category_name)"
                    + "VALUES('" + category + "');";
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

    public void saveChar(String cha) throws Exception {
        try {
            String sql1 = "INSERT INTO chars(char_name)"
                    + "VALUES('" + cha + "');";
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

    public void updateCfgActModTabs(ArrayList<String> unmodTabs) throws Exception {
        String stArray = "";
        if (unmodTabs.size() > 0) {
            stArray = utili.arrayStrToStrAlt(unmodTabs);
        }

        try {
            String sql1 = "UPDATE config_actual SET congif_unmod_tabs = '" + stArray + "';";
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