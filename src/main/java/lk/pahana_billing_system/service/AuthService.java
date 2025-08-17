package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.User;
import java.sql.SQLException;

public interface AuthService {
    User authenticateUser(String username, String password) throws SQLException;
}
