/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import ec.edu.espol.util.ArrayList;
import ec.edu.espol.util.CircularDoubleLinkedList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaFotosController implements Initializable {

    @FXML
    private Text nombreAlbum;
    private Button btnComentarios;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAnteriorFoto;
    @FXML
    private Button btnSiguienteFoto;
    @FXML
    private Button btnDetalles;
    private Usuario usuario;
    @FXML
    private ImageView imgview;
    private Imagen img;
    private String reaccion;
    @FXML
    private Button btnVisualizar;
    private String nombreUsuarioAlbum;
    private CircularDoubleLinkedList<Imagen> imagenes;
    private int indiceImagen;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAnteriorFoto.setDisable(true);
        btnSiguienteFoto.setDisable(true);
        btnEliminar.setDisable(true);
        btnDetalles.setDisable(true);
        indiceImagen = 0;
    }    


    @FXML
    private void eliminarFoto(MouseEvent event) {
        ArrayList<Imagen> imagenesTotal = Imagen.readFromFileArray("imagenes.txt");
        int indicee = 0;
        while(!(Objects.equals(imagenesTotal.get(indicee).getNombreImagen(), img.getNombreImagen()))){
            indicee++;
        }
        ArrayList<Imagen> imagenesAntes = new ArrayList<Imagen>(1);
        for(int i = 0; i < indicee; i++){
            imagenesAntes.addLast(imagenesTotal.get(i));
        }
        ArrayList<Imagen> imagenesDespues = new ArrayList<Imagen>(1);
        for(int i = (indicee + 1); i < imagenesTotal.size(); i++){
            imagenesDespues.addLast(imagenesTotal.get(i));
        }
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("imagenes.txt"))){
            for(Imagen imagen: imagenesAntes){
                bf.write(imagen.getNombreUsuario() + "|" + imagen.getAlbum() + "|" + imagen.getNombreImagen() + "|" + imagen.getLugar() + "|" + imagen.getFecha() + "|" + imagen.getDescripcion() + "|" 
                        + imagen.getMarcaCam() + "|" + imagen.getModeloCam() + "|" + imagen.getPersonas() + "|" + imagen.getHashtags() + "|" + imagen.getReaccion() +"\n");
            }
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR,"Error eliminando");
            a.show();
        }
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("imagenes.txt", true))){
                for(Imagen imagen: imagenesDespues){
                    if((Objects.equals(this.usuario.getNombreUsuario(), imagen.getNombreUsuario())) && (Objects.equals(this.nombreUsuarioAlbum,imagen.getAlbum()))){
                        String[] arreglo = imagen.getNombreImagen().split("_");
                        String[] subArreglo = arreglo[2].split("\\.");
                        int n = Integer.parseInt(subArreglo[0]) - 1;
                        String nuevoNombre = arreglo[0] + "_" + arreglo[1] + "_" + n + "." + subArreglo[1];
                        bf.write(imagen.getNombreUsuario() + "|" + imagen.getAlbum() + "|" + nuevoNombre + "|" + imagen.getLugar() + "|" + imagen.getFecha() + "|" + imagen.getDescripcion() + "|" 
                                + imagen.getMarcaCam() + "|" + imagen.getModeloCam() + "|" + imagen.getPersonas() + "|" + imagen.getHashtags() + "|" + imagen.getReaccion() +"\n");
                    }
                    else{
                        bf.write(imagen.getNombreUsuario() + "|" + imagen.getAlbum() + "|" + imagen.getNombreImagen() + "|" + imagen.getLugar() + "|" + imagen.getFecha() + "|" + imagen.getDescripcion() + "|" 
                            + imagen.getMarcaCam() + "|" + imagen.getModeloCam() + "|" + imagen.getPersonas() + "|" + imagen.getHashtags() + "|" + imagen.getReaccion() +"\n");
                    }
                }
        }
        catch(IOException ex){
                Alert a = new Alert(Alert.AlertType.ERROR,"Error eliminando");
                a.show();
        }  
        String rut = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + img.getNombreImagen();
        Path ruta = Paths.get(rut);
        try{
            Files.delete(ruta);
        } 
        catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error eliminando");
            a.show();
        }
        for(Imagen imagen: imagenesDespues){
            if((Objects.equals(this.usuario.getNombreUsuario(), imagen.getNombreUsuario())) && (Objects.equals(this.nombreUsuarioAlbum,imagen.getAlbum()))){
                        String[] arreglo = imagen.getNombreImagen().split("_");
                        String[] subArreglo = arreglo[2].split("\\.");
                        int n = Integer.parseInt(subArreglo[0]) - 1;
                        String nuevoNombre = arreglo[0] + "_" + arreglo[1] + "_" + n + "." + subArreglo[1];
                        String ruta1 = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + imagen.getNombreImagen();
                        String ruta2 = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + nuevoNombre;
                        File file1 = new File(ruta1);
                        File file2 = new File(ruta2);
                        file1.renameTo(file2);
            }
        }
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaInicial");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaInicialController pic = loader.getController();
            pic.recibirUsuario(this.usuario);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("¡Organiza tus fotos!");
            String rut1 = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
            Path ruta1 = Paths.get(rut1);
            Image imagen = new Image("file:" + ruta1);
            sg.getIcons().add(imagen);
            sg.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible eliminar");
            a.show();
        }
        Alert a = new Alert(AlertType.CONFIRMATION, "Foto eliminada con éxito");
        a.show();
    }  

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaInicial");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaInicialController pic = loader.getController();
            pic.recibirUsuario(this.usuario);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("¡Organiza tus fotos!");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            sg.getIcons().add(imagen);
            sg.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible regresar a la ventana principal");
            a.show();
        }
    }

    @FXML
    private void anteriorFoto(MouseEvent event) {
        if(imagenes.size() != 1){
        this.imagenes = new CircularDoubleLinkedList<>();
        ArrayList<Imagen> imagenes = Imagen.readFromFileArray("imagenes.txt");
        for(Imagen imagen: imagenes){
            if((Objects.equals(imagen.getNombreUsuario(),this.usuario.getNombreUsuario())) && (Objects.equals(imagen.getAlbum(),this.nombreUsuarioAlbum))){
                this.imagenes.addLast(imagen);
            }
        }
        if(indiceImagen == -1)
            indiceImagen = imagenes.size() - 1;
        ListIterator<Imagen> iterator = this.imagenes.listIterator();
        for(int i = 0; i < this.indiceImagen; i++){
            iterator.next();
        }
        this.img = iterator.previous();
        String rut = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + img.getNombreImagen();
        Path ruta = Paths.get(rut);
        Image imagen = new Image("file:" + ruta);
        imgview.setImage(imagen);
        this.indiceImagen--;
        }
    }

    @FXML
    private void siguienteFoto(MouseEvent event) {
        if(imagenes.size() != 1){
        this.imagenes = new CircularDoubleLinkedList<>();
        ArrayList<Imagen> imagenes = Imagen.readFromFileArray("imagenes.txt");
        for(Imagen imagen: imagenes){
            if((Objects.equals(imagen.getNombreUsuario(),this.usuario.getNombreUsuario())) && (Objects.equals(imagen.getAlbum(),this.nombreUsuarioAlbum))){
                this.imagenes.addLast(imagen);
            }
        }
        if(indiceImagen == imagenes.size())
            indiceImagen = 0;
        ListIterator<Imagen> iterator = this.imagenes.listIterator();
        for(int i = 0; i < this.indiceImagen; i++){
            iterator.next();
        }
        this.img = iterator.next();
        String rut = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + img.getNombreImagen();
        Path ruta = Paths.get(rut);
        Image imagen = new Image("file:" + ruta);
        imgview.setImage(imagen);
        this.indiceImagen++;
        }
    }

    @FXML
    private void accederDetalles(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetalles");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesController pdc = loader.getController();
            pdc.recibirUsuario(this.usuario);
            if(indiceImagen == imagenes.size())
                indiceImagen = 0;
            if(indiceImagen == -1)
                indiceImagen = imagenes.size() - 1;
            pdc.recibirImagen(img,indiceImagen);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("¡Organiza tus fotos!");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            sg.getIcons().add(imagen);
            sg.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible regresar a la ventana principal");
            a.show();
        }
    }
   
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void recibirNombreAlbum(String nombreAlbum){
        this.nombreAlbum.setText(nombreAlbum);
        this.nombreUsuarioAlbum = nombreAlbum;
    }
    
    public void recibirIndiceImagen(int indice){
        this.indiceImagen = indice;
    }
    
    @FXML
    private void visualizarFotos(MouseEvent event) {
        ArrayList<Imagen> imagenesUsuario = new ArrayList<>(1);
        this.imagenes = new CircularDoubleLinkedList<>();
        ArrayList<Imagen> imagenes = Imagen.readFromFileArray("imagenes.txt");
        for(Imagen imagen: imagenes){
            if((Objects.equals(imagen.getNombreUsuario(),this.usuario.getNombreUsuario())) && (Objects.equals(imagen.getAlbum(),this.nombreUsuarioAlbum))){
                imagenesUsuario.addLast(imagen);
                this.imagenes.addLast(imagen);
            }
        }
        if(imagenesUsuario.size() != 0)      
            this.img = imagenesUsuario.get(indiceImagen);
        else{
            this.img = null;
        }                                     
        if(!(Objects.equals(img, null))){
            String rut = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + img.getNombreImagen();
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            imgview.setImage(imagen);
            btnAnteriorFoto.setDisable(false);
            btnSiguienteFoto.setDisable(false);
            btnEliminar.setDisable(false);
            btnDetalles.setDisable(false);
            btnVisualizar.setDisable(true);
        }
    }

}
