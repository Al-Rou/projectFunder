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


public final class MainPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Projekt> projektList = new ArrayList<>();

    // Just prepare static data to display on screen
    static {
        projektList.add(new Projekt(1, "Ubuntu",
                "Es ist OS!", 5000,
                false,
                "dummy@dummy.com", 1,
                4));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("aufprojekte", projektList);
        request.setAttribute("zuprojekte", projektList);

        request.getRequestDispatcher("/view_main.ftl").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String firstname = request.getParameter("projekttitel");
        //long lastname = request.getParameter(limit)
        String email = request.getParameter("ersteller");
        String explanation = request.getParameter("projektbeschreibung");


        if (null != firstname
                && null != email && !firstname.isEmpty()
                && !email.isEmpty()) {

            synchronized (projektList) {
                projektList.add(new Projekt(2, firstname,
                        explanation, 1000, false,
                        email, 3, 3));
            }

        }

        doGet(request, response);
    }
}