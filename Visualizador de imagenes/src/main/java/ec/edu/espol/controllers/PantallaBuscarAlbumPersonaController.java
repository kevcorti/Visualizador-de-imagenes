/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Album;
import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.NumeroIncorrectoException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.PersonasExistentesException;
import ec.edu.espol.model.Usuario;
import ec.edu.espol.proyectoed_grupo5.App;
import ec.edu.espol.util.ArrayList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaBuscarAlbumPersonaController implements Initializable {

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnRegresar;
    @FXML
    private Text lblCantidadPersona;
    @FXML
    private TextField infoCantidadPersona;
    @FXML
    private TextField infoPersona;
    @FXML
    private Button btnCantidadPersona;
    @FXML
    private Button btnAnadir;
    private Usuario usuario;
    private ArrayList<Persona> personas;
    private int cantidad;
    private int cantidad2;
    @FXML
    private Button btnReiniciar;
    private ArrayList<Album> albumesResultados;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        infoPersona.setDisable(true);
        btnAnadir.setDisable(true);
        btnBuscar.setDisable(true);
        albumesResultados = new ArrayList<Album>(1);
    }    

    @FXML
    private void buscarAlbumes(MouseEvent event) {
        try{
            ArrayList<Album> albumesTotal = Album.readFromFile("albumes.txt");
            ArrayList<Album> albumesUsuario = new ArrayList<Album>(1);
            for(Album album: albumesTotal){
                if(Objects.equals(album.getNombreUsuario(), usuario.getNombreUsuario()))
                    albumesUsuario.addLast(album);
            }
            ArrayList<Imagen> imagenesTotal = Imagen.readFromFileArray("imagenes.txt");
            ArrayList<Imagen> imagenesUsuario = new ArrayList<Imagen>(1);
            for(Imagen imagen: imagenesTotal){
                if(Objects.equals(imagen.getNombreUsuario(),usuario.getNombreUsuario()))
                    imagenesUsuario.addLast(imagen);
            }
            ArrayList<String> nombreAlbumes = new ArrayList<String>(1);
            ArrayList<String> nombrePersona = new ArrayList<String>(1);
            for(Imagen imagen: imagenesUsuario){
                if(!(Objects.equals(imagen.getPersonas(),null))){
                    if(imagen.getPersonas().size() >= personas.size()){
                        for(Persona persona: imagen.getPersonas()){
                            nombrePersona.addLast(persona.getNombre());
                        }
                        for(Persona persona: personas){
                            if(nombrePersona.contains(persona.getNombre())){
                                nombreAlbumes.addLast(imagen.getAlbum());
                            }
                        }
                    }
                }
            }
            for(Album album: albumesUsuario){
                for(String nombreAlbum: nombreAlbumes){
                    if(Objects.equals(album.getNombre(), nombreAlbum)){
                        if(!(albumesResultados.contains(album)))
                            albumesResultados.addLast(album);
                    }
                }
            }
            if(albumesResultados.size() == 0)
                throw new PersonasExistentesException("No hay fotos en las que aparezcan todas las personas");
            Stage stg = (Stage) btnBuscar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaResultadosAlbumes");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaResultadosAlbumesController pic = loader.getController();
            pic.recibirUsuario(usuario);
            pic.recibirResultados(this.albumesResultados);
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
            Alert a = new Alert(AlertType.ERROR, "No es posible");
            a.show();
        }
        catch(PersonasExistentesException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void regresarAnterior(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaBuscarAlbumes");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaBuscarAlbumesController pic = loader.getController();
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
                this.personas = new ArrayList<Persona>(1);
                infoCantidadPersona.setDisable(true);
                btnCantidadPersona.setDisable(true);
                infoPersona.setDisable(false);
                btnAnadir.setDisable(false);
                if(cantidad == 1)
                    lblCantidadPersona.setText("Nombre");
                else{
                    cantidad2 = 1;
                    lblCantidadPersona.setText("Nombre #1");
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
    private void anadirPersonas(MouseEvent event) {
        try{
            if(Objects.equals(infoPersona.getText(), ""))
                throw new PanelVacioException("Ingrese el nombre por favor");
            if(cantidad == 1){
                personas.addLast(new Persona(infoPersona.getText()));
                infoPersona.setDisable(true);
                btnAnadir.setDisable(true);
                lblCantidadPersona.setText("Click BUSCAR");
                btnBuscar.setDisable(false);
            }
            else{
                personas.addLast(new Persona(infoPersona.getText()));
                infoPersona.setText("");
                cantidad2++;
                cantidad--;
                lblCantidadPersona.setText("Nombre #" + cantidad2);
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

    @FXML
    private void reiniciarDatos(MouseEvent event) {
        this.personas = null;
        infoCantidadPersona.setDisable(false);
        btnCantidadPersona.setDisable(false);
        lblCantidadPersona.setText("");
        infoPersona.setText("");
        infoPersona.setDisable(true);
        btnAnadir.setDisable(true);
        btnBuscar.setDisable(true);
    }
}
