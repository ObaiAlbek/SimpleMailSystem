package domain.user;

import domain.email.*;

public class UserEmail {

	private static int counter = 1000;
	private int accountID;
	private String email;
	private boolean status;
	private char[] password;
	private SentFolder sentFolder;
	private TrashFolder trashFolder;
	private Inbox inbox;
	
	public UserEmail(String username, char[] password) {
		this.email = username;
		this.password = password;
		this.accountID = counter++;
		this.status = true;
		this.sentFolder = new SentFolder();
		this.trashFolder = new TrashFolder();
		this.inbox = new Inbox();
	}

	public String getUserEmail() {
		return email;
	}

	public  char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public int getAccountID() {
		return accountID;
	}
	
	public SentFolder getSentFolder() {
		return sentFolder;
	}
	
	public TrashFolder getTrashFolder() {
		return trashFolder;
	}
	
	public Inbox getInbox() {
		return inbox;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void  signIn() {
		this.status = true;
	}
	
	public void signUp() {
		this.status = false;
	}
	
}
