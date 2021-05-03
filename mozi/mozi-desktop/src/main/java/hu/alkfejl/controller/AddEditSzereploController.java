package hu.alkfejl.controller;

import hu.alkfejl.model.Film;
import hu.alkfejl.model.Szereplo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditSzereploController implements Initializable {

    private Stage stage;
    private Szereplo szerplo;
    private Film film;

    @FXML
    private TextField ujszerplo;

    public void init(Stage stage, Szereplo szerplo,Film film) {
        this.stage = stage;
        this.szerplo = szerplo;
        this.film =film;

        ujszerplo.textProperty().bindBidirectional(szerplo.nevProperty());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onCancel(){
        stage.close();
    }
    @FXML
    public void onSave(){
        film.getSzereplok().remove(szerplo);
        film.getSzereplok().add(szerplo);
        stage.close();
    }
}
