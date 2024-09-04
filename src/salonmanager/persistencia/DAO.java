package salonmanager.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {

    protected Connection conexion = null;
    protected ResultSet resultado = null;
    protected Statement sentencia = null;

    private final String driver = "org.sqlite.JDBC"; // Cambia el driver a SQLite    
//    String urlBaseDeDatos = "jdbc:sqlite:" + "C:/Users/Gonzalo/Documents/NetbeansProject/bariaDB/salonmanager.s3db";
    String urlBaseDeDatos = "jdbc:sqlite:" + System.getProperty("user.dir") + "/bariaDB/salonmanager.s3db";

    protected void conectarBase() throws Exception {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(urlBaseDeDatos);
            
            // Ejecutar PRAGMA para habilitar claves foráneas
            try (Statement pragmaStmt = conexion.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            throw ex;
        }
    }

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
            conectarBase();
            sentencia = (Statement) conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            desconectarBase();
        }
    }

    protected void consultarBase(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = (Statement) conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}