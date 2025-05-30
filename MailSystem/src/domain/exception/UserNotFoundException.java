package domain.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String error) {
		super(error);
	}
}
