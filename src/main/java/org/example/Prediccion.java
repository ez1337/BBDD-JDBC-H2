package org.example;

import java.sql.*;
import java.util.List;

public class Prediccion {
    private String lugar;
    private String fecha;
    private List<String> estadoCielo;
    private double temperaturaMax;
    private double temperaturaMin;
    private double precipitacionTotal;
    private double viento;
    private double coberturaNubosa;
    private double humedad;

    public Prediccion(String lugar, String fecha, List<String> estadoCielo, double temperaturaMax, double temperaturaMin, double precipitacionTotal,
                      double velocidadViento, double coberturaNubosa, double humedad) {
        this.lugar = lugar;
        this.fecha = fecha;
        this.estadoCielo = estadoCielo;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.precipitacionTotal = precipitacionTotal;
        this.viento = velocidadViento;
        this.coberturaNubosa = coberturaNubosa;
        this.humedad = humedad;
    }

    public boolean prediccionValida(){
        if(lugar.isEmpty() || lugar.isBlank() || fecha.isEmpty() || fecha.isBlank()
        || estadoCielo.isEmpty() || temperaturaMax == 0.0d || temperaturaMin == 0.0d || precipitacionTotal == 0.0d
        || viento == 0.0d || coberturaNubosa == 0.0d || humedad == 0.0d){
            return false;
        }
        return true;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEstadoCielo() {
        StringBuilder estados = new StringBuilder();
        for(String s : estadoCielo){
            estados.append(s).append(",");
        }
        return estados.toString();
    }

    public void setEstadoCielo(List<String> estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public double getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(double temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public double getPrecipitacionTotal() {
        return precipitacionTotal;
    }

    public void setPrecipitacionTotal(double precipitacionTotal) {
        this.precipitacionTotal = precipitacionTotal;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public double getCoberturaNubosa() {
        return coberturaNubosa;
    }

    public void setCoberturaNubosa(double coberturaNubosa) {
        this.coberturaNubosa = coberturaNubosa;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    @Override
    public String toString() {
        return  lugar + "\n" +
                "Prediccion del dia: " + fecha +
                "\nestado del cielo: " + estadoCielo +
                ", temperatura maxima: " + temperaturaMax + " ºC" +
                ", temperatura minima: " + temperaturaMin + " ºC" +
                ", \nprecipitacion total: " + precipitacionTotal + " l/m2" +
                ", velocidad del viento: " + viento + " km/h" +
                ", cobertura nubosa: " + coberturaNubosa + " %"+
                ", humedad relativa: " + humedad + " % \n -----------------------------------------------------------------------------";
    }
}
