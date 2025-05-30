package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class EasyMailWindow extends JFrame {

	private JTable inboxTable;
	private DefaultTableModel inboxTableModel;

	public EasyMailWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 905, 702);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Profile Panel
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 230));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(10, 273, 223, 379);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel sentEmails = new JLabel("Sent");
		sentEmails.setForeground(new Color(0, 0, 255));
		sentEmails.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		sentEmails.setBounds(10, 11, 165, 39);
		panel.add(sentEmails);

		JLabel trash = new JLabel("Trash");
		trash.setForeground(new Color(0, 0, 255));
		trash.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		trash.setBounds(10, 61, 165, 39);
		panel.add(trash);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(230, 230, 230));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 11, 223, 239);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel profile = new JLabel("Profile");
		profile.setFont(new Font("Times New Roman", Font.BOLD, 30));
		profile.setBounds(10, 11, 203, 41);
		panel_1.add(profile);

		JLabel name = new JLabel("Full Name: ");
		name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		name.setBounds(10, 63, 203, 41);
		panel_1.add(name);

		JLabel username = new JLabel("Email: ");
		username.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		username.setBounds(10, 106, 203, 39);
		panel_1.add(username);

		JLabel editProfile = new JLabel("Edit profile");
		editProfile.setForeground(Color.BLUE);
		editProfile.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		editProfile.setBounds(10, 189, 165, 39);
		panel_1.add(editProfile);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBackground(new Color(230, 230, 230));
		panel_2.setBounds(255, 11, 624, 86);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel writeEmail = new JLabel("New Email");
		writeEmail.setForeground(Color.BLUE);
		writeEmail.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		writeEmail.setBounds(10, 11, 121, 64);
		panel_2.add(writeEmail);

		// ==== NEU: Inbox Panel mit JTable ====
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(255, 112, 624, 540);
		panel_3.setLayout(new BorderLayout());
		contentPane.add(panel_3);

		// NUR Spaltennamen, KEINE Daten
		String[] columnNames = { "From", "Subject", "Date" };
		inboxTableModel = new DefaultTableModel(columnNames, 0); // 0 bedeutet: keine Start-Daten

		inboxTable = new JTable(inboxTableModel);
		inboxTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		inboxTable.setRowHeight(24);
		inboxTable.setDefaultEditor(Object.class, null); // nicht editierbar

		JScrollPane scrollPane = new JScrollPane(inboxTable);
		panel_3.add(scrollPane, BorderLayout.CENTER);
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
