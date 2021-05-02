package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.*;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Szereplo;
import hu.alkfejl.model.Vetites;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditFilmController implements Initializable {

    private Film f;
    private FilmDAO filmDAO = new FilmDAOImpl();
    private VetitesDAO vetitesDAO= new VetitesDAOImpl();
    private SzereploDAO szereploDAO =new SzereploDAOImpl();

    @FXML
    private Button mentes;

    @FXML
    private TextField cim;
    @FXML
    private Spinner hossz;
    @FXML
    private Spinner korhatar;
    @FXML
    private TextField rendezo;
    @FXML
    private TextField leiras;
    @FXML
    private TextField boritoUrl;
    @FXML
    private TextField elozetesUrl;
    @FXML
    private ListView<Vetites> vetitesek;
    @FXML
    private ListView<Szereplo> szereplok;


    @FXML
    private Label nevHiba;

    public void setFilm(Film f) {
        this.f = f;

        List<Vetites> vetitesList = vetitesDAO.osszesFilmId(f);
        f.setVetitesek(FXCollections.observableList(vetitesList));

        List<Szereplo> szereploList = szereploDAO.osszesByFilmId(f);
        f.setSzereplok(FXCollections.observableList(szereploList));

        cim.textProperty().bindBidirectional(f.cimProperty());
        SpinnerValueFactory<Integer> hosszValeu = new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 240);
        hossz.setValueFactory(hosszValeu);
        hossz.getValueFactory().valueProperty().bindBidirectional(f.hosszProperty());
        hossz.setEditable(true);
        SpinnerValueFactory<Integer> korhatarValeu = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25);
        korhatar.setValueFactory(korhatarValeu);
        korhatar.getValueFactory().valueProperty().bindBidirectional(f.korhatarProperty());
        korhatar.setEditable(true);
        rendezo.textProperty().bindBidirectional(f.rendezőProperty());
        leiras.textProperty().bindBidirectional(f.leirasProperty());
        boritoUrl.textProperty().bindBidirectional(f.boritoUrlProperty());
        elozetesUrl.textProperty().bindBidirectional(f.elozetesUrlProperty());
        vetitesek.itemsProperty().bindBidirectional(f.vetitesekProperty());
        szereplok.itemsProperty().bindBidirectional(f.szereplokProperty());

    }

    @FXML
    public void onCancel(){
        App.loadFXML("/fxml/main-window.fxml");
    }

    @FXML
    public void onSave(){
       filmDAO.mentes(f);
        szereploDAO.deleteAll(f);
        f.getSzereplok().forEach(szereplo ->{
            szereplo.setFilm(f);
            szereploDAO.mentes(szereplo);
        });


       App.loadFXML("/fxml/main-window.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mentes.disableProperty().bind(cim.textProperty().isEmpty());

        vetitesek.setCellFactory(param -> {
            ListCell<Vetites> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Módosít");
            MenuItem deleteItem = new MenuItem("Törlés");

            contextMenu.getItems().addAll(editItem,deleteItem);

            editItem.setOnAction(event -> {
                Vetites item = cell.getItem();
                showVetitesDialog(item);
                System.out.println("Szoveg");
            });
            deleteItem.setOnAction(event -> {
                Vetites item = cell.getItem();
                f.getVetitesek().remove(cell.getItem());
                vetitesDAO.torles(item);
            });

            StringBinding cellTextBinding = new When(cell.itemProperty().isNotNull()).then(cell.itemProperty().asString()).otherwise("");
            cell.textProperty().bind(cellTextBinding);

            cell.emptyProperty().addListener((observable,wasEmpty,isNowEmpty)->{
                if(isNowEmpty){
                    cell.setContextMenu(null);
                }else{
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
        szereplok.setCellFactory(param -> {
            ListCell<Szereplo> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Törlés");

            contextMenu.getItems().addAll(deleteItem);

            deleteItem.setOnAction(event -> {
                f.getSzereplok().remove(cell.getItem());

            });

            StringBinding cellTextBinding = new When(cell.itemProperty().isNotNull()).then(cell.itemProperty().asString()).otherwise("");
            cell.textProperty().bind(cellTextBinding);

            cell.emptyProperty().addListener((observable,wasEmpty,isNowEmpty)->{
                if(isNowEmpty){
                    cell.setContextMenu(null);
                }else{
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });

        cim.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue != null && newValue.isEmpty()){
                nevHiba.setText("Nincs cím megadva");
            }else{
                nevHiba.setText("");
            }
        });

    }

    @FXML void addNewVetites(){
        showVetitesDialog();
    }
    @FXML void addNewSzereplo(){
        showSzereploDialog();
    }

    private void showVetitesDialog(Vetites vetites) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_edit_vetites.fxml"));
        try {
           Parent root = loader.load();
           AddEditVetitesController controller = loader.getController();

            controller.init(stage,vetites,f);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showVetitesDialog() {
        showVetitesDialog(new Vetites());
    }

    private void showSzereploDialog() {
        showSzereploDialog(new Szereplo());
    }
    private void showSzereploDialog(Szereplo szereplo) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_edit_szereplo.fxml"));
        try {
            Parent root = loader.load();
            AddEditSzereploController controller = loader.getController();

            controller.init(stage,szereplo,f);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
