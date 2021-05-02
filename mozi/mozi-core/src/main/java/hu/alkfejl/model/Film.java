package hu.alkfejl.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class Film {

    private IntegerProperty id = new SimpleIntegerProperty(this,"id");
    private StringProperty cim = new SimpleStringProperty(this,"cim");
    private ObjectProperty<Integer> hossz=new SimpleObjectProperty<>(30);
    private ObjectProperty<Integer> korhatar=new SimpleObjectProperty<>(0);
    private StringProperty rendező = new SimpleStringProperty(this,"rendező");
    private ObjectProperty<ObservableList<Szereplo>> szereplok =new SimpleObjectProperty<>(this,"szereplok");
    private ObjectProperty<ObservableList<Vetites>> vetitesek =new SimpleObjectProperty<>(this,"vetitesek");
    private StringProperty leiras = new SimpleStringProperty(this,"leiras");
    private StringProperty boritoUrl = new SimpleStringProperty(this,"boritoUrl");
    private StringProperty elozetesUrl = new SimpleStringProperty(this,"elozetesUrl");

    public String getElozetesUrl() {
        return elozetesUrl.get();
    }

    public StringProperty elozetesUrlProperty() {
        return elozetesUrl;
    }

    public void setElozetesUrl(String elozetesUrl) {
        this.elozetesUrl.set(elozetesUrl);
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

    public String getCim() {
        return cim.get();
    }

    public StringProperty cimProperty() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim.set(cim);
    }

    public int getHossz() {
        return hossz.get();
    }

    public ObjectProperty<Integer> hosszProperty() {
        return hossz;
    }

    public void setHossz(int hossz) {
        this.hossz.set(hossz);
    }

    public int getKorhatar() {
        return korhatar.get();
    }

    public ObjectProperty<Integer> korhatarProperty() {
        return korhatar;
    }

    public void setKorhatar(int korhatar) {
        this.korhatar.set(korhatar);
    }

    public String getRendező() {
        return rendező.get();
    }

    public StringProperty rendezőProperty() {
        return rendező;
    }

    public void setRendező(String rendező) {
        this.rendező.set(rendező);
    }

    public ObservableList<Szereplo> getSzereplok() {
        return szereplok.get();
    }

    public ObjectProperty<ObservableList<Szereplo>> szereplokProperty() {
        return szereplok;
    }

    public void setSzereplok(ObservableList<Szereplo> szereplok) { this.szereplok.set(szereplok); }

    public ObservableList<Vetites> getVetitesek() {
        return vetitesek.get();
    }

    public ObjectProperty<ObservableList<Vetites>> vetitesekProperty() {
        return vetitesek;
    }

    public void setVetitesek(ObservableList<Vetites> vetitesek) {
        this.vetitesek.set(vetitesek);
    }

    public String getLeiras() {
        return leiras.get();
    }

    public StringProperty leirasProperty() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras.set(leiras);
    }

    public String getBoritoUrl() {
        return boritoUrl.get();
    }

    public StringProperty boritoUrlProperty() {
        return boritoUrl;
    }

    public void setBoritoUrl(String boritoUrl) {
        this.boritoUrl.set(boritoUrl);
    }

    @Override
    public String toString() {
        return cim.getValue();
    }
}
