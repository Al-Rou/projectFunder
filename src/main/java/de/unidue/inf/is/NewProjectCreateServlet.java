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

    private static List<User> userList = new ArrayList<>();

    // Just prepare static data to display on screen
    static {
        userList.add(new User("Bill", "Gates",
                "bill@gates.com","The richest"));
        userList.add(new User("Steve", "Jobs",
                "steve@jobs.com","Now dead"));
        userList.add(new User("Larry", "Page",
                "larry@page.com",""));
        userList.add(new User("Sergey", "Brin",
                "sergey@brin.com","Idk!"));
        userList.add(new User("Larry", "Ellison",
                "larry@ellison.com",""));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("users", userList);

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