package lk.pahana_billing_system.dao;

import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    boolean addOrder(Order order) throws SQLException;
    boolean addOrderDetail(OrderDetail orderDetail) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    Order getOrderById(String orderId) throws SQLException;
    List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws SQLException;
}
