package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import domain.EasyMail;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class ComposeEmailWindow extends JFrame {

	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextArea textAreaSubject;
	private JTextArea textAreaContent;
	private EasyMail fassade;
	private EmailSentListener emailSentListener;


	public ComposeEmailWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 802, 730);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 230));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 762, 669);
		panel.setLayout(null);
		contentPane.add(panel);

		JLabel composeEmail = new JLabel("Compose Email");
		composeEmail.setFont(new Font("Times New Roman", Font.BOLD, 30));
		composeEmail.setBounds(21, 27, 259, 54);
		panel.add(composeEmail);

		txtFrom = new JTextField();
		txtFrom.setEnabled(false);
		txtFrom.setBounds(102, 92, 509, 41);
		panel.add(txtFrom);
		txtFrom.setColumns(10);
		txtFrom.setEditable(false);

		JLabel from = new JLabel("From: ");
		from.setFont(new Font("Times New Roman", Font.BOLD, 20));
		from.setBounds(21, 92, 71, 41);
		panel.add(from);

		JLabel to = new JLabel("To:");
		to.setFont(new Font("Times New Roman", Font.BOLD, 20));
		to.setBounds(21, 165, 71, 41);
		panel.add(to);

		txtTo = new JTextField();
		txtTo.setColumns(10);
		txtTo.setBounds(102, 167, 509, 41);
		panel.add(txtTo);

		JLabel subject = new JLabel("Subject:");
		subject.setFont(new Font("Times New Roman", Font.BOLD, 20));
		subject.setBounds(21, 239, 71, 41);
		panel.add(subject);

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

		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSend.setBounds(21, 622, 133, 36);
		panel.add(btnSend);

		btnSend.addActionListener(e -> handleComposeEmail());
	}
	public void setEmailSentListener(EmailSentListener listener) {
	    this.emailSentListener = listener;
	}

	public void handleComposeEmail() {
		String to = txtTo.getText();
		String subject = textAreaSubject.getText();
		String content = textAreaContent.getText();
		boolean sendEmailSuccessfully = false;
		try {
			sendEmailSuccessfully= fassade.sendEmail(to, subject, content);
			txtTo.setText("");
			textAreaSubject.setText("");
			textAreaContent.setText("");
			if (sendEmailSuccessfully) {
				showInfo("Your email was sent successfully");
				  if (emailSentListener != null) 
				        emailSentListener.onEmailSent();
				    
			}
			
		} catch (Exception e) {
			showError(e.getMessage());
		}
	}

	public void setSenderEmail(String username) {
		txtFrom.setText(username);
	}

	public void getFassade(EasyMail fassade) {
		this.fassade = fassade;
	}

	public void showWindow() {
		this.setVisible(true);
	}

	public void closeWindow() {
		this.dispose();
	}
	
	public void showInfo(String info) {
		JOptionPane.showMessageDialog(this,info,"Success", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String error) {
		JOptionPane.showMessageDialog(this,error,"Error", JOptionPane.ERROR_MESSAGE);
	}
}
