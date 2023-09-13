package salonmanager.persistencia;

import salonmanager.entidades.User;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.SalonManager;

public class DAOUser extends DAO {

    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void guardarTablaUsers(User user) throws Exception {
        try {
            String nombre = user.getNombre();
            String apellido = user.getApellido();
            String mail = user.getMail();
            String id = user.getId();
            String rol = user.getRol();
            String routeImage = user.getRouteImage();
            String nameImage = user.getNameImage();
            String pass = user.getPassword();
            boolean alta = user.isAlta();

            String sql1 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_alta)"
                    + "VALUES('" + id + "', '" + nombre + "', '" + apellido + "', '" + mail + "', '" + rol + "', '" + routeImage + "', '" + nameImage + "', '" + pass + "', " + alta + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.cargaRegistroExitoso();
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

    public ArrayList<String> listarUserMails() throws Exception {
        String sql = "SELECT user_mail FROM users WHERE user_alta = true";
        System.out.println(sql);
        consultarBase(sql);
        String mail = "";
        ArrayList<String> mails = new ArrayList<String>();
        while (resultado.next()) {
            mail = "";
            mail = resultado.getString(1);
            mails.add(mail);
        }
        desconectarBase();
        return mails;
    }

    public User consultaUser(String mail) throws Exception {
        try {
            String sql = "SELECT * FROM users WHERE user_mail = '" + mail + "' AND user_alta = true;";
            System.out.println(sql);
            consultarBase(sql);
            User user = new User();
            while (resultado.next()) {
                user.setId(resultado.getString(1));
                user.setNombre(resultado.getString(2));
                user.setApellido(resultado.getString(3));
                user.setMail(resultado.getString(4));
                user.setRol(resultado.getString(5));
                user.setRouteImage(resultado.getString(6));
                user.setNameImage(resultado.getString(7));
                user.setPassword(resultado.getString(8));
                user.setAlta(resultado.getBoolean(9));
            }
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<User> listarUsers() throws Exception {
        String sql = "SELECT * FROM users WHERE user_alta = true;";
        System.out.println(sql);
        consultarBase(sql);
        User user = null;
        ArrayList<User> users = new ArrayList<User>();
        while (resultado.next()) {
            user = new User();
            user.setId(resultado.getString(1));
            user.setNombre(resultado.getString(2));
            user.setApellido(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setAlta(resultado.getBoolean(9));
            users.add(user);
        }
        desconectarBase();
        return users;
    }

    public ArrayList<User> listarUsersCompleto() throws Exception {
        String sql = "SELECT * FROM users;";
        System.out.println(sql);
        consultarBase(sql);
        User user = null;
        ArrayList<User> users = new ArrayList<User>();
        while (resultado.next()) {
            user = new User();
            user.setId(resultado.getString(1));
            user.setNombre(resultado.getString(2));
            user.setApellido(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setAlta(resultado.getBoolean(9));
            users.add(user);
        }
        desconectarBase();
        return users;
    }

    public void userModAlta(String id, boolean alta) throws Exception {
        String sql = "UPDATE users "
                + "SET user_alta = " + alta
                + " WHERE user_id = '" + id + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }

    public void userModRol(String id, String newRol) throws Exception {
        String sql = "UPDATE users "
                + "SET user_rol = " + newRol
                + " WHERE user_id = '" + id + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }

//    public void guardarTablaUsers(User user) throws Exception {
//        try {
//            String nombre = SalonManager.encrypt(user.getNombre());
//            String apellido = SalonManager.encrypt(user.getApellido());
//            String mail = SalonManager.encrypt(user.getMail());
//            String id = SalonManager.encrypt(user.getId());
//            String rol = SalonManager.encrypt(user.getRol());
//            String routeImage = SalonManager.encrypt(user.getRouteImage());
//            String nameImage = SalonManager.encrypt(user.getNameImage());
//            String pass = SalonManager.encrypt(user.getPassword());
//            boolean alta = user.isAlta();
//
//            String sql1 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_alta)"
//                    + "VALUES('" + id + "', '" + nombre + "', '" + apellido + "', '" + mail + "', '" + rol + "', '" + routeImage + "', '" + nameImage + "', '" + pass + "', " + alta + ");";
//            System.out.println(sql1);
//            insertarModificarEliminar(sql1.trim());
//            utiliMsg.registroExitoso();
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 1062) {
//                utiliMsg.registroFallido();
//            } else {
//                e.printStackTrace();
//            }
//        } finally {
//            desconectarBase();
//        }
//    }
//
//    public ArrayList<String> listarUserMails() throws Exception {
//        String sql = "SELECT user_mail FROM users WHERE user_alta = true";
//        System.out.println(sql);
//        consultarBase(sql);
//        String mail = "";
//        ArrayList<String> mails = new ArrayList<String>();
//        while (resultado.next()) {
//            mail = "";
//            mail = SalonManager.decrypt(resultado.getString(1));
//            mails.add(mail);
//        }
//        desconectarBase();
//        return mails;
//    }
//
//    public User consultaUser(String mail) throws Exception {
//        try {
//            String sql = "SELECT * FROM users WHERE user_mail = '" + mail + "' AND user_alta = true;";
//            System.out.println(sql);
//            consultarBase(sql);
//            User user = new User();
//            while (resultado.next()) {
//                user.setId(SalonManager.decrypt(resultado.getString(1)));
//                user.setNombre(SalonManager.decrypt(resultado.getString(2)));
//                user.setApellido(SalonManager.decrypt(resultado.getString(3)));
//                user.setMail(SalonManager.decrypt(resultado.getString(4)));
//                user.setRol(SalonManager.decrypt(resultado.getString(5)));
//                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
//                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
//                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
//                user.setAlta(resultado.getBoolean(9));
//            }
//            return user;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }
//
//    public ArrayList<User> listarUsers() throws Exception {
//        String sql = "SELECT * FROM users WHERE user_alta = true;";
//        System.out.println(sql);
//        consultarBase(sql);
//        User user = null;
//        ArrayList<User> users = new ArrayList<User>();
//        while (resultado.next()) {
//            user = new User();
//            user.setId(SalonManager.decrypt(resultado.getString(1)));
//            user.setNombre(SalonManager.decrypt(resultado.getString(2)));
//            user.setApellido(SalonManager.decrypt(resultado.getString(3)));
//            user.setMail(SalonManager.decrypt(resultado.getString(4)));
//            user.setRol(SalonManager.decrypt(resultado.getString(5)));
//            user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
//            user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
//            user.setPassword(SalonManager.decrypt(resultado.getString(8)));
//            user.setAlta(resultado.getBoolean(9));
//            users.add(user);
//        }
//        desconectarBase();
//        return users;
//    }
//    
//    
//        public ArrayList<User> listarUsersCompleto() throws Exception {
//        String sql = "SELECT * FROM users;";
//        System.out.println(sql);
//        consultarBase(sql);
//        User user = null;
//        ArrayList<User> users = new ArrayList<User>();
//        while (resultado.next()) {
//            user = new User();
//            user.setId(SalonManager.decrypt(resultado.getString(1)));
//            user.setNombre(SalonManager.decrypt(resultado.getString(2)));
//            user.setApellido(SalonManager.decrypt(resultado.getString(3)));
//            user.setMail(SalonManager.decrypt(resultado.getString(4)));
//            user.setRol(SalonManager.decrypt(resultado.getString(5)));
//            user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
//            user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
//            user.setPassword(SalonManager.decrypt(resultado.getString(8)));
//            user.setAlta(resultado.getBoolean(9));
//            users.add(user);
//        }
//        desconectarBase();
//        return users;
//    }
//
//
//    public void userModAlta(String id, boolean alta) throws Exception {
//        String sql = "UPDATE users "
//                + "SET user_alta = " + alta
//                + " WHERE user_id = '" + SalonManager.encrypt(id) + "';";
//        System.out.println(sql);
//        insertarModificarEliminar(sql);
//        desconectarBase();
//    }
//
//    public void userModRol(String id, String newRol) throws Exception {
//        String sql = "UPDATE users "
//                + "SET user_rol = " + SalonManager.encrypt(newRol)
//                + " WHERE user_id = '" + SalonManager.encrypt(id) + "';";
//        System.out.println(sql);
//        insertarModificarEliminar(sql);
//        desconectarBase();
//    }
    public ArrayList<User> listWaiter() throws Exception {
        String sql = "SELECT * FROM users WHERE user_role = 'MOZO' AND user_alta = true;";
        System.out.println(sql);
        consultarBase(sql);
        User user = null;
        ArrayList<User> users = new ArrayList<User>();
        while (resultado.next()) {
            user = new User();
            user.setId(resultado.getString(1));
            user.setNombre(resultado.getString(2));
            user.setApellido(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setAlta(resultado.getBoolean(9));
            users.add(user);
        }
        desconectarBase();
        return users;
    }
}