package org.example;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DBname = "climate";
    private static final Path path = Path.of(DBname);
    private static final String JDBC_URL = "jdbc:h2:"+path.toAbsolutePath(); // Ruta de la base de datos
    private static final String USER = "admin"; // Usuario predeterminado
    private static final String PASSWORD = "abc123."; // Sin contraseña por defecto

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // Crear una tabla de ejemplo
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Prediction("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "LOCATION VARCHAR(255), "
                    + "DATE VARCHAR(255),"
                    + "SKY_STAT VARCHAR(255),"
                    + "MAX_TEMP DOUBLE(3,1),"
                    + "MIN_TEMP DOUBLE(3,1),"
                    + "TOTAL_PRECIPITATION DOUBLE(3,1),"
                    + "WIND_SPEED DOUBLE(4,2),"
                    + "CLOUD_PERCENTAGE DOUBLE(4,2),"
                    + "RELATIVE_HUMIDITY DOUBLE(4,2))";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

