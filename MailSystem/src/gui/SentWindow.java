package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.email.EmailNotFoundException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SentWindow extends TemplateWindow {

	private DefaultTableModel inboxTableModel;
	private JTextField searchField;

	public SentWindow() {
		super("Sent - EasyMail");
		initUI();
	}

	private void initUI() {
		initNavigationPanel();
		initTablePanel();
		initComposePanel();
		showUserDetails();
	}

	private void initComposePanel() {
		JPanel composePanel = createPanel(367, 11, 750, 86, new Color(230, 230, 230), true);
		contentPane.add(composePanel);
		composePanel.setLayout(null);

		searchField = new PlaceholderTextField("Search By subject");
		searchField.setBounds(500, 30, 150, 40);
		composePanel.add(searchField);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(660, 30, 80, 40);
		composePanel.add(searchButton);
		searchButton.addActionListener(e -> handleSearching());

	}

	public void handleSearching() {
		try {
			String getSubjct = searchField.getText();
			String email = fassade.searchEmailInSentFolder(getSubjct);
			inboxTableModel.setRowCount(0); 
			getAllSentEmails(email); 
		} catch (EmailNotFoundException e) {
			this.showError(e.getMessage());
		}
	}

	private void initNavigationPanel() {
		JPanel navigationPanel = createPanel(10, 273, 347, 451, new Color(230, 230, 230), true);
		contentPane.add(navigationPanel);
		navigationPanel.setLayout(null);

		JLabel inbox = createLabel("Inbox", 10, 11, 165, 39, 22);
		inbox.setForeground(Color.BLUE);
		inbox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		inbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EasyMailWindow easyMailWindow = new EasyMailWindow();
				closeWindow();
				easyMailWindow.showWindow();
				easyMailWindow.getAllInboxEmails("");
			}
		});
		navigationPanel.add(inbox);

		JLabel trash = createLabel("Trash", 10, 61, 165, 39, 22);
		trash.setForeground(Color.BLUE);
		trash.setCursor(new Cursor(Cursor.HAND_CURSOR));
		trash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TrashWindow trashWindow = new TrashWindow();
				closeWindow();
				trashWindow.showWindow();
				trashWindow.getAllTrashEmails();
			}
		});
		navigationPanel.add(trash);
	}

	private void initTablePanel() {
		JPanel tablePanel = createPanel(367, 105, 750, 619, null, true);
		contentPane.add(tablePanel);
		tablePanel.setLayout(null);

		JScrollPane scrollPane = createTable("To");
		inboxTableModel = (DefaultTableModel) inboxTable.getModel();
		scrollPane.setBounds(0, 0, 750, 619);
		tablePanel.add(scrollPane);
	}

	public void getAllSentEmails(String email) {
		if (email.trim().isEmpty()) {
			ArrayList<String> getEmails = fassade.sendAllEmailsToSentWindow();
			String[] splitEmail;
			if (getEmails.size() > 0)
				for (String tempEmail : getEmails) {
					splitEmail = tempEmail.split(",");
					String to = splitEmail[0].toString();
					String subject = splitEmail[1];
					String date = splitEmail[2];
					Object[] newEmail = { to, subject, date };
					inboxTableModel.addRow(newEmail);
				}

		} else {
			String[] splitEmail = email.split(",");
			Object[] newEmail = { splitEmail[0], splitEmail[1], splitEmail[2] };
			inboxTableModel.addRow(newEmail);
		}

	}
}
