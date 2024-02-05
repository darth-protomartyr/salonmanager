package salonmanager.persistencia;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import salonmanager.entidades.bussiness.Delivery;

public class DAODelivery extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveDelivery(Delivery deli) throws Exception {
        try {
            int cmrId = deli.getConsumer().getId();
            String tabId = "";
            if (deli.getTab() != null) {
                tabId = deli.getTab().getId();
            }
            String deliId = deli.getDeli().getId();
            boolean open = deli.isOpen();
            boolean active = deli.isActive();
            String sql1 = "INSERT INTO deliverys(delivery_id, delivery_consumer, delivery_tab, delivery_user, delivery_open, delivery_active)"
                    + "VALUES('" + deli.getId() + "'," + cmrId + ", '" + tabId + "', '" + deliId + "', " + open + ", " + active + ");";
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
            int cmrId = deli.getConsumer().getId();
            String tabId = deli.getTab().getId();
            String deliId = deli.getDeli().getId();
            boolean open = deli.isOpen();
            boolean active = deli.isActive();
            String sql1 = "UPDATE deliverys SET delivery_consumer = " + cmrId + ", delivery_tab = '" + tabId + "', delivery_user = '" + deliId
                    + "', delivery_open = '" + open + "', delivery_active = '" + active + "'"
                    + " WHERE delivery_id = " + orderId + ";";            
            
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
}
