package hu.alkfejl.dao;

;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Szereplo;

import java.util.List;

public interface SzereploDAO {

    List<Szereplo> osszesByFilmId(Film film);

    Szereplo mentes(Szereplo szerplo);

    void torles(Szereplo szerplo);

    void deleteAll(Film film);

}
