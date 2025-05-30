package domain.email;

public interface EmailFolder {
	
	    boolean addEmail(Email email);
	    Email removeEmail(String subject) throws EmailNotFoundException;
	    Email getEmailBySubject(String subject) throws EmailNotFoundException ;
	    int getNumberOfEmails();
	    boolean clearAllEmails();
	    String[] listAllEmails();
}
