package domain.user;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AddUserTest {
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void testLeererVorname() {
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("", "Test","nutzer", 2000, 1, "Januar", new char[]{'1'}, new char[]{'1'});
        });
        assertEquals("Alle Felder sind erforderlich!", ex.getMessage());
    }

    @Test
    void testLeererNachname() {
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("Max", "","nutzer", 2000, 1, "Januar", new char[]{'1'}, new char[]{'1'});
        });
        assertEquals("Alle Felder sind erforderlich!", ex.getMessage());
    }

    @Test
    void testLeererNutzername() {
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("Max", "Mustermann","", 2000, 1, "Januar", new char[]{'1'}, new char[]{'1'});
        });
        assertEquals("Alle Felder sind erforderlich!", ex.getMessage());
    }

    @Test
    void testLeeresPasswort() {
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("Max", "Mustermann","nutzer",2000, 1, "Januar", new char[]{}, new char[]{});
        });
        assertEquals("Alle Felder sind erforderlich!", ex.getMessage());
    }

    @Test
    void testPasswoerterUnterschiedlich() {
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("Max", "Mustermann","nutzer",2000, 1, "Januar", new char[]{'1','2'}, new char[]{'1','3'});
        });
        assertEquals("Passwörter stimmen nicht überein!", ex.getMessage());
    }

    @Test
    void testNutzernameBereitsVergeben() throws Exception {
        // Ersten Nutzer hinzufügen
        userManager.addUser("Max", "Mustermann","nutzer",2000, 1, "Januar" , new char[]{'1'}, new char[]{'1'});

        // Zweiter Nutzer mit gleicher E-Mail
        Exception ex = assertThrows(Exception.class, () -> {
            userManager.addUser("Moritz", "Muster","nutzer", 2001, 2, "Februar",  new char[]{'1'}, new char[]{'1'});
        });
        assertEquals("Diese E-Mail-Adresse ist bereits vergeben!", ex.getMessage());
    }

    @Test
    void testErfolgreichHinzufuegen() throws Exception {
        User result = userManager.addUser("Anna", "Beispiel","anna",1995, 15, "Mai", new char[]{'1'}, new char[]{'1'});
        assertNotNull(result);
    }
}
