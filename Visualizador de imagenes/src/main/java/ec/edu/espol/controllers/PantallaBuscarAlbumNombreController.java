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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaBuscarAlbumNombreController implements Initializable {

    @FXML
    private Button btnBuscar;
    @FXML
    private TextField infoNombreAlbum;
    private Usuario usuario;
    @FXML
    private Button btnRegresar;
    private ArrayList<Album> albumes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        albumes = new ArrayList<Album>(1);
    }    

    @FXML
    private void buscarAlbum(MouseEvent event) {
        try{
            if(Objects.equals(infoNombreAlbum.getText(),""))
                throw new PanelVacioException("Llenar los datos por favor");
            if(!(Album.verificarNombreAlbum(infoNombreAlbum.getText().toUpperCase(), usuario.getNombreUsuario())))
                throw new AlbumExistenteException("No existe álbum con ese nombre");
            ArrayList<Album> al = Album.readFromFile("albumes.txt");
            for(Album album: al){
                if(Objects.equals(album.getNombreUsuario(), usuario.getNombreUsuario()) && Objects.equals(album.getNombre(),infoNombreAlbum.getText().toUpperCase()))
                    albumes.addLast(album);
            }
            Stage stg = (Stage) btnBuscar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaResultadosAlbumes");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaResultadosAlbumesController pic = loader.getController();
            pic.recibirUsuario(usuario);
            pic.recibirResultados(this.albumes);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("¡Organiza tus fotos!");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            sg.getIcons().add(imagen);
            sg.show();
        }
        catch(PanelVacioException | AlbumExistenteException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(IOException ex){
            Alert a = new Alert(AlertType.ERROR, "No es posible");
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
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
}
