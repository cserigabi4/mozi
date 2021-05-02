package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class Foglalas {

    private IntegerProperty id = new SimpleIntegerProperty(this,"id");
    private ObjectProperty<Integer> felhasznaloId=new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> vetitesId=new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> ar=new SimpleObjectProperty<>(0);
    private ObjectProperty<List<Integer>> helyek =new SimpleObjectProperty<>(this,"helyek");
    private ObjectProperty<Film> film = new SimpleObjectProperty<>(this,"film");
    private ObjectProperty<Vetites> vetites = new SimpleObjectProperty<>(this,"vetites");


    public Vetites getVetites() {
        return vetites.get();
    }

    public ObjectProperty<Vetites> vetitesProperty() {
        return vetites;
    }

    public void setVetites(Vetites vetites) {
        this.vetites.set(vetites);
    }

    public Film getFilm() {
        return film.get();
    }

    public ObjectProperty<Film> filmProperty() {
        return film;
    }

    public void setFilm(Film film) {
        this.film.set(film);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Integer getFelhasznaloId() {
        return felhasznaloId.get();
    }

    public ObjectProperty<Integer> felhasznaloIdProperty() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(Integer felhasznaloId) {
        this.felhasznaloId.set(felhasznaloId);
    }

    public Integer getVetitesId() {
        return vetitesId.get();
    }

    public ObjectProperty<Integer> vetitesIdProperty() {
        return vetitesId;
    }

    public void setVetitesId(Integer vetitesId) {
        this.vetitesId.set(vetitesId);
    }

    public Integer getAr() {
        return ar.get();
    }

    public ObjectProperty<Integer> arProperty() {
        return ar;
    }

    public void setAr(Integer ar) {
        this.ar.set(ar);
    }

    public List<Integer> getHelyek() {
        return helyek.get();
    }

    public ObjectProperty<List<Integer>> helyekProperty() {
        return helyek;
    }

    public void setHelyek(List<Integer> helyek) {
        this.helyek.set(helyek);
    }
}
