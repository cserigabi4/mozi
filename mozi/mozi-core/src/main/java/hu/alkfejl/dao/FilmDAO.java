package hu.alkfejl.dao;

import hu.alkfejl.model.Film;

import java.util.List;

public interface FilmDAO {

    List<Film> osszes();

    List<Film> osszesAktualis();

    Film FilmById(int id);

    Film mentes(Film film);

    void torles(Film film);


}
