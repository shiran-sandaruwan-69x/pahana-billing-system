package lk.pahana_billing_system.service.impl;

import lk.pahana_billing_system.dao.CustomerDAO;
import lk.pahana_billing_system.dao.impl.CustomerDAOImpl;
import lk.pahana_billing_system.dto.CustomerDTO;
import lk.pahana_billing_system.model.Customer;
import lk.pahana_billing_system.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean registerCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    @Override
    public Customer getCustomerDetails(String id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.getAllCustomers().stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.setCustomerId(customer.getCustomerId());
                    dto.setName(customer.getName());
                    dto.setEmail(customer.getEmail());
                    dto.setPhone(customer.getPhone());
                    dto.setAddress(customer.getAddress());
                    dto.setAccountNo(customer.getAccountNo());
                    dto.setCreatedAt(customer.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateCustomerDetails(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @Override
    public boolean removeCustomer(String id) {
        return customerDAO.deleteCustomer(id);
    }

    @Override
    public int getTotalCustomerCount() {
        return customerDAO.getCustomerCount();
    }
}
