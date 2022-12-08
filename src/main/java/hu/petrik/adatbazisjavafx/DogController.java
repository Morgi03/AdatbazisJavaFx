package hu.petrik.adatbazisjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DogController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}