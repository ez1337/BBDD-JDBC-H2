package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int respuesta;
        String prueba = "./prueba.json";
        URL url = null;
        /*
        try {

            url = new URL("https:servizos.meteogalicia.gal/apiv4/getNumericForecastInfo?idConc=15003&variables=temperature,wind,sky_state,precipitation_amount,relative_humidity,cloud_area_fraction&API_KEY=4hk91p9mQV1qysT4PE1YJndSRCebJhd5E1uOf07nU1bcqiR0GN1qLy3SfkRp6f4B");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        try {
            // Configurar la URL y la conexión HTTP
            //url = new URL("https://servizos.meteogalicia.gal/apiv4/getNumericForecastInfo?coords=-8.396,43.37135&variables=temperature,wind,sky_state,precipitation_amount,relative_humidity,cloud_area_fraction&API_KEY=4hk91p9mQV1qysT4PE1YJndSRCebJhd5E1uOf07nU1bcqiR0GN1qLy3SfkRp6f4B");
            url = new URL("https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=15078&variables=relative_humidity&API_KEY=4hk91p9mQV1qysT4PE1YJndSRCebJhd5E1uOf07nU1bcqiR0GN1qLy3SfkRp6f4B");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // Leer JSON desde la URL
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();
            // Escribir JSON en un archivo
            Files.write(Paths.get(prueba), jsonContent.toString().getBytes(), StandardOpenOption.CREATE);
            System.out.println("JSON escrito en el archivo exitosamente.");
        } catch (MalformedURLException e) {
            System.out.println("La URL es incorrecta: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer o escribir el archivo: " + e.getMessage());
        }

        do{
            System.out.println("Que accion deseas realizar? \n1.Mostrar datos en pantalla \n2.Generar archivo .csv con datos de las 7 ciudades importantes \n3.Salir");
            respuesta = sc.nextInt();

            switch(respuesta){
                case 1:
                    try {
                        // Leer JSON desde la URL
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        StringBuilder jsonContent = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            jsonContent.append(line);
                        }
                        reader.close();

                        // Escribir JSON en un archivo
                        Files.write(Paths.get(prueba), jsonContent.toString().getBytes(), StandardOpenOption.CREATE);
                        System.out.println("JSON escrito en el archivo exitosamente.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<Prediccion> predicciones = new ArrayList<>();
                    try {
                        Object obj = (Object) new JSONParser().parse(new FileReader("prueba.json"));
                        JSONObject jsonObject = (JSONObject) obj;
                        JSONObject predConcello = (JSONObject) jsonObject.get("predConcello");
                        JSONArray listaPredDiaConcello = (JSONArray) predConcello.get("listaPredDiaConcello");
                        // Iterar sobre la lista de predicciones diarias
                        for (Object predDiaObj : listaPredDiaConcello) {
                            JSONObject predDia = (JSONObject) predDiaObj;

                            String fechaPrediccion = (String) predDia.get("dataPredicion");
                            // Extraer los datos de 'ceo' y 'vento'
                            JSONObject vento = (JSONObject) predDia.get("vento");
                            double ventoManha = ((Number) vento.get("manha")).doubleValue();
                            double ventoTarde = ((Number) vento.get("tarde")).doubleValue();
                            double ventoNoite = ((Number) vento.get("noite")).doubleValue();

                            JSONObject pchoiva = (JSONObject) predDia.get("pchoiva");

                            double temperaturaMax = ((Number) predDia.get("tMax")).doubleValue();
                            double temperaturaMin = ((Number) predDia.get("tMin")).doubleValue();

                            double precipitacionManha = ((Number) pchoiva.get("manha")).doubleValue();
                            double precipitacionTarde = ((Number) pchoiva.get("tarde")).doubleValue();
                            double precipitacionNoite = ((Number) pchoiva.get("noite")).doubleValue();
                            JSONObject ceo = (JSONObject) predDia.get("ceo");
                            double coberturaNubosaManha = ((Number) ceo.get("manha")).doubleValue();
                            double coberturaNubosaTarde = ((Number) ceo.get("tarde")).doubleValue();
                            double coberturaNubosaNoite = ((Number) ceo.get("noite")).doubleValue();

//                            System.out.println("Fecha: " + fechaPrediccion);
//                            System.out.println("Temperatura Máxima: " + temperaturaMax + ", Temperatura Mínima: " + temperaturaMin);
//                            System.out.println("Precipitación (Mañana): " + precipitacionManha + ", Precipitación (Tarde): " + precipitacionTarde + ", Precipitación (Noche): " + precipitacionNoite);
//                            System.out.println("Cobertura Nubosa (Mañana): " + coberturaNubosaManha + ", Cobertura Nubosa (Tarde): " + coberturaNubosaTarde + ", Cobertura Nubosa (Noche): " + coberturaNubosaNoite);
//                            System.out.println("Vento (Mañana): " + ventoManha + ", Vento (Tarde): " + ventoTarde + ", Vento (Noche): " + ventoNoite);
//                            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
                              Prediccion prediccion = new Prediccion( fechaPrediccion,  temperaturaMax, temperaturaMin, ventoManha, ventoTarde
                                      , ventoNoite, precipitacionManha, precipitacionTarde, precipitacionNoite, coberturaNubosaManha, coberturaNubosaTarde, coberturaNubosaNoite );
                              // Agregar la instancia a la lista
                              predicciones.add(prediccion);
                              // Imprimir los datos
                              System.out.println(prediccion);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (org.json.simple.parser.ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    break;
            }
        }while(respuesta != 3);
    }
}