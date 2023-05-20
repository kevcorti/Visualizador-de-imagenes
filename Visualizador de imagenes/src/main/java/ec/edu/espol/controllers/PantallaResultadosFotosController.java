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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaResultadosFotosController implements Initializable {

    @FXML
    private Button btnVisualizar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private ImageView imgview;
    @FXML
    private Button btnDetalles;
    private Usuario usuario;
    private Imagen img;
    private CircularDoubleLinkedList<Imagen> imagenes;
    private int indiceImagen;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnDetalles.setDisable(true);
        indiceImagen = 0;
    }    
    
    public void recibirDatos(Usuario usuario, CircularDoubleLinkedList<Imagen> imagenes){
        this.usuario = usuario;
        this.imagenes = imagenes;
    }
    
    @FXML
    private void visualizarFotos(MouseEvent event) {
        img = imagenes.get(indiceImagen);
        String rut = System.getProperty("user.dir") + "/src/main/resources/imagenes/" + img.getNombreImagen();
        Path ruta = Paths.get(rut);
        Image imagen = new Image("file:" + ruta);
        imgview.setImage(imagen);
        if(imagenes.size() != 1){
            btnAnterior.setDisable(false);
            btnSiguiente.setDisable(false);
        }
        btnDetalles.setDisable(false);
        btnVisualizar.setDisable(true);   
    }

    @FXML
    private void salirPrincipal(MouseEvent event) {
        try{
            Stage stg = (Stage)btnSalir.getScene().getWindow();
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

    @FXML
    private void siguienteFoto(MouseEvent event) {
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


    @FXML
    private void verDetalles(MouseEvent event) {
        try{
            Stage stg = (Stage)btnDetalles.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetallesResultados");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesResultadosController pdc = loader.getController();
            pdc.recibirUsuario(this.usuario);
            if(indiceImagen == imagenes.size())
                indiceImagen = 0;
            if(indiceImagen == -1)
                indiceImagen = imagenes.size() - 1;
            pdc.recibirImagen(img,indiceImagen);
            pdc.recibirListaImagenes(imagenes);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible");
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void recibirIndiceImagen(int indice){
        this.indiceImagen = indice;
    }
}
