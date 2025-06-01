package gui;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends TemplateWindow {

    private JTextField txtUsername;
    private JPasswordField password;
    private LoginListener loginListener;

    public LoginWindow() {
        super("Login - EasyMail");
		setBounds(100, 100, 614, 541);
		setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // Main Panel
        JPanel panel = createPanel(28, 25, 517, 450, new Color(230, 230, 230), true);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel logIn = createLabel("Log in", 218, 11, 200, 57, 30);
        panel.add(logIn);

        JLabel usernameLabel = createLabel("Username:", 10, 92, 120, 46, 25);
        panel.add(usernameLabel);

        txtUsername = createTextField(134, 92, 339, 46);
        panel.add(txtUsername);

        JLabel passwordLabel = createLabel("Password:", 10, 180, 120, 46, 25);
        panel.add(passwordLabel);

        password = createPasswordField(134, 180, 339, 46);
        panel.add(password);

        JButton btnLogIn = createButton("Submit", 10, 270, 120, 35, 16);
        panel.add(btnLogIn);

        // Button Action
        btnLogIn.addActionListener(e -> handleLogin());
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public boolean handleLogin() {
        String usernameInput = txtUsername.getText();
        char[] pass = password.getPassword();

        boolean loginSuccess = false;
        try {
            loginSuccess = fassade.userSignIn(usernameInput, pass);
        } catch (Exception e) {
            showError(e.getMessage());
        } finally {
            java.util.Arrays.fill(pass, ' '); 
        }

        if (loginSuccess && loginListener != null) {
            loginListener.onLoginSuccess();
        }
        return loginSuccess;
    }
}

