/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.NombreUsuarioException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
public class PantallaRegistrosController implements Initializable {

    @FXML
    private TextField infoNombre;
    @FXML
    private TextField infoApellido;
    @FXML
    private TextField infoUsuario;
    @FXML
    private TextField infoContra;
    @FXML
    private Button btnRegistrar;
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
    private void registrarUsuario(MouseEvent event) {
        try{
            if(Objects.equals(infoNombre.getText(),"") || Objects.equals(infoApellido.getText(),"") ||
                    Objects.equals(infoUsuario.getText(),"") || Objects.equals(infoContra.getText(),""))
                throw new PanelVacioException("Llenar todos los datos por favor");
            if(Usuario.verificarNombreUsuario(infoUsuario.getText()) != null)
                throw new NombreUsuarioException("Nombre de usuario ya existente. Escoger otro por favor");
            Usuario.crearUsuario(infoNombre.getText(),infoApellido.getText(),infoUsuario.getText(),infoContra.getText());  
        }
        catch(PanelVacioException | NombreUsuarioException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void regresarInicioSesion(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegistrar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaIniciarSesion");
            Scene sc = new Scene(loader.load(), 640, 480);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible regresar");
            a.show();
        }
    }
    
}
