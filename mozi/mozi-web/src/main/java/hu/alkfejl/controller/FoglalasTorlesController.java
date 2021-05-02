package hu.alkfejl.controller;

import hu.alkfejl.dao.FoglalasDao;
import hu.alkfejl.dao.FoglalasDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FoglalasTorlesController")
public class FoglalasTorlesController extends HttpServlet {
    FoglalasDao dao= new FoglalasDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        try {
            int foglalasID = Integer.parseInt(req.getParameter("foglalasId"));
            int vetitesId = Integer.parseInt(req.getParameter("vetitesId"));
            boolean torles=dao.torles(foglalasID,vetitesId);
            if(torles){
                resp.sendRedirect("FoglalasSzerkesztesController");
            }else {
                String s="Foglalast csak vetites elott 24 oraval lehet lemondani!";
                resp.sendRedirect("FoglalasSzerkesztesController?hiba="+s);
            }

        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
    }
}
