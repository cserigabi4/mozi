package hu.alkfejl.dao;

import hu.alkfejl.model.Felhasznalo;

public interface FelhasznaloDao {

    Felhasznalo getFelhasznaloById(int id);
    Felhasznalo mentes(Felhasznalo felhasznalo);
    Felhasznalo bejelentekes(String felhasznalonev, String jelszo);

}
