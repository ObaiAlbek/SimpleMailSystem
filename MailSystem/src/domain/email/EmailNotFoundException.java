package domain.email;

public class EmailNotFoundException extends Exception {
	public EmailNotFoundException(String error) {
		super(error);
	}
}
