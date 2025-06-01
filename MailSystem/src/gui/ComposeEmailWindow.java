package gui;

import javax.swing.*;
import java.awt.*;

public class ComposeEmailWindow extends TemplateWindow {

	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextArea textAreaSubject;
	private JTextArea textAreaContent;
	private EmailSentListener emailSentListener;

	public ComposeEmailWindow() {
		super("Compose Email - EasyMail"); 
		setBounds(100, 100, 802, 730);
		setLocationRelativeTo(null);
		initUI();
	}

	private void initUI() {
		JPanel panel = createPanel(10, 11, 762, 669, new Color(230, 230, 230), true);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel composeEmail = createLabel("Compose Email", 21, 27, 300, 54, 30);
		panel.add(composeEmail);

		JLabel fromLabel = createLabel("From: ", 21, 92, 71, 41, 20);
		panel.add(fromLabel);

		txtFrom = createTextField(102, 92, 509, 41);
		txtFrom.setEditable(false);
		panel.add(txtFrom);

		JLabel toLabel = createLabel("To:", 21, 165, 71, 41, 20);
		panel.add(toLabel);

		txtTo = createTextField(102, 167, 509, 41);
		panel.add(txtTo);

		JLabel subjectLabel = createLabel("Subject:", 21, 239, 71, 41, 20);
		panel.add(subjectLabel);

		// Subject TextArea + ScrollPane
		textAreaSubject = new JTextArea();
		textAreaSubject.setLineWrap(true);
		textAreaSubject.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		JScrollPane subjectScrollPane = new JScrollPane(textAreaSubject);
		subjectScrollPane.setBounds(102, 239, 509, 41);
		panel.add(subjectScrollPane);

		// Content TextArea + ScrollPane
		textAreaContent = new JTextArea();
		textAreaContent.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		JScrollPane contentScrollPane = new JScrollPane(textAreaContent);
		contentScrollPane.setBounds(21, 309, 617, 285);
		panel.add(contentScrollPane);

		JButton btnSend = createButton("Send", 21, 622, 133, 36, 20);
		panel.add(btnSend);

		btnSend.addActionListener(e -> handleComposeEmail());
	}

	public void setEmailSentListener(EmailSentListener listener) {
		this.emailSentListener = listener;
	}

	public void setSenderEmail(String username) {
		txtFrom.setText(username);
	}

	public void handleComposeEmail() {
		String to = txtTo.getText();
		String subject = textAreaSubject.getText();
		String content = textAreaContent.getText();
		boolean sendEmailSuccessfully = false;

		try {
			sendEmailSuccessfully = fassade.sendEmail(to, subject, content);
			if (sendEmailSuccessfully) {
				showInfo("Your email was sent successfully");
				if (emailSentListener != null) 
					emailSentListener.onEmailSent();
		
				txtTo.setText("");
				textAreaSubject.setText("");
				textAreaContent.setText("");
			}
		} catch (Exception e) {
			showError(e.getMessage());
		}
	}
}
