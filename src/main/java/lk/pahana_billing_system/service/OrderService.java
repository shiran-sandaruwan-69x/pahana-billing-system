package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import java.util.List;
import java.sql.SQLException;

public interface OrderService {
    boolean placeOrder(Order order, List<OrderDetail> orderDetails) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    double generateBill(String orderId) throws SQLException;
    List<OrderDetail> getOrderDetails(String orderId) throws SQLException;
    Order getOrderById(String orderId) throws SQLException;
}
