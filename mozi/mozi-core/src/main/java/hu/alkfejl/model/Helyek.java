package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Helyek {
    private ObjectProperty<Integer> foglalasId=new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> hely=new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> teremId=new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> vetitesId=new SimpleObjectProperty<>(0);

    public Integer getVetitesId() {
        return vetitesId.get();
    }

    public ObjectProperty<Integer> vetitesIdProperty() {
        return vetitesId;
    }

    public void setVetitesId(Integer vetitesId) {
        this.vetitesId.set(vetitesId);
    }

    public Integer getTeremId() {
        return teremId.get();
    }

    public ObjectProperty<Integer> teremIdProperty() {
        return teremId;
    }

    public void setTeremId(Integer teremId) {
        this.teremId.set(teremId);
    }

    public Integer getFoglalasId() {
        return foglalasId.get();
    }

    public ObjectProperty<Integer> foglalasIdProperty() {
        return foglalasId;
    }

    public void setFoglalasId(Integer foglalasId) {
        this.foglalasId.set(foglalasId);
    }

    public Integer getHely() {
        return hely.get();
    }

    public ObjectProperty<Integer> helyProperty() {
        return hely;
    }

    public void setHely(Integer hely) {
        this.hely.set(hely);
    }
}
