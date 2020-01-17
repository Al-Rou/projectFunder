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
import de.unidue.inf.is.stores.UserStore;


public final class ViewProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Projekt> projektList1 = new ArrayList<>();
    private static List<Projekt> projektList2 = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProjektStore projektStore = new ProjektStore();
        projektList1 = projektStore.findenErstellteProjekteVon("dummy@dummy.com");
        projektList2 = projektStore.findenUnterstuezteProjekteVon("dummy@dummy.com");
        request.setAttribute("aufprojekte", projektList1);
        request.setAttribute("zuprojekte", projektList2);

        int erstAnzahl = projektStore.findenAnzahlErstellteProjekteVon("dummy@dummy.com");
        int unteAnzahl = projektStore.findenAnzahlUnterstuezteProjekteVon("dummy@dummy.com");
        String erstAnzahlStr = Integer.toString(erstAnzahl);
        String unteAnzahlStr = Integer.toString(unteAnzahl);
        request.setAttribute("kontoinhab", "dummy@dummy.com");
        request.setAttribute("anzahlerst", erstAnzahlStr);
        request.setAttribute("anzahlunte", unteAnzahlStr);

        request.getRequestDispatcher("/view_profile.ftl").forward(request, response);
    }
}
