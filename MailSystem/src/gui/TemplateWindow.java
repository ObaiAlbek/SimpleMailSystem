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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class TemplateWindow extends JFrame {

	protected JPanel contentPane;
	protected static  EasyMail fassade = new EasyMail();
	protected JLabel fullName, username, editProfile;
	protected JTable inboxTable;

	public TemplateWindow(String title) {
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1143, 774);
		setLocationRelativeTo(null);
		initContentPane();
	}

	private void initContentPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}
	
	
	
	protected void showUserDetails() {
		JPanel profilePanel = createPanel(10, 11, 347, 239, new Color(230, 230, 230), true);
		contentPane.add(profilePanel);
		profilePanel.setLayout(null);
		JLabel profile = createLabel("Profile", 10, 11, 203, 41, 30);
		profilePanel.add(profile);
		editProfile = createLabel("Edit profile", 10, 189, 165, 39, 22);
		editProfile.setForeground(Color.BLUE);
		profilePanel.add(editProfile);
		editProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editProfile.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	EditProfileWindow editProfile = new EditProfileWindow(); 
	            	editProfile.setUpdateProfileListener(() -> {
	            		showUserDetails();
	            	});
	     	
	            }
	        });

		String[] getDetails = fassade.sendUserDetails();
		String fullName = getDetails[0];
		String username = getDetails[1];
		this.fullName =	createLabel("",10,63, 327, 41,20);
		this.username = createLabel("",10, 106, 327, 39,20);
		this.fullName.setText("Full Name: " + fullName);
		this.username.setText("Email: " + username);
		profilePanel.add(this.fullName);
		profilePanel.add(this.username);
	}

	// Neue Methode
	protected JPanel createPanel(int x, int y, int width, int height, Color bgColor, boolean withBorder) {
		JPanel panel = new JPanel();
		panel.setBounds(x, y, width, height);
		panel.setBackground(bgColor != null ? bgColor : new Color(230, 230, 230));
		panel.setLayout(null);
		if (withBorder) {
			panel.setBorder(new LineBorder(Color.BLACK, 2));
		}
		return panel;
	}

	protected JScrollPane createTable(String from_To) {
		String[] columnNames = { from_To, "Subject", "Date" };
		DefaultTableModel inboxTableModel = new DefaultTableModel(columnNames, 0);
		inboxTable = new JTable(inboxTableModel);
		inboxTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		inboxTable.setRowHeight(24);
		inboxTable.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(inboxTable);
		scrollPane.setBounds(0, 0, 750, 619);
		return scrollPane;
	}

	

	protected JLabel createLabel(String text, int x, int y, int width, int height, int fontSize) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
		label.setBounds(x, y, width, height);
		return label;
	}

	protected JButton createButton(String text, int x, int y, int width, int height, int fontSize) {
		JButton button = new JButton(text);
		button.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
		button.setBounds(x, y, width, height);
		return button;
	}

	protected JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textField.setBounds(x, y, width, height);
		return textField;
	}

	protected JPasswordField createPasswordField(int x, int y, int width, int height) {
		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passwordField.setBounds(x, y, width, height);
		return passwordField;
	}

	protected void showWindow() {
		this.setVisible(true);
	}

	protected void closeWindow() {
		this.dispose();
	}

	protected void showError(String error) {
		JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	protected void showInfo(String info) {
		JOptionPane.showMessageDialog(this, info, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
