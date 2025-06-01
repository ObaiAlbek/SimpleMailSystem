package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditProfileWindow extends TemplateWindow {

	private JTextField firstNameField, lastNameField;
	private JPasswordField passwordField, confirmPasswordField;
	private JComboBox<Integer> dayComboBox, yearComboBox;
	private JComboBox<String> monthComboBox;
	private UpdateProfileListener updateListener;

	public EditProfileWindow() {
		super("Sent - Edit Profile");
		setBounds(100, 100, 754, 893);
		setLocationRelativeTo(null);
		initUI();
	}

	private void initUI() {
		JPanel panel = createPanel(81, 80, 573, 709, new Color(230, 230, 230), true);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel titleLabel = createLabel("Edit Profile - EasyMail", 160, 11, 387, 53, 30);
		panel.add(titleLabel);

		// First Name
		panel.add(createLabel("First Name:", 10, 87, 200, 30, 25));
		firstNameField = createTextField(284, 96, 239, 29);
		panel.add(firstNameField);

		// Last Name
		panel.add(createLabel("Last Name:", 10, 150, 200, 30, 25));
		lastNameField = createTextField(284, 150, 239, 29);
		panel.add(lastNameField);

		// Birthdate
		panel.add(createLabel("Birthdate:", 10, 229, 200, 30, 25));

		Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
		dayComboBox = new JComboBox<>(days);
		dayComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dayComboBox.setBounds(284, 229, 50, 29);
		panel.add(dayComboBox);

		String[] months = { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September",
				"Oktober", "November", "Dezember" };
		monthComboBox = new JComboBox<>(months);
		monthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		monthComboBox.setBounds(344, 229, 110, 29);
		panel.add(monthComboBox);

		Integer[] years = IntStream.rangeClosed(1900, java.time.LocalDate.now().getYear()).boxed()
				.toArray(Integer[]::new);
		yearComboBox = new JComboBox<>(years);
		yearComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		yearComboBox.setBounds(464, 229, 80, 29);
		yearComboBox.setSelectedItem(2000);
		panel.add(yearComboBox);

	
		// Password
		panel.add(createLabel("Password:", 10, 325, 200, 30, 25));
		passwordField = createPasswordField(284, 331, 239, 29);
		panel.add(passwordField);

		// Confirm Password
		panel.add(createLabel("Confirm Password:", 10, 405, 200, 30, 25));
		confirmPasswordField = createPasswordField(284, 411, 239, 29);
		panel.add(confirmPasswordField);

		// Submit Button
		JButton registerButton = createButton("submit", 10, 485, 159, 43, 18);
		panel.add(registerButton);
		registerButton.addActionListener(e -> handleEditProfile());

		showWindow();
	}
	
	 public void setUpdateProfileListener(UpdateProfileListener updateListener) {
	        this.updateListener = updateListener;
	    }
	
	 
	public void handleEditProfile() {
		try {
			String username = fassade.getUsernameFromCurrentUser();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			char[] password = passwordField.getPassword();
			char[] passwordConfirmation = confirmPasswordField.getPassword();
			int day = (int) dayComboBox.getSelectedItem();
			int year = (int) yearComboBox.getSelectedItem();
			String month = (String) monthComboBox.getSelectedItem();

			fassade.updateUser(firstName, lastName, username, year, day, month, password, passwordConfirmation);
			Arrays.fill(password, ' ');
			Arrays.fill(passwordConfirmation, ' ');
			
		    if (updateListener != null) {
		    	updateListener.onUpdateSuccess();
		    	showInfo("Profile updated successfully!");
	        }
			
		} catch (Exception e) {
			showError(e.getMessage());
		}
	}
	
	private void restInputs() {
		this.firstNameField.setText("");
		this.lastNameField.setText("");
		this.passwordField.setText("");
		this.confirmPasswordField.setText("");
	}

}
