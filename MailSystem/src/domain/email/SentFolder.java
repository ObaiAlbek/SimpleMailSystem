package domain.email;

import java.util.ArrayList;

public class SentFolder implements EmailFolder {

    private ArrayList<Email> emails;

    public SentFolder() {
        this.emails = new ArrayList<>();
    }

    @Override
    public boolean addEmail(Email email) {
        if (email == null) return false;
       return emails.add(email);
    }

    @Override
    public Email removeEmail(String subject) throws EmailNotFoundException {
        Email emailToBeRemoved = getEmailBySubject(subject);
        if (emailToBeRemoved == null) 
            throw new EmailNotFoundException("Email not found!");
        
        emails.remove(emailToBeRemoved);
        return emailToBeRemoved;
    }

    @Override
    public Email getEmailBySubject(String subject) throws EmailNotFoundException {
        if (subject == null) 
            throw new EmailNotFoundException("Email not found!");
        
        for (Email email : emails) 
            if (email.getSubject().equalsIgnoreCase(subject)) 
                return email;
     
        return null;
    }

    @Override
    public int getNumberOfEmails() {
        return emails.size();
    }

    @Override
    public boolean clearAllEmails() {
        if (emails.isEmpty())
            return false; 
        
        emails.clear();
        return true;
    }

    @Override
    public String[] listAllEmails() {
        String[] subjects = new String[emails.size()];
        for (int i = 0; i < emails.size(); i++) 
            subjects[i] = emails.get(i).toString();
        
        return subjects;
    }
}
