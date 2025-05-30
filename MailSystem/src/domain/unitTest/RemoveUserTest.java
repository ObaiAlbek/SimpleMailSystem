package domain.unitTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.UserManager;
import domain.exception.UserNotFoundException;

class RemoveUserTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() throws Exception {
        userManager = new UserManager();
        userManager.addUser("Jane", "Doe", "janedoe", 1992, 20, "April", "securePass1".toCharArray(), "securePass1".toCharArray());
    }

    @Test
    void testRemoveUserSuccessfully() throws Exception {
        boolean removed = userManager.removeUser("janedoe@easymail.de");
        assertTrue(removed);

        assertNull(userManager.getUserByUsername("janedoe@easymail.de"));
    }

    @Test
    void testRemoveUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userManager.removeUser("nonexistent@easymail.de");
        });
    }

    @Test
    void testRemoveUserNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.removeUser(null);
        });
    }

    @Test
    void testRemoveUserEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.removeUser("   ");
        });
    }
}

