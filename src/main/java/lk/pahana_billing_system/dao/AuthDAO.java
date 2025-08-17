package lk.pahana_billing_system.dao;

import lk.pahana_billing_system.model.User;
import java.sql.SQLException;

public interface AuthDAO {
    User findUserByUsernameAndPassword(String username, String password) throws SQLException;
}
