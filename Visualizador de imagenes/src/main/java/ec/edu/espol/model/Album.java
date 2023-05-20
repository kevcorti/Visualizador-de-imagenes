/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;
import ec.edu.espol.util.ArrayList;
import ec.edu.espol.util.CircularDoubleLinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 *
 * @author Kevin C, Kenny J., Richard P.
 * @param <E>
 */
public class Album <E> {
    private String nombre;
    private CircularDoubleLinkedList<Imagen> imagenes; 
    private String desc;
    private String nombreUsuario;

    public Album(String nombre, String desc, String nombreUsuario) {
        this.nombre = nombre.toUpperCase();
        this.imagenes = new CircularDoubleLinkedList<>();
        this.desc = desc.toUpperCase();
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CircularDoubleLinkedList<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(CircularDoubleLinkedList<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
    
    public static void crearAlbum(String nombre, String desc, String nombreUsuario){
        Album album = new Album(nombre,desc,nombreUsuario);
        album.saveFile("albumes.txt");
    }
    
    public void saveFile(String nomfile){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(nomfile, true))){
            bf.write(this.nombreUsuario + "|" + this.nombre + "|" + this.desc + "\n");
            Alert a = new Alert(AlertType.CONFIRMATION, "Álbum agregado con éxito");
            a.show();
        }
        catch(IOException ex){
            Alert a = new Alert(AlertType.ERROR, "No es posible agregarlo");
            a.show();
        }
    }
    
    public static ArrayList<Album> readFromFile(String nomfile){
        ArrayList<Album> albumes = new ArrayList<>(1);
        try(BufferedReader bf = new BufferedReader(new FileReader(nomfile))){
            String linea;
            while((linea = bf.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                Album album = new Album(arreglo[1],arreglo[2],arreglo[0]);
                albumes.addLast(album);
            }
        }
        catch(IOException ex){
            Alert a = new Alert(AlertType.ERROR, "No es posible obtener los albumes");
            a.show();
        }
        return albumes;
    }
    
    public static boolean verificarNombreAlbum(String nombre, String nombreUsuario){
        ArrayList<Album> albumes = readFromFile("albumes.txt");
        for(Album album: albumes){
            if(Objects.equals(nombre,album.nombre) && Objects.equals(nombreUsuario, album.nombreUsuario))
                return true;
        }
        return false;
    }
}
