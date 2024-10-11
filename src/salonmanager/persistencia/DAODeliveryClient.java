package salonmanager.persistencia;

import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.bussiness.Register;

public class DAODeliveryClient extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAORegister daoReg = new DAORegister();
    public void saveConsumer(DeliveryClient cmr) throws Exception {
        try {
            String id = SalonManager.encryptInt(cmr.getId());
            String street = SalonManager.encrypt(cmr.getStreet());
            String numSt = SalonManager.encrypt(cmr.getNumSt());
            String deptFloor = SalonManager.encrypt(cmr.getDeptFloor());
            String deptNum = SalonManager.encrypt(cmr.getDeptNum());
            String district = SalonManager.encrypt(cmr.getDistrict());
            String area = SalonManager.encrypt(cmr.getArea());
            String details = SalonManager.encrypt(cmr.getDetails());
            String name = SalonManager.encrypt(cmr.getName());
            String lastname = SalonManager.encrypt(cmr.getLastname());
            String phone = SalonManager.encrypt(cmr.getPhone());
            String socialNetwork = SalonManager.encrypt(cmr.getSocialNetwork());
            String active = SalonManager.encryptBoolean(cmr.isConsumerActive());

            String sql1 = "INSERT INTO delivery_clients(delivery_client_id, delivery_client_street, delivery_client_street_num, delivery_client_dept_floor, delivery_client_dept_num, delivery_client_district, delivery_client_area, delivery_client_details, delivery_client_name, delivery_client_lastname, delivery_client_phone, delivery_client_social_network, delivery_client_active)"
                    + "VALUES('" + id + "', '" + street + "', '" + numSt + "', '" + deptFloor + "', '" + deptNum + "', '" + district + "', '" + area + "', '" + details + "', '" + name + "', '" + lastname + "', '" + phone + "', '" + socialNetwork + "', '" + active + "');";

            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            
            Register rgo = new Register(new Timestamp(new Date().getTime()), SalonManager.getUserId(), "Cliente", "" + id, 1, name + " " + lastname);
            daoReg.saveRegister(rgo);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
    }

    public DeliveryClient getConsumerByPhone(String phone) throws Exception {
        try {
            String sql = "SELECT * FROM delivery_clients WHERE delivery_client_phone = '" + SalonManager.encrypt(phone) + "' AND delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            DeliveryClient cmr = new DeliveryClient();
            while (resultado.next()) {
                cmr.setId(SalonManager.decryptInt(resultado.getString(1)));
                cmr.setStreet(SalonManager.decrypt(resultado.getString(2)));
                cmr.setNumSt(SalonManager.decrypt(resultado.getString(3)));
                cmr.setDeptFloor(SalonManager.decrypt(resultado.getString(4)));
                cmr.setDeptNum(SalonManager.decrypt(resultado.getString(5)));
                cmr.setDistrict(SalonManager.decrypt(resultado.getString(6)));
                cmr.setArea(SalonManager.decrypt(resultado.getString(7)));
                cmr.setDetails(SalonManager.decrypt(resultado.getString(8)));
                cmr.setName(SalonManager.decrypt(resultado.getString(9)));
                cmr.setLastname(SalonManager.decrypt(resultado.getString(10)));
                cmr.setPhone(SalonManager.decrypt(resultado.getString(11)));
                cmr.setSocialNetwork(SalonManager.decrypt(resultado.getString(12)));
                cmr.setConsumerActive(SalonManager.decryptBoolean(resultado.getString(13)));
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
            String sql = "SELECT delivery_client_phone FROM delivery_clients WHERE delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
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

    public ArrayList<String> getConsumersName() throws Exception {
        try {
            String sql = "SELECT delivery_client_name, delivery_client_lastname, delivery_client_id FROM delivery_clients WHERE delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                String n = SalonManager.decrypt(resultado.getString(1));
                String l = SalonManager.decrypt(resultado.getString(2));
                int i = SalonManager.decryptInt(resultado.getString(3));
                String id = i + "";
                cmrs.add(n + "/" + l + "/" + id);
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
            String sql = "SELECT delivery_clients_phone FROM delivery_clients WHERE delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
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

    public void updateConsumer(DeliveryClient cmr, int id) throws Exception {
        try {
            String street = SalonManager.encrypt(cmr.getStreet());
            String numSt = SalonManager.encrypt(cmr.getNumSt());
            String deptFloor = SalonManager.encrypt(cmr.getDeptFloor());
            String deptNum = SalonManager.encrypt(cmr.getDeptNum());
            String district = SalonManager.encrypt(cmr.getDistrict());
            String area = SalonManager.encrypt(cmr.getArea());
            String details = SalonManager.encrypt(cmr.getDetails());
            String name = SalonManager.encrypt(cmr.getName());
            String lastname = SalonManager.encrypt(cmr.getLastname());
            String phone = SalonManager.encrypt(cmr.getPhone());
            String socialNetwork = SalonManager.encrypt(cmr.getSocialNetwork());
            String active = SalonManager.encryptBoolean(cmr.isConsumerActive());

            String sql1 = "UPDATE delivery_clients SET delivery_client_street = '" + street + "', delivery_client_street_num = '" + numSt
                    + "', delivery_client_dept_floor = '" + deptFloor + "', delivery_client_dept_num = '" + deptNum + "', delivery_client_district = '" + district
                    + "', delivery_client_area = '" + area + "', delivery_client_details = '" + details + "', delivery_client_name = '" + name + "', delivery_client_lastname = '" + lastname + "', delivery_client_phone = '" + phone
                    + "', delivery_client_social_network = '" + socialNetwork + "', delivery_client_active = '" + active
                    + "' WHERE delivery_client_id = '" + SalonManager.encryptInt(id) + "';";

            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            
            Register rgo = new Register(new Timestamp(new Date().getTime()), SalonManager.getUserId(), "Cliente", "" + id, 2, name  +" " + lastname);
            daoReg.saveRegister(rgo);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
    }

    public int getConsumerId() throws Exception {
        try {
            ArrayList<Integer> ids = new ArrayList<>();
            int max = 1;
            int id = 0;
            String sql = "SELECT delivery_client_id FROM delivery_clients;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                id = SalonManager.decryptInt(resultado.getString(1));
                ids.add(id);
            }
            int max2 = 0;

            if (ids.size() > 0) {
                max2 = Collections.max(ids);
            }

            max = max + max2;
            return max;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public DeliveryClient getConsumerById(int i) throws Exception {
        try {
            String sql = "SELECT * FROM delivery_clients WHERE delivery_client_id = '" + SalonManager.encryptInt(i) + "' AND delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            DeliveryClient cmr = new DeliveryClient();
            while (resultado.next()) {
                cmr.setId(SalonManager.decryptInt(resultado.getString(1)));
                cmr.setStreet(SalonManager.decrypt(resultado.getString(2)));
                cmr.setNumSt(SalonManager.decrypt(resultado.getString(3)));
                cmr.setDeptFloor(SalonManager.decrypt(resultado.getString(4)));
                cmr.setDeptNum(SalonManager.decrypt(resultado.getString(5)));
                cmr.setDistrict(SalonManager.decrypt(resultado.getString(6)));
                cmr.setArea(SalonManager.decrypt(resultado.getString(7)));
                cmr.setDetails(SalonManager.decrypt(resultado.getString(8)));
                cmr.setName(SalonManager.decrypt(resultado.getString(9)));
                cmr.setLastname(SalonManager.decrypt(resultado.getString(10)));
                cmr.setPhone(SalonManager.decrypt(resultado.getString(11)));
                cmr.setSocialNetwork(SalonManager.decrypt(resultado.getString(12)));
                cmr.setConsumerActive(SalonManager.decryptBoolean(resultado.getString(13)));
            }
            return cmr;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public String getClientNameById(int client) throws Exception {
        try {
            boolean act = true;
            String active = SalonManager.encryptBoolean(act);
            String sql = "SELECT delivery_client_name, delivery_client_lastname FROM delivery_clients WHERE delivery_client_id = '" + SalonManager.encryptInt(client) + "' AND delivery_client_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            String nameC = "";
            while (resultado.next()) {
                String name = SalonManager.decrypt(resultado.getString(1));
                String lastName = SalonManager.decrypt(resultado.getString(2));
                name = name.substring(0, 1) + ".";
                nameC = name + " " + lastName;
            }
            return nameC;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<DeliveryClient> listarDeliveryClientByActive(boolean all) throws Exception {
        ArrayList<DeliveryClient> cmrs = new ArrayList<>();
        try {
            String sql = "";
            if (all) {
                sql = "SELECT * FROM delivery_clients;";
            } else {
                sql = "SELECT * FROM delivery_clients WHERE delivery_client_active = '" + SalonManager.encryptBoolean(true) + "';";
            }
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                DeliveryClient cmr = new DeliveryClient();
                cmr.setId(SalonManager.decryptInt(resultado.getString(1)));
                cmr.setStreet(SalonManager.decrypt(resultado.getString(2)));
                cmr.setNumSt(SalonManager.decrypt(resultado.getString(3)));
                cmr.setDeptFloor(SalonManager.decrypt(resultado.getString(4)));
                cmr.setDeptNum(SalonManager.decrypt(resultado.getString(5)));
                cmr.setDistrict(SalonManager.decrypt(resultado.getString(6)));
                cmr.setArea(SalonManager.decrypt(resultado.getString(7)));
                cmr.setDetails(SalonManager.decrypt(resultado.getString(8)));
                cmr.setName(SalonManager.decrypt(resultado.getString(9)));
                cmr.setLastname(SalonManager.decrypt(resultado.getString(10)));
                cmr.setPhone(SalonManager.decrypt(resultado.getString(11)));
                cmr.setSocialNetwork(SalonManager.decrypt(resultado.getString(12)));
                cmr.setConsumerActive(SalonManager.decryptBoolean(resultado.getString(13)));
                cmrs.add(cmr);
            }
            return cmrs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}
