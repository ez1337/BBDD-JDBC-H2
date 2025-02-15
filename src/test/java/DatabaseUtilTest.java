import org.example.DatabaseUtil;
import org.example.Prediccion;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseUtilTest {
    private DatabaseUtil dbUtil;

    @BeforeAll
    void setUp() {
        dbUtil = DatabaseUtil.getInstance();
        dbUtil.initializeDatabase();
    }

    @Test
    void testInsertPrediction() {
        Prediccion pred = new Prediccion("Madrid", "2025-02-11", List.of("Despejado"), 30.0, 20.0, 0.0, 10.5, 15.0, 60.0);
        dbUtil.savePrediction(pred);

        HashMap<Integer, Prediccion> predictions = dbUtil.getAllPredictions();
        assertFalse(predictions.isEmpty(), "La predicción no se insertó correctamente");
    }

    @Test
    void testSQLInjectionOnInsert() {
        Prediccion pred = new Prediccion("TestCity'); DROP TABLE PREDICTION; --", "2025-02-11", List.of("Despejado"), 30.0, 20.0, 0.0, 10.5, 15.0, 60.0);
        assertDoesNotThrow(() -> dbUtil.savePrediction(pred), "La inyección SQL podría haber afectado la BD");
    }

    @Test
    void testDeletePrediction() {
        Prediccion pred = new Prediccion("Barcelona", "2025-03-15", List.of("Nublado"), 25.0, 18.0, 2.0, 8.5, 20.0, 70.0);
        dbUtil.savePrediction(pred);

        HashMap<Integer, Prediccion> predictions = dbUtil.getAllPredictions();
        int idToDelete = predictions.keySet().iterator().next();

        dbUtil.deletePrediction(idToDelete);
        predictions = dbUtil.getAllPredictions();

        assertFalse(predictions.containsKey(idToDelete), "La predicción no se eliminó correctamente");
    }

    @Test
    void testSQLInjectionOnDelete() {
        assertDoesNotThrow(() -> dbUtil.deletePrediction(Integer.parseInt("1 OR 1=1")), "La inyección SQL podría haber afectado la BD");
    }
}

