package lk.pahana_billing_system.service.impl;

import lk.pahana_billing_system.dao.AuthDAO;
import lk.pahana_billing_system.dao.impl.AuthDAOImpl;
import lk.pahana_billing_system.model.User;
import lk.pahana_billing_system.service.AuthService;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {
    private AuthDAO authDAO = new AuthDAOImpl();

    @Override
    public User authenticateUser(String username, String password) throws SQLException {
        System.out.println("step 2 username"+ username + "password : "+ password);
        return authDAO.findUserByUsernameAndPassword(username, password);
    }
}
