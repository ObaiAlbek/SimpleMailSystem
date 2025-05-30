package domain.email;

import java.util.ArrayList;
import java.util.ArrayList;

public class TrashFolder implements EmailFolder {

    private ArrayList<Email> removedEmails;

    public TrashFolder() {
        this.removedEmails = new ArrayList<>();
    }

    @Override
    public boolean addEmail(Email email) {
        if (email == null) {
            return false;
        }
        removedEmails.add(email);
        return true;
    }

    @Override
    public Email removeEmail(String subject) throws EmailNotFoundException {
        Email emailToBeRemoved = getEmailBySubject(subject);
        if (emailToBeRemoved == null) 
            throw new EmailNotFoundException("Email not found in trash!");
        
        removedEmails.remove(emailToBeRemoved);
        return null;
    }

    @Override
    public Email getEmailBySubject(String subject) {
        if (subject == null) 
        	 throw new IllegalArgumentException("No input should be null!");
        
        for (Email email : removedEmails) 
            if (email.getSubject().equalsIgnoreCase(subject)) 
                return email;
   
        return null;
    }

    @Override
    public int getNumberOfEmails() {
        return removedEmails.size();
    }

    @Override
    public boolean clearAllEmails() {
        if (removedEmails.isEmpty()) return false;
       
        removedEmails.clear();
        return true;
    }

    @Override
    public ArrayList<Email> listAllEmails() {
        return new ArrayList<>(removedEmails); 
    }
}

