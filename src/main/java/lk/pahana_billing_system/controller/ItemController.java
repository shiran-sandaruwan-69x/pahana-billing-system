package lk.pahana_billing_system.controller;

import com.google.gson.Gson;
import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.service.ItemService;
import lk.pahana_billing_system.service.impl.ItemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

@WebServlet(name = "ItemController", urlPatterns = {
        "/items",
        "/items/add",
        "/items/edit",
        "/items/delete"
})
public class ItemController extends HttpServlet{
    private final ItemService itemService = new ItemServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/items/delete":
                    deleteItem(request, response);
                    break;
                case "/items":
                default:
                    listItems(request, response);
                    break;
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/items/add":
                    addItem(request, response);
                    break;
                case "/items/edit":
                    updateItem(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/items");
                    break;
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        List<Item> items = itemService.getAllItems();
        if (items == null) {
            items = Collections.emptyList();
        }

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("items", items);

        String jsonResponse = gson.toJson(responseMap);
        out.write(jsonResponse);
        out.flush();
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Item item = gson.fromJson(sb.toString(), Item.class);
        item.setItemCode(UUID.randomUUID().toString().substring(0, 6));
        System.out.println("item"+item.toString());
        boolean isAdded = itemService.addItem(item);

        PrintWriter out = response.getWriter();
        if (isAdded) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Item added successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to add item\"}");
        }
        out.flush();
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Item item = gson.fromJson(sb.toString(), Item.class);

        if (item.getItemCode() == null || item.getItemCode().trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid item code\"}");
            return;
        }

        boolean isUpdated = itemService.updateItem(item);

        PrintWriter out = response.getWriter();
        if (isUpdated) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Item updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to update item\"}");
        }
        out.flush();
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        System.out.println("delete item id" + id);
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid item ID\"}");
            return;
        }

        boolean isDeleted = itemService.deleteItem(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Item deleted successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to delete Item\"}");
        }
        out.flush();

    }
}
