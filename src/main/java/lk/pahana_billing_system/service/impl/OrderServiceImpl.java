package lk.pahana_billing_system.service.impl;

import lk.pahana_billing_system.dao.ItemDAO;
import lk.pahana_billing_system.dao.OrderDAO;
import lk.pahana_billing_system.dao.impl.ItemDAOImpl;
import lk.pahana_billing_system.dao.impl.OrderDAOImpl;
import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import lk.pahana_billing_system.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean placeOrder(Order order, List<OrderDetail> orderDetails) throws SQLException {
        boolean orderAdded = orderDAO.addOrder(order);
        if (orderAdded) {
            for (OrderDetail detail : orderDetails) {
                if (!orderDAO.addOrderDetail(detail)) {
                    throw new SQLException("Failed to add order detail for item " + detail.getItemCode());
                }
                Item item = itemDAO.getItemByCode(detail.getItemCode());
                if (item != null) {
                    item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
                    itemDAO.updateItem(item);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    @Override
    public double generateBill(String orderId) throws SQLException {
        double totalAmount = 0.0;
        List<OrderDetail> details = orderDAO.getOrderDetailsByOrderId(orderId);
        for(OrderDetail detail : details) {
            totalAmount += detail.getQty() * detail.getUnitPrice();
        }
        return totalAmount;
    }

    @Override
    public List<OrderDetail> getOrderDetails(String orderId) throws SQLException {
        return orderDAO.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public Order getOrderById(String orderId) throws SQLException {
        return orderDAO.getOrderById(orderId);
    }
}
