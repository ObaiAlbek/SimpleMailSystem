package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;

import domain.EasyMail;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginWindow extends JFrame {
	private JTextField txtUsername;
	private JPasswordField password;
	private EasyMail fassade;
	private JPanel panel;
	private LoginListener loginListener;
	
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 614, 541);
		setLocationRelativeTo(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(230, 230, 230));
		panel.setBounds(28, 25, 517, 450);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel logIn = new JLabel("Log in");
		logIn.setFont(new Font("Times New Roman", Font.BOLD, 30));
		logIn.setBounds(218, 11, 88, 57);
		panel.add(logIn);
		
		JLabel username = new JLabel("Username:");
		username.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		username.setBounds(10, 92, 120, 46);
		panel.add(username);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		txtUsername.setBounds(134, 92, 339, 46);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		password = new JPasswordField();
		password.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		password.setBounds(134, 180, 339, 46);
		panel.add(password);
		
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		password.setBounds(10, 180, 120, 46);
		panel.add(password);
		
		JButton btnLogIn = new JButton("Submit");
		btnLogIn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnLogIn.setBounds(10, 270, 120, 35);
		panel.add(btnLogIn);
		btnLogIn.addActionListener(e -> handleLogin() );
	}
	

	public void setLoginListener(LoginListener loginListener) {
	    this.loginListener = loginListener;
	}

	public boolean handleLogin() {
	    String username = txtUsername.getText();
	    char[] pass = password.getPassword();
	    
	    boolean loginSuccess = false;
	    try {
	        loginSuccess = fassade.userSignIn(username, pass);
	    } catch (Exception e) {
	        showError(e.getMessage());
	    } finally {
	        java.util.Arrays.fill(pass, ' ');
	    }

	    if (loginSuccess && loginListener != null) 
	        loginListener.onLoginSuccess();
	    
	    return loginSuccess;
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
		JOptionPane.showMessageDialog(this,  error,"Errore", JOptionPane.ERROR_MESSAGE);
	}
	
}
