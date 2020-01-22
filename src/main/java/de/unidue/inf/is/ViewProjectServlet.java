package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.Spenden;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.ProjektStore;
import de.unidue.inf.is.stores.UserStore;


public final class ViewProjectServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Projekt> projektList = new ArrayList<>();
    private static ProjektStore projektStore = new ProjektStore();
    private static UserStore userStore = new UserStore();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String tit = "Ubuntu Touch Pro";
        Integer proKenn = Integer.parseInt(request.getParameter("kennung"));
        Projekt projekt = projektStore.findenProjektMitKennung(proKenn);
        request.setAttribute("projekte", projekt);

        //Double totalspend = projektStore.findenTotalSpendeVomProjekt(proKenn);
        //String totalspendStr = Double.toString(totalspend);

        /*List<Spenden> spendenList = projektStore.findenSpenderVomProjekt(proKenn);
        request.setAttribute("spendern", spendenList);


        HashMap<String,String> showKomment = userStore.werSagteWas(userStore.findenSchreibtVonProjekt(proKenn));
        request.setAttribute("kommente", showKomment);*/

        request.getRequestDispatcher("/view_project.ftl").forward(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        doGet(request, response);
    }
}