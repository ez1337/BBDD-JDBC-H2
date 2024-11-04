package org.example;

public class Main {
    public static void main(String[] args) {
        String URL_API = "https://jsonplaceholder.typicode.com/users";

        // Cliente para la conexión
        Client client = ClientBuilder.newClient();
        // Definición de URL
        WebTarget target = client.target(URL_API);
        // Recogemos el resultado en una variable String
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        // Escribimos por consola el resultado de json
//      System.out.println(response);

        //Almacenamos la información del json en una lista java
        ArrayList<User> listUser = saveJsonToList(response);

    }
}