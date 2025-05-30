package domain.email;

import java.util.ArrayList;

public interface EmailFolder {
	
	    boolean addEmail(Email email);
	    Email removeEmail(String subject) throws EmailNotFoundException;
	    Email getEmailBySubject(String subject) throws EmailNotFoundException ;
	    int getNumberOfEmails();
	    boolean clearAllEmails();
	    ArrayList<Email>listAllEmails();
}
