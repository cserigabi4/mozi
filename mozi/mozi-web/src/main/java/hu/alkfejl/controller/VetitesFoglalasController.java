package hu.alkfejl.controller;

import hu.alkfejl.dao.*;
import hu.alkfejl.model.*;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/VetitesFoglalasController")
public class VetitesFoglalasController extends HttpServlet {
    VetitesDAO vetitesDAO=new VetitesDAOImpl();
    TeremDAO teremDAO=new TeremDAOImpl();
    FoglalasDao foglalasDAO=new FoglalasDaoImpl();
    HelyDAO helyDAO = new HelyDAOImpl();
    Terem terem;
    Vetites vetites;
    Foglalas foglalas =new Foglalas();

    public VetitesFoglalasController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vetitesIdStr = req.getParameter("vetitesId");
        String filmCim = req.getParameter("film");
        String foglalasIdStr = req.getParameter("foglalasId");

        if(foglalasIdStr != null && !foglalasIdStr.isEmpty()){
            int foglalasId = Integer.parseInt(foglalasIdStr);
            foglalas.setId(foglalasId);
        }
        if(foglalasIdStr == null){
            foglalas.setId(0);
        }

        if(vetitesIdStr != null && !vetitesIdStr.isEmpty()){
            int vetitesId = Integer.parseInt(vetitesIdStr);
            vetites = vetitesDAO.vetitesById(vetitesId);
            terem = teremDAO.teremById(vetites.getTerem());
            List<Integer> helyek = new ArrayList<>();
            helyek = helyDAO.helyekByteremId(terem.getId(),vetites.getId());
            req.setAttribute("helyek", helyek);
            req.setAttribute("terem", terem);
        }

        req.setAttribute("film", filmCim);
        req.getRequestDispatcher("pages/foglalas.jsp").forward(req, resp);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        FoglalasDao foglalasDAO=new FoglalasDaoImpl();
        List<Integer> helyek=new ArrayList<>();
        int x=0;
        for(int i=1;i<=terem.getSorokSzama();i++){
            for(int j=1;j<=terem.getOszlopokSzama();j++){
                x++;
                String s= String.valueOf(x);
                if(request.getParameter(s) !=null){
                    helyek.add(x);
                }
            }
        }


        foglalas.setVetitesId(vetites.getId());
        Felhasznalo felhasznalo=new Felhasznalo();
        felhasznalo= (Felhasznalo) request.getSession().getAttribute("felhasznalo");
        foglalas.setFelhasznaloId(felhasznalo.getId());
        foglalas.setHelyek(helyek);
        foglalas.setAr(vetites.getAr()*helyek.size());
        foglalasDAO.mentes(foglalas);

        response.sendRedirect("FoglalasSzerkesztesController");
    }

}
