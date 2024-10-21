package eu.andreatt.ejercicioh_dein.bbdd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBBDD {
    private final Connection conexion;
    //private static final String URL = "jdbc:mariadb://192.168.1.20:3306/personas";
    private static final String URL = "jdbc:mariadb://172.20.104.143:3306/personas";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypass";

    public ConexionBBDD(Connection conexion) {
        this.conexion = conexion;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
