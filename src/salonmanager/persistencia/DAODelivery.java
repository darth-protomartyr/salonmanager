package salonmanager.persistencia;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Delivery;

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
            
            String activeUser = SalonManager.encryptBoolean(active);
            String activeOpen = SalonManager.encryptBoolean(open);

            String sql1 = "INSERT INTO deliverys(delivery_id, delivery_consumer_phone, delivery_tab_id, delivery_user_id, delivery_open, delivery_active)"
                    + "VALUES('" + SalonManager.encrypt(deli.getId()) + "','" + SalonManager.encrypt(cmrPhone) + "', '" + SalonManager.encrypt(tabId) + "', '" + SalonManager.encrypt(deliId) + "', '" + activeOpen + "', '" + activeUser + "');";
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
            
            String activeUser = SalonManager.encryptBoolean(active);
            String activeOpen = SalonManager.encryptBoolean(open);
            
            String sql1 = "UPDATE deliverys SET delivery_consumer_phone = " + SalonManager.encrypt(cmrPhone)
                    + ", delivery_tab_id = '" + SalonManager.encrypt(tabId) + "', delivery_user_id = '" + SalonManager.encrypt(deliId)
                    + "', delivery_open = '" + activeOpen + "', delivery_active = '" + activeUser 
                    + "' WHERE delivery_id = '" + SalonManager.encrypt(orderId) + "';";

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
            String activeDelivery = SalonManager.encryptBoolean(false);            
            
            String sql1 = "UPDATE deliverys SET delivery_active = '" + activeDelivery + "' WHERE delivery_id = '" + SalonManager.encrypt(deli.getId()) + "';";
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
            String sql = "SELECT * FROM deliverys WHERE delivery_tab_id = '" + SalonManager.encrypt(idTab) + "';";
            System.out.println(sql);
            consultarBase(sql);
            String id = "";
            String phone = "";
            String tab = "";
            String deliId = "";
            boolean open = false;
            boolean active = false;
            while (resultado.next()) {
                id = SalonManager.decrypt(resultado.getString(1));
                phone = SalonManager.decrypt(resultado.getString(2));
                tab = SalonManager.decrypt(resultado.getString(3));
                deliId = SalonManager.decrypt(resultado.getString(4));
                open = SalonManager.decryptBoolean(resultado.getString(5));
                active = SalonManager.decryptBoolean(resultado.getString(6));
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
            String sql1 = "UPDATE deliverys SET delivery_tab_id = " + SalonManager.encrypt(tabId) + " WHERE delivery_id = '" + SalonManager.encrypt(deli) + "';";
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