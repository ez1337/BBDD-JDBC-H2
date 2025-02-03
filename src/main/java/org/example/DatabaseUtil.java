package org.example;

import java.nio.file.Path;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DatabaseUtil {
    private static final String DBname = "climate";
    private static final Path path = Path.of(DBname);
    private static final String JDBC_URL = "jdbc:h2:"+path.toAbsolutePath(); // Ruta de la base de datos
    private static final String USER = "admin"; // Usuario predeterminado
    private static final String PASSWORD = "abc123."; // Sin contraseña por defecto
    private Connection conn;
    private PreparedStatement pstmt = null;

    /**
     * Creado singleton de la clase para que exista una única conexión a la base de datos
     * Establece la conexión con BBDD al inicializar el constructor
     */
    private static volatile DatabaseUtil INSTANCE;

    private DatabaseUtil() throws SQLException{
    }

    public static DatabaseUtil getInstance(){
        try{
            if(DatabaseUtil.INSTANCE == null){
                INSTANCE = new DatabaseUtil();
            }
        } catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }
        return INSTANCE;
    }

    public void getConnection() {
        try{
            this.conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDatabase() {
        try{
            conn.setAutoCommit(false);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS Prediction("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "LOCATION VARCHAR(255), "
                    + "DATE VARCHAR(255),"
                    + "SKY_STAT ARRAY[],"
                    + "MAX_TEMP DOUBLE(3,1),"
                    + "MIN_TEMP DOUBLE(3,1),"
                    + "TOTAL_PRECIPITATION DOUBLE(3,1),"
                    + "WIND_SPEED DOUBLE(4,2),"
                    + "CLOUD_PERCENTAGE DOUBLE(4,2),"
                    + "RELATIVE_HUMIDITY DOUBLE(4,2))";
            pstmt = conn.prepareStatement(createTableSQL);
            conn.commit();

        } catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        } finally{
            // Cerrar la conexión y el objeto PreparedStatement
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public void savePrediction(Prediccion pred){
        if(pred.prediccionValida()){
            try{
                conn.setAutoCommit(false);

                String sql = "INSERT INTO Prediction (LOCATION, DATE, SKY_STAT, MAX_TEMP, MIN_TEMP, TOTAL_PRECIPITATION, WIND_SPEED, CLOUD_PERCENTAGE, RELATIVE_HUMIDITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, pred.getLugar());
                pstmt.setString(2, pred.getFecha());
                pstmt.setString(3,  pred.getEstadoCielo());
                pstmt.setDouble(4, pred.getTemperaturaMax());
                pstmt.setDouble(5, pred.getTemperaturaMin());
                pstmt.setDouble(6, pred.getPrecipitacionTotal());
                pstmt.setDouble(7, pred.getViento());
                pstmt.setDouble(8, pred.getCoberturaNubosa());
                pstmt.setDouble(9, pred.getHumedad());

                int filasModificadas = pstmt.executeUpdate();

                conn.commit();

                // Mostrar el número de filas modificadas
                System.out.println("Número de filas modificadas: " + filasModificadas);

            }catch(SQLException e){
                System.err.println("Error SQL: " + e.getMessage());
            }finally {
                // Cerrar la conexión y el objeto PreparedStatement
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public void deletePrediction(Prediccion pred){
        int id = 0;
        HashMap<Integer,Prediccion> predictions = getAllPredictions();
        for(int i : predictions.keySet()){
            if(predictions.values().equals(pred)){
                id = i;
                predictions.remove(i);
            }
        }

        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM Prediction WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int filasModificadas = pstmt.executeUpdate();

            conn.commit();
            System.out.println("Predicción eliminada \n"+"Filas afectadas: "+filasModificadas);

        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public HashMap<Integer,Prediccion> getAllPredictions(){
        HashMap<Integer,Prediccion> map = new HashMap<>();
        try{
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM departamentos";

            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            conn.commit();

            while(rs.next()){
                int id = rs.getInt("ID");
                String location = rs.getString("LOCATION");
                String date = rs.getString("DATE");
                String skystat = rs.getString("SKY_STAT");
                double maxTemp = rs.getDouble("MAX_TEMP");
                double minTemp = rs.getDouble("MIN_TEMP");
                double precipitation = rs.getDouble("TOTAL_PRECIPITATION");
                double windSpeed = rs.getDouble("WIND_SPEED");
                double cloud = rs.getDouble("CLOUD_PERCENTAGE");
                double humidity = rs.getDouble("RELATIVE_HUMIDITY");

                Prediccion pred = new Prediccion(location,date,convertStringToList(skystat),maxTemp,minTemp,precipitation,windSpeed,cloud,humidity);
                map.put(id,pred);

            }
        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return map;
    }

    public List<String> convertStringToList(String s){
        List<String> sky = List.of(s.split(","));
        return sky;
    }
}

