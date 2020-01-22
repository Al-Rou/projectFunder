package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.Spenden;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.ProjektStore;


public final class NewProjectFundServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Projekt> projektList = new ArrayList<>();
    private static ProjektStore projektStore = new ProjektStore();
    private static String errorMessage = "";
    private static String tas;
    private static Integer kenn = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tas = null;

        tas = request.getParameter("kennung");
        kenn = Integer.parseInt(tas);
        projektList = projektStore.findenProjektMitKennung(kenn);
        request.setAttribute("projekte", projektList);
        request.setAttribute("tashere", tas);
        request.setAttribute("error", errorMessage);

        request.getRequestDispatcher("/new_project_fund.ftl").forward(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String betrag = request.getParameter("spendenbetrag");
        if ((betrag == null) || (betrag.isEmpty()))
        {
            errorMessage = "Geben Sie bitte eine Zahl ein!";
            doGet(request, response);
        }
        Double betragZahl = Double.parseDouble(betrag);
        if (betragZahl == 0.00)
        {
            errorMessage = "Nicht luestig! Bitte geben Sie einen echten Betrag ein!";
            doGet(request, response);
        }
        String sicht;
        String sichtWahl = request.getParameter("version");
        if (sichtWahl.equalsIgnoreCase("Anonym"))
        {
            sicht = "privat";
        }
        else if ((sichtWahl == null) || (sichtWahl.equalsIgnoreCase("")))
        {
            sicht = "oeffentlich";
        }
        else
        {
            sicht = "oeffentlich";
        }
        Spenden neuSpenden = new Spenden(kenn,
                "dummy@dummy.com",
                betragZahl, sicht);
        projektStore.addSpenden(neuSpenden);
        errorMessage = "Erfolg beim Spenden!";
        doGet(request, response);
    }
}