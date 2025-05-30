package domain.user;

import domain.email.*;

public class UserEmail {

	private static int counter = 1000;
	private int accountID;
	private String username;
	private char[] password;
	private SentFolder sentFolder;
	private TrashFolder trashFolder;
	private Inbox inbox;
	
	public UserEmail(String username, char[] password) {
		this.username = username;
		this.password = password;
		this.accountID = counter++;
		this.sentFolder = new SentFolder();
		this.trashFolder = new TrashFolder();
		this.inbox = new Inbox();
	}

	public String getUsername() {
		return username;
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
	
}
