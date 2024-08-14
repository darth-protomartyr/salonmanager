package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOConfig extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveConfigGeneral(int totalTab, ArrayList<Integer> numTab, ArrayList<String> strPan, ArrayList<String> strCat, ArrayList<String> chartPan, int tip, boolean cfgActive, boolean init) throws Exception {
        try {
            if (init) {
                deleteConfigGeneral();
            }
            String nums = SalonManager.encrypt(utili.arrayIntToStr(numTab));
            String pans = SalonManager.encrypt(utili.arrayStrToStr(strPan));
            String cats = SalonManager.encrypt(utili.arrayStrToStr(strCat));
            String charts = SalonManager.encrypt(utili.arrayStrToStr(chartPan));
            String act = SalonManager.encryptBoolean(cfgActive);

            String sql1 = "INSERT INTO config_general(config_table_total, config_table_num_panes, config_table_name_panes, config_table_name_categories, config_table_chart_panes, config_table_tip , config_active)"
                    + "VALUES('" + SalonManager.encryptInt(totalTab) + "', '" + nums + "', '" + pans + "', '" + cats + "', '" + charts + "', '" + SalonManager.encryptInt(tip) + "', '" + act + "');";

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
            cfnGen.setTotalTable(SalonManager.decryptInt(resultado.getString(1)));
            cfnGen.setTableNum(utili.strToArrayInt(SalonManager.decrypt(resultado.getString(2))));
            cfnGen.setTablePan(utili.strToArrayStr(SalonManager.decrypt(resultado.getString(3))));
            cfnGen.setTableItemCategories(utili.strToArrayStr(SalonManager.decrypt(resultado.getString(4))));
            cfnGen.setTablePanCh(utili.strToArrayStr(SalonManager.decrypt(resultado.getString(5))));
            cfnGen.setTipPc(SalonManager.decryptInt(resultado.getString(6)));
            cfnGen.setActiveConfig(SalonManager.decryptBoolean(resultado.getString(7)));
        }
        desconectarBase();
        return cfnGen;
    }

    public ConfigActual askConfigActual() throws Exception {
        String sql = "SELECT * FROM config_actual;";
        consultarBase(sql);
        ConfigActual cfnAct = new ConfigActual();
        while (resultado.next()) {
            cfnAct.setOpenWs(SalonManager.decryptBoolean(resultado.getString(1)));
            cfnAct.setOpenIdWs(SalonManager.decryptInt(resultado.getString(2)));
            String defers = resultado.getString(3);
            ArrayList<String> deferTabs = new ArrayList<>();
            if (defers != null) {
                deferTabs = utili.strToArrayStrAlt(SalonManager.decrypt(defers));
            }
            cfnAct.setArrayDeferWs(deferTabs);
            String mods = resultado.getString(4);
            ArrayList<String> modTabs = new ArrayList<>();
            if (mods != null) {
                modTabs = utili.strToArrayStrAlt(SalonManager.decrypt(mods));
            }
            cfnAct.setArrayUnModTabs(modTabs);
        }
        desconectarBase();
        return cfnAct;
    }

    public void updateCfgActOpenWs(boolean boo) throws Exception {
        try {
            String bool = SalonManager.encryptBoolean(boo);
            String sql1 = "UPDATE config_actual SET config_open_ws = '" + bool + "';";
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
            String sql1 = "UPDATE config_actual SET config_open_ws_id = '" + SalonManager.encryptInt(idWs) + "';";
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
            String sql1 = "UPDATE config_actual SET congif_defer_close_ws = '" + SalonManager.encrypt(stArray) + "';";
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
            String space = SalonManager.decrypt(resultado.getString(1));
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
            String cha = SalonManager.decrypt(resultado.getString(1));
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
            String category = SalonManager.decrypt(resultado.getString(1));
            categories.add(category);
        }
        desconectarBase();
        return categories;
    }

    public ArrayList<String> askCategoriesConfig() throws Exception {
        ArrayList<String> categories = new ArrayList<>();
        ConfigGeneral cfgGen = askConfigGeneral();
        categories = cfgGen.getTableItemCategories();
        return categories;
    }

    public void saveSpace(String space) throws Exception {
        try {
            String sql1 = "INSERT INTO spaces(space_name)"
                    + "VALUES('" + SalonManager.encrypt(space) + "');";
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
                    + "VALUES('" + SalonManager.encrypt(category) + "');";
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
                    + "VALUES('" + SalonManager.encrypt(cha) + "');";
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
            String sql1 = "UPDATE config_actual SET congif_unmod_tabs = '" + SalonManager.encrypt(stArray) + "';";
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

    public boolean askCfgNull() throws Exception {
        boolean ask = false;
        String sql = "SELECT config_active FROM config_general;";
        consultarBase(sql);
        while (resultado.next()) {
            ask = SalonManager.decryptBoolean(resultado.getString(1));
        }
        desconectarBase();
        return ask;
    }

    public void saveConfigActual(boolean wsOpen, int wsId, ArrayList<String> defers, ArrayList<String> mods) throws Exception {
        try {
            
            String def = SalonManager.encrypt(utili.arrayStrToStr(defers));
            String mod = SalonManager.encrypt(utili.arrayStrToStr(mods));
            String act = SalonManager.encryptBoolean(wsOpen);
            String id = SalonManager.encryptInt(wsId);
            String sql1 = "INSERT INTO config_actual(config_open_ws, config_open_ws_id, congif_defer_close_ws, congif_unmod_tabs)"
                    + "VALUES('" + act + "', '" + id + "', '" + def + "', '" + mod + "');";
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
