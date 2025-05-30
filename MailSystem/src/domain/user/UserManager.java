package domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class UserManager {

    private ArrayList<User> users;
    private User currentUser;
    
    public UserManager() {
        this.users = new ArrayList<>();
    }

	public User addUser(String firstName, String lastName, String username, int year, int day, String monthName,
			char[] password, char[] passwordConfirmation) throws Exception {

		if (firstName == null || lastName == null || username == null || password == null
				|| passwordConfirmation == null) {
			throw new IllegalArgumentException("No input should be null!");
		}

		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() || password.length == 0
				|| passwordConfirmation.length == 0) {
			throw new IllegalArgumentException("All fields are required!");
		}
		if (password.length < 5)
			throw new IllegalArgumentException("Password should be stronger!");
		
		if (!Arrays.equals(password, passwordConfirmation))
			throw new IllegalArgumentException("Passwords do not match!");

		String email = username + "@easymail.de";
		if (findUserByUsername(email) != null)
			throw new UserAlreadyExistsException("This email address is already taken!");

		int month = getMonthNumber(monthName);
		if (month == 0)
			throw new IllegalArgumentException("Invalid month name: " + monthName);

		LocalDate birthDate = LocalDate.of(year, month, day);
		char[] passwordCopy = Arrays.copyOf(password, password.length);
		User newUser = new User(firstName, lastName, birthDate, email, passwordCopy);

		users.add(newUser);

		Arrays.fill(password, ' ');
		Arrays.fill(passwordConfirmation, ' ');
		return newUser;
	}

	public User checkLogin(String username, char[] password)throws Exception{
		if (username == null || password == null)
			throw new UserAlreadyExistsException("This email address is already taken!");

		for (User user : users)
			if (user.getUsermail().getUsername().equalsIgnoreCase(username)
					&& Arrays.equals(user.getUsermail().getPassword(), password)) {
				Arrays.fill(password, ' ');
				return user;
			}
		Arrays.fill(password, ' ');
		throw new UserNotFoundException("This email address is not found!");
	}

	public boolean removeUser(String username) throws UserNotFoundException {
		if (username == null || username.trim().isEmpty())
			throw new IllegalArgumentException("Username cannot be null or empty!");

		User userToBeRemoved = findUserByUsername(username);
		if (userToBeRemoved == null)
			throw new UserNotFoundException("This email address is not found!");

		users.remove(userToBeRemoved);
		return true;
	}

	public int getNumberOfUsers() {
		return users.size();
	}

	public User updateUser(String username, String firstName, String lastName, char[] password, char[] confirm)
			throws Exception {
		User userToBeUpdated = findUserByUsername(username);
		if (userToBeUpdated == null)
			throw new UserNotFoundException("This email address is not found!");

		if (firstName == null || lastName == null || password == null || confirm == null)
			throw new IllegalArgumentException("Fields cannot be null!");

		if (firstName.trim().isEmpty() || lastName.trim().isEmpty())
			throw new IllegalArgumentException("First name and last name are required!");

		if (!Arrays.equals(password, confirm))
			throw new IllegalArgumentException("Passwords do not match!");

		userToBeUpdated.setFirstname(firstName);
		userToBeUpdated.setLastname(lastName);

		char[] passwordCopy = Arrays.copyOf(password, password.length);
		userToBeUpdated.getUsermail().setPassword(passwordCopy);
		Arrays.fill(password, ' ');
		Arrays.fill(confirm, ' ');

		return userToBeUpdated;
	}

	public User getUserByUsername(String username) {
		this.currentUser = findUserByUsername(username);
		if (this.currentUser == null)
			return null;

		return currentUser;
	}

	public User findUserByUsername(String username) {
		for (User tempUser : users)
			if (tempUser.getUsermail().getUsername().equalsIgnoreCase(username))
				return tempUser;

		return null;
	}

	private int getMonthNumber(String txtMonth) {
		switch (txtMonth.toLowerCase()) {
		case "januar":return 1;
		case "februar":return 2;
		case "märz":return 3;
		case "april":return 4;
		case "mai":return 5;
		case "juni":return 6;
		case "juli":return 7;
		case "august":return 8;
		case "september":return 9;
		case "oktober":return 10;
		case "november":return 11;
		case "dezember":return 12;
		default:return 0;
		}
	}

}
