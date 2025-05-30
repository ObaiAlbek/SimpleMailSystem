package domain;

import java.time.LocalDateTime;

import domain.email.Email;
import domain.user.*;

public class EasyMail {
	private UserManager userManager;
	private User currentUser;

	public EasyMail() {
		this.userManager = new UserManager();
	}

	public void userRegister(String firstname, String lastName, String username, int year, int day, String monthName,
			char[] password, char[] passwordConfirmation) throws Exception {
		this.currentUser = userManager.addUser(firstname, lastName, username, year, day, monthName, password,
				passwordConfirmation);
	}

	public boolean userSignIn(String username, char[] password) throws Exception {
		this.currentUser = userManager.checkLogin(username, password);
		if (this.currentUser == null)
			return false;
		
		this.currentUser.getUsermail().signIn();
		return this.currentUser.getUsermail().getStatus();
	}

	public boolean removeUser(String username) throws UserNotFoundException {
		return userManager.removeUser(username);
	}

	public void updateUser(String username, String firstName, String lastName, char[] password, char[] confirm)
			throws Exception {
		this.currentUser = userManager.updateUser(username, firstName, lastName, password, confirm);
	}

	public int getNumberOfUsers() {
		return userManager.getNumberOfUsers();
	}

	public boolean sendEmail(String receiverEmail, String subject, String content) throws Exception {
		if (receiverEmail.trim().isEmpty() || subject.trim().isEmpty() || content.trim().isEmpty())
			throw new IllegalArgumentException("All fields are required!");

		if (!currentUser.getUsermail().getStatus())
			throw new IllegalStateException("No user is currently logged in!");

		User sender = this.currentUser;
		User receiver = userManager.findUserByUsername(receiverEmail);
		if (receiver == null)
			throw new UserNotFoundException("The receiver is not found!");

		LocalDateTime timestamp = LocalDateTime.now();
		Email newEmail = new Email(sender, receiver, subject, content, timestamp);
		sender.getUsermail().getSentFolder().addEmail(newEmail);
		return receiver.getUsermail().getInbox().addEmail(newEmail);
	}

	public String[] listAllEmailsInInbox() {
		int size = currentUser.getUsermail().getInbox().getNumberOfEmails();
		String[] treffer = new String[size];

		for (int i = 0; i < treffer.length; i++)
			treffer[i] = currentUser.getUsermail().getInbox().toString();

		return treffer;
	}

	public String[] listAllEmailsInSentFolder() {
		int size = currentUser.getUsermail().getSentFolder().getNumberOfEmails();
		String[] treffer = new String[size];

		for (int i = 0; i < treffer.length; i++)
			treffer[i] = currentUser.getUsermail().getSentFolder().toString();

		return treffer;
	}

	public String[] listAllEmailsInTrashFolder() {
		int size = currentUser.getUsermail().getTrashFolder().getNumberOfEmails();
		String[] treffer = new String[size];

		for (int i = 0; i < treffer.length; i++)
			treffer[i] = currentUser.getUsermail().getTrashFolder().toString();

		return treffer;
	}

	public boolean removeEmailFromInbox(String subject) throws Exception {
		if (subject.trim().isEmpty())
			throw new IllegalArgumentException("Subject field is required!");

		if (!this.currentUser.getUsermail().getStatus())
			throw new IllegalStateException("No user is currently logged in!");

		Email removedEmail = this.currentUser.getUsermail().getInbox().removeEmail(subject);
		return this.currentUser.getUsermail().getTrashFolder().addEmail(removedEmail);

	}

	public boolean removeEmailFromSentFolder(String subject) throws Exception {
		if (subject.trim().isEmpty())
			throw new IllegalArgumentException("Subject field is required!");

		if (!this.currentUser.getUsermail().getStatus())
			throw new IllegalStateException("No user is currently logged in!");

		Email removedEmail = this.currentUser.getUsermail().getInbox().removeEmail(subject);
		return this.currentUser.getUsermail().getTrashFolder().addEmail(removedEmail);
	}

	public void removeEmailFromTrash(String subject) throws Exception {
		if (subject.trim().isEmpty())
			throw new IllegalArgumentException("Subject field is required!");

		if (!this.currentUser.getUsermail().getStatus())
			throw new IllegalStateException("No user is currently logged in!");

		this.currentUser.getUsermail().getTrashFolder().removeEmail(subject);
	}

}
	

