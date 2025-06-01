package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TrashWindow extends TemplateWindow {

    private DefaultTableModel inboxTableModel;

    public TrashWindow() {
        super("Trash - EasyMail");
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
                easyMailWindow.getAllInboxEmails();
            }
        });
        navigationPanel.add(inbox);

        JLabel sentEmails = createLabel("Sent", 10, 61, 165, 39, 22);
        sentEmails.setForeground(Color.BLUE);
        sentEmails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sentEmails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SentWindow sentWindow = new SentWindow();
                closeWindow();
                sentWindow.showWindow();
                sentWindow.getAllSentEmails();
            }
        });
        navigationPanel.add(sentEmails);
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

    public void getAllTrashEmails() {
        ArrayList<String> getEmails = fassade.sendAllEmailsToTrashWindow();
        for (String tempEmail : getEmails) {
            String[] splitEmail = tempEmail.split(",");
            Object[] newEmail = {splitEmail[0], splitEmail[1], splitEmail[2]};
            inboxTableModel.addRow(newEmail);
        }
    }
}
