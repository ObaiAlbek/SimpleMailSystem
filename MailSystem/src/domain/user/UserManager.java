package domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class UserManager {

    private HashMap<String,User> users;
    
    public UserManager(){
        this.users = new HashMap<>();
    }
	public User addUser(String firstName, String lastName, String email, int year, int day, String monthName,
			char[] password, char[] passwordConfirmation) throws Exception {

		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || password.length == 0
				|| passwordConfirmation.length == 0) {
			throw new IllegalArgumentException("All fields are required!");
		}
		if (password.length < 5)
			throw new IllegalArgumentException("Password should be stronger!");
		
		if (!Arrays.equals(password, passwordConfirmation))
			throw new IllegalArgumentException("Passwords do not match!");
		
		String domain = "@easymail.de";
		email += domain;
		
        if (users.containsKey(email)) 
            throw new UserAlreadyExistsException("Email already registered!");
        
		int month = getMonthNumber(monthName);
		if (month == 0)
			throw new IllegalArgumentException("Invalid month name: " + monthName);

		LocalDate birthDate = LocalDate.of(year, month, day);
		char[] passwordCopy = Arrays.copyOf(password, password.length);

		User newUser = new User(firstName, lastName, birthDate, email, passwordCopy);
		users.put(email, newUser);
		
		Arrays.fill(password, ' ');
		Arrays.fill(passwordConfirmation, ' ');
		return newUser;
	}

	public User checkLogin(String userEmail, char[] password) throws Exception {
      
		if (userEmail.trim().isEmpty()|| password.length == 0) 
			throw new IllegalArgumentException("All fields are required!");
	
        // Email generieren (case-insensitive)
		userEmail = userEmail.toLowerCase();
        User user = users.get(userEmail);
        if (user == null) {
            Arrays.fill(password, ' ');
            throw new UserNotFoundException("User not found!");
        }

        if (!Arrays.equals(user.getUsermail().getPassword(), password)) {
            Arrays.fill(password, ' ');
            throw new SecurityException("Invalid password!");
        }

        Arrays.fill(password, ' ');
        return user;
    }


	public boolean removeUser(String userEmail) throws UserNotFoundException {
		if (userEmail.trim().isEmpty())
			throw new IllegalArgumentException("email is required!");
			
		userEmail = userEmail.toLowerCase();
        User removed = users.remove(userEmail);
        
        if (removed == null) 
            throw new UserNotFoundException("User not found!");
        
        return true;
    }

	public int getNumberOfUsers() {
		return users.size();
	}

	public User updateUser(String firstName, String lastName, String userEmail, int year, int day, String monthName,
	        char[] password, char[] passwordConfirmation) throws Exception {

	    User userToBeUpdated = findUserByUsername(userEmail);
	    if (userToBeUpdated == null)
	        throw new UserNotFoundException("This email address is not found!");

	    if (firstName != null && !firstName.trim().isEmpty()) {
	        userToBeUpdated.setFirstname(firstName);
	    }

	    if (lastName != null && !lastName.trim().isEmpty()) {
	        userToBeUpdated.setLastname(lastName);
	    }

	    if (year > 0 && day > 0 && monthName != null && !monthName.trim().isEmpty()) {
	        int month = getMonthNumber(monthName);
	        if (month == 0) {
	            throw new IllegalArgumentException("Invalid month name: " + monthName);
	        }
	        LocalDate birthDate = LocalDate.of(year, month, day);
	        userToBeUpdated.setBirthdate(birthDate);
	    }

	    if (password != null && password.length > 0) {
	        if (passwordConfirmation == null || passwordConfirmation.length == 0) {
	            throw new IllegalArgumentException("Password confirmation required!");
	        }

	        if (!Arrays.equals(password, passwordConfirmation)) {
	            throw new IllegalArgumentException("Passwords do not match!");
	        }

	        if (password.length < 6) {
	            throw new IllegalArgumentException("Password must be at least 6 characters long!");
	        }

	        char[] passwordCopy = Arrays.copyOf(password, password.length);
	        userToBeUpdated.getUsermail().setPassword(passwordCopy);
	    }

	    Arrays.fill(password, ' ');
	    Arrays.fill(passwordConfirmation, ' ');

	    return userToBeUpdated;
	}


	
	public User findUserByUsername(String userEmail) {
        return users.get(userEmail);
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
