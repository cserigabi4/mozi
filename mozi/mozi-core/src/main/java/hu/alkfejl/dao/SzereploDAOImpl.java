package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Szereplo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SzereploDAOImpl implements SzereploDAO{

    private static final String INSERT_SZEREPLO = "INSERT INTO szerplok (nev,film_id) values (?,?)";
    private static final String DELETE_SZEREPLO = "DELETE FROM szerplok where nev=? and film_id=?";
    private static final String SELECT_SZEREPLOK_BY_FILM = "SELECT * FROM szerplok where film_id=?";
    private String connectionUrl;

    public SzereploDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl =  MoziConfiguration.getValeu("db.url");
    }

    @Override
    public List<Szereplo> osszesByFilmId(Film film) {
        List<Szereplo> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_SZEREPLOK_BY_FILM)
        ){
            statement.setInt(1,film.getId());
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Szereplo szereplo = new Szereplo();
                szereplo.setNev(rs.getString("nev"));
                szereplo.setFilm(film);
                result.add(szereplo);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Szereplo mentes(Szereplo szerplo) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt=  c.prepareStatement(INSERT_SZEREPLO)
        ) {
            stmt.setString(1, szerplo.getNev());
            stmt.setInt(2, szerplo.getFilm().getId());
            int affectedRows = stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return szerplo;
    }

    @Override
    public void torles(Szereplo szerplo) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_SZEREPLO);
        ){
            stmt.setString(1, szerplo.getNev());
            stmt.setInt(2, szerplo.getFilm().getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteAll(Film film) {
        osszesByFilmId(film).forEach(this::torles);
    }
}
