package salonmanager.persistencia;

import salonmanager.entidades.bussiness.User;
import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.utilidades.Utilidades;

public class DAOUser extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    public void saveUser(User user) throws Exception {
        try {
            String name = SalonManager.encrypt(user.getName());
            String apellido = SalonManager.encrypt(user.getLastName());
            String mail = SalonManager.encrypt(user.getMail());
            String id = SalonManager.encrypt(user.getId());
            String rol = "";
            if (user.getRol() == "") {
                rol = SalonManager.encrypt("NULL");
            } else {
                rol = SalonManager.encrypt(user.getRol());
            }
            String routeImage = SalonManager.encrypt(user.getRouteImage());
            String nameImage = SalonManager.encrypt(user.getNameImage());
            String pass = SalonManager.encrypt(user.getPassword());
            String phone = SalonManager.encrypt(user.getPhone());
            String activeUser = SalonManager.encryptBoolean(true);

            String sql1 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)"
                    + "VALUES('" + id + "', '" + name + "', '" + apellido + "', '" + mail + "', '" + rol + "', '" + routeImage + "', '" + nameImage + "', '" + pass + "', '" + phone + "', '" + activeUser + "');";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            if (rol.equals("NULL")) {
                utiliMsg.cargaUsuarioRolNull();
            } else {
                utiliMsg.cargaUsuarioRolFull();
            }

            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void saveUserExpress(User user) throws Exception {
        try {
            String name = SalonManager.encrypt(user.getName());
            String apellido = SalonManager.encrypt(user.getLastName());
            String mail = SalonManager.encrypt(user.getMail());
            String id = SalonManager.encrypt(user.getId());
            String rol = SalonManager.encrypt(user.getRol());
            if (rol == null) {
                rol = "NULL";
            }
            String routeImage = SalonManager.encrypt(user.getRouteImage());
            String nameImage = SalonManager.encrypt(user.getNameImage());
            String pass = SalonManager.encrypt(user.getPassword());
            String phone = SalonManager.encrypt(user.getPhone());
            String activeUser = SalonManager.encryptBoolean(user.isActiveUser());

            String sql1 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)"
                    + "VALUES('" + id + "', '" + name + "', '" + apellido + "', '" + mail + "', '" + rol + "', '" + routeImage + "', '" + nameImage + "', '" + pass + "', '" + phone + "', '" + activeUser + "');";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> listarUserMails() throws Exception {
        boolean act = true;
        String active = SalonManager.encryptBoolean(act);
        ArrayList<String> mails = new ArrayList<>();
        try {
            String sql = "SELECT user_mail FROM users WHERE user_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            String mail = "";
            while (resultado.next()) {
                mail = SalonManager.decrypt(resultado.getString(1));
                mails.add(mail);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
        return mails;
    }

    public ArrayList<String> listarUserByRol(String rol) throws Exception {
        boolean act = true;
        String active = SalonManager.encryptBoolean(act);
        try {
            String sql = "SELECT user_id FROM users WHERE user_role = '" + SalonManager.encrypt(rol) + "' AND user_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> ids = new ArrayList<>();
            while (resultado.next()) {
                String id = SalonManager.decrypt(resultado.getString(1));
                ids.add(id);
            }
            return ids;
        } catch (Exception e) {
            throw e;
        }  finally {
            desconectarBase();
        }

    }

    public User consultaUser(String mail) throws Exception {
        try {
            boolean act = true;
            String active = SalonManager.encryptBoolean(act);
            String sql = "SELECT * FROM users WHERE user_mail = '" + SalonManager.encrypt(mail) + "' AND user_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            User user = new User();
            while (resultado.next()) {
                user.setId(SalonManager.decrypt(resultado.getString(1)));
                user.setName(SalonManager.decrypt(resultado.getString(2)));
                user.setLastName(SalonManager.decrypt(resultado.getString(3)));
                user.setMail(SalonManager.decrypt(resultado.getString(4)));
                String role = SalonManager.decrypt(resultado.getString(5));
                if (role.equals("NULL")) {
                    role = null;
                }
                user.setRol(role);
                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
                user.setPhone(SalonManager.decrypt(resultado.getString(9)));
                user.setActiveUser(SalonManager.decryptBoolean(resultado.getString(10)));
            }
            return user;
        } catch (Exception e) {
            throw e;
        }  finally {
            desconectarBase();
        }
    }

    public ArrayList<User> listarUsers() throws Exception {
        boolean act = true;
        ArrayList<User> users = new ArrayList<>();
        String active = SalonManager.encryptBoolean(act);
        String sql = "SELECT * FROM users WHERE user_active = '" + active + "';";
        try {
            System.out.println(sql);
            consultarBase(sql);
            User user = null;
            while (resultado.next()) {
                user = new User();
                user.setId(SalonManager.decrypt(resultado.getString(1)));
                user.setName(SalonManager.decrypt(resultado.getString(2)));
                user.setLastName(SalonManager.decrypt(resultado.getString(3)));
                user.setMail(SalonManager.decrypt(resultado.getString(4)));
                String role = SalonManager.decrypt(resultado.getString(5));
                if (role.equals("NULL")) {
                    role = null;
                }
                user.setRol(role);
                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
                user.setPhone(SalonManager.decrypt(resultado.getString(9)));
                user.setActiveUser(SalonManager.decryptBoolean(resultado.getString(10)));
                users.add(user);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }  finally {
            desconectarBase();
        }
        return users;
    }

    public ArrayList<User> listarUsersCompleto() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_role =  '" + SalonManager.encrypt("MANAGER") + "' OR user_role = '" + SalonManager.encrypt("MOZO") + "' OR user_role = '" + SalonManager.encrypt("CAJERO") + "' OR user_role = '" + SalonManager.encrypt("DELIVERY") + "' OR user_role = '" + SalonManager.encrypt("NULL") + "';";
        try {
            System.out.println(sql);
            consultarBase(sql);
            User user = null;
            while (resultado.next()) {
                user = new User();
                user.setId(SalonManager.decrypt(resultado.getString(1)));
                user.setName(SalonManager.decrypt(resultado.getString(2)));
                user.setLastName(SalonManager.decrypt(resultado.getString(3)));
                user.setMail(SalonManager.decrypt(resultado.getString(4)));
                String role = SalonManager.decrypt(resultado.getString(5));
                if (role.equals("NULL")) {
                    role = null;
                }
                user.setRol(role);
                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
                user.setPhone(SalonManager.decrypt(resultado.getString(9)));
                user.setActiveUser(SalonManager.decryptBoolean(resultado.getString(10)));

                users.add(user);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }  finally {
            desconectarBase();
        }

        return users;
    }

    public void userModActiveUser(String id, boolean activeUser) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_active = '" + SalonManager.encryptBoolean(activeUser)
                    + "' WHERE user_id = '" + SalonManager.encrypt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }

    }

    public void userModRol(String id, String newRol) throws Exception {
        try {
            String sql = "UPDATE users SET user_rol = " + SalonManager.encrypt(newRol) + " WHERE user_id = '" + SalonManager.encrypt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<User> listUserByRol(String rol, boolean fin) throws Exception {
        boolean act = true;
        ArrayList<User> users = new ArrayList<>();
        String active = SalonManager.encryptBoolean(act);
        try {
            String sql = "SELECT * FROM users WHERE user_role = '" + SalonManager.encrypt(rol) + "' AND user_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            User user = null;
            while (resultado.next()) {
                user = new User();
                user.setId(SalonManager.decrypt(resultado.getString(1)));
                user.setName(SalonManager.decrypt(resultado.getString(2)));
                user.setLastName(SalonManager.decrypt(resultado.getString(3)));
                user.setMail(SalonManager.decrypt(resultado.getString(4)));
                String role = SalonManager.decrypt(resultado.getString(5));
                if (role.equals("NULL")) {
                    role = null;
                }
                user.setRol(role);
                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
                user.setPhone(SalonManager.decrypt(resultado.getString(9)));
                user.setActiveUser(SalonManager.decryptBoolean(resultado.getString(10)));
                users.add(user);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }  finally {
            if (fin) {
                desconectarBase();
            }
        }
        return users;
    }

    public void saveWaiterTable(Table tab) throws Exception {
        try {
            String sql = "INSERT INTO waiter_tabs(waiter_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encrypt(tab.getWaiter().getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public User getWaiterByTable(String tabId) throws Exception {
        User waiter = null;
        String waiterId = "";
        try {
            String sql = "SELECT waiter_id_fkey FROM waiter_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                waiterId = SalonManager.decrypt(resultado.getString(1));
            }

            ArrayList<User> waiters = listUserByRol("MOZO", false);
            for (int i = 0; i < waiters.size(); i++) {
                if (waiters.get(i).getId().equals(waiterId)) {
                    waiter = waiters.get(i);
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }  finally {
            desconectarBase();
        }
        return waiter;
    }

    public User getUserById(String deliId, boolean fin) throws Exception {
        try {
            boolean act = true;
            String active = SalonManager.encryptBoolean(act);
            String sql = "SELECT * FROM users WHERE user_id = '" + SalonManager.encrypt(deliId) + "' AND user_active = '" + active + "';";
            System.out.println(sql);
            consultarBase(sql);
            User user = new User();
            while (resultado.next()) {
                user.setId(SalonManager.decrypt(resultado.getString(1)));
                user.setName(SalonManager.decrypt(resultado.getString(2)));
                user.setLastName(SalonManager.decrypt(resultado.getString(3)));
                user.setMail(SalonManager.decrypt(resultado.getString(4)));
                String role = SalonManager.decrypt(resultado.getString(5));
                if (role.equals("NULL")) {
                    role = null;
                }
                user.setRol(role);
                user.setRouteImage(SalonManager.decrypt(resultado.getString(6)));
                user.setNameImage(SalonManager.decrypt(resultado.getString(7)));
                user.setPassword(SalonManager.decrypt(resultado.getString(8)));
                user.setPhone(SalonManager.decrypt(resultado.getString(9)));
                user.setActiveUser(SalonManager.decryptBoolean(resultado.getString(10)));
            }
            return user;
        } catch (Exception e) {
            throw e;
        }  finally {
            if (fin) {
                desconectarBase();
            }
        }
    }

    public String getUserNameById(String deliId) throws Exception {
        try {
            boolean act = true;
            String active = SalonManager.encryptBoolean(act);
            String sql = "SELECT user_name, user_last_name FROM users WHERE user_id = '" + SalonManager.encrypt(deliId) + "' AND user_active = '" + active + "';";
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
        }  finally {
            desconectarBase();
        }
    }

    public void updateUser(User userAux, String id) throws Exception {

        String name = SalonManager.encrypt(userAux.getName());
        String lastName = SalonManager.encrypt(userAux.getLastName());
        String mail = SalonManager.encrypt(userAux.getMail());
        String rol = SalonManager.encrypt(userAux.getRol());
        if (rol == null) {
            rol = "NULL";
        }
        String routeImage = SalonManager.encrypt(userAux.getRouteImage());
        String nameImage = SalonManager.encrypt(userAux.getNameImage());
        String password = SalonManager.encrypt(userAux.getPassword());
        String phone = SalonManager.encrypt(userAux.getPhone());
        String activeUser = SalonManager.encryptBoolean(userAux.isActiveUser());
        try {
            String sql1 = "UPDATE users SET user_name = '" + name + "', user_last_name ='" + lastName + "', user_mail = '" + mail
                    + "', user_role = '" + rol + "', user_image_route = '" + routeImage + "', user_image_name = '" + nameImage
                    + "', user_password = '" + password + "', user_phone = '" + phone + "', user_active = '" + activeUser
                    + "' WHERE user_id = '" + id + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }  finally {
            desconectarBase();
        }
    }

    public User getCashierByWorkshift(int wsId) throws Exception {
        User cashier = null;
        String cashierId = "";
        try {
            String sql = "SELECT cashier_id_fkey FROM cashier_workshifts WHERE workshift_id_fkey = '" + SalonManager.encryptInt(wsId) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                cashierId = SalonManager.decrypt(resultado.getString(1));
            }
            cashier = getUserById(cashierId, false);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorRegistroFallido();
            } else {
                e.printStackTrace();
            }
        }  finally {
            desconectarBase();
        }
        return cashier;
    }

    public void saveCashierWorkshift(Workshift ws) throws Exception {
        try {
            boolean act = true;
            String active = SalonManager.encryptBoolean(act);
            String sql = "INSERT INTO cashier_workshifts(cashier_workshift_active, cashier_id_fkey, workshift_id_fkey) ";
            String parcialA = "VALUES( '" + active + "', '" + SalonManager.encrypt(ws.getCashierWs().getId()) + "', '" + SalonManager.encryptInt(ws.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateActUser(String userId, boolean act) throws Exception {
        String bool = SalonManager.encryptBoolean(act);
        try {
            String sql = "UPDATE users SET user_active = '" + bool + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateRolUser(String userId, String rol) throws Exception {
        try {
            String sql = "UPDATE users SET user_role = '" + SalonManager.encrypt(rol) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateNameUser(String userId, String name) throws Exception {
        try {
            String sql = "UPDATE users SET user_name = '" + SalonManager.encrypt(name) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateLastNameUser(String userId, String lastName) throws Exception {
        try {
            String sql = "UPDATE users SET user_last_name = '" + SalonManager.encrypt(lastName) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateMailUser(String userId, String mail) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_mail = '" + SalonManager.encrypt(mail) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateRouteImageUser(String userId, String route) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_image_route = '" + SalonManager.encrypt(route) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateNameImageUser(String userId, String name) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_image_name = '" + SalonManager.encrypt(name) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updatePhoneUser(String userId, String phone) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_phone = '" + SalonManager.encrypt(phone) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updatePassUser(String userId, String pass) throws Exception {
        try {
            String sql = "UPDATE users "
                    + "SET user_password = '" + SalonManager.encrypt(pass) + "' WHERE user_id = '" + SalonManager.encrypt(userId) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }
}
