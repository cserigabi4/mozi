package hu.alkfejl.controller;

import hu.alkfejl.dao.*;
import hu.alkfejl.model.Film;
import hu.alkfejl.model.Szereplo;
import hu.alkfejl.model.Vetites;
import javafx.collections.ObservableList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/FilmLeirasController")
public class FilmLeirasController extends HttpServlet {
    private FilmDAO dao = FilmDAOImpl.getInstance();
    private VetitesDAO vetitesdao = new VetitesDAOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmIdStr = req.getParameter("filmId");
        if(filmIdStr != null && !filmIdStr.isEmpty()){
            int filmId = Integer.parseInt(filmIdStr);
            List<Vetites> vetitesek = vetitesdao.osszesAktualisFilmId(filmId);
            Film film = dao.FilmById(filmId);

         // film.setSzereplok((ObservableList<Szereplo>) szereplo);
           // System.out.println(szereplo);
            req.setAttribute("film", film);
            req.setAttribute("vetites", vetitesek);
           // req.setAttribute("szereplo", szereplo);
        }
    }
}