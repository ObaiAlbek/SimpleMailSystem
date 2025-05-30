package domain.user;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CheckLoginTest {
    private UserManager userManager;

    @BeforeEach
    void setUp() throws Exception {
        userManager = new UserManager();
        char[] pw = {'1','2','3','4'};
        userManager.addUser("Ali", "Test","ali.test",2000, 1, "Januar", pw, pw.clone());
    }

    @Test
    void testLoginMitKorrektenDaten() {
        assertTrue(userManager.checkLogin("ali.test@easymail.de", new char[]{'1','2','3','4'}));
    }

    @Test
    void testLoginMitFalschemPasswort() {
        assertFalse(userManager.checkLogin("ali.test@easymail.de", new char[]{'1','1','1','1'}));
    }

    @Test
    void testLoginMitFalschemNutzername() {
        assertFalse(userManager.checkLogin("nicht.vorhanden@easymail.de", new char[]{'1','2','3','4'}));
    }
}
