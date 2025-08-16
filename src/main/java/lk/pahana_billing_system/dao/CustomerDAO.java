package lk.pahana_billing_system.dao;

import lk.pahana_billing_system.model.Customer;
import java.util.List;
public interface CustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(String id);
    List<Customer> getAllCustomers();
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    int getCustomerCount();
}
