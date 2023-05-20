package ec.edu.espol.proyectoed_grupo5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.image.Image;

/**
 * @author Kevin C, Kenny J., Richard P.
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("pantallaIniciarSesion").load(), 640, 480);
        stage.setScene(scene);
        stage.setTitle("¡Organiza tus fotos!");
        String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.png";
        Path ruta = Paths.get(rut);
        Image imagen = new Image("file:" + ruta);
        stage.getIcons().add(imagen);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load());
    }

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

}