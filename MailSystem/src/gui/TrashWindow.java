package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import domain.EasyMail;

public class TrashWindow extends JFrame {


	private JTable inboxTable;
	private DefaultTableModel inboxTableModel;
	private EasyMail fassade;
	private JLabel fullName,username;
	
	public TrashWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1143, 774);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Profile Panel
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 230));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(10, 273, 347, 451);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel inbox = new JLabel("Inbox");
		inbox.setForeground(new Color(0, 0, 255));
		inbox.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		inbox.setBounds(10, 11, 165, 39);
		panel.add(inbox);
		inbox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		inbox.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	EasyMailWindow easyMail = new EasyMailWindow();
		    	closeWindow();
		    	easyMail.showWindow();
		    	easyMail.getFassade(fassade);
		    	easyMail.getAllInboxEmails();
		    }
		});

		

		JLabel sentEmails = new JLabel("Sent");
		sentEmails.setForeground(new Color(0, 0, 255));
		sentEmails.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		sentEmails.setBounds(10, 61, 165, 39);
		panel.add(sentEmails);
		
		sentEmails.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sentEmails.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	SentWindow sentWindow = new SentWindow();
		    	closeWindow();
		    	sentWindow.showWindow();
		    	sentWindow.getFassade(fassade);
		    	sentWindow.getAllSentEmails();
		    }
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(230, 230, 230));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 11, 347, 239);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel profile = new JLabel("Profile");
		profile.setFont(new Font("Times New Roman", Font.BOLD, 30));
		profile.setBounds(10, 11, 203, 41);
		panel_1.add(profile);

		fullName = new JLabel("Full Name: ");
		fullName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		fullName.setBounds(10, 63, 327, 41);
		panel_1.add(fullName);

		username = new JLabel("Email: ");
		username.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		username.setBounds(10, 106, 327, 39);
		panel_1.add(username);

		JLabel editProfile = new JLabel("Edit profile");
		editProfile.setForeground(Color.BLUE);
		editProfile.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		editProfile.setBounds(10, 189, 165, 39);
		panel_1.add(editProfile);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBackground(new Color(230, 230, 230));
		panel_2.setBounds(367, 11, 750, 86);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(367, 105, 750, 619);
		contentPane.add(panel_3);

		String[] columnNames = { "From", "Subject", "Date" };
		inboxTableModel = new DefaultTableModel(columnNames, 0); 

		inboxTable = new JTable(inboxTableModel);
		inboxTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		inboxTable.setRowHeight(24);
		inboxTable.setDefaultEditor(Object.class, null); 
		panel_3.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(inboxTable);
		scrollPane.setBounds(0, 0, 750, 619);
		panel_3.add(scrollPane);
	}
	
	public void showUserDetails() {
		String[] getDetails = fassade.sendUserDetails();
		String fullName = getDetails[0];
		String username = getDetails[1];
		
		this.fullName.setText(this.fullName.getText() + fullName);
		this.username.setText(this.username.getText() + username);
	}
	
	public void getAllTrashEmails() {
		ArrayList<String> getEmails = fassade.sendAllEmailsToTrashWindow();
		String[] splitEmail;
		if (getEmails.size() > 0)
			for (String tempEmail :getEmails ) {
				splitEmail = tempEmail.split(",");
				String from = splitEmail[0].toString();
				String subject = splitEmail[1];
				String date = splitEmail[2];
				Object[] newEmail = {from, subject, date};
				inboxTableModel.addRow(newEmail);
			}
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
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, "Error", error, JOptionPane.ERROR_MESSAGE);
	}

}
