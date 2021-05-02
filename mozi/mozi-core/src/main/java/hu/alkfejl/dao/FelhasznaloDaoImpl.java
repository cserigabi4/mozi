package hu.alkfejl.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Felhasznalo;

import java.sql.*;

public class FelhasznaloDaoImpl implements FelhasznaloDao{
    private static final String INSERT_FELHASZNALO = "INSERT INTO felhasznalok (nev, felhasznalonev, jelszo) VALUES (?,?,?)";
    //TODO: IDE NEM IS KELL UPDATE
    private static final String UPDATE_FELHASZNALO = "UPDATE felhasznalok SET nev=?, felhasznalonev=?, jelszo=? WHERE id=?";
    private static FelhasznaloDao instance;
    private static final String DB_CONN_STR = MoziConfiguration.getValeu("db.url");

    public static FelhasznaloDao getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new FelhasznaloDaoImpl();
        }
        return instance;
    }

    @Override
    public Felhasznalo getFelhasznaloById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_CONN_STR);
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM felhasznalok WHERE id = ?")
        ) {
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Felhasznalo u = new Felhasznalo();
                u.setId(rs.getInt(1));
                u.setNev(rs.getString(2));
                u.setFelhasznalonev(rs.getString(3));
                u.setJelszo(rs.getString(4));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Felhasznalo mentes(Felhasznalo felhasznalo) {
        try (Connection conn = DriverManager.getConnection(DB_CONN_STR);
             PreparedStatement pst = felhasznalo.getId() <= 0 ? conn.prepareStatement(INSERT_FELHASZNALO, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_FELHASZNALO)
        ) {
            pst.setString(1, felhasznalo.getNev());
            pst.setString(2, felhasznalo.getFelhasznalonev());


                String hashedPwd = BCrypt.withDefaults().hashToString(12, felhasznalo.getJelszo().toCharArray());
                pst.setString(3, hashedPwd);

            int affectedRows = pst.executeUpdate();
            if(affectedRows == 0){
                return null;
            }

            if(felhasznalo.getId() <= 0){ // INSERT
                ResultSet genKeys = pst.getGeneratedKeys();
                if(genKeys.next()){
                    felhasznalo.setId(genKeys.getInt(1));
                }
            }

            felhasznalo.setJelszo("");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return felhasznalo;

    }

    @Override
    public Felhasznalo bejelentekes(String felhasznalonev, String jelszo) {

        try (Connection conn = DriverManager.getConnection(DB_CONN_STR);
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM felhasznalok WHERE felhasznalonev = ?")
        ) {
            pst.setString(1, felhasznalonev);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                String dbPass = rs.getString("jelszo");

                BCrypt.Result result = BCrypt.verifyer().verify(jelszo.toCharArray(), dbPass);
                if(result.verified){
                    Felhasznalo felhasznalo = new Felhasznalo();
                    felhasznalo.setNev(rs.getString("nev"));
                    felhasznalo.setFelhasznalonev(rs.getString("felhasznalonev"));
                    felhasznalo.setId(rs.getInt("id"));
                    return felhasznalo;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
