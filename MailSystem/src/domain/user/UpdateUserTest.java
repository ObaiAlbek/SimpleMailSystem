package domain.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UpdateUserTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() throws Exception {
        userManager = new UserManager();
        userManager.addUser("John", "Doe", "johndoe", 1990, 15, "Mai", "password123".toCharArray(), "password123".toCharArray());
    }

    @Test
    void testUpdateUserSuccessfully() throws Exception {
        User updated = userManager.updateUser(
            "johndoe@easymail.de", 
            "Johnny", 
            "Doeman", 
            "newpass123".toCharArray(), 
            "newpass123".toCharArray()
        );
        assertNull(updated);
        
        User updatedUser = userManager.getUserByUsername("johndoe@easymail.de");
        assertEquals("Johnny", updatedUser.getFirstname());
        assertEquals("Doeman", updatedUser.getLastname());
    }

    @Test
    void testUpdateUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userManager.updateUser(
                "unknown@easymail.de", 
                "Johnny", 
                "Doeman", 
                "newpass123".toCharArray(), 
                "newpass123".toCharArray()
            );
        });
    }

    @Test
    void testUpdateUserNullFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                null, 
                "Doeman", 
                "newpass123".toCharArray(), 
                "newpass123".toCharArray()
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "Johnny", 
                null, 
                "newpass123".toCharArray(), 
                "newpass123".toCharArray()
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "Johnny", 
                "Doeman", 
                null, 
                "newpass123".toCharArray()
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "Johnny", 
                "Doeman", 
                "newpass123".toCharArray(), 
                null
            );
        });
    }

    @Test
    void testUpdateUserEmptyFirstnameOrLastname() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "   ", 
                "Doeman", 
                "newpass123".toCharArray(), 
                "newpass123".toCharArray()
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "Johnny", 
                "    ", 
                "newpass123".toCharArray(), 
                "newpass123".toCharArray()
            );
        });
    }

    @Test
    void testUpdateUserPasswordMismatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.updateUser(
                "johndoe@easymail.de", 
                "Johnny", 
                "Doeman", 
                "newpass123".toCharArray(), 
                "differentpass".toCharArray()
            );
        });
    }
}
