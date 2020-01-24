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
import de.unidue.inf.is.utils.DBUtil;


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
        String sicht = "oeffentlich";
        String sichtWahl = request.getParameter("version");
        if (sichtWahl != null)
        {
            sicht = "privat";
        }
        String betrag = request.getParameter("spendenbetrag");
        if (betrag.isEmpty())
        {
            errorMessage = "Geben Sie bitte eine Zahl ein!";
            doGet(request, response);
        }
        else {
            Double betragZahl = Double.parseDouble(betrag);
            List<Double> checkGuthaben = projektStore.findenGuthaben(DBUtil.derBenutzer);
            if (checkGuthaben == null) {
                errorMessage = "Sie haben wohl kein Konto!";
                doGet(request, response);
            } else {
                Double unterschied = null;
                if (betragZahl <= 0.00) {
                    errorMessage = "Nicht lustig! Bitte geben Sie einen echten Betrag ein!";
                    doGet(request, response);
                } else {
                    unterschied = checkGuthaben.get(0) - betragZahl;
                    if (unterschied < 0) {
                        errorMessage = "Sie haben nicht genug Guthaben!";
                        doGet(request, response);
                    } else {
                        Spenden neuSpenden = new Spenden(kenn,
                                DBUtil.derBenutzer,
                                betragZahl, sicht);
                        if (projektStore.wennSpenderExistiert(DBUtil.derBenutzer))
                        {
                            List<Double> existierterBetrag = projektStore.findenSpendenbetragVomProjektMitEmail(kenn, DBUtil.derBenutzer);
                            if (existierterBetrag != null) {
                                Double neuBetrag = existierterBetrag.get(0) + betragZahl;
                                projektStore.updateSpendenbetragVomProjektMitEmail(neuBetrag, kenn, DBUtil.derBenutzer);
                                projektStore.reduzierenGuthaben(DBUtil.derBenutzer, unterschied);
                                errorMessage = "Erfolg beim Spenden!";
                                doGet(request, response);
                            } else {
                                errorMessage = "Etwas ist falsch!!";
                                doGet(request, response);
                            }
                        }
                        else
                        {
                            projektStore.addSpenden(neuSpenden);
                            projektStore.reduzierenGuthaben(DBUtil.derBenutzer, unterschied);
                            errorMessage = "Erfolg beim Spenden!";
                            doGet(request, response);
                        }
                    }
                }
            }
        }
    }
}