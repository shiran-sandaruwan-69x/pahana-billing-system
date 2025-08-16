package lk.pahana_billing_system.dao.impl;

import lk.pahana_billing_system.dao.CustomerDAO;
import lk.pahana_billing_system.model.Customer;
import lk.pahana_billing_system.utility.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (customerId, name, email, phone, address, accountNo, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getAccountNo());
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM Customer WHERE customerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customerId"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setAccountNo(rs.getString("accountNo"));
                customer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                customer.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customerId"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setAccountNo(rs.getString("accountNo"));
                customer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                customer.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET name = ?, email = ?, phone = ?, address = ?, accountNo = ?, updated_at = ? WHERE customerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getAccountNo());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, customer.getCustomerId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String sql = "DELETE FROM Customer WHERE customerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getCustomerCount() {
        String sql = "SELECT COUNT(*) FROM Customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
