package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemMonitor extends DAO {
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveIMon(ItemMonitor im) throws Exception {
        String sql = "";
        int indiLenght = im.getIndications().length();
        if (indiLenght < 450) {
            try {
                sql = "INSERT INTO item_monits(item_monit_id, item_monit_table_id, item_monit_item_id, item_monit_tipe, item_monit_init_bool, item_monit_init_date, item_monit_cook_bool, item_monit_cook_date, item_monit_ready_bool, item_monit_ready_date, item_monit_otw_bool, item_monit_otw_date, item_monit_open, item_monit_active, item_monit_indications) "
                        + "VALUES( '" + SalonManager.encrypt(im.getIdIMon()) + "', '" + SalonManager.encrypt(im.getTableIMon().getId()) + "', '" + SalonManager.encryptInteger(im.getItemIMon().getId()) + "', '" + SalonManager.encrypt(im.getPosIMon()) + "', '" + SalonManager.encryptBoolean(im.isInitIMon()) + "', '" + SalonManager.encryptTs(im.getDateInitIMon()) + "', '" + SalonManager.encryptBoolean(im.isCookIMon()) + "', '" + SalonManager.encryptTs(im.getDateCookIMon()) + "', '" + SalonManager.encryptBoolean(im.isReadyIMon()) + "', '" + SalonManager.encryptTs(im.getDateReadyIMon()) + "', '" + SalonManager.encryptBoolean(im.isOtwIMon()) + "', '" + SalonManager.encryptTs(im.getDateOtwIMon()) + "', '" + SalonManager.encryptBoolean(im.isOpenItemMonitor()) + "', '" + SalonManager.encryptBoolean(im.isActiveItemMonitor()) + "', '" + SalonManager.encrypt(im.getIndications()) + "');";
                System.out.println(sql);
                insertarModificarEliminar(sql);
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    utiliMsg.errorCargaDB();
                } else {
                    e.printStackTrace();
                }
            } finally {
                desconectarBase();
            }
        } else {
            utiliMsg.errorIndiLenghtExcess();
        }
    }

    public void downOpenImon(ItemMonitor im) throws Exception {
        try {
            String sql = "UPDATE item_monits SET item_monit_open = '" + SalonManager.encryptBoolean(false) + "' WHERE item_monit_id = '" + SalonManager.encrypt(im.getIdIMon()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<ItemMonitor> getItemsMonitorOpen() throws Exception {
        try {
            String sql = "SELECT * FROM item_monits WHERE item_monit_open = '" + SalonManager.encryptBoolean(true) +"' AND item_monit_active = '" + SalonManager.encryptBoolean(true) +"';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<ItemMonitor> aims = new ArrayList<>();
            while (resultado.next()) {
                ItemMonitor im = new ItemMonitor();
                String idImon = SalonManager.decrypt(resultado.getString(1));
                String tableIdIMon = SalonManager.decrypt(resultado.getString(2));
                int itemIdIMon = SalonManager.decryptInteger(resultado.getString(3));
                String posImon = SalonManager.decrypt(resultado.getString(4));
                boolean initIMon = SalonManager.decryptBoolean(resultado.getString(5));
                String create1 = resultado.getString(6);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp dateInitIMon = SalonManager.decryptTs(create1);
                boolean cookIMon = SalonManager.decryptBoolean(resultado.getString(7));
                String create2 = resultado.getString(8);
                if (create2 == null) {
                    create2 = "";
                }
                Timestamp dateCookIMon = SalonManager.decryptTs(create2);
                boolean readyIMon = SalonManager.decryptBoolean(resultado.getString(9));
                String create3 = resultado.getString(10);
                if (create3 == null) {
                    create3 = "";
                }
                Timestamp dateReadyIMon = SalonManager.decryptTs(create3);
                boolean otwIMon = SalonManager.decryptBoolean(resultado.getString(11));
                String create4 = resultado.getString(12);
                if (create4 == null) {
                    create4 = "";
                }
                Timestamp dateOtwIMon = SalonManager.decryptTs(create4);
                boolean openItemMonitor = SalonManager.decryptBoolean(resultado.getString(13));
                boolean activeOpenMonitor = SalonManager.decryptBoolean(resultado.getString(14));
                String indications = SalonManager.decrypt(resultado.getString(15));

                im = new ItemMonitor(idImon, tableIdIMon, itemIdIMon, posImon, initIMon, dateInitIMon, cookIMon, dateCookIMon, readyIMon, dateReadyIMon, otwIMon, dateOtwIMon, openItemMonitor, activeOpenMonitor, indications);
                aims.add(im);
            }
            return aims;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ItemMonitor getItemsMonitorById(String imId) throws Exception {
        try {
            String sql = "SELECT * FROM item_monits WHERE item_monit_id = " + SalonManager.encrypt(imId) + " AND item_monit_active = '" + SalonManager.encryptBoolean(true) +"';";
            System.out.println(sql);
            consultarBase(sql);
            ItemMonitor im = new ItemMonitor();

            while (resultado.next()) {
                String idImon = SalonManager.decrypt(resultado.getString(1));
                String tableIdIMon = SalonManager.decrypt(resultado.getString(2));
                int itemIdIMon = SalonManager.decryptInteger(resultado.getString(3));
                String posImon = SalonManager.decrypt(resultado.getString(4));
                boolean initIMon = SalonManager.decryptBoolean(resultado.getString(5));
                String create1 = resultado.getString(6);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp dateInitIMon = SalonManager.decryptTs(create1);
                boolean cookIMon = SalonManager.decryptBoolean(resultado.getString(7));
                String create2 = resultado.getString(8);
                if (create2 == null) {
                    create2 = "";
                }
                Timestamp dateCookIMon = SalonManager.decryptTs(create2);
                boolean readyIMon = SalonManager.decryptBoolean(resultado.getString(9));
                String create3 = resultado.getString(10);
                if (create3 == null) {
                    create3 = "";
                }
                Timestamp dateReadyIMon = SalonManager.decryptTs(create3);
                boolean otwIMon = SalonManager.decryptBoolean(resultado.getString(11));
                String create4 = resultado.getString(12);
                if (create4 == null) {
                    create4 = "";
                }
                Timestamp dateOtwIMon = SalonManager.decryptTs(create4);
                boolean openItemMonitor = SalonManager.decryptBoolean(resultado.getString(13));
                boolean activeOpenMonitor = SalonManager.decryptBoolean(resultado.getString(14));
                String indications = SalonManager.decrypt(resultado.getString(15));
                im = new ItemMonitor(idImon, tableIdIMon, itemIdIMon, posImon, initIMon, dateInitIMon, cookIMon, dateCookIMon, readyIMon, dateReadyIMon, otwIMon, dateOtwIMon, openItemMonitor, activeOpenMonitor, indications);
            }
            return im;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

}
