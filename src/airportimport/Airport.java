/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportimport;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public class Airport implements Comparable<Airport> {

    private String code;
    private String name;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    
    public Airport a = null;
    public double dist = Double.MAX_VALUE; // MAX_VALUE assumed to be infinity
    public Airport previous = null;
    public final Map<Airport, Double> neighbours = new HashMap<>();

    
    public Airport(Airport a) {
        this.a = a;
    }

    public Airport(String code, String name, String city, String country, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public void printPath() {
        if (this == this.previous) {
            System.out.printf("%s", this.a);
        } else if (this.previous == null) {
            System.out.printf("%s(unreached)", this.a);
        } else {
            this.previous.printPath();
            System.out.printf(" -> %s(%d)", this.a, this.dist);
        }
    }

    public int compareTo(Airport other) {
        if (dist == other.dist) {
            return (a.name).compareTo(other.name);
        }

        return Double.compare(dist, other.dist);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + dist + ")";
    }
}
