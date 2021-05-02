package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;

public class Vetites {

    /*
  Hozz létre egy film cím adat tagot majd a daoimpl-ben állísdbe az id alapján a címet utána a táblázatban meg kapod a címet!!
   */
    private IntegerProperty id = new SimpleIntegerProperty(this,"id");
    private ObjectProperty<Integer> film=new SimpleObjectProperty<>(1);
    private ObjectProperty<Integer> terem=new SimpleObjectProperty<>(1);
    private ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>(this,"datum");
    private ObjectProperty<Integer> ido = new SimpleObjectProperty<>(1);
    private ObjectProperty<Integer> ar=new SimpleObjectProperty<>(500);

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Integer getFilm() {
        return film.get();
    }

    public ObjectProperty<Integer> filmProperty() {
        return film;
    }

    public void setFilm(Integer film) {
        this.film.set(film);
    }

    public Integer getTerem() {
        return terem.get();
    }

    public ObjectProperty<Integer> teremProperty() {
        return terem;
    }

    public void setTerem(Integer terem) {
        this.terem.set(terem);
    }

    public LocalDate getDatum() {
        return datum.get();
    }

    public ObjectProperty<LocalDate> datumProperty() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum.set(datum);
    }

    public Integer getIdo() {
        return ido.get();
    }

    public ObjectProperty<Integer> idoProperty() {
        return ido;
    }

    public void setIdo(Integer ido) {
        this.ido.set(ido);
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

    @Override
    public String toString() {
        return "Terem: "+terem.getValue()+ " Dátum:"+datum.getValue() + " Idő:" + ido.getValue();
    }
}
