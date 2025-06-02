package domain.email;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.user.*;

public class Email {
	private User sender;
	private User receiver;
	private String subject;
	private String content;
	private LocalDateTime date;
	
	public Email(User sender, User receiver, String subject, String content, LocalDateTime date) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
		this.date = date;
	}
	
	public User getSender() {
		return sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public String getSubject() {
		return subject;
	}
	public String getContent() {
		return content;
	}
	public LocalDateTime getDate() {
		return date;
	}
	
	private String formattDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDate = date.format(formatter);
        return formattedDate;
	}

	public String showEmailsInSent() {
		return receiver.getUsermail().getUserEmail() + "," +  subject + "," + formattDate() + "," + content ;
	}
	
	public String showEmails() {
		return sender.getUsermail().getUserEmail() + "," +  subject + "," + formattDate() + "," + content ;
	}
}
