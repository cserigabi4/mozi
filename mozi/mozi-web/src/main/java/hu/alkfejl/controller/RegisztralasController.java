package hu.alkfejl.controller;

import hu.alkfejl.dao.FelhasznaloDao;
import hu.alkfejl.dao.FelhasznaloDaoImpl;
import hu.alkfejl.model.Felhasznalo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisztralasController")
public class RegisztralasController extends HttpServlet {
    FelhasznaloDao dao = FelhasznaloDaoImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        Felhasznalo user = new Felhasznalo();
        user.setNev(req.getParameter("nev"));
        user.setFelhasznalonev(req.getParameter("felhasznalonev"));
        user.setJelszo(req.getParameter("jelszo"));
        dao.mentes(user);
        resp.sendRedirect("pages/bejelentkezes.jsp");
    }
}
