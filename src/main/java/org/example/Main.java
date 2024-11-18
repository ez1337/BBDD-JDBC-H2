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
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import  org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int respuesta;
        String prueba = "./prueba.json";
        URL url = null;
        try {

            url = new URL("https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=15030&request_locale=gl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        do{
            System.out.println("Que accion deseas realizar? \n1.Mostrar datos en pantalla \n2.Generar archivo .csv con datos de las 7 ciudades importantes \n3.Salir");
            respuesta = sc.nextInt();

            switch(respuesta){
                case 1:
                    try {
                        //URL url = new URL("https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=15030&request_locale=gl");
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

                    try {
                        Object obj = (Object) new JSONParser().parse(new FileReader("prueba.json"));
                        JSONObject js = (JSONObject) obj;

                        String ceo = (String)js.get("ceo");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> lineas;
                    /*
                    try {
                        lineas = Files.readAllLines(Paths.get(prueba),
                                StandardCharsets.UTF_8);
                        for (String linea : lineas) {
                            if(linea){

                            }

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }*/
                    break;
                case 2:
                    break;
            }
        }while(respuesta != 3);
    }
}