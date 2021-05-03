package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Foglalas;
import hu.alkfejl.model.Szereplo;
import hu.alkfejl.model.Vetites;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilmDAOImpl implements FilmDAO {
    private static final String SELECT_ALL_FILMEK = "SELECT * FROM filmek";
    private static final String INSERT_FILM = "INSERT INTO filmek (cim,hossz,korhatar,rendezo,leiras,boritoUrl,elozetesUrl) values (?,?,?,?,?,?,?)";
    private static final String UPDATE_FILM = "UPDATE filmek SET cim=?, hossz=?, korhatar=?, rendezo=?,  leiras=?, boritoUrl=?, elozetesUrl=? WHERE id=? ";
    private static final String DELETE_FILM = "DELETE FROM filmek where id=?";
    private static final String SELECT_FILM_BY_ID = "SELECT * FROM filmek where id=?";
    private SzereploDAO szereploDAO = new SzereploDAOImpl();
    private VetitesDAO vetitesDAO = new VetitesDAOImpl();
    private String connectionUrl;
    private static FilmDAO instance;

    public FilmDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl =  MoziConfiguration.getValeu("db.url");
    }

    public static FilmDAO getInstance() {
        if(instance == null){
            instance = new FilmDAOImpl();
        }
        return instance;
    }


    @Override
    public List<Film> osszes() {
        List<Film> result =new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet  rs = stm.executeQuery(SELECT_ALL_FILMEK);
        ){
            while(rs.next()){
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setCim(rs.getString("cim"));
                film.setHossz(rs.getInt("hossz"));
                film.setKorhatar(rs.getInt("korhatar"));
                film.setRendező(rs.getString("rendezo"));
                film.setLeiras(rs.getString("leiras"));
                film.setBoritoUrl(rs.getString("boritoUrl"));
                film.setElozetesUrl(rs.getString("elozetesUrl"));
                List<Vetites> vetites=vetitesDAO.osszesFilmId(film.getId());
                film.setVetitesek(FXCollections.observableList(vetites));
                List<Szereplo> szereplok=szereploDAO.osszesByFilmId(film);
                film.setSzereplok(FXCollections.observableList(szereplok));
                result.add(film);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Film> osszesAktualis() {
        List<Film> result =new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet  rs = stm.executeQuery(SELECT_ALL_FILMEK);
        ){
            while(rs.next()){
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setCim(rs.getString("cim"));
                film.setHossz(rs.getInt("hossz"));
                film.setKorhatar(rs.getInt("korhatar"));
                film.setRendező(rs.getString("rendezo"));
                film.setLeiras(rs.getString("leiras"));
                film.setBoritoUrl(rs.getString("boritoUrl"));
                film.setElozetesUrl(rs.getString("elozetesUrl"));
                VetitesDAO vetitesDAO=new VetitesDAOImpl();
                List<Vetites> vetites= vetitesDAO.osszesAktualisFilmId(film.getId());
                if(vetites.size() !=0){
                    result.add(film);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Film FilmById(int id) {
        Film film = new Film();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(SELECT_FILM_BY_ID);
        ){
            stmt.setInt(1,id);
            ResultSet  rs = stmt.executeQuery();
            if(rs.next()){
                film.setId(rs.getInt("id"));
                film.setCim(rs.getString("cim"));
                film.setHossz(rs.getInt("hossz"));
                film.setKorhatar(rs.getInt("korhatar"));
                film.setRendező(rs.getString("rendezo"));
                film.setLeiras(rs.getString("leiras"));
                film.setBoritoUrl(rs.getString("boritoUrl"));
                film.setElozetesUrl(rs.getString("elozetesUrl"));

                List<Szereplo> szereplok=szereploDAO.osszesByFilmId(film);
                film.setSzereplok(FXCollections.observableList(szereplok));
                return film;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return film;
    }

    @Override
    public Film mentes(Film film) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= film.getId() <= 0 ? c.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_FILM)
        ) {
            if(film.getId() > 0){
                stmt.setInt(8, film.getId());
            }
            stmt.setString(1, film.getCim());
            stmt.setInt(2, film.getHossz());
            stmt.setInt(3, film.getKorhatar());
            stmt.setString(4, film.getRendező());
            stmt.setString(5, film.getLeiras());
            stmt.setString(6, film.getBoritoUrl());
            stmt.setString(7, film.getElozetesUrl());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }
            if(film.getId() <= 0){ // INSERT
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    film.setId(genKeys.getInt(1));
                }
            }
            for(Vetites vet : film.getVetitesek()){
                vetitesDAO.mentes(vet,film.getId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return film;
    }

    @Override
    public void torles(Film film) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_FILM);
        ){
            for(Vetites vet : film.getVetitesek()){
                vetitesDAO.torles(vet);
            }
            for(Szereplo szereplo : film.getSzereplok()){
                szereploDAO.torles(szereplo);
            }
            stmt.setInt(1,film.getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
