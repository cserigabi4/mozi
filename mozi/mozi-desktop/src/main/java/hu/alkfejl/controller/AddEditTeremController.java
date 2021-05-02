package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.FilmDAO;
import hu.alkfejl.dao.FilmDAOImpl;
import hu.alkfejl.dao.TeremDAO;
import hu.alkfejl.dao.TeremDAOImpl;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Terem;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class AddEditTeremController {

    private Terem t;
    private TeremDAO teremDAO = new TeremDAOImpl();


    @FXML
    private Spinner oszlopokSzama;
    @FXML
    private Spinner sorokSzama;

    public void setTerem(Terem t) {
        this.t = t;

        SpinnerValueFactory<Integer> sorokSzamaValeu = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        sorokSzama.setValueFactory(sorokSzamaValeu);
        sorokSzama.getValueFactory().valueProperty().bindBidirectional(t.sorokSzamaProperty());

        SpinnerValueFactory<Integer> oszlopokSzamaValeu = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        oszlopokSzama.setValueFactory(oszlopokSzamaValeu);
        oszlopokSzama.getValueFactory().valueProperty().bindBidirectional(t.oszlopokSzamaProperty());


    }

    @FXML
    public void onCancel(){
        App.loadFXML("/fxml/termek-window.fxml");
    }

    @FXML
    public void onSave(){
        t = teremDAO.mentes(t);
        App.loadFXML("/fxml/termek-window.fxml");
    }




}
