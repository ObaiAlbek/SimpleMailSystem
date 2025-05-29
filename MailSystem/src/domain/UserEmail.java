package domain;

public class UserEmail {

	private static int counter = 1000;
	private int accountID;
	private String username;
	private char[] password;
	
	public UserEmail(String username, char[] password) {
		this.username = username;
		this.password = password;
		this.accountID = counter++;
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
	
}
