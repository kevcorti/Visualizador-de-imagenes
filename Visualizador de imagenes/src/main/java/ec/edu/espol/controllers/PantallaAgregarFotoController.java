/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Album;
import ec.edu.espol.model.AlbumExistenteException;
import ec.edu.espol.model.FechaIncorrectaException;
import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.ImagenVaciaException;
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
import java.time.LocalDate;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin C, Kenny J., Richard P.
 */
public class PantallaAgregarFotoController implements Initializable {
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField infoAlbum;
    @FXML
    private TextField infoLugar;
    @FXML
    private TextField infoDia;
    @FXML
    private TextField infoMes;
    @FXML
    private TextField infoAn;
    @FXML
    private TextField infoDesc;
    @FXML
    private TextField infoMarcaCam;
    @FXML
    private TextField infoModeloCam;
    private Usuario usuario;
    @FXML
    private Button btnBuscarFoto;
    @FXML
    private Button btnAgregar;
    private File archivoImagen;
    @FXML
    private Button btnAnadirPersonas;
    @FXML
    private Button btnAnadirHashtags;
    private ArrayList<Persona> personas;
    private ArrayList<String> hashtags;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        personas = null;
        hashtags = null;
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


    @FXML
    private void buscarFoto(MouseEvent event) {
        FileChooser fc = new FileChooser();
            fc.setTitle("Buscar Imagen");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
            Stage stage = (Stage)btnBuscarFoto.getScene().getWindow();
            archivoImagen = fc.showOpenDialog(stage);         
    }

    @FXML
    private void agregarFoto(MouseEvent event) {
        try{
            if(Objects.equals(infoAlbum.getText(),"") || Objects.equals(infoLugar.getText(),"") || Objects.equals(infoDia.getText(),"") ||
                    Objects.equals(infoMes.getText(),"") || Objects.equals(infoAn.getText(),"") || Objects.equals(infoDesc.getText(),"") ||
                    Objects.equals(infoMarcaCam.getText(),"") || Objects.equals(infoModeloCam.getText(),""))
                throw new PanelVacioException("Llenar todos los datos por favor");
            if(archivoImagen == null)
                throw new ImagenVaciaException("Favor escoger el archivo de la foto a agregar");
            if(!(Album.verificarNombreAlbum(infoAlbum.getText().toUpperCase(),usuario.getNombreUsuario())))
                throw new AlbumExistenteException("Álbum no existente, ingrese un nombre correcto");
            int dia = Integer.parseInt(infoDia.getText());
            int mes = Integer.parseInt(infoMes.getText());
            int an = Integer.parseInt(infoAn.getText());
            if((dia<=0) || (dia>31))
                throw new FechaIncorrectaException("Fecha incorrecta ingresada. Verificar");
            if((mes<=0) || (mes>12))
                throw new FechaIncorrectaException("Fecha incorrecta ingresada. Verificar");
            if(((dia >= 30)&&((mes == 2)||(mes == 02))))
                throw new FechaIncorrectaException("Fecha incorrecta ingresada. Verificar");
            if(an > 2022)
                throw new FechaIncorrectaException("Fecha incorrecta ingresada. Verificar");
            ArrayList<Imagen> imagenes = Imagen.readFromFileArray("imagenes.txt");
            int n = 0;
            for(Imagen imagen: imagenes){
                if(Objects.equals(imagen.getNombreUsuario(),usuario.getNombreUsuario()) && Objects.equals(imagen.getAlbum(),infoAlbum.getText().toUpperCase()))
                    n++;
            }
            Imagen img = new Imagen(infoAlbum.getText(),"foto",infoLugar.getText(),LocalDate.of(an,mes,dia),infoDesc.getText(),infoMarcaCam.getText(),
                                        infoModeloCam.getText(),personas,hashtags,"SIN REACCIÓN",usuario.getNombreUsuario());
            Imagen.guardarImagen(this.archivoImagen, img, n, usuario.getNombreUsuario());
            Alert a = new Alert(AlertType.CONFIRMATION,"Foto agregada con éxito");
            a.show();
        }
        catch(PanelVacioException | ImagenVaciaException | AlbumExistenteException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(NumberFormatException ex){
            Alert a = new Alert(AlertType.ERROR, "Ingresar números correctos por favor");
            a.show();
        }
        catch(FechaIncorrectaException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void anadirPersonas(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaAnadirPersonas");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaAnadirPersonasController pafc = loader.getController();
            pafc.recibirUsuario(usuario);
            pafc.recibirDatos(infoAlbum.getText(), infoLugar.getText(), infoDia.getText(), infoMes.getText(), infoAn.getText(),
                    infoDesc.getText(), infoModeloCam.getText(), infoMarcaCam.getText(), hashtags, archivoImagen);
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
    private void anadirHashtags(MouseEvent event) {
        try{
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaAnadirHashtags");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaAnadirHashtagsController pafc = loader.getController();
            pafc.recibirUsuario(usuario);
            pafc.recibirDatos(infoAlbum.getText(), infoLugar.getText(), infoDia.getText(), infoMes.getText(), infoAn.getText(),
                    infoDesc.getText(), infoModeloCam.getText(), infoMarcaCam.getText(), personas, archivoImagen);
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
    
    public void regresarDesdePersonas(String album, String lugar, String dia, String mes, String an, String desc, String modCam, String marCam, ArrayList<Persona> personas, ArrayList<String> hashtags, File archivoImagen){
        infoAlbum.setText(album);
        infoLugar.setText(lugar);
        infoDia.setText(dia);
        infoMes.setText(mes);
        infoAn.setText(an);
        infoDesc.setText(desc);
        infoModeloCam.setText(modCam);
        infoMarcaCam.setText(marCam);
        this.personas = personas;
        this.hashtags = hashtags;
        this.archivoImagen = archivoImagen;
    }
    
    public void recibirDatosPersonas(String album, String lugar, String dia, String mes, String an, String desc, String modCam, String marCam, ArrayList<Persona> personas, ArrayList<String> hashtags, File archivoImagen){
        infoAlbum.setText(album);
        infoLugar.setText(lugar);
        infoDia.setText(dia);
        infoMes.setText(mes);
        infoAn.setText(an);
        infoDesc.setText(desc);
        infoModeloCam.setText(modCam);
        infoMarcaCam.setText(marCam);
        this.personas = personas;
        this.hashtags = hashtags;
        this.archivoImagen = archivoImagen;
        btnAnadirPersonas.setDisable(true);
        if(!(Objects.equals(this.hashtags,null)))
            btnAnadirHashtags.setDisable(true);
    }
    
    public void recibirDatosHashtags(String album, String lugar, String dia, String mes, String an, String desc, String modCam, String marCam, ArrayList<Persona> personas, ArrayList<String> hashtags, File archivoImagen){
        infoAlbum.setText(album);
        infoLugar.setText(lugar);
        infoDia.setText(dia);
        infoMes.setText(mes);
        infoAn.setText(an);
        infoDesc.setText(desc);
        infoModeloCam.setText(modCam);
        infoMarcaCam.setText(marCam);
        this.personas = personas;
        this.hashtags = hashtags;
        this.archivoImagen = archivoImagen;
        btnAnadirHashtags.setDisable(true);
        if(!(Objects.equals(this.personas,null)))
            btnAnadirPersonas.setDisable(true);
    }
    
    public void regresarDesdeHashtags(String album, String lugar, String dia, String mes, String an, String desc, String modCam, String marCam, ArrayList<Persona> personas, ArrayList<String> hashtags, File archivoImagen){
        infoAlbum.setText(album);
        infoLugar.setText(lugar);
        infoDia.setText(dia);
        infoMes.setText(mes);
        infoAn.setText(an);
        infoDesc.setText(desc);
        infoModeloCam.setText(modCam);
        infoMarcaCam.setText(marCam);
        this.personas = personas;
        this.hashtags = hashtags;
        this.archivoImagen = archivoImagen;
    }
}
