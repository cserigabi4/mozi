package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.*;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Foglalas;
import hu.alkfejl.model.Helyek;
import hu.alkfejl.model.Vetites;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class FoglalasokWindowController implements Initializable {
    FoglalasDao dao = new FoglalasDaoImpl();
    @FXML
    private TableView<Foglalas> foglalasokTabla;
    @FXML
    private TableColumn<Foglalas, Film> filmOszlop;
    @FXML
    private TableColumn<Foglalas, String> foglalasNevOszlop;
    @FXML
    private TableColumn<Foglalas, Integer> foglalasOszlop;
    @FXML
    private TableColumn<Foglalas, Vetites> vetitesOszlop;
    @FXML
    private TableColumn<Foglalas, Integer> helyekOszlop;
    @FXML
    private TableColumn<Foglalas, Integer> arOszlop;


    @FXML
    private TableColumn<Foglalas, Void> esemenyOszlop;
    @FXML
    private StackPane menuPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuData();
        refreshTable();
        foglalasOszlop.setCellValueFactory(new PropertyValueFactory<>("id"));
        filmOszlop.setCellValueFactory(new PropertyValueFactory<>("film"));
        foglalasNevOszlop.setCellValueFactory(new PropertyValueFactory<>("felhasznaloId"));
        vetitesOszlop.setCellValueFactory(new PropertyValueFactory<>("vetites"));
        helyekOszlop.setCellValueFactory(new PropertyValueFactory<>("helyek"));
        arOszlop.setCellValueFactory(new PropertyValueFactory<>("ar"));

        esemenyOszlop.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Törlés");
            private final Button editBtn = new Button("Szerkesztés");

            {
                deleteBtn.setOnAction(event -> {
                    Foglalas f =getTableRow().getItem();
                    dao.torlesDesktop(f.getId());
                    refreshTable();
                });
                editBtn.setOnAction(event -> {
                    refreshTable();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editBtn, deleteBtn);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });
    }
    private void refreshTable() {
        foglalasokTabla.getItems().setAll(dao.osszes());
    }
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        TreeItem<String> filmek = new TreeItem<>("Filmek");
        TreeItem<String> termek = new TreeItem<>("Termek");
        TreeItem<String> foglalások = new TreeItem<>("Foglalások");
        treeItemRoot1.getChildren().addAll(filmek,termek,foglalások);
        menuPane.getChildren().add(treeView);
        treeView.getSelectionModel().selectedItemProperty().addListener( (observable,oldValue,newValue) -> {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            String selectedMenu;
            selectedMenu = selectedItem.getValue();
            switch (selectedMenu){
                case "Filmek":
                    FilmekFxml();
                    break;
                case "Termek":
                    TermekFxml();
                    break;
            }
        });
    }
    @FXML
    public void FilmekFxml(){
        App.loadFXML("/fxml/main-window.fxml");
    }
    public void TermekFxml(){
        App.loadFXML("/fxml/termek-window.fxml");
    }

}