package hu.alkfejl.dao;

import hu.alkfejl.model.Helyek;

import java.util.List;

public interface HelyDAO {

    List<Integer> helyekByFoglalasId(int foglalasId);

    List<Integer> helyekByteremId(int teremId, int vetitesId);

    Helyek mentes(Helyek hely);

    void torles(int foglalasId);
}
