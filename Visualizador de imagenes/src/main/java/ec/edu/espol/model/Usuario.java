/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import static ec.edu.espol.model.Album.readFromFile;
import ec.edu.espol.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Objects;
import javafx.scene.control.Alert;

/**
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class Usuario {
    private String nombreUsuario;
    private String contra;
    private String nombre;
    private String apellido;
    private ArrayList<Album> albumes;
    
    public Usuario(String nombre, String apellido, String nombreUsuario, String contra){
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario.toUpperCase();
        this.contra = contra.toUpperCase();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    
    public void saveFile(String nomfile){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(nomfile, true))){
            bf.write(this.nombre + "|" + this.apellido + "|" + this.nombreUsuario + "|" + this.contra + "\n");
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Usuario registrado con éxito");
            a.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible agregarlo");
            a.show();
        }
    }

    public static ArrayList<Usuario> readFromFile(String nomfile){
        ArrayList<Usuario> usuarios = new ArrayList<>(1);
        try(BufferedReader bf = new BufferedReader(new FileReader(nomfile))){
            String linea;
            while((linea = bf.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                Usuario usuario = new Usuario(arreglo[0],arreglo[1],arreglo[2],arreglo[3]);
                usuarios.addLast(usuario);
            }
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible obtener los usuarios");
            a.show();
        }
        return usuarios;
    }
    
    public static Usuario verificarNombreUsuario(String nombre){
        ArrayList<Usuario> usuarios = readFromFile("usuarios.txt");
        for(Usuario usuario: usuarios){
            if(Objects.equals(nombre.toUpperCase(),usuario.nombreUsuario))
                return usuario;
        }
        return null;
    }
    
    public static Usuario verificarContra(String contra, String nombreUsuario){
        ArrayList<Usuario> usuarios = readFromFile("usuarios.txt");
        for(Usuario usuario: usuarios){
            if(Objects.equals(contra.toUpperCase(),usuario.contra) && Objects.equals(nombreUsuario.toUpperCase(), usuario.nombreUsuario))
                return usuario;
        }
        return null;
    }
    
    public static void crearUsuario(String nombre, String apellido, String nombreUsuario, String contra){
        Usuario usuario = new Usuario(nombre,apellido, nombreUsuario, contra);
        usuario.saveFile("usuarios.txt");
    }
}
