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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tit = "Ubuntu Touch Pro";
        request.setAttribute("protitel", tit);
        request.setAttribute("proersteller", "dummy@dummy.com");
        ProjektStore projektStore = new ProjektStore();
        String beschreib = projektStore.findenBeschreibungVon(tit);
        request.setAttribute("probeschreibung", beschreib);
        Double finan = projektStore.findenFinanzierungsLimitVon(tit);
        String finanStr = Double.toString(finan);
        request.setAttribute("profinanzierungslimit", finanStr);
        Double totalspend = projektStore.findenTotalSpendeVomProjekt(projektStore.findenKennungVon(tit));
        String totalspendStr = Double.toString(totalspend);
        request.setAttribute("prospend", totalspendStr);
        String statu = projektStore.findenStatusVomProjekt(projektStore.findenKennungVon(tit));
        request.setAttribute("prostat", statu);
        request.setAttribute("provorgaenger", "Ubuntu Touch");

        List<Spenden> spendenList = projektStore.findenSpenderVomProjekt(projektStore.findenKennungVon(tit));
        request.setAttribute("spendern", spendenList);

        UserStore userStore = new UserStore();
        HashMap<String,String> showKomment = userStore.werSagteWas(userStore.findenSchreibtVonProjekt(projektStore.findenKennungVon(tit)));
        request.setAttribute("kommente", showKomment);

        request.getRequestDispatcher("/view_project.ftl").forward(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        doGet(request, response);
    }
}