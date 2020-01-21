package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.ProjektStore;
import de.unidue.inf.is.stores.StoreException;
import de.unidue.inf.is.utils.DBUtil;


public final class MainPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private List<Projekt> projektList1 = new ArrayList<>();
    private List<Projekt> projektList2 = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProjektStore projektStore = new ProjektStore();
        projektList1 = projektStore.findenOffeneProjekte();
        projektList2 = projektStore.findenAbgeschlosseneProjekte();
        request.setAttribute("aufprojekte", projektList1);
        request.setAttribute("zuprojekte", projektList2);

        request.getRequestDispatcher("/view_main.ftl").forward(request, response);
    }
}