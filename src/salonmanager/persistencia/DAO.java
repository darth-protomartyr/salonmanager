package salonmanager.persistencia;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gonzalo
 */
public class DAO {

    protected Connection conexion = null;
    protected ResultSet resultado = null;
    protected Statement sentencia = null;

    
    
//LocalHost
//LocalHost
//LocalHost
//LocalHost

    private final String user = "root";
    private final String pass = "root";
    private final String db = "salonmanager";
    private final String driver = "com.mysql.cj.jdbc.Driver";

    protected void conectarBase() throws Exception {
        try {
            Class.forName(driver);
            String urlBaseDeDatos = "jdbc:mysql://localhost:3306/" + db + "?useSSL=false";
            conexion = (Connection) DriverManager.getConnection(urlBaseDeDatos, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            throw ex;
        }
    }
    

    
// Remote host
// Remote host    
// Remote host    
// Remote host    
        
//    private final String user = "rvgsacom_mari";
//    private final String pass = "rvgsacom_mari";
//    private final String db = "rvgsacom_mari";
//    private final String driver = "com.mysql.cj.jdbc.Driver";
//
//    protected void conectarBase() throws Exception {
//        try {
//            Class.forName(driver);
//            String urlBaseDeDatos = "jdbc:mysql://rvgsa.com.ar:3307/" + db + "?useSSL=false";
//            conexion = (Connection) DriverManager.getConnection(urlBaseDeDatos, user, pass);
//        } catch (ClassNotFoundException | SQLException ex) {
//            throw ex;
//        }
//    }
    
    
//SQLite
//SQLite
//SQLite
//SQLite
    
//    private final String driver = "org.sqlite.JDBC"; //Cambia el driver a SQLite    
//    String urlBaseDeDatos = "jdbc:sqlite:" + "C:/Users/Gonzalo/Documents/NetbeansProject/bariaDB/salonmanager.s3db";
//
//    protected void conectarBase() throws Exception {
//        try {
//            Class.forName(driver);
//            conexion = DriverManager.getConnection(urlBaseDeDatos);
//        } catch (ClassNotFoundException | SQLException ex) {
//            throw ex;
//        }
//    }

    protected void desconectarBase() throws Exception {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) { 
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    protected void insertarModificarEliminar(String sql) throws Exception {
        try {
            System.out.println(sql);
            conectarBase();
            sentencia = (Statement) conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            //conexion.rollback();
            throw ex;
        } finally {
            desconectarBase();
        }
    }

    protected void consultarBase(String sql) throws Exception {
        try {
            System.out.println(sql);
            conectarBase();
            sentencia = (Statement) conexion.createStatement();
            
            resultado = sentencia.executeQuery(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}