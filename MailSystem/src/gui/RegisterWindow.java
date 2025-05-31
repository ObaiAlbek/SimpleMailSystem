package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import domain.EasyMail;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.IntStream;

public class RegisterWindow extends JFrame {
	
	private EasyMail fassade;
	private LoginWindow login;
	private JTextField firstNameField, lastNameField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<Integer> dayComboBox, yearComboBox;
    private JComboBox<String> monthComboBox;
    
    
    public RegisterWindow() {
    	setResizable(false);
    	this.fassade = new EasyMail();
    
        setTitle("RegisterWindow - EasyMail");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 754, 893);
    	setLocationRelativeTo(null); 
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel.setBounds(81, 80, 573, 709);
        panel.setLayout(null);
        contentPane.add(panel);

        JLabel titleLabel = new JLabel("RegisterWindow - EasyMail");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        titleLabel.setBounds(85, 11, 387, 53);
        panel.add(titleLabel);

        // First Name
        panel.add(createLabel("First Name:", 10, 87));
        firstNameField = createTextField(284, 96);
        panel.add(firstNameField);

        // Last Name
        panel.add(createLabel("Last Name:", 10, 150));
        lastNameField = createTextField(284, 150);
        panel.add(lastNameField);

        // Birthdate
        panel.add(createLabel("Birthdate:", 10, 229));

        Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        dayComboBox.setBounds(284, 229, 50, 29);
        panel.add(dayComboBox);

        String[] months = {
        	    "Januar", "Februar", "März", "April", "Mai", "Juni",
        	    "Juli", "August", "September", "Oktober", "November", "Dezember"
        	};
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        monthComboBox.setBounds(344, 229, 110, 29);
        panel.add(monthComboBox);

        Integer[] years = IntStream.rangeClosed(1900, java.time.LocalDate.now().getYear())
                                   .boxed().toArray(Integer[]::new);
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        yearComboBox.setBounds(464, 229, 80, 29);
        yearComboBox.setSelectedItem(2000); // default year
        panel.add(yearComboBox);

        // Username
        panel.add(createLabel("Username:", 10, 317));
        usernameField = createTextField(284, 323);
        panel.add(usernameField);

        // Password
        panel.add(createLabel("Password:", 10, 405));
        passwordField = createPasswordField(284, 411);
        panel.add(passwordField);

        // Confirm Password
        panel.add(createLabel("Confirm Password:", 10, 485));
        confirmPasswordField = createPasswordField(284, 491);
        panel.add(confirmPasswordField);

        // RegisterWindow Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        registerButton.setBounds(10, 565, 159, 43);
        panel.add(registerButton);
        registerButton.addActionListener(e -> handleRegister());

        // Link to Login
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(new Color(0, 0, 160));
        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        loginLabel.setBounds(406, 566, 117, 29);
        panel.add(loginLabel);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	handleLogIn();
            }
        });
        
        showWindow();
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textField.setBounds(x, y, 239, 29);
        return textField;
    }

    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        passwordField.setBounds(x, y, 239, 29);
        return passwordField;
    }

    private void handleRegister() {
    	try {
    		String firstName = firstNameField.getText();
        	String lastName = lastNameField.getText();
        	String userName = usernameField.getText();
        	char[] password = passwordField.getPassword();
        	char[] passwordConfirmation = confirmPasswordField.getPassword();
        	int day = (int) dayComboBox.getSelectedItem();
        	int year = (int) yearComboBox.getSelectedItem();
        	String month = (String) monthComboBox.getSelectedItem();
        	

			fassade.userRegister(firstName, lastName, userName, year, day, month, password, passwordConfirmation);
			Arrays.fill(password, ' ');
			Arrays.fill(passwordConfirmation, ' ');
			restInputs();
			this.closeWindow();
			showEasyMailWindow();
			
		} catch (Exception e) {
			showError(e.getMessage());
		}
    }
    
    private void handleLogIn() {
        login = new LoginWindow();
        login.showWindow();
        login.getFassade(fassade); 
        
        login.setLoginListener(() -> {
        	
            login.dispose(); 
            closeWindow();
            showEasyMailWindow();
        });
     
    }
    
    private void showEasyMailWindow() {
    	EasyMailWindow easyMail = new EasyMailWindow();
		easyMail.showWindow();
		easyMail.getFassade(fassade);
		easyMail.showUserDetails();
    }
    
    
    public void showWindow() {
    	this.setVisible(true);
    }
    public void closeWindow() {
    	this.dispose();
    }
    public void showError(String error) {
    	JOptionPane.showMessageDialog(this, error, "Error" , JOptionPane.ERROR_MESSAGE);
    }
    public void restInputs() {
    	this.firstNameField.setText("");
    	this.lastNameField.setText("");
    	this.usernameField.setText("");
    	this.passwordField.setText("");
    	this.passwordField.setText("");
    }

}
