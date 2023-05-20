/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Album;
import ec.edu.espol.model.AlbumExistenteException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaCrearAlbumController implements Initializable {

    @FXML
    private Button btnCrearAlbum;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField infoNombre;
    @FXML
    private TextField infoDesc;
    @FXML
    private Button btnLimpiar;
    private Usuario usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearAlbum(MouseEvent event){
        try{
            if(Objects.equals(infoNombre.getText(),"") || Objects.equals(infoDesc.getText(),""))
                throw new PanelVacioException("Ingrese todos los datos por favor");
            if(Album.verificarNombreAlbum(infoNombre.getText().toUpperCase(),usuario.getNombreUsuario()))
                throw new AlbumExistenteException("Nombre de album ya existente, ingrese uno nuevo");
            Album.crearAlbum(infoNombre.getText(), infoDesc.getText(), usuario.getNombreUsuario());
        }
        catch(PanelVacioException  | AlbumExistenteException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
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
    private void limpiarDatos(MouseEvent event) {
        infoNombre.setText("");
        infoDesc.setText("");
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
