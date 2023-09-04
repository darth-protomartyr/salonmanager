package salonmanager.persistencia;

import salonmanager.entidades.Register;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.Register;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAO;

public class DAORegister extends DAO {
    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();
    User user1 = sm.getUserIn();

    public void guardarTablaRegistros(Register rgo) throws Exception {
        try {
            Timestamp ejecution = rgo.getEjecution();
            String object = rgo.getObject();
            String operation = rgo.getOperation();
            String modification = rgo.getModification();
            String mail = user1.getMail();
            String mailMod = rgo.getUserModify();
            String sql1 = "INSERT INTO registers(register_ejecution, register_user, register_user_modify, register_operation, register_object, register_modification)"
                    + "VALUES('" + ejecution + "', '" + mail + "', '" + mailMod + "', '" + operation + "', '" + object + "', '" + modification + "');";
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

//    public Register consultaRegisterByObj(String object) throws Exception {
//        try {
//            String sql = "SELECT * FROM registers WHERE register_object = '" + object + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            Register rgo = new Register();
//            while (resultado.next()) {
//                rgo.setId(resultado.getInt(1));
//                rgo.setEjecution(resultado.getTimestamp(2));
//                rgo.setUser(resultado.getString(3));
//                rgo.setOperation(resultado.getString(4));
//                rgo.setObject(resultado.getString(5));
//                rgo.setModification(resultado.getString(6));
//            }
//            return rgo;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }
//    public Register consultaRegisterByUser(String user) throws Exception {
//        try {
//            String sql = "SELECT * FROM registers WHERE register_user = '" + user + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            Register rgo = new Register();
//            while (resultado.next()) {
//                rgo.setId(resultado.getInt(1));
//                rgo.setEjecution(resultado.getTimestamp(2));
//                rgo.setUser(resultado.getString(3));
//                rgo.setOperation(resultado.getString(4));
//                rgo.setObject(resultado.getString(5));
//                rgo.setModification(resultado.getString(6));
//            }
//            return rgo;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }
    
    public ArrayList<Register> consultaListRegister() throws Exception {
        try {
            ArrayList<Register> registers = new ArrayList<Register>();
            String sql = "SELECT * FROM registers;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Register rgo = new Register();
                rgo.setId(resultado.getInt(1));
                rgo.setEjecution(resultado.getTimestamp(2));
                rgo.setUser(resultado.getString(3));
                rgo.setUserModify(resultado.getString(4));
                rgo.setOperation(resultado.getString(5));
                rgo.setObject(resultado.getString(6));
                rgo.setModification(resultado.getString(7));
                registers.add(rgo);
            }
            return registers;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Register> consultaListRegisterByUser(String user) throws Exception {
        try {
            ArrayList<Register> registers = new ArrayList<Register>();
            String sql = "SELECT * FROM registers WHERE register_user = '" + user + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Register rgo = new Register();
                rgo.setId(resultado.getInt(1));
                rgo.setEjecution(resultado.getTimestamp(2));
                rgo.setUser(resultado.getString(3));
                rgo.setUserModify(resultado.getString(4));
                rgo.setOperation(resultado.getString(5));
                rgo.setObject(resultado.getString(6));
                rgo.setModification(resultado.getString(7));
                registers.add(rgo);
            }
            return registers;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Register> consultaListRegisterByObj(String obj) throws Exception {
        try {
            ArrayList<Register> registers = new ArrayList<Register>();
            String sql = "SELECT * FROM registers WHERE register_object = '" + obj + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Register rgo = new Register();
                rgo.setId(resultado.getInt(1));
                rgo.setEjecution(resultado.getTimestamp(2));
                rgo.setUser(resultado.getString(3));
                rgo.setUserModify(resultado.getString(4));
                rgo.setOperation(resultado.getString(5));
                rgo.setObject(resultado.getString(6));
                rgo.setModification(resultado.getString(7));
                registers.add(rgo);
            }
            return registers;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}