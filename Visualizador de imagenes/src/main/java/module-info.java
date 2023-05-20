module ec.edu.espol.proyectoed_grupo5 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.proyectoed_grupo5 to javafx.fxml;
    exports ec.edu.espol.proyectoed_grupo5;
    
    opens ec.edu.espol.controllers to javafx.fxml;
    exports ec.edu.espol.controllers;
    
    opens ec.edu.espol.util to javafx.fxml;
    exports ec.edu.espol.util;
    
    opens ec.edu.espol.model to javafx.fxml;
    exports ec.edu.espol.model;
}
