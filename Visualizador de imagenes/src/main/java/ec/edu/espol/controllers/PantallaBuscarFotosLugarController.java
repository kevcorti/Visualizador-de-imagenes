/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Album;
import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.LugarExistenteException;
import ec.edu.espol.model.PanelVacioException;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaBuscarFotosLugarController implements Initializable {

    @FXML
    private Button infoBuscar;
    @FXML
    private Button infoRegresar;
    @FXML
    private TextField infoLugar;
    private Usuario usuario;
    private CircularDoubleLinkedList<Imagen> imagenes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenes = new CircularDoubleLinkedList<Imagen>();
    }    

    @FXML
    private void buscarFotos(MouseEvent event) {
        try{
            if(Objects.equals(infoLugar.getText(),""))
                throw new PanelVacioException("Llenar los datos por favor");
            if(!(Imagen.verificarLugar(infoLugar.getText().toUpperCase(), usuario.getNombreUsuario())))
                throw new LugarExistenteException("No existen fotos relacionadas a ese lugar"); 
            ArrayList<Imagen> imagenesTotal = Imagen.readFromFileArray("imagenes.txt");
            for(Imagen imagen: imagenesTotal){
                if(Objects.equals(imagen.getNombreUsuario(), usuario.getNombreUsuario()) && Objects.equals(imagen.getLugar(), infoLugar.getText().toUpperCase()))
                    imagenes.addLast(imagen);
            }
            Stage stg = (Stage) infoBuscar.getScene().getWindow();
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
        catch(PanelVacioException | LugarExistenteException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible");
            a.show();
        }
    }

    @FXML
    private void regresarAnterior(MouseEvent event) {
        try{
            Stage stg = (Stage)infoRegresar.getScene().getWindow();
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
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
