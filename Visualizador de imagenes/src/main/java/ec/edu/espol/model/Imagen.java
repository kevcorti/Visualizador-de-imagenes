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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDate;

import java.util.Objects;
import javafx.scene.control.Alert;
/**
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class Imagen {
    private String nombreUsuario;
    private String album;
    private String lugar;
    private LocalDate fecha;
    private String descripcion;
    private String marcaCam;
    private String modeloCam; 
    private ArrayList<Persona> personas;
    private ArrayList<String> hashtags;
    private String reaccion;
    private String nombreImagen;

    public Imagen(String album,String nombreImagen,String lugar,LocalDate fecha, String desc,String marcaCam, String modeloCam, ArrayList<Persona> personas,ArrayList<String> hashtags,String reaccion, String nombreUsuario) {
        this.album = album.toUpperCase();
        this.lugar = lugar.toUpperCase();
        this.fecha = fecha;
        this.descripcion = desc.toUpperCase();
        this.marcaCam = marcaCam.toUpperCase();
        this.modeloCam = modeloCam.toUpperCase(); 
        this.personas = personas;
        this.hashtags = hashtags;
        this.reaccion = reaccion;
        this.nombreImagen = nombreImagen;
        this.nombreUsuario = nombreUsuario.toUpperCase();
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarcaCam() {
        return marcaCam;
    }

    public void setMarcaCam(String marcaCam) {
        this.marcaCam = marcaCam;
    }

    public String getModeloCam() {
        return modeloCam;
    }

    public void setModeloCam(String modeloCam) {
        this.modeloCam = modeloCam;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getReaccion() {
        return reaccion;
    }

    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public static void agregarReaccion(String reaccion, String nombreArchivo, Imagen img){
        ArrayList<Imagen> imagenes = Imagen.readFromFileArray("imagenes.txt");
                try(BufferedWriter bf = new BufferedWriter(new FileWriter("imagenes.txt"))){
                    for(Imagen imagen: imagenes){
                        if(Objects.equals(imagen.nombreImagen, img.nombreImagen)){
                            bf.write(img.getNombreUsuario() + "|" + img.getAlbum() + "|" + img.getNombreImagen() + "|" + img.getLugar() + "|" + img.getFecha() + "|" + img.getDescripcion() + "|" 
                            + img.getMarcaCam() + "|" + img.getModeloCam() + "|" + img.getPersonas() + "|" + img.getHashtags() + "|" + reaccion.toUpperCase() +"\n");
                        }
                        else{
                            bf.write(img.getNombreUsuario() + "|" + img.getAlbum() + "|" + img.getNombreImagen() + "|" + img.getLugar() + "|" + img.getFecha() + "|" + img.getDescripcion() + "|" 
                            + img.getMarcaCam() + "|" + img.getModeloCam() + "|" + img.getPersonas() + "|" + img.getHashtags() + "|" + img.getReaccion() +"\n");
                        }
                    }
                }    
                catch(IOException ex){
                    Alert a = new Alert(Alert.AlertType.ERROR,"Error seleccionando reacción");
                    a.show();
                }
    }
    
    public static void agregarImagen(String nombreUsuario, Imagen imagen, String infoImagen,String nombreArchivo){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(nombreArchivo, true))){
            bf.write(nombreUsuario + "|" + imagen.album + "|" + infoImagen + "|" + imagen.lugar + "|" + imagen.fecha + "|" + imagen.descripcion + "|" + imagen.marcaCam + "|" +
                    imagen.modeloCam + "|" + imagen.personas + "|" + imagen.hashtags + "|" + imagen.reaccion +"\n");
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al subir imagen");
            a.show();
        }
    }
    
    public static ArrayList<Imagen> readFromFileArray(String nomfile){
        ArrayList<Imagen> imagenes = new ArrayList<>(1);
        try(BufferedReader bf = new BufferedReader(new FileReader(nomfile))){
            String linea;
            while((linea = bf.readLine()) != null){
                ArrayList<Persona> personas = new ArrayList<>(1);
                String[] arregloPersonas;
                ArrayList<String> hashtags = new ArrayList<>(1);
                String[] arregloHashtags;
                String[] arreglo = linea.split("\\|");
                String[] fecha = arreglo[4].split("-");
                int an = Integer.parseInt(fecha[0]);
                int mes = Integer.parseInt(fecha[1]);
                int dia = Integer.parseInt(fecha[2]);
                if(Objects.equals(arreglo[8], "null"))
                    personas = null;
                else{
                    arregloPersonas = arreglo[8].split(",");
                    for(int i=0; i < arregloPersonas.length; i++){
                        personas.addLast(new Persona(arregloPersonas[i]));
                    }
                }
                if(Objects.equals(arreglo[9], "null"))
                    hashtags = null;
                else{
                    arregloHashtags = arreglo[9].split(",");
                    for(int i=0; i < arregloHashtags.length; i++){
                        hashtags.addLast(arregloHashtags[i]);
                    }
                }
                Imagen imagen = new Imagen(arreglo[1],arreglo[2],arreglo[3],LocalDate.of(an,mes,dia),arreglo[5],arreglo[6],arreglo[7],personas,hashtags,arreglo[10],arreglo[0]);
                imagenes.addLast(imagen);
            }
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible obtener las imágenes");
            a.show();
        }
        return imagenes;
    }
    
    public static CircularDoubleLinkedList<Imagen> readFromFile(String nomfile){
        CircularDoubleLinkedList<Imagen> imagenes = new CircularDoubleLinkedList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(nomfile))){
            String linea;
            ArrayList<Persona> personas = new ArrayList<>(1);
            String[] arregloPersonas;
            ArrayList<String> hashtags = new ArrayList<>(1);
            String[] arregloHashtags;
            while((linea = bf.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                String[] fecha = arreglo[4].split("-");
                int an = Integer.parseInt(fecha[0]);
                int mes = Integer.parseInt(fecha[1]);
                int dia = Integer.parseInt(fecha[2]);
                if(Objects.equals(arreglo[8], "null"))
                    personas = null;
                else{
                    arregloPersonas = arreglo[8].split(",");
                    for(int i=0; i < arregloPersonas.length; i++){
                        personas.addLast(new Persona(arregloPersonas[i]));
                    }
                }
                if(Objects.equals(arreglo[9], "null"))
                    hashtags = null;
                else{
                    arregloHashtags = arreglo[9].split(",");
                    for(int i=0; i < arregloHashtags.length; i++){
                        hashtags.addLast(arregloHashtags[i]);
                    }
                }
                Imagen imagen = new Imagen(arreglo[1],arreglo[2],arreglo[3],LocalDate.of(an,mes,dia),arreglo[5],arreglo[6],arreglo[7],personas,hashtags,arreglo[10],arreglo[0]);
                imagenes.addLast(imagen);
            }
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible obtener las imágenes");
            a.show();
        }
        return imagenes;
    }
    
    public static void guardarImagen(File file,Imagen imagen,int n,String nombreUsuario){
        if(file != null){
                try{
                    String dest;
                    if(file.getPath().endsWith(".jpg")){
                        dest = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + nombreUsuario + "_" + imagen.album + "_" + n + ".jpg";
                        agregarImagen(nombreUsuario,imagen,nombreUsuario + "_" + imagen.album + "_" + n + ".jpg", "imagenes.txt");
                    }
                    else if(file.getPath().endsWith(".png")){
                        dest = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + nombreUsuario + "_" + imagen.album + "_" + n + ".png";
                        agregarImagen(nombreUsuario,imagen,nombreUsuario + "_" + imagen.album + "_" + n + ".png", "imagenes.txt");
                    }
                    else{
                        dest = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + nombreUsuario + "_" + imagen.album + "_" + n + ".gif";
                        agregarImagen(nombreUsuario,imagen,nombreUsuario + "_" + imagen.album + "_" + n + ".gif", "imagenes.txt");
                    }
                    Path destino = Paths.get(dest);
                    String orig = file.getPath();
                    Path origen = Paths.get(orig);
                    Files.copy(origen, destino, REPLACE_EXISTING);
                }
                catch(IOException ex){
                    Alert a = new Alert(Alert.AlertType.ERROR, "ERROR al cargar imagen");
                    a.show();
                }
            }
    }
    
    public static boolean verificarLugar(String lugar, String nombreUsuario){
        ArrayList<Imagen> imagenes = readFromFileArray("imagenes.txt");
        for(Imagen imagen: imagenes){
            if(Objects.equals(imagen.nombreUsuario,nombreUsuario) && Objects.equals(imagen.lugar, lugar))
                return true;
        }
        return false;
    }
}
