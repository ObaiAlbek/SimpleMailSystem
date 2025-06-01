package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.email.Email;
import domain.email.EmailFolder;
import domain.user.*;

public class EasyMail {
	private UserManager userManager;
	private User currentUser;

	public EasyMail() {
		this.userManager = new UserManager();
		  try {
	        	this.currentUser = userManager.addUser ("obai","albek","obai.albek",1,1,"Januar",new char[] {'1','2','3','4','5','6'} , new char[]{'1','2','3','4','5','6'});
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void userRegister(String firstname, String lastName, String username, int year, int day, String monthName,char[] password, char[] passwordConfirmation) throws Exception {
		this.currentUser = userManager.addUser(firstname, lastName, username, year, day, monthName, password,passwordConfirmation);
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
		boolean sent = receiver.getUsermail().getInbox().addEmail(newEmail);
		
		return sent;
	}
	
	public String[] sendUserDetails() {
		String[] details = new String[2];
		String name = this.currentUser.getFirstname() + " " + this.currentUser.getLastname();
		String username =  this.currentUser.getUsermail().getUsername();
		details[0] = name; 
		details[1] = username;
		
		return details;
	}
	
	public String getUsernameFromCurrentUser() {
		return this.currentUser.getUsermail().getUsername();
	}

	public ArrayList<String> sendAllEmailstoSentWindow() {
		ArrayList<Email> allEmails = this.currentUser.getUsermail().getSentFolder().listAllEmails();
		ArrayList<String> treffer = new ArrayList<>();

		for (Email tempEmail : allEmails)
			treffer.add(tempEmail.showEmailsInSent());

		return treffer;
	}
	
	public ArrayList<String>sendAllEmailsToInboxWindow() {
		ArrayList<Email> allEmails = this.currentUser.getUsermail().getInbox().listAllEmails();
		ArrayList<String> treffer = new ArrayList<>();
		
		for (Email tempEmail : allEmails)
			treffer.add(tempEmail.showEmails());

		return treffer;
		
	}
	
	public ArrayList<String>sendAllEmailsToTrashWindow() {
		ArrayList<Email> allEmails = this.currentUser.getUsermail().getTrashFolder().listAllEmails();
		ArrayList<String> treffer = new ArrayList<>();
		
		for (Email tempEmail : allEmails)
			treffer.add(tempEmail.showEmails());

		return treffer;
		
	}


	private void validateEmailOperation(String subject) {
		if (subject == null || subject.trim().isEmpty()) {
			throw new IllegalArgumentException("Subject field is required!");
		}
		if (this.currentUser == null || !this.currentUser.getUsermail().getStatus()) {
			throw new IllegalStateException("No user is currently logged in!");
		}
	}

	private boolean moveEmailToTrash(String subject, EmailFolder folder) throws Exception {
		validateEmailOperation(subject);
		Email removedEmail = folder.removeEmail(subject);
		return this.currentUser.getUsermail().getTrashFolder().addEmail(removedEmail);
	}



	public boolean removeEmailFromInbox(String subject) throws Exception {
		return moveEmailToTrash(subject, this.currentUser.getUsermail().getInbox());
	}

	public boolean removeEmailFromSentFolder(String subject) throws Exception {
		return moveEmailToTrash(subject, this.currentUser.getUsermail().getSentFolder());
	}

	public void removeEmailFromTrash(String subject) throws Exception {
		validateEmailOperation(subject);
		this.currentUser.getUsermail().getTrashFolder().removeEmail(subject);
	}



}
	

