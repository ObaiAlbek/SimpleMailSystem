package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EasyMailTest {

	private EasyMail easyMail;
	private char[] password;
	private char[] confirmPassword;

	@BeforeEach
	void setUp() throws Exception {
		easyMail = new EasyMail();
		password = "password123".toCharArray();
		confirmPassword = "password123".toCharArray();

		// User registrieren
		easyMail.userRegister("Alice", "Wonder", "alice", 1995, 20, "Juli", password, confirmPassword);
		easyMail.userRegister("Bob", "Marley", "bob", 1990, 1, "Juni", password, confirmPassword);
	}

	@Test
	void testUserSignin() throws Exception {
		boolean userSignIn = easyMail.userSignIn("alice@easymail.de", "password123".toCharArray());
		assertTrue(userSignIn);
	}

	@Test
	void testSendEmail() throws Exception {
		easyMail.userSignIn("alice@easymail.de", "password123".toCharArray());
		boolean result = easyMail.sendEmail("bob@easymail.de", "Hello", "This is a test email.");

//		assertTrue(result);
//		String[] sentEmails = easyMail.listAllEmailsInSentFolder();
//		assertEquals(1, sentEmails.length);
	}
}

