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

@WebServlet("/BejelentkezesController")
public class BejelentkezesController extends HttpServlet {
    public BejelentkezesController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
      //  System.out.println("itt");
        FelhasznaloDao felhasznaloDAO = FelhasznaloDaoImpl.getInstance();
        String felhasznalonev = request.getParameter("felhasznalonev");
        String jelszo = request.getParameter("jelszo");

        Felhasznalo felhasznalo = felhasznaloDAO.bejelentekes(felhasznalonev, jelszo);
        if( felhasznalo == null){
           request.setAttribute("hiba", "Hibás felhasználónév vagy jelszó!");
            request.getRequestDispatcher("pages/hiba.jsp").forward(request, response);

        }
        request.getSession().setAttribute("felhasznalo", felhasznalo);
        response.sendRedirect("index.jsp");
    }


}
