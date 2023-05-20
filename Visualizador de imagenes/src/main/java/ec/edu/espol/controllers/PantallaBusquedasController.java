/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaBusquedasController implements Initializable {

    @FXML
    private Button btnBuscarAlbumes;
    @FXML
    private Button btnBuscarFotos;
    private Usuario usuario;
    @FXML
    private Button btnRegresar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarAlbumes(MouseEvent event) {
        try{
            Stage stg = (Stage)btnBuscarAlbumes.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaBuscarAlbumes");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaBuscarAlbumesController pbac = loader.getController();
            pbac.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible buscar albumes");
            a.show();
        }
    }

    @FXML
    private void buscarFotos(MouseEvent event) {
        try{
            Stage stg = (Stage)btnBuscarFotos.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaBuscarFotos");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaBuscarFotosController pbfc = loader.getController();
            pbfc.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible buscar fotos");
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
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
}
