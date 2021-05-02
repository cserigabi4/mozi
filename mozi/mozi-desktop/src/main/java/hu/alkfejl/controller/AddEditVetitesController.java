package hu.alkfejl.controller;

import hu.alkfejl.dao.TeremDAO;
import hu.alkfejl.dao.TeremDAOImpl;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Terem;
import hu.alkfejl.model.Vetites;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditVetitesController implements Initializable {

    private Stage stage;
    private Vetites vetites;
    private Film film;


    public void init(Stage stage, Vetites vetites, Film film) {
        this.stage = stage;
        this.vetites = vetites;
        this.film = film;


        termek.valueProperty().bindBidirectional(vetites.teremProperty());
        datum.valueProperty().bindBidirectional(vetites.datumProperty());
        SpinnerValueFactory<Integer> idoValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 22);
        ido.setValueFactory(idoValue);
        ido.getValueFactory().valueProperty().bindBidirectional(vetites.idoProperty());
        SpinnerValueFactory<Integer> arValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 10000,500,500);
        ar.setValueFactory(arValue);
        ar.getValueFactory().valueProperty().bindBidirectional(vetites.arProperty());

    }

    @FXML
    private ComboBox<Integer> termek;
    @FXML
    private  DatePicker datum;
    @FXML
    private Spinner ido;
    @FXML
    private Spinner ar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TeremDAO d = new TeremDAOImpl();
         termek.getItems().setAll(d.osszesId());
    }

    @FXML
    public void onCancel(){
        stage.close();
    }
    @FXML
    public void onSave(){
        film.getVetitesek().remove(vetites);
        film.getVetitesek().add(vetites);
        stage.close();
    }

}
