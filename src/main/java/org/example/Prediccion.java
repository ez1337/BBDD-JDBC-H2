package org.example;

public class Prediccion {
    private String fecha;
    private double temperaturaMax;
    private double temperaturaMin;
    private double ventoManha;
    private double ventoTarde;
    private double ventoNoite;
    private double precipitacionManha;
    private double precipitacionTarde;
    private double precipitacionNoite;
    private double coberturaNubosaManha;
    private double coberturaNubosaTarde;
    private double coberturaNubosaNoite;

    public Prediccion(String fecha, double temperaturaMax, double temperaturaMin,
                      double ventoManha, double ventoTarde, double ventoNoite,
                      double precipitacionManha, double precipitacionTarde, double precipitacionNoite,
                      double coberturaNubosaManha, double coberturaNubosaTarde, double coberturaNubosaNoite) {
        this.fecha = fecha;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.ventoManha = ventoManha;
        this.ventoTarde = ventoTarde;
        this.ventoNoite = ventoNoite;
        this.precipitacionManha = precipitacionManha;
        this.precipitacionTarde = precipitacionTarde;
        this.precipitacionNoite = precipitacionNoite;
        this.coberturaNubosaManha = coberturaNubosaManha;
        this.coberturaNubosaTarde = coberturaNubosaTarde;
        this.coberturaNubosaNoite = coberturaNubosaNoite;
    }

    // Getters y Setters para todos los campos

    @Override
    public String toString() {
        return "Prediccion: " +
                "fecha : " + fecha + '\n' +
                " Temperatura maxima: " + temperaturaMax +
                ", Temperatura minima: " + temperaturaMin +
                ". Vento: maña:" + ventoManha +
                ", tarde:" + ventoTarde +
                ", noite: " + ventoNoite +
                "\n Precipitacion: maña:" + precipitacionManha +
                ", tarde:" + precipitacionTarde +
                ", noite:" + precipitacionNoite +
                ", Cobertura nubosa: maña:" + coberturaNubosaManha +
                ", tarde: " + coberturaNubosaTarde +
                ", noite:" + coberturaNubosaNoite +
                "\n --------------------------------------------------------------";
    }

}
