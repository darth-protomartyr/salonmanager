package salonmanager.persistencia;

import salonmanager.entidades.User;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.Session;
import salonmanager.entidades.Table;
import salonmanager.entidades.DeliveryConsumer;

public class DAOConsumer extends DAO {

    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveConsumer(DeliveryConsumer cmr) throws Exception {
        try {
            String street = cmr.getStreet();
            String numSt = cmr.getNumSt();
            String deptFloor = cmr.getDeptFloor();
            String deptNum = cmr.getDeptNum();
            String district = cmr.getDistrict();
            String area = cmr.getArea();
            String details = cmr.getDetails();
            String name = cmr.getName();
            String phone = cmr.getPhone();
            String socialNetwork = cmr.getSocialNetwork();
            boolean active = cmr.isConsumerActive();

            String sql1 = "INSERT INTO consumers(consumer_street, consumer_street_num, consumer_dept_floor, consumer_dept_num, consumer_district, consumer_area, consumer_details, consumer_name, consumer_phone, consume_social_network, consumer_active)"
                    + "VALUES('" + street + "', '" + numSt + "', '" + deptFloor + "', '" + deptNum + "', '" + district + "', '" + area + "', '" + details + "', '" + name + "', '" + phone + "', '" + socialNetwork + "', " + active + ");";

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

    public ArrayList<String> listarPhones() throws Exception {
        try {
            String sql = "SELECT consumers_phone FROM consumers WHERE consumer_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> phones = new ArrayList<String>();
            while (resultado.next()) {
                String st = resultado.getString(1);
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
            String street = cmr.getStreet();
            String numSt = cmr.getNumSt();
            String deptFloor = cmr.getDeptFloor();
            String deptNum = cmr.getDeptNum();
            String district = cmr.getDistrict();
            String area = cmr.getArea();
            String details = cmr.getDetails();
            String name = cmr.getName();
            String phone = cmr.getPhone();
            String socialNetwork = cmr.getSocialNetwork();
            boolean active = cmr.isConsumerActive();

            String sql1 = "UPDATE consumers SET consumer_street = '" + street + "', consumer_street_num = '" + numSt
                    + "', consumer_dept_floor = '" + deptFloor +"', consumer_dept_num = '" + deptNum + "', consumer_district = '" + district
                    + "', consumer_area = '" + area +"', consumer_details = '" + details +"', consumer_name = '" + name + "', consumer_phone = '" + phone
                    + "', consume_social_network = '" + socialNetwork + "', consumer_active = '" + active
                    + "' WHERE consumer_id = " + id + ";";
            
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
}
