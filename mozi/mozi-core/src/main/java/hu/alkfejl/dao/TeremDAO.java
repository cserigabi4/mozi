package hu.alkfejl.dao;

import hu.alkfejl.model.Film;
import hu.alkfejl.model.Terem;

import java.util.List;

public interface TeremDAO {


    List<Terem> osszes();

    List<Integer> osszesId();

    Terem teremById(int teremId);

    Terem mentes(Terem terem);

    void torles(Terem terem);

}
