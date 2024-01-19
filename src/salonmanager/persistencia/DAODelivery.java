package salonmanager.persistencia;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import salonmanager.entidades.Delivery;

public class DAODelivery extends DAO {

    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveDelivery(Delivery deli) throws Exception {
        try {
            int cmrId = deli.getConsumer().getId();
            String tabId = deli.getTab().getId();
            String deliId = deli.getDeli().getId();
            boolean open = deli.isOpen();
            boolean active = deli.isActive();
            String sql1 = "INSERT INTO deliverys(delivery_consumer, delivery_tab, delivery_user, delivery_open, delivery_active)"
                    + "VALUES(" + cmrId + ", '" + tabId + "', '" + deliId + "', '" + open + "', " + open + ", " + active + ");";
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
            int orderId = deli.getId();
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


/*
    public DeliveryConsumer getConsumerByPhone(String phone) throws Exception {
        try {
            String sql = "SELECT * FROM consumers WHERE consumer_phone = '" + phone + "' AND consumer_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            DeliveryConsumer cmr = new DeliveryConsumer();
            while (resultado.next()) {
                cmr.setId(resultado.getInt(1));
                cmr.setStreet(resultado.getString(2));
                cmr.setNumSt(resultado.getString(3));
                cmr.setDeptFloor(resultado.getString(4));
                cmr.setDeptNum(resultado.getString(5));
                cmr.setDistrict(resultado.getString(6));
                cmr.setArea(resultado.getString(7));
                cmr.setDetails(resultado.getString(8));
                cmr.setName(resultado.getString(9));
                cmr.setPhone(resultado.getString(10));
                cmr.setSocialNetwork(resultado.getString(11));
                cmr.setConsumerActive(resultado.getBoolean(12));
            }
            return cmr;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }


    public ArrayList<String> getConsumersPhone() throws Exception {
        try {
            String sql = "SELECT consumer_phone FROM consumers WHERE consumer_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<String>();
            while (resultado.next()) {
                String ph = resultado.getString(1);
                cmrs.add(ph);
            }
            return cmrs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
*/

//    public ArrayList<String> listarDeliveryConsumersPhones() throws Exception {
//        try {
//            String sql = "SELECT consumers_phone FROM consumers WHERE consumer_active = true;";
//            System.out.println(sql);
//            consultarBase(sql);
//            ArrayList<String> phones = new ArrayList<String>();
//            while (resultado.next()) {
//                String st = resultado.getString(1);
//                phones.add(st);
//            }
//            return phones;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }        
//    }  
}
