package lk.pahana_billing_system.controller;

import lk.pahana_billing_system.model.User;
import lk.pahana_billing_system.service.AuthService;
import lk.pahana_billing_system.service.impl.AuthServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{
    private final AuthService authService = new AuthServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                User user = authService.authenticateUser(username, password);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/customers");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "An error occurred during login. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else if ("/login".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
