/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaDetallesController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private Label infoLugar;
    @FXML
    private Label infoFecha;
    @FXML
    private Label infoMarcaCamara;
    @FXML
    private Label infoModeloCamara;
    @FXML
    private Label infoDesc;
    @FXML
    private Label infoPersonas;
    @FXML
    private Label infoHashtags;
    private Usuario usuario;
    private Imagen imagen;
    @FXML
    private Button btnEliminarPersonas;
    @FXML
    private Button btnAnadirPersonas;
    @FXML
    private Button btnModificarPersonas;
    private int indice;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regresarFotos(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaFotos");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaFotosController pfc = loader.getController();
            pfc.recibirUsuario(this.usuario);
            pfc.recibirIndiceImagen(indice);
            pfc.recibirNombreAlbum(imagen.getAlbum());
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
            
    public void recibirImagen(Imagen imagen, int indice){
        this.indice = indice;
        this.imagen = imagen;
        this.infoLugar.setText(imagen.getLugar());
        this.infoFecha.setText(imagen.getFecha().toString());
        this.infoMarcaCamara.setText(imagen.getMarcaCam());
        this.infoModeloCamara.setText(imagen.getModeloCam());
        this.infoDesc.setText(imagen.getDescripcion());
        if(!Objects.equals(imagen.getPersonas(), null))
            this.infoPersonas.setText(imagen.getPersonas().toString());
        else{  
            this.infoPersonas.setText("No hay personas en la foto");
            btnEliminarPersonas.setDisable(true);
            btnModificarPersonas.setDisable(true);
        }
        if(!Objects.equals(imagen.getHashtags(), null))
            this.infoHashtags.setText(imagen.getHashtags().toString());
        else{
            this.infoHashtags.setText("No hay hashtags asociados a la foto");
        }
    }

    @FXML
    private void eliminarPersonas(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetallesEliminarPersonas");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesEliminarPersonasController pfc = loader.getController();
            pfc.recibirDatos(usuario, imagen, indice);
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

    @FXML
    private void anadirPersonas(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetallesAnadirPersonas");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesAnadirPersonasController pfc = loader.getController();
            pfc.recibirDatos(usuario, imagen, indice);
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

    @FXML
    private void modificarPersonas(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetallesModificarPersonas");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesModificarPersonasController pfc = loader.getController();
            pfc.recibirDatos(usuario, imagen, indice);
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
    
}
