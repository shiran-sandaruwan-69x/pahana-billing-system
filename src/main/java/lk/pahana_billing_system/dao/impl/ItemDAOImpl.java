package lk.pahana_billing_system.dao.impl;

import lk.pahana_billing_system.dao.ItemDAO;
import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean addItem(Item item) throws SQLException {
        String query = "INSERT INTO Item (itemCode, description, unitPrice, qtyOnHand) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getUnitPrice());
            ps.setInt(4, item.getQtyOnHand());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("itemCode"));
                item.setDescription(rs.getString("description"));
                item.setUnitPrice(rs.getDouble("unitPrice"));
                item.setQtyOnHand(rs.getInt("qtyOnHand"));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public Item getItemByCode(String itemCode) throws SQLException {
        String query = "SELECT * FROM Item WHERE itemCode = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, itemCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemCode(rs.getString("itemCode"));
                    item.setDescription(rs.getString("description"));
                    item.setUnitPrice(rs.getDouble("unitPrice"));
                    item.setQtyOnHand(rs.getInt("qtyOnHand"));
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        String query = "UPDATE Item SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE itemCode = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, item.getDescription());
            ps.setDouble(2, item.getUnitPrice());
            ps.setInt(3, item.getQtyOnHand());
            ps.setString(4, item.getItemCode());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException {
        String query = "DELETE FROM Item WHERE itemCode = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, itemCode);
            return ps.executeUpdate() > 0;
        }
    }
}
