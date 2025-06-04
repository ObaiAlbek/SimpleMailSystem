package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.email.EmailNotFoundException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EasyMailWindow extends TemplateWindow {

    private DefaultTableModel inboxTableModel;
    private  JTextField searchField;

    public EasyMailWindow() {
        super("EasyMail");
        initUI();
        showWindow();
    }

    private void initUI() {
        initNavigationPanel();
        initComposePanel();
        initTablePanel();
        getAllInboxEmails(""); 
        showUserDetails();
    }

   

    private void initNavigationPanel() {
        JPanel navigationPanel = createPanel(10, 273, 347, 451, new Color(230, 230, 230), true);
        contentPane.add(navigationPanel);
        navigationPanel.setLayout(null);

        JLabel sentEmails = createLabel("Sent", 10, 11, 165, 39, 22);
        sentEmails.setForeground(Color.BLUE);
        sentEmails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sentEmails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleSentClick();
            }
        });
        navigationPanel.add(sentEmails);

        JLabel trash = createLabel("Trash", 10, 61, 165, 39, 22);
        trash.setForeground(Color.BLUE);
        trash.setCursor(new Cursor(Cursor.HAND_CURSOR));
        trash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTrashClick();
            }
        });
        navigationPanel.add(trash);
    }
    
   

    private void initComposePanel() {
        JPanel composePanel = createPanel(367, 11, 750, 86, new Color(230, 230, 230), true);
        contentPane.add(composePanel);
        composePanel.setLayout(null);

        JLabel writeEmail = createLabel("New Email", 10, 11, 121, 64, 22);
        writeEmail.setForeground(Color.BLUE);
        writeEmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        writeEmail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleComposeEmail();
            }
        });
        composePanel.add(writeEmail);

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
			String email = fassade.searchEmailInInboxFolder(getSubjct);
			inboxTableModel.setRowCount(0); 
	        getAllInboxEmails(email); 
		} catch (EmailNotFoundException e) {
			this.showError(e.getMessage());
			getAllInboxEmails("");
		}
	}

    private void initTablePanel() {
        JPanel tablePanel = createPanel(367, 105, 750, 619, null, true);
        contentPane.add(tablePanel);
        tablePanel.setLayout(null);

        JScrollPane scrollPane = createTable("From");
        inboxTableModel = (DefaultTableModel) inboxTable.getModel();
        scrollPane.setBounds(0, 0, 750, 619);
        tablePanel.add(scrollPane);
    }

    private void handleSentClick() {
        SentWindow sentWindow = new SentWindow();
        closeWindow();
        sentWindow.showWindow();
        sentWindow.getAllSentEmails("");
        showUserDetails();
    }

    private void handleTrashClick() {
        TrashWindow trashWindow = new TrashWindow();
        closeWindow();
        trashWindow.showWindow();
        trashWindow.getAllTrashEmails();
        showUserDetails();
    }

    public void handleComposeEmail() {
        ComposeEmailWindow emailWindow = new ComposeEmailWindow();
        emailWindow.showWindow();

        String senderEmail = fassade.getUsernameFromCurrentUser();
        emailWindow.setSenderEmail(senderEmail);

        emailWindow.setEmailSentListener(() -> {
            inboxTableModel.setRowCount(0); 
           getAllInboxEmails(""); 
        });
    }

	public void getAllInboxEmails(String foundedEmail) {
		if (foundedEmail.trim().isEmpty()) {
			inboxTableModel.setRowCount(0);
			ArrayList<String> getEmails = fassade.sendAllEmailsToInboxWindow();
			if (getEmails != null && !getEmails.isEmpty())
				for (String tempEmail : getEmails) {
					String[] splitEmail = tempEmail.split(",");
					Object[] newEmail = { splitEmail[0], splitEmail[1], splitEmail[2] };
					inboxTableModel.addRow(newEmail);
				}
		} else {
			String[] splitEmail = foundedEmail.split(",");
			Object[] newEmail = { splitEmail[0], splitEmail[1], splitEmail[2] };
			inboxTableModel.addRow(newEmail);
		}

	}
}


