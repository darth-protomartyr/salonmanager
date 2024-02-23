package salonmanager.persistencia;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.bussiness.Table;

public class DAODelivery extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveDelivery(Delivery deli) throws Exception {
        try {
            String cmrPhone = deli.getConsumer().getPhone();
            String tabId = "";
            if (deli.getTab() != null) {
                tabId = deli.getTab().getId();
            }
            String deliId = deli.getDeli().getId();
            boolean open = deli.isOpen();
            boolean active = deli.isActive();
            String sql1 = "INSERT INTO deliverys(delivery_id, delivery_consumer_phone, delivery_tab_id, delivery_user_id, delivery_open, delivery_active)"
                    + "VALUES('" + deli.getId() + "'," + cmrPhone + ", '" + tabId + "', '" + deliId + "', " + open + ", " + active + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void updateDelivery(Delivery deli) throws Exception {
        try {
            String orderId = deli.getId();
            String cmrPhone = deli.getConsumer().getPhone();
            String tabId = "";
            if (deli.getTab() != null) {
                tabId = deli.getTab().getId();
            }
            String deliId = deli.getDeli().getId();
            boolean open = deli.isOpen();
            boolean active = deli.isActive();
            String sql1 = "UPDATE deliverys SET delivery_consumer_phone = " + cmrPhone
                    + ", delivery_tab_id = '" + tabId + "', delivery_user_id = '" + deliId
                    + "', delivery_open = " + open + ", delivery_active = " + active 
                    + " WHERE delivery_id = '" + orderId + "';";

            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void updateDownAct(Delivery deli) throws Exception {

        try {
            String sql1 = "UPDATE deliverys SET delivery_active = " + false + " WHERE delivery_id = '" + deli.getId() + "';";
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

    public Delivery findDeliveryByTableId(String idTab) throws Exception {
        Delivery deli = null;
        try {
            String sql = "SELECT * FROM deliverys WHERE delivery_tab_id = '" + idTab + "';";
            System.out.println(sql);
            consultarBase(sql);
            String id = "";
            String phone = "";
            String tab = "";
            String deliId = "";
            boolean open = false;
            boolean active = false;

            while (resultado.next()) {
                id = resultado.getString(1);
                phone = resultado.getString(2);
                tab = resultado.getString(3);
                deliId = resultado.getString(4);
                open = resultado.getBoolean(5);
                active = resultado.getBoolean(6);
            }
            deli = new Delivery(id, phone, tab, deliId, open, active);
            return deli;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void updateDeliveryTable(String tabId, String deli) throws Exception {
        try {
            String sql1 = "UPDATE deliverys SET delivery_tab_id = " + tabId + " WHERE delivery_id = '" + deli + "';";
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
