package lk.pahana_billing_system.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CustomerController", urlPatterns = {
        "/customers",
        "/customers/add",
        "/customers/edit",
        "/customers/delete"
})
public class CustomerController extends HttpServlet{
}
