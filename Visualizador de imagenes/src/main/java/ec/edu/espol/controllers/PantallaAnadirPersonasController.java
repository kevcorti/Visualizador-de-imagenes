/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.NumeroIncorrectoException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import ec.edu.espol.util.ArrayList;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaAnadirPersonasController implements Initializable {

    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnReiniciar;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField infoCantidad;
    @FXML
    private Button btnCantidad;
    @FXML
    private Text lblPersonas;
    @FXML
    private TextField infoPersonas;
    @FXML
    private Button btnAnadir;
    private String album;
    private String lugar;
    private String dia;
    private String mes;
    private String an;
    private String desc;
    private String modCam;
    private String marCam;
    private ArrayList<String> hashtags;
    private File file;
    private ArrayList<Persona> personas;
    private int cantidad;
    private int cantidad2;
    private Usuario usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnContinuar.setDisable(true);
        infoPersonas.setDisable(true);
        btnAnadir.setDisable(true);
    }    

    @FXML
    private void enviarInfo(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaAgregarFoto");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaAgregarFotoController pafc = loader.getController();
            pafc.recibirUsuario(this.usuario);
            pafc.recibirDatosPersonas(album, lugar, dia, mes, an, desc, modCam, marCam, personas, hashtags, file);
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
    private void reiniciarInfo(MouseEvent event) {
        this.personas = null;
        infoCantidad.setDisable(false);
        btnCantidad.setDisable(false);
        lblPersonas.setText("");
        infoPersonas.setText("");
        infoPersonas.setDisable(true);
        btnAnadir.setDisable(true);
        btnContinuar.setDisable(true);
    }
    
    @FXML
    private void regresarAnterior(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaAgregarFoto");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaAgregarFotoController pafc = loader.getController();
            pafc.recibirUsuario(this.usuario);
            pafc.regresarDesdePersonas(album, lugar, dia, mes, an, desc, modCam, marCam, null, hashtags, file);
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
    private void verificarCantidad(MouseEvent event) {
        try{
            if(Objects.equals(infoCantidad.getText(),""))
                throw new PanelVacioException("Ingrese el valor por favor");
            int cantidad = Integer.parseInt(infoCantidad.getText());
            if(cantidad <= 0 ){
                throw new NumeroIncorrectoException("Ingresar cantidad correcta");
            }
            else{
                this.cantidad = cantidad;
                this.personas = new ArrayList<Persona>(1);
                infoCantidad.setDisable(true);
                btnCantidad.setDisable(true);
                infoPersonas.setDisable(false);
                btnAnadir.setDisable(false);
                if(cantidad == 1)
                    lblPersonas.setText("Nombre");
                else{
                    cantidad2 = 1;
                    lblPersonas.setText("Nombre #1");
                }
            }
        }
        catch(PanelVacioException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(NumberFormatException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "Ingresar cantidad correcta");
            a.show();
        }
        catch(NumeroIncorrectoException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void anadirPersona(MouseEvent event) {
        try{
            if(Objects.equals(infoPersonas.getText(), ""))
                throw new PanelVacioException("Ingrese el nombre por favor");
            if(cantidad == 1){
                personas.addLast(new Persona(infoPersonas.getText()));
                infoPersonas.setDisable(true);
                btnAnadir.setDisable(true);
                lblPersonas.setText("Click CONTINUAR");
                btnContinuar.setDisable(false);
            }
            else{
                personas.addLast(new Persona(infoPersonas.getText()));
                infoPersonas.setText("");
                cantidad2++;
                cantidad--;
                lblPersonas.setText("Nombre #" + cantidad2);
            }
        }
        catch(PanelVacioException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }
    
    public void recibirDatos(String album, String lugar, String dia, String mes, String an, String desc, String modCam, String marCam, ArrayList<String> hashtags, File archivoImagen){
        this.album = album;
        this.lugar = lugar;
        this.dia = dia;
        this.mes = mes;
        this.an = an;
        this.desc = desc;
        this.modCam = modCam;
        this.marCam = marCam;
        this.hashtags = hashtags;
        this.file = archivoImagen;
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
