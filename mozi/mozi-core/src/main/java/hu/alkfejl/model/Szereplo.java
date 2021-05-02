package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Szereplo {
    private ObjectProperty<Film> film = new SimpleObjectProperty<>(this,"film");
    private StringProperty nev = new SimpleStringProperty(this,"nev");

    public Film getFilm() {
        return film.get();
    }

    public ObjectProperty<Film> filmProperty() {
        return film;
    }

    public void setFilm(Film film) {
        this.film.set(film);
    }

    public String getNev() {
        return nev.get();
    }

    public StringProperty nevProperty() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev.set(nev);
    }

    @Override
    public String toString() {
        return nev.getValue();
    }
}
