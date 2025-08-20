package lk.pahana_billing_system.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lk.pahana_billing_system.dto.*;
import lk.pahana_billing_system.model.Customer;
import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import lk.pahana_billing_system.service.CustomerService;
import lk.pahana_billing_system.service.ItemService;
import lk.pahana_billing_system.service.OrderService;
import lk.pahana_billing_system.service.impl.CustomerServiceImpl;
import lk.pahana_billing_system.service.impl.ItemServiceImpl;
import lk.pahana_billing_system.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "OrderController", urlPatterns = {
        "/orders",
        "/orders/place",
        "/orders/bill"
})
public class OrderController extends HttpServlet{
    private final OrderService orderService = new OrderServiceImpl();
    private final CustomerService customerService = new CustomerServiceImpl();
    private final ItemService itemService = new ItemServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/orders":
                    listOrders(request, response);
                    break;
                case "/orders/bill":
                    generateBillJson(request, response);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            if ("/orders/place".equals(action)) {
                placeOrderJson(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        List<Order> orders = orderService.getAllOrders();
        if (orders == null) {
            orders = Collections.emptyList();
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("orders", orders);

        response.setStatus(HttpServletResponse.SC_OK);
        writeJson(response, responseMap);
    }

    private void placeOrderJson(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        Type orderPayloadType = new TypeToken<OrderPayloadDTO>(){}.getType();
        OrderPayloadDTO payload = gson.fromJson(sb.toString(), orderPayloadType);

        String customerId = payload.getCustomerId();
        List<OrderDetailDTO> orderDetailDTOs = payload.getOrderDetails();

        String orderId = UUID.randomUUID().toString().substring(0, 6);
        java.util.Date utilDate = new java.util.Date();
        Date sqlDate = new Date(utilDate.getTime());

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(customerId);
        order.setDate(sqlDate);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO dto : orderDetailDTOs) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setItemCode(dto.getItemCode());
            orderDetail.setQty(dto.getQty());
            orderDetail.setUnitPrice(dto.getUnitPrice());
            orderDetails.add(orderDetail);
        }

        boolean success = orderService.placeOrder(order, orderDetails);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write(gson.toJson(new OrderPlacementResponse(orderId)));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Failed to place order.\"}");
        }
        out.flush();
    }

    private void generateBillJson(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String orderId = request.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Order ID is required.\"}");
            out.flush();
            return;
        }

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\": \"Order not found.\"}");
            out.flush();
            return;
        }

        Customer customer = customerService.getCustomerDetails(order.getCustomerId());
        String customerName = (customer != null) ? customer.getName() : "Unknown Customer";

        List<OrderDetail> details = orderService.getOrderDetails(orderId);
        List<OrderDetailResponseDTO> orderDetailsResponse = new ArrayList<>();
        double fullTotal = 0;

        for (OrderDetail detail : details) {
            OrderDetailResponseDTO detailDTO = new OrderDetailResponseDTO();
            detailDTO.setItemNo(detail.getItemCode());
            detailDTO.setItemPrice(detail.getUnitPrice());
            detailDTO.setQty(detail.getQty());
            double price = detail.getUnitPrice() * detail.getQty();
            detailDTO.setPrice(price);
            orderDetailsResponse.add(detailDTO);
            fullTotal += price;
        }

        BillResponseDTO bill = new BillResponseDTO();
        bill.setCustomerName(customerName);
        bill.setOrderNo(orderId);
        bill.setOrderDetails(orderDetailsResponse);
        bill.setFullTotal(fullTotal);

        response.setStatus(HttpServletResponse.SC_OK);
        out.write(gson.toJson(bill));
        out.flush();
    }

    private void writeJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(data));
        out.flush();
    }

}
