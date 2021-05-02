package hu.alkfejl.dao;

import hu.alkfejl.config.MoziConfiguration;
import hu.alkfejl.model.Terem;
import hu.alkfejl.model.Vetites;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeremDAOImpl implements TeremDAO{
    private static final String SELECT_ALL_TERMEK = "SELECT * FROM Termek";
    private static final String SELECT_ALL_ID = "SELECT id FROM Termek";
    private static final String SELECT_TEREM_BY_ID = "SELECT * FROM Termek WHERE id=?";
    private static final String DELETE_TEREM = "DELETE FROM termek where id=?";
    private static final String INSERT_TEREM = "INSERT INTO termek (sorokSzama,oszlopokSzama) values (?,?)";
    private static final String UPDATE_TEREM = "UPDATE termek SET sorokSzama=?, oszlopokSzama=? WHERE id=? ";
    private String connectionUrl;

    public TeremDAOImpl() {
        this.connectionUrl = MoziConfiguration.getValeu("db.url");
    }
    @Override
    public List<Terem> osszes() {
        List<Terem> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_ALL_TERMEK);
        ){
            while(rs.next()){
                Terem terem = new Terem();
                terem.setId(rs.getInt("id"));
                terem.setSorokSzama(rs.getInt("sorokSzama"));
                terem.setOszlopokSzama(rs.getInt("oszlopokSzama"));
                result.add(terem);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Integer> osszesId(){
        List<Integer> result=new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_ALL_ID);
        ){
            while(rs.next()){
                Integer x = new Integer(rs.getInt("id"));
                result.add(x);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Terem teremById(int teremId){
        Terem terem=new Terem();
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = c.prepareStatement(SELECT_TEREM_BY_ID)
        ){
            statement.setInt(1,teremId);
            ResultSet rs=statement.executeQuery();
            terem.setId(rs.getInt("id"));
            terem.setSorokSzama(rs.getInt("sorokSzama"));
            terem.setOszlopokSzama(rs.getInt("oszlopokSzama"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return terem;
    }

    @Override
    public Terem mentes(Terem terem) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= terem.getId() <= 0 ? c.prepareStatement(INSERT_TEREM, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_TEREM)
        ) {
            if(terem.getId() > 0){
                stmt.setInt(3, terem.getId());
            }
            stmt.setInt(1, terem.getSorokSzama());
            stmt.setInt(2, terem.getOszlopokSzama());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }
            if(terem.getId() <= 0){
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    terem.setId(genKeys.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return terem;


    }

    @Override
    public void torles(Terem terem) {
        try(Connection c = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt= c.prepareStatement(DELETE_TEREM);
        ){
            stmt.setInt(1,terem.getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
