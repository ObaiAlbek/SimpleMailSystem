package domain.user;

import java.time.LocalDate;

public class User {

	private static int counter = 1000;
	private int userID;
	private String firstname;
	private String lastname;
	private LocalDate birthdate;
	private UserEmail userEmail;

	public User(String firstname, String lastname, LocalDate birthdate,String email, char[] password) {
		this.userID = counter++;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.userEmail = new UserEmail(email,password);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public int getUserID() {
		return userID;
	}

	public UserEmail getUsermail() {
		return userEmail;
	}

}
