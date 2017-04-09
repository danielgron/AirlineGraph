/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphimpl;

/**
 *
 * @author danie
 * @param <Data>
 */
public class LinkedElement<Data> {
    Data data;
    LinkedElement<Data> rest;
    
    public LinkedElement(Data data, LinkedElement rest){
        this.data=data;
        this.rest=rest;
    }
    public Data getData(){
        return data;
    }
}
