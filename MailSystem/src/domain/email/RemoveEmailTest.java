package domain.email;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;
import domain.email.EmailNotFoundException;
import domain.user.*;

class RemoveEmailTest {

	private User user;
	private Email email;

	@BeforeEach
	void setUp() {
		user = new User("Alice", "Wonder", LocalDate.of(1995, 7, 20), "alicewonder", "wonderPass".toCharArray());
		email = new Email(user,new User("Bob", "Marley", LocalDate.of(1990, 6, 1), "bobmarley", "bobPass".toCharArray()),
				"Trash Test Subject", "This email will go to trash.", LocalDateTime.now());
	}

	@Test
	void testAddEmailToTrash() {
		boolean added = user.getUsermail().getTrashFolder().addEmail(email);

		assertTrue(added); // erfolgreich hinzugefügt
		assertEquals(1, user.getUsermail().getTrashFolder().getNumberOfEmails()); // Anzahl prüfen
	}


	@Test
	void testRemoveEmailNotFoundInTrash() {
		assertThrows(EmailNotFoundException.class, () -> {
			user.getUsermail().getTrashFolder().removeEmail("Nonexistent Subject");
		});
	}

}
