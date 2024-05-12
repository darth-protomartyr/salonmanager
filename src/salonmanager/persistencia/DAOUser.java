package salonmanager.persistencia;

import salonmanager.entidades.bussiness.User;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;

public class DAOUser extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public void saveUser(User user) throws Exception {
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

    public void saveUserExpress(User user) throws Exception {
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

    public ArrayList<String> listarUserByRol(String rol) throws Exception {
        String sql = "SELECT user_id FROM users WHERE user_role = '" + rol + "' AND user_active = true";
        System.out.println(sql);
        consultarBase(sql);
//        String id = "";
        ArrayList<String> ids = new ArrayList<String>();
        while (resultado.next()) {
            String id = resultado.getString(1);
            ids.add(id);
        }
        desconectarBase();
        return ids;
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

    public ArrayList<User> listUserByRol(String rol) throws Exception {
        String sql = "SELECT * FROM users WHERE user_role = '" + rol + "' AND user_active = true;";
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

        ArrayList<User> waiters = listUserByRol("MOZO");
        for (int i = 0; i < waiters.size(); i++) {
            if (waiters.get(i).getId().equals(waiterId)) {
                waiter = waiters.get(i);
            }
        }
        return waiter;
    }

    public User getUserById(String deliId) throws Exception {
        try {
            String sql = "SELECT * FROM users WHERE user_id = '" + deliId + "' AND user_active = true;";
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

    public String getUserNameById(String deliId) throws Exception {
        try {
            String sql = "SELECT user_name, user_last_name FROM users WHERE user_id = '" + deliId + "' AND user_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            String nameC = "";
            while (resultado.next()) {
                String name = resultado.getString(1);
                String lastName = resultado.getString(2);
                nameC = name + " " + lastName; 
            }
            return nameC;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void updateUser(User userAux, String id) throws Exception {

        String name = userAux.getName();
        String lastName = userAux.getLastName();
        String mail = userAux.getMail();
        String rol = userAux.getRol();
        String routeImage = userAux.getRouteImage();
        String nameImage = userAux.getNameImage();
        String password = userAux.getPassword();
        String phone = userAux.getPhone();
        boolean activeUser = userAux.isActiveUser();
        try {
            String sql1 = "UPDATE users SET user_name = '" + name + "', user_last_name ='" + lastName + "', user_mail = '" + mail
                    + "', user_role = '" + rol + "', user_image_route = '" + routeImage + "', user_image_name = '" + nameImage
                    + "', user_password = '" + password + "', user_phone = '" + phone + "', user_active = " + activeUser
                    + " WHERE user_id = '" + id + "';";
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

    public User getCashierByWorkshift(int wsId) throws Exception {
        User cashier = null;
        String cashierId = "";
        try {
            String sql = "SELECT cashier_id_fkey FROM cashier_workshifts WHERE workshift_id_fkey = '" + wsId + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cashierId = resultado.getString(1);
            }
            cashier = getUserById(cashierId);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
        return cashier;
    }

    public void saveCashierWorkshift(Workshift ws) throws Exception {
        try {
            String sql = "INSERT INTO cashier_workshifts(cashier_workshift_active, cashier_id_fkey, workshift_id_fkey) ";
            String parcialA = "VALUES( " + true + ", '" + ws.getCashierWs().getId() + "', " + ws.getId() + ");";
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
