package hu.alkfejl.dao;


import hu.alkfejl.model.Film;
import hu.alkfejl.model.Vetites;

import java.util.List;

public interface VetitesDAO {

    List<Vetites> osszes();

    List<Vetites> osszesFilmId(Film film);

    List<Vetites> osszesFilmId(int filmId);

    Vetites vetitesById(int vetitesId);

    Vetites mentes(Vetites vetites, int filmId);

    void torles(Vetites vetites);

    List<Vetites> osszesAktualisFilmId(int filmId);

}
