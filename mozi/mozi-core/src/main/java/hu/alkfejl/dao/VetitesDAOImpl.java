package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Vetites;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VetitesDAOImpl implements VetitesDAO{
    private static final String SELECT_ALL_VETITES = "SELECT * FROM vetitesek";
    private static final String SELECT_FILMEK_BY_ID = "SELECT * FROM vetitesek where film_id=?";
    private static final String SELECT_VETITES_BY_ID = "SELECT * FROM vetitesek where id=?";
    private static final String INSERT_VETITES = "INSERT INTO vetitesek (terem_id,datum,ido,ar,film_id) values (?,?,?,?,?)";
    private static final String UPDATE_VETITES = "UPDATE vetitesek SET terem_id=?, datum=?,ido=?, ar=? where id=?";
    private static final String DELETE_VETITES = "DELETE FROM vetitesek where id=?";
    private String connectionUrl;

    public VetitesDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl =  MoziConfiguration.getValeu("db.url");
    }


    @Override
    public List<Vetites> osszes() {
        List<Vetites> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_ALL_VETITES);
        ){
            while(rs.next()){
                Vetites vetites = new Vetites();
                vetites.setId(rs.getInt("id"));
                vetites.setFilm(rs.getInt("film_id"));
                vetites.setTerem(rs.getInt("terem_id"));
                Date date= Date.valueOf(rs.getString("datum"));
                vetites.setDatum(date == null ? LocalDate.now() : date.toLocalDate());
                vetites.setIdo(rs.getInt("ido"));
                vetites.setAr(rs.getInt("ar"));
                result.add(vetites);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vetites> osszesFilmId(Film film) {
        return osszesFilmId(film.getId());
    }

    @Override
    public List<Vetites> osszesFilmId(int filmId) {
        List<Vetites> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_FILMEK_BY_ID)
        ){
            statement.setInt(1,filmId);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Vetites vetites = new Vetites();
                vetites.setId(rs.getInt("id"));
                vetites.setFilm(rs.getInt("film_id"));
                vetites.setTerem(rs.getInt("terem_id"));
                Date date= Date.valueOf(rs.getString("datum"));
                vetites.setDatum(date == null ? LocalDate.now() : date.toLocalDate());
                vetites.setIdo(rs.getInt("ido"));
                vetites.setAr(rs.getInt("ar"));

                result.add(vetites);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Vetites> osszesAktualisFilmId(int filmId) {
        List<Vetites> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_FILMEK_BY_ID)
        ){
            statement.setInt(1,filmId);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Vetites vetites = new Vetites();
                vetites.setId(rs.getInt("id"));
                vetites.setFilm(rs.getInt("film_id"));
                vetites.setTerem(rs.getInt("terem_id"));
                Date date= Date.valueOf(rs.getString("datum"));
                vetites.setDatum(date == null ? LocalDate.now() : date.toLocalDate());
                vetites.setIdo(rs.getInt("ido"));
                vetites.setAr(rs.getInt("ar"));
                LocalDateTime date2 = LocalDateTime.now();
                LocalDateTime date3 = vetites.getDatum().atTime(vetites.getIdo(),00);
                if(date2.isBefore(date3)){
                    result.add(vetites);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }




    @Override
    public Vetites vetitesById(int vetitesId) {
        Vetites vetites=new Vetites();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_VETITES_BY_ID)
        ){
            statement.setInt(1,vetitesId);
            ResultSet rs=statement.executeQuery();
            if(rs.next()){
                vetites.setId(rs.getInt("id"));
                vetites.setFilm(rs.getInt("film_id"));
                vetites.setTerem(rs.getInt("terem_id"));
                Date date= Date.valueOf(rs.getString("datum"));
                vetites.setDatum(date == null ? LocalDate.now() : date.toLocalDate());
                vetites.setIdo(rs.getInt("ido"));
                vetites.setAr(rs.getInt("ar"));
                return vetites;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vetites;
    }

    @Override
    public Vetites mentes(Vetites vetites, int filmId) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= vetites.getId() <= 0 ? c.prepareStatement(INSERT_VETITES, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_VETITES)
        ) {
            if(vetites.getId() > 0){
                stmt.setInt(5, vetites.getId());
            }else{
                stmt.setInt(5, filmId);
            }
            stmt.setInt(1, vetites.getTerem());
            stmt.setString(2, vetites.getDatum().toString());
            stmt.setInt(3,vetites.getIdo());
            stmt.setInt(4, vetites.getAr());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }
            if(vetites.getId() <= 0){
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    vetites.setId(genKeys.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return vetites;
    }
    @Override
    public void torles(Vetites vetites) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_VETITES);
        ){
            stmt.setInt(1,vetites.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
