package salonmanager.persistencia;

import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.DeliveryConsumer;

public class DAODeliveryConsumer extends DAO {

    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveConsumer(DeliveryConsumer cmr) throws Exception {
        try {
            String id = SalonManager.encryptInteger(cmr.getId());
            String street = SalonManager.encrypt(cmr.getStreet());
            String numSt = SalonManager.encrypt(cmr.getNumSt());
            String deptFloor = SalonManager.encrypt(cmr.getDeptFloor());
            String deptNum = SalonManager.encrypt(cmr.getDeptNum());
            String district = SalonManager.encrypt(cmr.getDistrict());
            String area = SalonManager.encrypt(cmr.getArea());
            String details = SalonManager.encrypt(cmr.getDetails());
            String name = SalonManager.encrypt(cmr.getName());
            String phone = SalonManager.encrypt(cmr.getPhone());
            String socialNetwork = SalonManager.encrypt(cmr.getSocialNetwork());
            String active = SalonManager.encryptBoolean(cmr.isConsumerActive());

            String sql1 = "INSERT INTO consumers(consumer_id, consumer_street, consumer_street_num, consumer_dept_floor, consumer_dept_num, consumer_district, consumer_area, consumer_details, consumer_name, consumer_phone, consume_social_network, consumer_active)"
                    + "VALUES('" +  SalonManager.encrypt(id) + "', '" + SalonManager.encrypt(street) + "', '" +  SalonManager.encrypt(numSt) + "', '" +  SalonManager.encrypt(deptFloor) + "', '" +  SalonManager.encrypt(deptNum) + "', '" +  SalonManager.encrypt(district) + "', '" +  SalonManager.encrypt(area) + "', '" +  SalonManager.encrypt(details) + "', '" +  SalonManager.encrypt(name) + "', '" +  SalonManager.encrypt(phone) + "', '" +  SalonManager.encrypt(socialNetwork) + "', '" +  SalonManager.encrypt(active) + "');";

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

    
    public DeliveryConsumer getConsumerByPhone(String phone) throws Exception {
        try {
            String sql = "SELECT * FROM consumers WHERE consumer_phone = '" + SalonManager.encrypt(phone) + "' AND consumer_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            DeliveryConsumer cmr = new DeliveryConsumer();
            while (resultado.next()) {
                cmr.setId(SalonManager.decryptInteger(resultado.getString(1)));
                cmr.setStreet(SalonManager.decrypt(resultado.getString(2)));
                cmr.setNumSt(SalonManager.decrypt(resultado.getString(3)));
                cmr.setDeptFloor(SalonManager.decrypt(resultado.getString(4)));
                cmr.setDeptNum(SalonManager.decrypt(resultado.getString(5)));
                cmr.setDistrict(SalonManager.decrypt(resultado.getString(6)));
                cmr.setArea(SalonManager.decrypt(resultado.getString(7)));
                cmr.setDetails(SalonManager.decrypt(resultado.getString(8)));
                cmr.setName(SalonManager.decrypt(resultado.getString(9)));
                cmr.setPhone(SalonManager.decrypt(resultado.getString(10)));
                cmr.setSocialNetwork(SalonManager.decrypt(resultado.getString(11)));
                cmr.setConsumerActive(SalonManager.decryptBoolean(resultado.getString(12)));
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
            String sql = "SELECT consumer_phone FROM consumers WHERE consumer_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                String ph = SalonManager.decrypt(resultado.getString(1));
                cmrs.add(ph);
            }
            return cmrs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    
    public ArrayList<String> listarPhones() throws Exception {
        try {
            String sql = "SELECT consumers_phone FROM consumers WHERE consumer_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> phones = new ArrayList<>();
            while (resultado.next()) {
                String st = SalonManager.decrypt(resultado.getString(1));
                phones.add(st);
            }
            return phones;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    
    public void updateConsumer(DeliveryConsumer cmr, int id) throws Exception {
        try {
            String street = SalonManager.encrypt(cmr.getStreet());
            String numSt = SalonManager.encrypt(cmr.getNumSt());
            String deptFloor = SalonManager.encrypt(cmr.getDeptFloor());
            String deptNum = SalonManager.encrypt(cmr.getDeptNum());
            String district = SalonManager.encrypt(cmr.getDistrict());
            String area = SalonManager.encrypt(cmr.getArea());
            String details = SalonManager.encrypt(cmr.getDetails());
            String name = SalonManager.encrypt(cmr.getName());
            String phone = SalonManager.encrypt(cmr.getPhone());
            String socialNetwork = SalonManager.encrypt(cmr.getSocialNetwork());
            String active = SalonManager.encryptBoolean(cmr.isConsumerActive());

            String sql1 = "UPDATE consumers SET consumer_street = '" + street + "', consumer_street_num = '" + numSt
                    + "', consumer_dept_floor = '" + deptFloor +"', consumer_dept_num = '" + deptNum + "', consumer_district = '" + district
                    + "', consumer_area = '" + area +"', consumer_details = '" + details +"', consumer_name = '" + name + "', consumer_phone = '" + phone
                    + "', consume_social_network = '" + socialNetwork + "', consumer_active = '" + active
                    + "' WHERE consumer_id = '" + SalonManager.encryptInteger(id) + "';";
            
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
    
    public int getConsumerId() throws Exception {
        try {       
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM consumers;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}