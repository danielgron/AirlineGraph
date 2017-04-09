/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportimport;

/**
 *
 * @author Daniel
 */
public class Route {
    private Airline airline;
    private Airport source;
    private Airport destination;
    private Path path;

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }


    public Route(Airline airline, Airport source, Airport destination, Path path) {
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.path= path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
    
    
    
    
    
}
