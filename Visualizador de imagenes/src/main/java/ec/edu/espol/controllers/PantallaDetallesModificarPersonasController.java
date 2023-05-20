/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Imagen;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.PersonaExistenteException;
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
public class PantallaDetallesModificarPersonasController implements Initializable {

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField infoPersonaAnterior;
    @FXML
    private TextField infoNuevaPersona;
    private Usuario usuario;
    private Imagen imagen;
    private int indice;
    private ArrayList<String> nombrePersonas;
    private ArrayList<Persona> nuevasPersonas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombrePersonas = new ArrayList<String>(1);
    }    

    @FXML
    private void modificarPersona(MouseEvent event) {
        try{
            if(Objects.equals(infoPersonaAnterior.getText(), "") || Objects.equals(infoNuevaPersona.getText(), ""))
                throw new PanelVacioException("Llenar los datos por favor");
            if(!(nombrePersonas.contains(infoPersonaAnterior.getText().toUpperCase())))
                throw new PersonaExistenteException("Esa persona no se encuentra etiquetada en esta imagen. No es posible modificarla");
            nuevasPersonas = new ArrayList<Persona>(1);
            for(String s: nombrePersonas){
                if(!(Objects.equals(s,infoPersonaAnterior.getText().toUpperCase())))
                    nuevasPersonas.addLast(new Persona(s));
                else{
                    nuevasPersonas.addLast(new Persona(infoNuevaPersona.getText()));
                }
            }
            imagen.setPersonas(nuevasPersonas);
            ArrayList<Imagen> imagenesTotal = Imagen.readFromFileArray("imagenes.txt");
            try(BufferedWriter bf = new BufferedWriter(new FileWriter("imagenes.txt"))){
                    for(Imagen i: imagenesTotal){
                        if(Objects.equals(imagen.getNombreImagen(), i.getNombreImagen())){
                            bf.write(imagen.getNombreUsuario() + "|" + imagen.getAlbum() + "|" + imagen.getNombreImagen() + "|" + imagen.getLugar() + "|" + imagen.getFecha() + "|" + imagen.getDescripcion() + "|" 
                            + imagen.getMarcaCam() + "|" + imagen.getModeloCam() + "|" + imagen.getPersonas() + "|" + imagen.getHashtags() + "|" + imagen.getReaccion() +"\n");
                        }
                        else{
                            bf.write(i.getNombreUsuario() + "|" + i.getAlbum() + "|" + i.getNombreImagen() + "|" + i.getLugar() + "|" + i.getFecha() + "|" + i.getDescripcion() + "|" 
                            + i.getMarcaCam() + "|" + i.getModeloCam() + "|" + i.getPersonas() + "|" + i.getHashtags() + "|" + i.getReaccion() +"\n");
                        }
                    }
            }     
            catch(IOException ex){
                Alert a = new Alert(Alert.AlertType.ERROR,"Error");
                a.show();
            }
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetalles");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesController pfc = loader.getController();
            pfc.recibirUsuario(this.usuario);
            pfc.recibirImagen(imagen, indice);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("¡Organiza tus fotos!");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            sg.getIcons().add(imagen);
            sg.show();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Persona modificada");
            a.show();
        }
        catch(PanelVacioException | PersonaExistenteException ex){
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
            Stage stg = (Stage)btnRegresar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaDetalles");
            Scene sc = new Scene(loader.load(), 640, 480);
            PantallaDetallesController pfc = loader.getController();
            pfc.recibirUsuario(this.usuario);
            pfc.recibirImagen(imagen, indice);
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
    
    public void recibirDatos(Usuario usuario, Imagen imagen, int indice){
        this.usuario = usuario;
        this.imagen = imagen;
        this.indice = indice;
        for(Persona persona: imagen.getPersonas()){
            nombrePersonas.addLast(persona.getNombre());
        }
    }
}
