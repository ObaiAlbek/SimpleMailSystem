package domain.email;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;
import domain.email.EmailNotFoundException;
import domain.user.User;

class SendEmailTest {

	private User sender;
	private User receiver;

	@BeforeEach
	void setUp() {
		sender = new User("John", "Doe", LocalDate.of(1990, 1, 1), "johndoe", "password123".toCharArray());
		receiver = new User("Jane", "Smith", LocalDate.of(1992, 5, 10), "janesmith", "securePass".toCharArray());
	}

	@Test
	void testAddEmailToSentFolder() {
		Email email = new Email(sender, receiver, "Test Subject", "This is a test email content.", LocalDate.now());
		boolean added = sender.getUsermail().getSentFolder().addEmail(email);

		assertTrue(added); 
		assertEquals(1, sender.getUsermail().getSentFolder().getNumberOfEmails()); // 1 Email gespeichert
	}
	
	@Test
	void testRemoveEmailFromSentFolder() throws EmailNotFoundException {
		Email email = new Email(sender, receiver, "Test Subject", "This is a test email content.", LocalDate.now());
		boolean added = sender.getUsermail().getSentFolder().addEmail(email);
	
		assertTrue(added); 
		assertEquals(1, sender.getUsermail().getSentFolder().getNumberOfEmails());
		
		Email removed = sender.getUsermail().getSentFolder().removeEmail("Test Subject");
		assertNotNull(removed);
		assertEquals(0, sender.getUsermail().getSentFolder().getNumberOfEmails());

		
	}
}
