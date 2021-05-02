package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Foglalas;
import hu.alkfejl.model.Helyek;
import hu.alkfejl.model.Vetites;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelyDAOImpl implements  HelyDAO{
    private static final String SELECT_HELYEK_BY_ID = "SELECT * FROM helyek where foglalasID=?";
    private static final String INSERT_HELYEK = "INSERT INTO helyek (foglalasId,hely,terem_id,vetites_id) values (?,?,?,?)";
    private static final String DELETE_HELY = "DELETE FROM helyek where foglalasID=?";
    private static final String SELECT_HELYEK_BY_TEREM_ID = "SELECT * FROM helyek where terem_id=? AND vetites_id=?";

    private String connectionUrl;

    public HelyDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl =  MoziConfiguration.getValeu("db.url");
    }

    @Override
    public List<Integer> helyekByFoglalasId(int foglalasId) {
        List<Integer> result = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(SELECT_HELYEK_BY_ID);
        ){
        stmt.setInt(1,foglalasId);
            ResultSet rs2=stmt.executeQuery();
            while(rs2.next()){
                result.add(rs2.getInt("hely"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Integer> helyekByteremId(int teremId,int vetitesId) {
        List<Integer> result = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = c.prepareStatement(SELECT_HELYEK_BY_TEREM_ID);
        ){
            stmt.setInt(1,teremId);
            stmt.setInt(2,vetitesId);
            ResultSet rs2=stmt.executeQuery();
            while(rs2.next()){
                result.add(rs2.getInt("hely"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Helyek mentes(Helyek hely) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt2 = c.prepareStatement(INSERT_HELYEK);
        ) {
                stmt2.setInt(1,hely.getFoglalasId());
                stmt2.setInt(2,hely.getHely());
                stmt2.setInt(3,hely.getTeremId());
                stmt2.setInt(4,hely.getVetitesId());
                stmt2.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return hely;
    }


    @Override
    public void torles(int foglalasId) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt2= c.prepareStatement(DELETE_HELY);
        ){
            stmt2.setInt(1,foglalasId);
            stmt2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
