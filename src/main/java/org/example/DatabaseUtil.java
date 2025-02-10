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

    public void initializeDatabase() {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS PREDICTION("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "LOCATION VARCHAR(255), "
                + "`DATE` VARCHAR(255), "
                + "SKY_STAT VARCHAR(255), "
                + "MAX_TEMP DECIMAL(3,1), "
                + "MIN_TEMP DECIMAL(3,1), "
                + "TOTAL_PRECIPITATION DECIMAL(3,1), "
                + "WIND_SPEED DECIMAL(4,2), "
                + "CLOUD_PERCENTAGE DECIMAL(4,2), "
                + "RELATIVE_HUMIDITY DECIMAL(4,2))";

        try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(createTableSQL)){
            conn.setAutoCommit(false);

            pstmt.execute();

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Table 'Prediction' created successfully");

        } catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    public void savePrediction(Prediccion pred){
        final String sql = "INSERT INTO PREDICTION (LOCATION, DATE, SKY_STAT, MAX_TEMP, MIN_TEMP, TOTAL_PRECIPITATION, WIND_SPEED, CLOUD_PERCENTAGE, RELATIVE_HUMIDITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if(pred.prediccionValida()){
            try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
                conn.setAutoCommit(false);

                pstmt.setString(1, pred.getLugar());
                pstmt.setString(2, pred.getFecha());
                pstmt.setString(3, pred.getEstadoCielo());
                pstmt.setDouble(4, pred.getTemperaturaMax());
                pstmt.setDouble(5, pred.getTemperaturaMin());
                pstmt.setDouble(6, pred.getPrecipitacionTotal());
                pstmt.setDouble(7, pred.getViento());
                pstmt.setDouble(8, pred.getCoberturaNubosa());
                pstmt.setDouble(9, pred.getHumedad());

                pstmt.executeUpdate();

                conn.commit();
                conn.setAutoCommit(true);
            }catch(SQLException e){
                System.err.println("Error SQL: " + e.getMessage());
            }
        }
    }

    public void deletePrediction(int id){
        final String sql = "DELETE FROM PREDICTION WHERE ID = ?";
        try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            conn.setAutoCommit(false);
            pstmt.setInt(1, id);
            int filasModificadas = pstmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Predicción con ID = "+ id +" eliminada \n"+"Filas afectadas: "+filasModificadas);

        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }
    }

    public HashMap<Integer,Prediccion> getAllPredictions(){
        HashMap<Integer,Prediccion> map = new HashMap<>();
        final String sql = "SELECT * FROM PREDICTION";
        try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);

            ResultSet rs = pstmt.executeQuery();

            conn.commit();
            conn.setAutoCommit(true);

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
        }
        return map;
    }

    public List<String> convertStringToList(String s){
        List<String> sky = List.of(s.split(","));
        return sky;
    }
}

