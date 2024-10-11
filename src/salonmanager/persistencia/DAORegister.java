package salonmanager.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Register;

public class DAORegister extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    SalonManager sm = new SalonManager();

    public void saveRegister(Register rgo) throws Exception {
        try {
            int i = getRegisterId();
            String id = SalonManager.encryptInt(i);
            String ejecution = SalonManager.encryptTs(rgo.getEjecution());
            String userId = SalonManager.encrypt(rgo.getUserId());
            String objectK = SalonManager.encrypt(rgo.getObjectKind());
            String objectId = SalonManager.encrypt(rgo.getObjectId());
            String operation = SalonManager.encrypt(rgo.getOperation());

            String sql1 = "INSERT INTO registers(register_id, register_ejecution, register_userId, register_object_kind, register_object_id, register_operation" + ")"
                    + "VALUES('" + id + "', '" + ejecution + "', '" + userId + "', '" + objectK + "', '" + objectId + "', '" + operation + "');";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Register> listRegister() throws Exception {
        ArrayList<Register> rgos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM registers;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Register rgo = new Register();
                rgo.setId(SalonManager.decryptInt(resultado.getString(1)));
                rgo.setEjecution(SalonManager.decryptTs(resultado.getString(2)));
                rgo.setUserId(SalonManager.decrypt(resultado.getString(3)));
                rgo.setObjectKind(SalonManager.decrypt(resultado.getString(4)));
                rgo.setObjectId(SalonManager.decrypt(resultado.getString(5)));
                rgo.setOperation(SalonManager.decrypt(resultado.getString(6)));
                rgos.add(rgo);
            }
            return rgos;
        } catch (Exception e) {
            throw e;
        }
    }

//    public Register listRegisterByObj(String kind, int i, boolean idKind) throws Exception {
//        try {
//            String id = "";
//            if (idKind) {
//                id = SalonManager.encrypt(i + "");
//            } else {
//                id = SalonManager.encryptInt(i);
//            }
//            
//            String sql = "SELECT * FROM registers WHERE register_object_kind = '" + SalonManager.encrypt(kind) + "' AND register_object_id = '" + id + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            Register rgo = new Register();
//            while (resultado.next()) {
//                rgo.setId(SalonManager.decryptInt(resultado.getString(1)));
//                rgo.setEjecution(SalonManager.decryptTs(resultado.getString(2)));
//                rgo.setUserId(SalonManager.decrypt(resultado.getString(3)));
//                rgo.setObjectKind(SalonManager.decrypt(resultado.getString(4)));
//                rgo.setObjectId(SalonManager.decrypt(resultado.getString(5)));
//                rgo.setOperation(SalonManager.decrypt(resultado.getString(6)));
//            }
//            return rgo;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//    
//    public Register listRegisterByUser(String id) throws Exception {
//        try {
//            String sql = "SELECT * FROM registers WHERE register_userId = '" + SalonManager.encrypt(id) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            Register rgo = new Register();
//            while (resultado.next()) {
//                rgo.setId(SalonManager.decryptInt(resultado.getString(1)));
//                rgo.setEjecution(SalonManager.decryptTs(resultado.getString(2)));
//                rgo.setUserId(SalonManager.decrypt(resultado.getString(3)));
//                rgo.setObjectKind(SalonManager.decrypt(resultado.getString(4)));
//                rgo.setObjectId(SalonManager.decrypt(resultado.getString(5)));
//                rgo.setOperation(SalonManager.decrypt(resultado.getString(6)));
//            }
//            return rgo;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//    
//    
//    public Register listRegisterByOperation(String op) throws Exception {
//        try {
//            String sql = "SELECT * FROM registers WHERE register_operation = '" + SalonManager.encrypt(op) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            Register rgo = new Register();
//            while (resultado.next()) {
//                rgo.setId(SalonManager.decryptInt(resultado.getString(1)));
//                rgo.setEjecution(SalonManager.decryptTs(resultado.getString(2)));
//                rgo.setUserId(SalonManager.decrypt(resultado.getString(3)));
//                rgo.setObjectKind(SalonManager.decrypt(resultado.getString(4)));
//                rgo.setObjectId(SalonManager.decrypt(resultado.getString(5)));
//                rgo.setOperation(SalonManager.decrypt(resultado.getString(6)));
//            }
//            return rgo;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    
    
    public int getRegisterId() throws Exception {
        try {

            ArrayList<Integer> ids = new ArrayList<>();
            int max = 1;
            int id = 0;
            String sql = "SELECT register_id FROM registers;";
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

}
