package lk.pahana_billing_system.controller;

import com.google.gson.Gson;
import lk.pahana_billing_system.dto.CustomerDTO;
import lk.pahana_billing_system.model.Customer;
import lk.pahana_billing_system.service.CustomerService;
import lk.pahana_billing_system.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CustomerController", urlPatterns = {
        "/customers",
        "/customers/add",
        "/customers/edit",
        "/customers/delete"
})
public class CustomerController extends HttpServlet{
    private final CustomerService customerService = new CustomerServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/customers/add":
                    showAddForm(request, response);
                    break;
                case "/customers/edit":
                    showEditForm(request, response);
                    break;
                case "/customers/delete":
                    deleteCustomer(request, response);
                    break;
                case "/customers":
                default:
                    listCustomers(request, response);
                    break;
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/customers/add":
                    addCustomer(request, response);
                    break;
                case "/customers/edit":
                    updateCustomer(request, response);
                    break;
                default:
                    listCustomers(request, response);
                    break;
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        out.write(gson.toJson(new HashMap<String, List<CustomerDTO>>() {{ put("customers", customers); }}));
        out.flush();
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not used by React frontend, but kept for compatibility
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Add form not supported in JSON API");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        Customer existingCustomer = customerService.getCustomerDetails(id);
        if (existingCustomer != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(existingCustomer));
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Customer not found\"}");
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID().toString().substring(0, 6));
        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setAddress(request.getParameter("address"));
        customer.setAccountNo(request.getParameter("accountNo"));

        boolean isAdded = customerService.registerCustomer(customer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (isAdded) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Customer added successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to add customer\"}");
        }
        out.flush();
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setAddress(request.getParameter("address"));
        customer.setAccountNo(request.getParameter("accountNo"));

        boolean isUpdated = customerService.updateCustomerDetails(customer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (isUpdated) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Customer updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to update customer\"}");
        }
        out.flush();
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        boolean isDeleted = customerService.removeCustomer(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Customer deleted successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to delete customer\"}");
        }
        out.flush();
    }
}
