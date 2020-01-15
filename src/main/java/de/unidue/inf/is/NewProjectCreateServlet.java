package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.ProjektStore;


public final class NewProjectCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String email;
    private static List<String> vorgaengerList = new ArrayList<>();
    private static ProjektStore projektStore = new ProjektStore();

    public void setEmail(String e)
    {
        email = e;
    }
    public String getEmail(){
        return email;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        vorgaengerList = projektStore.vorgaengerList(getEmail());
        request.setAttribute("vorprojekte", vorgaengerList);

        request.getRequestDispatcher("/new_project.ftl").forward(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String titel = request.getParameter("titel");
        String finanzLimitStr = request.getParameter("amount");
        String email = request.getParameter("email");
        String kategorie = request.getParameter("group");
        String vorg = request.getParameter("version");
        String explanation = request.getParameter("explanation");
        Integer vorgInt;
        Integer katInt;
        Double finanzLimit = Double.parseDouble(finanzLimitStr);
        if (vorg.equalsIgnoreCase("kein vorg"))
        {
            vorgInt = null;
        }
        else
        {
            vorgInt = 1;
        }
        if (kategorie.contains("Health"))
        {
            katInt = 1;
        }
        else if (kategorie.contains("Art"))
        {
            katInt = 2;
        }
        else if (kategorie.contains("Education"))
        {
            katInt = 3;
        }
        else
        {
            katInt = 4;
        }
        if (null != titel && null != finanzLimitStr
                && finanzLimit > 100) {

            ProjektStore projektStore = new ProjektStore();
            Integer neuKennung = projektStore.findenLetzteKennung()+1;
            Projekt neuProjekt = new Projekt(neuKennung,titel,explanation,
                    finanzLimit,"offen",email,vorgInt,katInt);
            projektStore.addProjekt(neuProjekt);
        }
        doGet(request, response);
    }
}