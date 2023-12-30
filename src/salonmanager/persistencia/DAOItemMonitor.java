package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.ItemMonitor;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemMonitor extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveIMon(ItemMonitor im) throws Exception {
        String sql = "";
        int indiLenght = im.getIndications().length();
        if (indiLenght < 450) {
            try {
                sql = "INSERT INTO item_monits(item_monit_id, item_monit_table_id, item_monit_item_id, item_monit_tipe, item_monit_init_bool, item_monit_init_date, item_monit_cook_bool, item_monit_cook_date, item_monit_ready_bool, item_monit_ready_date, item_monit_otw_bool, item_monit_otw_date, item_monit_open, item_monit_active, item_monit_indications) "
                        + "VALUES( '" + im.getIdIMon() + "', '" + im.getTableIMon().getId() + "', " + im.getItemIMon().getId() + ", '" + im.getPosIMon() + "', " + im.isInitIMon() + ", '" + im.getDateInitIMon() + "', " + im.isCookIMon() + ", " + im.getDateCookIMon() + ", " + im.isReadyIMon() + ", " + im.getDateReadyIMon() + ", " + im.isOtwIMon() + ", " + im.getDateOtwIMon() + ", " + im.isOpenItemMonitor() + ", " + im.isActiveItemMonitor() + ", '" + im.getIndications() + "');";
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
            String sql = "UPDATE item_monits SET item_monit_open = " + false + " WHERE item_monit_id = '" + im.getIdIMon() + "';";
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
            String sql = "SELECT * FROM item_monits WHERE item_monit_open = true AND item_monit_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<ItemMonitor> aims = new ArrayList<ItemMonitor>();
            while (resultado.next()) {
                ItemMonitor im = new ItemMonitor();
                String idImon = resultado.getString(1);
                String tableIdIMon = resultado.getString(2);
                int itemIdIMon = resultado.getInt(3);
                String posImon = resultado.getString(4);
                boolean initIMon = resultado.getBoolean(5);
                Timestamp dateInitIMon = resultado.getTimestamp(6);
                boolean cookIMon = resultado.getBoolean(7);
                Timestamp dateCookIMon = resultado.getTimestamp(8);
                boolean readyIMon = resultado.getBoolean(9);
                Timestamp dateReadyIMon = resultado.getTimestamp(10);
                boolean otwIMon = resultado.getBoolean(11);
                Timestamp dateOtwIMon = resultado.getTimestamp(12);
                boolean openItemMonitor = resultado.getBoolean(13);
                boolean activeOpenMonitor = resultado.getBoolean(14);
                String indications = resultado.getString(15);

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
            String sql = "SELECT * FROM item_monits WHERE item_monit_id = " + imId + " AND item_monit_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            ItemMonitor im = new ItemMonitor();

            while (resultado.next()) {
                String idImon = resultado.getString(1);
                String tableIdIMon = resultado.getString(2);
                int itemIdIMon = resultado.getInt(3);
                String posImon = resultado.getString(4);
                boolean initIMon = resultado.getBoolean(5);
                Timestamp dateInitIMon = resultado.getTimestamp(6);
                boolean cookIMon = resultado.getBoolean(7);
                Timestamp dateCookIMon = resultado.getTimestamp(8);
                boolean readyIMon = resultado.getBoolean(9);
                Timestamp dateReadyIMon = resultado.getTimestamp(10);
                boolean otwIMon = resultado.getBoolean(11);
                Timestamp dateOtwIMon = resultado.getTimestamp(12);
                boolean openItemMonitor = resultado.getBoolean(13);
                boolean activeOpenMonitor = resultado.getBoolean(14);
                String indications = resultado.getString(15);
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
