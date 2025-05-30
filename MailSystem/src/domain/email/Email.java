package domain.email;
import java.time.LocalDateTime;

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

	@Override
	public String toString() {
		return "Email [sender=" + sender + ", receiver=" + receiver + ", subject=" + subject + ", content=" + content
				+ ", date=" + date + "]";
	}
}
