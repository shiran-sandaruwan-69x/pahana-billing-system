package lk.pahana_billing_system.controller;

import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.service.ItemService;
import lk.pahana_billing_system.service.impl.ItemServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ItemController", urlPatterns = {
        "/items",
        "/items/add",
        "/items/edit",
        "/items/delete"
})
public class ItemController extends HttpServlet{
    private final ItemService itemService = new ItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/items/add":
                    showAddForm(request, response);
                    break;
                case "/items/edit":
                    showEditForm(request, response);
                    break;
                case "/items/delete":
                    deleteItem(request, response);
                    break;
                case "/items":
                default:
                    listItems(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
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
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/items.jsp");
        dispatcher.forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/add_item.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String itemCode = request.getParameter("itemCode");
        Item item = itemService.getItemByCode(itemCode);
        request.setAttribute("item", item);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_item.jsp");
        dispatcher.forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Item item = new Item();
        item.setItemCode(request.getParameter("itemCode"));
        item.setDescription(request.getParameter("description"));
        item.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
        item.setQtyOnHand(Integer.parseInt(request.getParameter("qtyOnHand")));

        itemService.addItem(item);
        response.sendRedirect(request.getContextPath() + "/items");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Item item = new Item();
        item.setItemCode(request.getParameter("itemCode"));
        item.setDescription(request.getParameter("description"));
        item.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
        item.setQtyOnHand(Integer.parseInt(request.getParameter("qtyOnHand")));

        itemService.updateItem(item);
        response.sendRedirect(request.getContextPath() + "/items");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String itemCode = request.getParameter("itemCode");
        itemService.deleteItem(itemCode);
        response.sendRedirect(request.getContextPath() + "/items");
    }
}
