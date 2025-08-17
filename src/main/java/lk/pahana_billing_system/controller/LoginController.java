package lk.pahana_billing_system.controller;

import com.google.gson.Gson;
import lk.pahana_billing_system.dto.LoginRequest;
import lk.pahana_billing_system.dto.Response;
import lk.pahana_billing_system.model.User;
import lk.pahana_billing_system.service.AuthService;
import lk.pahana_billing_system.service.impl.AuthServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{
    private final AuthService authService = new AuthServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
            // Read JSON payload
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            // Parse JSON
            LoginRequest loginRequest = gson.fromJson(sb.toString(), LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            System.out.println("step 1 username"+ username + "password : "+ password);
            try {
                User user = authService.authenticateUser(username, password);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(gson.toJson(new Response("Login successful", true)));
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(gson.toJson(new Response("Invalid username or password", false)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(gson.toJson(new Response("An error occurred during login: " + e.getMessage(), false)));
            }
    }
}
