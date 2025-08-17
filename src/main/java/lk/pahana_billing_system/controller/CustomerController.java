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
import java.io.BufferedReader;

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

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        List<CustomerDTO> customers = customerService.getAllCustomers();

        if (customers == null) {
            customers = java.util.Collections.emptyList();
        }

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("customers", customers);

        String jsonResponse = gson.toJson(responseMap);
        out.write(jsonResponse);
        out.flush();
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Customer customer = gson.fromJson(sb.toString(), Customer.class);
        customer.setCustomerId(UUID.randomUUID().toString().substring(0, 6));

        boolean isAdded = customerService.registerCustomer(customer);

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
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Customer customer = gson.fromJson(sb.toString(), Customer.class);

        if (customer.getCustomerId() == null || customer.getCustomerId().trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        boolean isUpdated = customerService.updateCustomerDetails(customer);

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
