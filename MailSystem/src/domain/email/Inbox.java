package domain.email;

import java.util.ArrayList;

public class Inbox implements EmailFolder {

    private ArrayList<Email> receivedEmails;

    public Inbox() {
        this.receivedEmails = new ArrayList<>();
    }

    @Override
    public boolean addEmail(Email email) {
        if (email == null) {
            return false;
        }
        receivedEmails.add(email);
        return true;
    }

    @Override
    public Email removeEmail(String subject) throws EmailNotFoundException {
        Email emailToBeRemoved = getEmailBySubject(subject);
        if (emailToBeRemoved == null) {
            throw new EmailNotFoundException("Email not found in inbox!");
        }
        receivedEmails.remove(emailToBeRemoved);
        return emailToBeRemoved;
    }

    @Override
    public Email getEmailBySubject(String subject) throws EmailNotFoundException {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty!");
        }
        for (Email email : receivedEmails) {
            if (email.getSubject().equalsIgnoreCase(subject)) {
                return email;
            }
        }
        throw new EmailNotFoundException("Email with subject '" + subject + "' not found!");
    }

    @Override
    public int getNumberOfEmails() {
        return receivedEmails.size();
    }

    @Override
    public boolean clearAllEmails() {
        if (receivedEmails.isEmpty()) {
            return false;
        }
        receivedEmails.clear();
        return true;
    }

    @Override
    public ArrayList<Email> listAllEmails() {
        return new ArrayList<>(receivedEmails); // sichere Kopie
    }

}

