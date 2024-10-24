package eu.andreatt.ejercicioh_dein.bbdd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBBDD {
    private final Connection conexion;
    private static final String URL = "jdbc:mysql://localhost:33066/personas";
    private static final String USER = "dein";
    private static final String PASSWORD = "1234";

    public ConexionBBDD(Connection conexion) {
        this.conexion = conexion;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
