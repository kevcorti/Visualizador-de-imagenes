/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Album;
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaInicialController implements Initializable {

    @FXML
    private Button btnNuevoAlbum;
    @FXML
    private Button btnEliminarAlbum;
    @FXML
    private Button btnAgregarFoto;
    @FXML
    private Button btnBuscar;
    @FXML
    private HBox hboxAlbumes;
    @FXML
    private Text lblNombres;
    private Usuario usuario;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnVisualizar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void crearAlbum(MouseEvent event) {
        try{
            Stage stg = (Stage)btnNuevoAlbum.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaCrearAlbum");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaCrearAlbumController pcac = loader.getController();
            pcac.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible crear album");
            a.show();
        }
    }

    @FXML
    private void eliminarAlbum(MouseEvent event) {
        try{
            Stage stg = (Stage)btnEliminarAlbum.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaEliminarAlbum");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaEliminarAlbumController peac = loader.getController();
            peac.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible eliminar álbum");
            a.show();
        }
    }

    @FXML
    private void agregarFoto(MouseEvent event) {
        try{
            Stage stg = (Stage)btnAgregarFoto.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaAgregarFoto");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaAgregarFotoController pac = loader.getController();
            pac.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible registrar nueva foto");
            a.show();
        }
    }

    @FXML
    private void buscar(MouseEvent event) {
        try{
            Stage stg = (Stage)btnCerrar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaBusquedas");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaBusquedasController pbc = loader.getController();
            pbc.recibirUsuario(this.usuario);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible buscar");
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        lblNombres.setText("Álbumes de " + usuario.getNombre());
        this.usuario = usuario;
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        try{
            Stage stg = (Stage)btnCerrar.getScene().getWindow();
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible cerrar sesión");
            a.show();
        }
    }

    @FXML
    private void visualizarAlbumes(MouseEvent event) {
        hboxAlbumes.getChildren().clear();
        ArrayList<Album> albumes = Album.readFromFile("albumes.txt");
        for(Album album: albumes){
            if(Objects.equals(album.getNombreUsuario(),this.usuario.getNombreUsuario())){
                VBox vboxAlbumes = new VBox();
                Label lbl = new Label();
                Image img = new Image("img/carpeta.png");
                ImageView imgview = new ImageView(img);
                imgview.setFitHeight(280);
                imgview.setFitWidth(200);
                imgview.setOnMouseClicked((MouseEvent e) -> {
                    try{
                        Stage stg = (Stage)btnNuevoAlbum.getScene().getWindow();
                        stg.close();
                        FXMLLoader loader = App.loadFXML("pantallaFotos");
                        Scene sc = new Scene(loader.load(), 640, 480);
                        PantallaFotosController pfc = loader.getController();
                        pfc.recibirUsuario(this.usuario);
                        pfc.recibirNombreAlbum(album.getNombre());
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
                    Alert a = new Alert(Alert.AlertType.ERROR, "No es posible acceder al álbum");
                    a.show();
                    } 
                });
                lbl.setText(album.getNombre());
                vboxAlbumes.getChildren().add(lbl);
                vboxAlbumes.getChildren().add(imgview);
                vboxAlbumes.setSpacing(10);
                vboxAlbumes.setAlignment(Pos.CENTER);
                hboxAlbumes.getChildren().add(vboxAlbumes);
            }
        }
    }
}

