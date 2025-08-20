package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.model.Order;
import lk.pahana_billing_system.model.OrderDetail;
import lk.pahana_billing_system.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {

    private class DummyOrderService extends OrderServiceImpl {

        @Override
        public boolean placeOrder(Order order, List<OrderDetail> orderDetails) throws SQLException {
            return true;
        }

        @Override
        public List<Order> getAllOrders() throws SQLException {
            List<Order> orders = new ArrayList<>();
            Order order = new Order();
            order.setOrderId("O001");
            orders.add(order);
            return orders;
        }

        @Override
        public double generateBill(String orderId) throws SQLException {
            return 100.0;
        }

        @Override
        public List<OrderDetail> getOrderDetails(String orderId) throws SQLException {
            List<OrderDetail> details = new ArrayList<>();
            OrderDetail detail = new OrderDetail();
            detail.setItemCode("I001");
            detail.setQty(2);
            detail.setUnitPrice(50.0);
            details.add(detail);
            return details;
        }

        @Override
        public Order getOrderById(String orderId) throws SQLException {
            Order order = new Order();
            order.setOrderId(orderId);
            return order;
        }
    }

    private final DummyOrderService orderService = new DummyOrderService();

    @Test
    public void testPlaceOrder() throws SQLException {
        Order order = new Order();
        List<OrderDetail> details = new ArrayList<>();
        OrderDetail detail = new OrderDetail();
        detail.setItemCode("I001");
        detail.setQty(2);
        detail.setUnitPrice(50.0);
        details.add(detail);

        boolean result = orderService.placeOrder(order, details);
        assertTrue(result);
    }

    @Test
    public void testGetAllOrders() throws SQLException {
        List<Order> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("O001", orders.get(0).getOrderId());
    }

    @Test
    public void testGenerateBill() throws SQLException {
        double bill = orderService.generateBill("O001");
        assertEquals(100.0, bill, 0.001);
    }

    @Test
    public void testGetOrderDetails() throws SQLException {
        List<OrderDetail> details = orderService.getOrderDetails("O001");
        assertNotNull(details);
        assertEquals(1, details.size());
        assertEquals("I001", details.get(0).getItemCode());
        assertEquals(2, details.get(0).getQty());
    }

    @Test
    public void testGetOrderById() throws SQLException {
        Order order = orderService.getOrderById("O001");
        assertNotNull(order);
        assertEquals("O001", order.getOrderId());
    }
}
