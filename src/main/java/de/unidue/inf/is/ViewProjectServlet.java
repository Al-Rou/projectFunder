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


public final class ViewProjectServlet extends HttpServlet {

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

        request.getRequestDispatcher("/view_project.ftl").forward(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String explanation = request.getParameter("explanation");



        if (null != firstname && null != lastname
                && null != email && !firstname.isEmpty()
                && !lastname.isEmpty() && !email.isEmpty()) {

            synchronized (projektList) {
                /*userList.add(new User(firstname, lastname,
                        email, explanation));*/
            }

        }

        doGet(request, response);
    }
}