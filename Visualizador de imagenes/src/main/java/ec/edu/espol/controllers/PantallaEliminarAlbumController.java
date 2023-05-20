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
public class PantallaEliminarAlbumController implements Initializable {

    @FXML
    private Button btnEliminarAlbum;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField infoAlbum;
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void eliminarAlbum(MouseEvent event) {
        try{
            if(Objects.equals(infoAlbum.getText(),""))
                throw new PanelVacioException("Ingresar el nombre del álbum por favor");
            if(!(Album.verificarNombreAlbum(infoAlbum.getText().toUpperCase(),usuario.getNombreUsuario())))
                throw new AlbumExistenteException("Álbum no existente, ingresar nombre correcto por favor");
            modificarArchivo(infoAlbum.getText().toUpperCase());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Álbum eliminado");
            a.show();
        }
        catch(PanelVacioException | AlbumExistenteException ex){
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
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void modificarArchivo(String nombreAlbum){
        ArrayList<Album> albumes = Album.readFromFile("albumes.txt");
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("albumes.txt"))){
            for(Album album: albumes){
                if(!(Objects.equals(album.getNombre(),nombreAlbum))){
                    bf.write(album.getNombreUsuario() + "|" + album.getNombre() + "|" + album.getDesc() + "\n");
                }
            }
        }
        catch(IOException ex){
            Alert a = new Alert(AlertType.ERROR, "Error modificando archivo");
            a.show();
        }    
    }
}