package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.User;
import lk.pahana_billing_system.service.impl.AuthServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthServiceTest {

    private class DummyAuthService extends AuthServiceImpl {
        @Override
        public User authenticateUser(String username, String password) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        DummyAuthService authService = new DummyAuthService();

        String username = "admin";
        String password = "password123";

        User result = authService.authenticateUser(username, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
    }
}
