package hu.alkfejl.dao;

import hu.alkfejl.model.Film;
import hu.alkfejl.model.Foglalas;

import java.util.List;

public interface FoglalasDao {

    List<Foglalas> osszes();

    List<Foglalas> osszesFoglalasById(int foglalasId);

    Foglalas mentes(Foglalas foglalas);

    boolean torles(int foglalasId,int vetitesId);

    void torlesDesktop(int foglalasId);

}
