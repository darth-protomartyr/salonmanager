package salonmanager.persistencia;

import salonmanager.entidades.User;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.Session;
import salonmanager.entidades.Table;

public class DAOUser extends DAO {

    UtilidadesGraficas utiliF = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveTablaUsers(User user) throws Exception {
        try {
            String name = user.getName();
            String apellido = user.getLastName();
            String mail = user.getMail();
            String id = user.getId();
            String rol = user.getRol();
            String routeImage = user.getRouteImage();
            String nameImage = user.getNameImage();
            String pass = user.getPassword();
            String phone = user.getPhone();
            boolean activeUser = user.isActiveUser();

            String sql1 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)"
                    + "VALUES('" + id + "', '" + name + "', '" + apellido + "', '" + mail + "', '" + rol + "', '" + routeImage + "', '" + nameImage + "', '" + pass + "', '" + phone + "', " + activeUser + ");";
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

    public ArrayList<String> listarUserMails() throws Exception {
        String sql = "SELECT user_mail FROM users WHERE user_active = true";
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
            String sql = "SELECT * FROM users WHERE user_mail = '" + mail + "' AND user_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            User user = new User();
            while (resultado.next()) {
                user.setId(resultado.getString(1));
                user.setName(resultado.getString(2));
                user.setLastName(resultado.getString(3));
                user.setMail(resultado.getString(4));
                user.setRol(resultado.getString(5));
                user.setRouteImage(resultado.getString(6));
                user.setNameImage(resultado.getString(7));
                user.setPassword(resultado.getString(8));
                user.setPhone(resultado.getString(9));
                user.setActiveUser(resultado.getBoolean(10));
            }
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<User> listarUsers() throws Exception {
        String sql = "SELECT * FROM users WHERE user_active = true;";
        System.out.println(sql);
        consultarBase(sql);
        User user = null;
        ArrayList<User> users = new ArrayList<User>();
        while (resultado.next()) {
            user = new User();
            user.setId(resultado.getString(1));
            user.setName(resultado.getString(2));
            user.setLastName(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setPhone(resultado.getString(9));
            user.setActiveUser(resultado.getBoolean(10));
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
            user.setName(resultado.getString(2));
            user.setLastName(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setPhone(resultado.getString(9));
            user.setActiveUser(resultado.getBoolean(10));
            
            users.add(user);
        }
        desconectarBase();
        return users;
    }

    public void userModActiveUser(String id, boolean activeUser) throws Exception {
        String sql = "UPDATE users "
                + "SET user_active = " + activeUser
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

    public ArrayList<User> listWaiter() throws Exception {
        String sql = "SELECT * FROM users WHERE user_role = 'MOZO' AND user_active = true;";
        System.out.println(sql);
        consultarBase(sql);
        User user = null;
        ArrayList<User> users = new ArrayList<User>();
        while (resultado.next()) {
            user = new User();
            user.setId(resultado.getString(1));
            user.setName(resultado.getString(2));
            user.setLastName(resultado.getString(3));
            user.setMail(resultado.getString(4));
            user.setRol(resultado.getString(5));
            user.setRouteImage(resultado.getString(6));
            user.setNameImage(resultado.getString(7));
            user.setPassword(resultado.getString(8));
            user.setPhone(resultado.getString(9));
            user.setActiveUser(resultado.getBoolean(10));
            users.add(user);
        }
        desconectarBase();
        return users;
    }

    public void saveWaiterTable(Table tab) throws Exception {
        try {
            String sql = "INSERT INTO waiter_tabs(waiter_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + tab.getWaiter().getId() + "', '" + tab.getId() + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public User getWaiterByTable(String tabId) throws Exception {
        User waiter = null;
        String waiterId = "";
        String sql = "SELECT waiter_id_fkey FROM waiter_tabs WHERE table_id_fkey = '" + tabId + "';";
        System.out.println(sql);
        consultarBase(sql);
        while (resultado.next()) {
            waiterId = resultado.getString(1);
        }

        ArrayList<User> waiters = listWaiter();
        for (int i = 0; i < waiters.size(); i++) {
            if (waiters.get(i).getId().equals(waiterId)) {
                waiter = waiters.get(i);
            }
        }
        return waiter;
    }

    public void saveCashierInit(Session sess) throws Exception {
        try {
            String sql = "INSERT INTO cashier_session_init(cashier_init_id_fkey, session_init_id_fkey, cashier_session_init_active) ";
            String parcialA = "VALUES('" + sess.getOpener().getId() + "', " + sess.getId() + ", " + true + ");";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void saveCashierEnds(Session sess) throws Exception {
        try {
            String sql = "INSERT INTO cashier_session_ends(cashier_ends_id_fkey, session_ends_id_fkey, cashier_session_init_active) ";
            String parcialA = "VALUES('" + sess.getCloser().getId() + "', " + sess.getId() + ", " + true + ");";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }
}
