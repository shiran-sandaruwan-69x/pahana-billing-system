package lk.pahana_billing_system.dao.impl;

import lk.pahana_billing_system.dao.OrderDAO;
import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import lk.pahana_billing_system.utility.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (orderId, date, customerId, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getOrderId());
            stmt.setDate(2, order.getDate());
            stmt.setString(3, order.getCustomerId());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderDetail (orderId, itemCode, qty, unitPrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderDetail.getOrderId());
            stmt.setString(2, orderDetail.getItemCode());
            stmt.setInt(3, orderDetail.getQty());
            stmt.setDouble(4, orderDetail.getUnitPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.orderId, o.date, o.customerId, c.name AS customerName " +
                "FROM Orders o " +
                "JOIN Customer c ON o.customerId = c.customerId " +
                "ORDER BY o.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getString("orderId"));
                order.setDate(rs.getDate("date"));
                order.setCustomerId(rs.getString("customerId"));
                order.setCustomerName(rs.getString("customerName"));
               // order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order getOrderById(String orderId) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE orderId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getString("orderId"));
                    order.setDate(rs.getDate("date"));
                    order.setCustomerId(rs.getString("customerId"));
                    return order;
                }
            }
        }
        return null;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws SQLException {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetail WHERE orderId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderId(rs.getString("orderId"));
                    detail.setItemCode(rs.getString("itemCode"));
                    detail.setQty(rs.getInt("qty"));
                    detail.setUnitPrice(rs.getDouble("unitPrice"));
                    details.add(detail);
                }
            }
        }
        return details;
    }
}
