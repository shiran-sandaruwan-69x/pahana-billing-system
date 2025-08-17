package lk.pahana_billing_system.service;

import lk.pahana_billing_system.dto.CustomerDTO;
import lk.pahana_billing_system.model.Customer;

import java.util.List;

public interface CustomerService {
    boolean registerCustomer(Customer customer);
    Customer getCustomerDetails(String id);
    List<CustomerDTO> getAllCustomers();
    boolean updateCustomerDetails(Customer customer);
    boolean removeCustomer(String id);
    int getTotalCustomerCount();
}
