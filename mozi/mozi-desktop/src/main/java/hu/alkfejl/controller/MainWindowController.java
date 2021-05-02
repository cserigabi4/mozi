package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.FilmDAO;
import hu.alkfejl.dao.FilmDAOImpl;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Terem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    FilmDAO dao = new FilmDAOImpl();
    private List<Film> all;

    @FXML
    private TableView<Film> filmekTabla;
    @FXML
    private TableColumn<Film, String> cimOszlop;
    @FXML
    private TableColumn<Film, Integer> hosszOszlop;
    @FXML
    private TableColumn<Film, Integer> korhatarOszlop;
    @FXML
    private TableColumn<Film, String> rendezoOszlop;
    @FXML
    private TableColumn<Film, String> leirasOszlop;
    @FXML
    private TableColumn<Film, Void> esemenyOszlop;

    @FXML
    private StackPane menuPane;


    @FXML
    private TextField nevKereses;

    @FXML
    public void onKereses(){
      List<Film> filter=  all.stream().filter(film -> film.getCim().contains(nevKereses.getText())).collect(Collectors.toList());
      filmekTabla.getItems().setAll(filter);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        setMenuData();
        refreshTable();
        cimOszlop.setCellValueFactory(new PropertyValueFactory<>("cim"));
        hosszOszlop.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        korhatarOszlop.setCellValueFactory(new PropertyValueFactory<>("korhatar"));
        rendezoOszlop.setCellValueFactory(new PropertyValueFactory<>("rendező"));
        leirasOszlop.setCellValueFactory(new PropertyValueFactory<>("leiras"));
        esemenyOszlop.setCellFactory(param -> new TableCell<>(){
            private final Button deleteBtn =new Button("Törlés");
            private final Button editBtn = new Button("Szerkesztés");

            {
                deleteBtn.setOnAction(event -> {
                    Film f = getTableRow().getItem();
                    deleteFilm(f);
                    refreshTable();
                });
                editBtn.setOnAction(event -> {
                    Film f = getTableRow().getItem();
                    editFilm(f);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editBtn,deleteBtn);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        TreeItem<String> filmek = new TreeItem<>("Filmek");
        TreeItem<String> termek = new TreeItem<>("Termek");
        TreeItem<String> foglalasok = new TreeItem<>("Foglalások");
        treeItemRoot1.getChildren().addAll(filmek,termek,foglalasok);
        menuPane.getChildren().add(treeView);
        treeView.getSelectionModel().selectedItemProperty().addListener( (observable,oldValue,newValue) -> {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            String selectedMenu;
            selectedMenu = selectedItem.getValue();
            switch (selectedMenu){
                case "Termek":
                        TermekFxml();
                        break;
                case "Foglalások":
                        FoglalasokFxml();
                    break;
            }
        });
    }

    private void editFilm(Film f) {
        FXMLLoader fxmlLoader = App.loadFXML("/fxml/add_edit_film.fxml");
        AddEditFilmController controller= fxmlLoader.getController();
        controller.setFilm(f);
    }

    private void deleteFilm(Film f) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztos törli? " + f.getCim(),ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                dao.torles(f);
            }
        });

    }

    private void refreshTable(){
        all = dao.osszes();
        filmekTabla.getItems().setAll(all);
    }

    @FXML
    public void onExit(){
        Platform.exit();
    }

    @FXML
    public void onAddNewFilm(){
        FXMLLoader fxmlLoader = App.loadFXML("/fxml/add_edit_film.fxml");
        AddEditFilmController controller= fxmlLoader.getController();
        controller.setFilm(new Film());
    }
    @FXML
    public void onAddNewTerem(){
        FXMLLoader fxmlLoader = App.loadFXML("/fxml/add_edit_terem.fxml");
        AddEditTeremController controller= fxmlLoader.getController();
        controller.setTerem(new Terem());
    }

    @FXML
    public void TermekFxml(){
        App.loadFXML("/fxml/termek-window.fxml");
    }
    @FXML
    public void FoglalasokFxml(){
        App.loadFXML("/fxml/foglalasok-window.fxml");
    }


}
