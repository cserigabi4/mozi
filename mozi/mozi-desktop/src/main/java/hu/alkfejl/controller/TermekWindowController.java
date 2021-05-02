package hu.alkfejl.controller;


import hu.alkfejl.App;
import hu.alkfejl.dao.TeremDAO;
import hu.alkfejl.dao.TeremDAOImpl;
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
import java.util.ResourceBundle;

public class TermekWindowController implements Initializable {

    TeremDAO dao = new TeremDAOImpl();

    @FXML
    private TableView<Terem> termekTabla;
    @FXML
    private TableColumn<Terem, String> idOszlop;
    @FXML
    private TableColumn<Terem, Integer> sorokSzamaOszlop;
    @FXML
    private TableColumn<Terem, Integer> oszlopokSzamaOszlop;
    @FXML
    private TableColumn<Terem, Void> esemenyOszlop;
    @FXML
    private StackPane menuPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuData();
        refreshTable();
        idOszlop.setCellValueFactory(new PropertyValueFactory<>("id"));
        sorokSzamaOszlop.setCellValueFactory(new PropertyValueFactory<>("sorokSzama"));
        oszlopokSzamaOszlop.setCellValueFactory(new PropertyValueFactory<>("oszlopokSzama"));
        esemenyOszlop.setCellFactory(param -> new TableCell<>(){
            private final Button deleteBtn =new Button("Törlés");
            private final Button editBtn = new Button("Szerkesztés");

            {
                deleteBtn.setOnAction(event -> {
                    Terem t = getTableRow().getItem();
                    deleteTerem(t);
                    refreshTable();
                });
                editBtn.setOnAction(event -> {
                    Terem t = getTableRow().getItem();
                    editTerem(t);
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

    private void editTerem(Terem t) {
        FXMLLoader fxmlLoader = App.loadFXML("/fxml/add_edit_terem.fxml");
        AddEditTeremController controller= fxmlLoader.getController();
        controller.setTerem(t);
    }

    private void deleteTerem(Terem t) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztos törli? " + t.getId(),ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                dao.torles(t);
            }
        });
    }

    private void refreshTable() {
        termekTabla.getItems().setAll(dao.osszes());
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
                case "Filmek":
                    FilmekFxml();
                    break;
                case "Foglalások":
                    FoglalasokFxml();
                    break;
            }
        });
    }

    @FXML
    public void FilmekFxml(){
        App.loadFXML("/fxml/main-window.fxml");
    }
    public void FoglalasokFxml(){
        App.loadFXML("/fxml/foglalasok-window.fxml");
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





}

