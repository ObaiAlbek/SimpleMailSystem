package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.IntStream;

public class RegisterWindow extends TemplateWindow {

    private LoginWindow login;
    private JTextField firstNameField, lastNameField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<Integer> dayComboBox, yearComboBox;
    private JComboBox<String> monthComboBox;

    public RegisterWindow() {
        super("RegisterWindow - EasyMail"); 
        setBounds(100, 100, 754, 893);
		setLocationRelativeTo(null);
        initUI();
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
            closeWindow();
            showEasyMailWindow();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void handleLogIn() {
        login = new LoginWindow();
        login.showWindow();
        login.setLoginListener(() -> {
            login.closeWindow();
            closeWindow();
            showEasyMailWindow();
        });
    }

    private void showEasyMailWindow() {
        EasyMailWindow easyMail = new EasyMailWindow();
        easyMail.showWindow();
    }
    
    
    

    private void initUI() {
        JPanel panel = createPanel(81, 80, 573, 709, new Color(230, 230, 230), true);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel titleLabel = createLabel("Register - EasyMail", 85, 11, 387, 53, 30);
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

        String[] months = { "Januar", "Februar", "März", "April", "Mai", "Juni",
                            "Juli", "August", "September", "Oktober", "November", "Dezember" };
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        monthComboBox.setBounds(344, 229, 110, 29);
        panel.add(monthComboBox);

        Integer[] years = IntStream.rangeClosed(1900, java.time.LocalDate.now().getYear()).boxed().toArray(Integer[]::new);
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        yearComboBox.setBounds(464, 229, 80, 29);
        yearComboBox.setSelectedItem(2000);
        panel.add(yearComboBox);

        // Username
        panel.add(createLabel("Username:", 10, 317, 200, 30, 25));
        usernameField = createTextField(284, 323, 239, 29);
        panel.add(usernameField);

        // Password
        panel.add(createLabel("Password:", 10, 405, 200, 30, 25));
        passwordField = createPasswordField(284, 411, 239, 29);
        panel.add(passwordField);

        // Confirm Password
        panel.add(createLabel("Confirm Password:", 10, 485, 200, 30, 25));
        confirmPasswordField = createPasswordField(284, 491, 239, 29);
        panel.add(confirmPasswordField);

        // Register Button
        JButton registerButton = createButton("Register", 10, 565, 159, 43, 18);
        panel.add(registerButton);
        registerButton.addActionListener(e -> handleRegister());

        // Link to Login
        JLabel loginLabel = createLabel("Login", 406, 566, 117, 29, 25);
        loginLabel.setForeground(new Color(0, 0, 160));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(loginLabel);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleLogIn();
            }
        });

        showWindow();
    }

  

    private void restInputs() {
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.usernameField.setText("");
        this.passwordField.setText("");
        this.confirmPasswordField.setText("");
    }
}

