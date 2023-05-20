/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.NumeroIncorrectoException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.PersonasExistentesException;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import ec.edu.espol.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *  @author Kevin C, Kenny J., Richard P.
 */
public class PantallaBuscarFotosHashtagController implements Initializable {

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnReiniciar;
    @FXML
    private Button btnRegresar;
    @FXML
    private Text lblPersona;
    @FXML
    private TextField infoCantidadPersona;
    @FXML
    private TextField infoPersona;
    @FXML
    private Button btnCantidadPersona;
    @FXML
    private Button btnAnadir;
    private Usuario usuario;
    private CircularDoubleLinkedList<Imagen> imagenes;
    private ArrayList<String> hashtags;
    private int cantidad;
    private int cantidad2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenes = new CircularDoubleLinkedList<Imagen>();
        infoPersona.setDisable(true);
        btnAnadir.setDisable(true);
        btnBuscar.setDisable(true);
    }    

    @FXML
    private void BuscarFotos(MouseEvent event) {
        try{
            ArrayList<Imagen> imagenesTotal = Imagen.readFromFileArray("imagenes.txt");
            ArrayList<Imagen> imagenesUsuario = new ArrayList<Imagen>(1);
            for(Imagen imagen: imagenesTotal){
                if(Objects.equals(imagen.getNombreUsuario(),usuario.getNombreUsuario()))
                    imagenesUsuario.addLast(imagen);
            }
            ArrayList<String> nombreHashtags = new ArrayList<String>(1);
            ArrayList<Imagen> i = new ArrayList<Imagen>(1);
            for(Imagen imagen: imagenesUsuario){
                if(!(Objects.equals(imagen.getHashtags(),null))){
                    if(imagen.getHashtags().size() >= hashtags.size()){
                        for(String hashtag: imagen.getHashtags()){
                            nombreHashtags.addLast(hashtag);
                        }
                        for(String hash: hashtags){
                            if(nombreHashtags.contains(hash)){
                                i.addLast(imagen);
                            }
                        }
                    }
                }
            }
            for(Imagen imagen: i){
                if(!(imagenes.contains(imagen)))
                            imagenes.addLast(imagen);
            }
            if(imagenes.size() == 0)
                throw new PersonasExistentesException("No hay fotos relacionadas con algún hashtag ingresado");
            Stage stg = (Stage) btnBuscar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaResultadosFotos");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaResultadosFotosController pic = loader.getController();
            pic.recibirDatos(usuario, imagenes);
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
        catch(PersonasExistentesException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void reiniciarInfo(MouseEvent event) {
        this.hashtags = null;
        infoCantidadPersona.setDisable(false);
        btnCantidadPersona.setDisable(false);
        lblPersona.setText("");
        infoPersona.setText("");
        infoPersona.setDisable(true);
        btnAnadir.setDisable(true);
        btnBuscar.setDisable(true);
    }

    @FXML
    private void regresarAnterior(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaBuscarFotos");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaBuscarFotosController pic = loader.getController();
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible");
            a.show();
        }
    }

    @FXML
    private void verificarCantidad(MouseEvent event) {
        try{
            if(Objects.equals(infoCantidadPersona.getText(),""))
                throw new PanelVacioException("Ingrese el valor por favor");
            int cantidad = Integer.parseInt(infoCantidadPersona.getText());
            if(cantidad <= 0 ){
                throw new NumeroIncorrectoException("Ingresar cantidad correcta");
            }
            else{
                this.cantidad = cantidad;
                this.hashtags = new ArrayList<String>(1);
                infoCantidadPersona.setDisable(true);
                btnCantidadPersona.setDisable(true);
                infoPersona.setDisable(false);
                btnAnadir.setDisable(false);
                if(cantidad == 1)
                    lblPersona.setText("Hashtag");
                else{
                    cantidad2 = 1;
                    lblPersona.setText("Hashtag #1");
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
    private void anadirHashtags(MouseEvent event) {
        try{
            if(Objects.equals(infoPersona.getText(), ""))
                throw new PanelVacioException("Ingrese el hashtag por favor");
            if(cantidad == 1){
                hashtags.addLast(infoPersona.getText());
                infoPersona.setDisable(true);
                btnAnadir.setDisable(true);
                lblPersona.setText("Click BUSCAR");
                btnBuscar.setDisable(false);
            }
            else{
                hashtags.addLast(infoPersona.getText());
                infoPersona.setText("");
                cantidad2++;
                cantidad--;
                lblPersona.setText("Hashtag #" + cantidad2);
            }
        }
        catch(PanelVacioException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
