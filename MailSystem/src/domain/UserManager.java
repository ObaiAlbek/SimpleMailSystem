package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import domain.exception.UserAlreadyExistsException;

public class UserManager {

    private ArrayList<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public boolean addUser(String firstName, String lastName, String username,
                           int year, int day, String monthName,
                           char[] password, char[] passwordConfirmation) throws Exception {

        if (firstName == null || lastName == null || username == null || password == null || passwordConfirmation == null) {
            throw new IllegalArgumentException("No input should be null!");
        }

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() ||
            password.length == 0 || passwordConfirmation.length == 0) {
            throw new IllegalArgumentException("All fields are required!");
        }

        if (!Arrays.equals(password, passwordConfirmation)) 
            throw new IllegalArgumentException("Passwords do not match!");

        String email = username + "@easymail.de";

        for (User tempUser : users) 
            if (tempUser.getUsermail().getUsername().equalsIgnoreCase(email)) 
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
        return true;
    }

    public boolean checkLogin(String username, char[] password) {
        if (username == null || password == null) return false;

        for (User user : users) 
            if (user.getUsermail().getUsername().equalsIgnoreCase(username)
                && Arrays.equals(user.getUsermail().getPassword(), password)) {
                Arrays.fill(password, ' ');
                return true;
            }
        Arrays.fill(password, ' ');
        return false;
    }


    private int getMonthNumber(String txtMonth) {
        switch (txtMonth.toLowerCase()) {
            case "januar": return 1;
            case "februar": return 2;
            case "märz": return 3;
            case "april": return 4;
            case "mai": return 5;
            case "juni": return 6;
            case "juli": return 7;
            case "august": return 8;
            case "september": return 9;
            case "oktober": return 10;
            case "november": return 11;
            case "dezember": return 12;
            default: return 0;
        }
    }
}

