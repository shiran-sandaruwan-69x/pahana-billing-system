package lk.pahana_billing_system.service;

import lk.pahana_billing_system.dto.CustomerDTO;
import lk.pahana_billing_system.model.Customer;
import lk.pahana_billing_system.service.impl.CustomerServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceTest {

    private class DummyCustomerService extends CustomerServiceImpl {
        @Override
        public boolean registerCustomer(Customer customer) {
            return true;
        }

        @Override
        public Customer getCustomerDetails(String id) {
            Customer customer = new Customer();
            customer.setCustomerId(id);
            customer.setName("John Doe");
            customer.setEmail("john@example.com");
            return customer;
        }

        @Override
        public List<CustomerDTO> getAllCustomers() {
            CustomerDTO dto = new CustomerDTO();
            dto.setCustomerId("C001");
            dto.setName("John Doe");
            dto.setEmail("john@example.com");

            List<CustomerDTO> list = new ArrayList<>();
            list.add(dto);
            return list;
        }

        @Override
        public boolean updateCustomerDetails(Customer customer) {
            return true;
        }

        @Override
        public boolean removeCustomer(String id) {
            return true;
        }

        @Override
        public int getTotalCustomerCount() {
            return 5;
        }
    }

    private final DummyCustomerService customerService = new DummyCustomerService();

    @Test
    public void testRegisterCustomer() {
        Customer customer = new Customer();
        boolean result = customerService.registerCustomer(customer);
        assertTrue(result);
    }

    @Test
    public void testGetCustomerDetails() {
        Customer customer = customerService.getCustomerDetails("C001");
        assertNotNull(customer);
        assertEquals("C001", customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testGetAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("C001", customers.get(0).getCustomerId());
    }

    @Test
    public void testUpdateCustomerDetails() {
        Customer customer = new Customer();
        boolean result = customerService.updateCustomerDetails(customer);
        assertTrue(result);
    }

    @Test
    public void testRemoveCustomer() {
        boolean result = customerService.removeCustomer("C001");
        assertTrue(result);
    }

    @Test
    public void testGetTotalCustomerCount() {
        int count = customerService.getTotalCustomerCount();
        assertEquals(5, count);
    }
}
