package hu.petrik.adatbazisjavafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DogController {

    @FXML
    private TableView<Dog> dogTable;
    @FXML
    private TableColumn<Dog, String> nameCol;
    @FXML
    private TableColumn<Dog, Integer> ageCol;
    @FXML
    private TableColumn<Dog, String> breedCol;
    private DogDB db;
    @FXML
    private Button deleteButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField breedInput;
    @FXML
    private Button updateButton;
    @FXML
    private TextField nameInput;
    @FXML
    private Spinner<Integer> ageInput;

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        try {
            db = new DogDB();
            readDogs();
        } catch (SQLException e) {
            Platform.runLater(() -> {
                sqlAlert(e);
                Platform.exit();
            });
        }
    }

    private void sqlAlert(SQLException e) {
        alert(Alert.AlertType.ERROR, "Hiba történt az adatbázis kapcsolat kialakításakor", e.getMessage());
    }

    private void readDogs() throws SQLException {
        List<Dog> dogs = db.readDogs();
        dogTable.getItems().clear();
        dogTable.getItems().addAll(dogs);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String conentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(conentText);
        return alert.showAndWait();
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameInput.getText().trim();
        int age = ageInput.getValue();
        String breed = breedInput.getText().trim();
        if (name.isEmpty()) {
            alert(Alert.AlertType.WARNING, "A név megadása kötelező", "");
            return;
        }
        if (breed.isEmpty()) {
            alert(Alert.AlertType.WARNING, "A faj megadása kötelező", "");
            return;
        }
        Dog dog = new Dog(name, age, breed);
        try {
            if (db.createDog(dog)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
                readDogs();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        nameInput.setText("");
        ageInput.getValueFactory().setValue(0);
        breedInput.setText("");
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectedIndex = dogTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING, "A törléshez előbb válasszon ki kutyát a táblázatból", "");
            return;
        }
        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION, "Biztos, hogy törölni szeretné a kiválasztott kutyát?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES))) {
            return;
        }
        Dog selected = dogTable.getSelectionModel().getSelectedItem();
        try {
            if (db.deleteDog(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readDogs();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    @FXML
    public void cancelClick(ActionEvent actionEvent) {
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
    }
}