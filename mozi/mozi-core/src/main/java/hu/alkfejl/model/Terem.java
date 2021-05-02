package hu.alkfejl.model;

import javafx.beans.property.*;

public class Terem {

    /*
    id int
    sorok száma int
    oszlopok int
     */

    private IntegerProperty id = new SimpleIntegerProperty(this,"id");
    private ObjectProperty<Integer> sorokSzama=new SimpleObjectProperty<>(1);
    private ObjectProperty<Integer> oszlopokSzama=new SimpleObjectProperty<>(1);

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getSorokSzama() {
        return sorokSzama.get();
    }

    public ObjectProperty<Integer> sorokSzamaProperty() {
        return sorokSzama;
    }

    public void setSorokSzama(int sorokSzama) {
        this.sorokSzama.set(sorokSzama);
    }

    public int getOszlopokSzama() {
        return oszlopokSzama.get();
    }

    public ObjectProperty<Integer> oszlopokSzamaProperty() {
        return oszlopokSzama;
    }

    public void setOszlopokSzama(int oszlopokSzama) {
        this.oszlopokSzama.set(oszlopokSzama);
    }
}
