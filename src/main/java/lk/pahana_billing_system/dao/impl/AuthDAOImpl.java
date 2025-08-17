package lk.pahana_billing_system.dao.impl;

import lk.pahana_billing_system.dao.AuthDAO;
import lk.pahana_billing_system.model.User;
import lk.pahana_billing_system.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AuthDAOImpl implements AuthDAO {
    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
        System.out.println("username : "+ username + "password : " + password);
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    System.out.println("Authentication successful for user: {}"+ username);
                    return user;
                } else {
                    System.out.println("Authentication failed for user: {}"+ username);
                }
            }
        }
        return null;
    }
}
