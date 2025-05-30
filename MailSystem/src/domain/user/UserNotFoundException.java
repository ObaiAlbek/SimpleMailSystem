package domain.user;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String error) {
		super(error);
	}
}
