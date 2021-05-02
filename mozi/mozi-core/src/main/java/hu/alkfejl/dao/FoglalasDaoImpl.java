package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Foglalas;
import hu.alkfejl.model.Helyek;
import hu.alkfejl.model.Vetites;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FoglalasDaoImpl implements FoglalasDao {
    private static final String INSERT_FOGLALAS = "INSERT INTO foglalas (felhasznaloId,vetitesId,ar) values (?,?,?)";
    private static final String UPDATE_FOGLALAS = "UPDATE foglalas SET ar=? WHERE id=?";
    private static final String SELECT_FOGLALAS_BY_ID = "SELECT * FROM foglalas where felhasznaloId=?";
    private static final String DELETE_FOGLALAS = "DELETE FROM foglalas where id=?";
    private static final String SELECT_ALL_FOGLALAS = "SELECT * FROM foglalas";

    private String connectionUrl;
    VetitesDAO vetitesDAO = new VetitesDAOImpl();
    FilmDAO filmDAO = new FilmDAOImpl();
    HelyDAO helyDAO = new HelyDAOImpl();

    public FoglalasDaoImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl =  MoziConfiguration.getValeu("db.url");
    }

    @Override
    public List<Foglalas> osszes(){
        List<Foglalas> result =new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet  rs = stm.executeQuery(SELECT_ALL_FOGLALAS);
        ){
            while(rs.next()){
                Foglalas foglalas = new Foglalas();
                foglalas.setId(rs.getInt("id"));
                foglalas.setFelhasznaloId(rs.getInt("felhasznaloId"));
                foglalas.setVetitesId(rs.getInt("vetitesId"));
                foglalas.setAr(rs.getInt("ar"));
                List<Integer> helyek = new ArrayList<>();
                helyek= helyDAO.helyekByFoglalasId(foglalas.getId());
                foglalas.setHelyek(helyek);
                Vetites vetites=new Vetites();
                vetites=vetitesDAO.vetitesById(foglalas.getVetitesId());
                foglalas.setVetites(vetites);
                Film film=new Film();
                film=filmDAO.FilmById(vetites.getFilm());
                foglalas.setFilm(film);
                result.add(foglalas);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Foglalas> osszesFoglalasById(int felhasznaloID) {
        List<Foglalas> result = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_FOGLALAS_BY_ID);
        ){
            statement.setInt(1,felhasznaloID);
            ResultSet rs=statement.executeQuery();

            while(rs.next()){
                Foglalas foglalas = new Foglalas();
                foglalas.setId(rs.getInt("id"));
                foglalas.setFelhasznaloId(rs.getInt("felhasznaloId"));
                foglalas.setVetitesId(rs.getInt("vetitesId"));
                foglalas.setAr(rs.getInt("ar"));
                List<Integer> helyek = new ArrayList<>();
                helyek= helyDAO.helyekByFoglalasId(foglalas.getId());
                foglalas.setHelyek(helyek);
                Vetites vetites=new Vetites();
                vetites=vetitesDAO.vetitesById(foglalas.getVetitesId());
                foglalas.setVetites(vetites);
                Film film=new Film();
                film=filmDAO.FilmById(vetites.getFilm());
                foglalas.setFilm(film);
                result.add(foglalas);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    @Override
    public Foglalas mentes(Foglalas foglalas) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= foglalas.getId() <= 0 ? c.prepareStatement(INSERT_FOGLALAS, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_FOGLALAS);
        ) {
            if(foglalas.getId() > 0){ //update
                stmt.setInt(1, foglalas.getAr());
                stmt.setInt(2, foglalas.getId());
                helyDAO.torles(foglalas.getId());
                for(Integer hely : foglalas.getHelyek()){
                    Helyek helyek =new Helyek();
                    helyek.setHely(hely);
                    helyek.setFoglalasId(foglalas.getId());
                    Vetites vetites = vetitesDAO.vetitesById(foglalas.getVetitesId());
                    int teremid = vetites.getTerem();
                    helyek.setTeremId(teremid);
                    helyek.setVetitesId(vetites.getId());
                    helyDAO.mentes(helyek);
                }
            }else {
                stmt.setInt(1, foglalas.getFelhasznaloId());
                stmt.setInt(2, foglalas.getVetitesId());
                stmt.setInt(3, foglalas.getAr());

                int affectedRows = stmt.executeUpdate();
                if(affectedRows == 0){
                    return null;
                }
                if(foglalas.getId() <= 0){ // INSERT
                    ResultSet genKeys = stmt.getGeneratedKeys();
                    if (genKeys.next()) {
                        foglalas.setId(genKeys.getInt(1));
                    }
                }
                for(Integer hely : foglalas.getHelyek()){
                    Helyek helyek =new Helyek();
                    helyek.setHely(hely);
                    helyek.setFoglalasId(foglalas.getId());
                    Vetites vetites = vetitesDAO.vetitesById(foglalas.getVetitesId());
                    int teremid = vetites.getTerem();
                    helyek.setTeremId(teremid);
                    helyek.setVetitesId(vetites.getId());
                    helyDAO.mentes(helyek);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return foglalas;
    }

    @Override
    public boolean torles(int foglalasId,int vetitesId) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_FOGLALAS);
        ){
            Vetites vetites = vetitesDAO.vetitesById(vetitesId);
            LocalDateTime date2 = LocalDateTime.now();
            LocalDateTime date3 = vetites.getDatum().atTime(vetites.getIdo(),00);
            if(date2.isBefore(date3.minusDays(1))){
                stmt.setInt(1,foglalasId);
                stmt.executeUpdate();
                helyDAO.torles(foglalasId);
                System.out.println(date2);
                System.out.println(date3);
                return true;
            }else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    @Override
    public void torlesDesktop(int foglalasId) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_FOGLALAS);
        ){
                stmt.setInt(1,foglalasId);
                stmt.executeUpdate();
                helyDAO.torles(foglalasId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
