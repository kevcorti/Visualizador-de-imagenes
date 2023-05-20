/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

/**
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class Persona {
    private String nombre;

    public Persona(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }
    
    public String toString(){
        return this.nombre;
    }
}
