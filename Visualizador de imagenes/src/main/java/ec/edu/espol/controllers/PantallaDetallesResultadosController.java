/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import ec.edu.espol.util.CircularDoubleLinkedList;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class PantallaDetallesResultadosController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private Label lblLugar;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblMarCam;
    @FXML
    private Label lblModCam;
    @FXML
    private Label lblDesc;
    @FXML
    private Label lblPersonas;
    @FXML
    private Label lblHashtags;
    private Usuario usuario;
    private int indice;
    private Imagen imagen;
    private CircularDoubleLinkedList<Imagen> imagenes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regresarAnterior(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaResultadosFotos");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaResultadosFotosController pic = loader.getController();
            pic.recibirDatos(usuario, imagenes);
            pic.recibirIndiceImagen(indice);
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
        this.lblLugar.setText(imagen.getLugar());
        this.lblFecha.setText(imagen.getFecha().toString());
        this.lblMarCam.setText(imagen.getMarcaCam());
        this.lblModCam.setText(imagen.getModeloCam());
        this.lblDesc.setText(imagen.getDescripcion());
        if(!Objects.equals(imagen.getPersonas(), null))
            this.lblPersonas.setText(imagen.getPersonas().toString());
        else{  
            this.lblPersonas.setText("No hay personas en la foto");
        }
        if(!Objects.equals(imagen.getHashtags(), null))
            this.lblHashtags.setText(imagen.getHashtags().toString());
        else{
            this.lblHashtags.setText("No hay hashtags asociados a la foto");
        }
    }
    
    public void recibirListaImagenes(CircularDoubleLinkedList<Imagen> imagenes){
        this.imagenes = imagenes;
    }
}
