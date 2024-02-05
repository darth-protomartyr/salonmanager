package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import salonmanager.entidades.bussiness.Session;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOSession extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveSession(Session s) throws Exception {
        boolean error = false;
        String sql = "";
        if (error == false) {
            try {
                if (s.getCloseSession() == null) {
                    sql = "INSERT INTO sessions(session_open, session_close, session_state, session_total_mount, session_error_mount, session_active) "
                            + "VALUES( '" + s.getOpenSession() + "', " + s.getCloseSession() + ", " + s.isStateSession() + ", " + s.getTotalSession() + ", " + s.getErrorSession() + ", " + s.isActiveSession() + ");";
                } else {
                    sql = "INSERT INTO sessions(session_open, session_close, session_state, session_total_mount, session_error_mount, session_active) "
                            + "VALUES( '" + s.getOpenSession() + "', '" + s.getCloseSession() + "', " + s.isStateSession() + ", " + s.getTotalSession() + ", " + s.getErrorSession() + ", " + s.isActiveSession() + ");";
                }
                System.out.println(sql);
                insertarModificarEliminar(sql);
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
    }


    public int findSessionId(Timestamp openSession) throws Exception {
        int id = 0;
        try {
            String sql = "SELECT session_id FROM sessions WHERE session_open = '" + openSession + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                id = resultado.getInt(1);
            }
            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public Session askSessionById(int id) throws Exception {
        Session sess = new Session();
        try {
            String sql = "SELECT * FROM sessions WHERE session_id= " + id + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                sess.setId(resultado.getInt(1));
                sess.setOpenSession(resultado.getTimestamp(2));
                sess.setCloseSession(resultado.getTimestamp(3));
                sess.setStateSession(resultado.getBoolean(4));
                sess.setTotalSession(resultado.getDouble(5));
                sess.setErrorSession(resultado.getDouble(6));
                sess.setActiveSession(resultado.getBoolean(7));       
            }
            return sess;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}
