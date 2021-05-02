package hu.alkfejl.controller;

import hu.alkfejl.dao.FoglalasDao;
import hu.alkfejl.dao.FoglalasDaoImpl;
import hu.alkfejl.model.Felhasznalo;
import hu.alkfejl.model.Foglalas;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FoglalasSzerkesztesController")
public class FoglalasSzerkesztesController extends HttpServlet {
    FoglalasDao foglalasDAO=new FoglalasDaoImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Felhasznalo felhasznalo=new Felhasznalo();
        felhasznalo= (Felhasznalo) request.getSession().getAttribute("felhasznalo");

        List<Foglalas> foglalasok = new ArrayList<>();
        foglalasok=foglalasDAO.osszesFoglalasById(felhasznalo.getId());
        request.setAttribute("foglalasok", foglalasok);
       if(request.getParameter("hiba") != null){
           request.setAttribute("hiba", request.getParameter("hiba"));
       }else{
           request.setAttribute("hiba", null);
       }
        request.getRequestDispatcher("pages/foglalas-szerkesztes.jsp").forward(request, response);
    }
}
