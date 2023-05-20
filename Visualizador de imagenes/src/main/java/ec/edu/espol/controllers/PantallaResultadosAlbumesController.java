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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaResultadosAlbumesController implements Initializable {

    @FXML
    private Button btnVisualizar;
    @FXML
    private Button btnSalir;
    @FXML
    private ScrollPane panelScroll;
    @FXML
    private HBox hboxAlbumes;
    private Usuario usuario;
    private ArrayList<Album> albumesResultados;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void visualizarAlbumes(MouseEvent event) {
        hboxAlbumes.getChildren().clear();
        for(Album album: albumesResultados){
            VBox vboxAlbumes = new VBox();
            Label lbl = new Label();
            Image img = new Image("img/carpeta.png");
            ImageView imgview = new ImageView(img);
            imgview.setFitHeight(280);
            imgview.setFitWidth(200);
            imgview.setOnMouseClicked((MouseEvent e) -> {
                    try{
                        Stage stg = (Stage)btnVisualizar.getScene().getWindow();
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

    @FXML
    private void salirPrincipal(MouseEvent event) {
        try{
            Stage stg = (Stage)btnSalir.getScene().getWindow();
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible");
            a.show();
        }
    }
    
    public void recibirUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void recibirResultados(ArrayList<Album> albumes){
        this.albumesResultados = albumes;
    }
}
