package Projet_MICDA_VOYAGE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Connexion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Connexion() {
		setTitle("Page Connexion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245,248, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Connexion = new JPanel();
		Connexion.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Connexion.setBackground(Color.decode("#04224c"));
		Connexion.setBounds(250, 30, 500, 600);
		contentPane.add(Connexion);
		Connexion.setLayout(null);
		
		JLabel lableTitle = new JLabel("Page Connexion");
		lableTitle.setBounds(50, 18, 400, 50);
		lableTitle.setForeground(UIManager.getColor("Button.highlight"));
		lableTitle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		lableTitle.setHorizontalAlignment(SwingConstants.CENTER);
		Connexion.add(lableTitle);
		
		txtUser = new JTextField();
		txtUser.setBounds(200, 200, 225, 30);
		Connexion.add(txtUser);
		txtUser.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(200, 300, 225, 30);
		Connexion.add(txtPassword);
		
		JLabel labelUsername = new JLabel("Username :");
		labelUsername.setForeground(UIManager.getColor("Button.highlight"));
		labelUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelUsername.setBounds(100, 207, 100, 16);
		Connexion.add(labelUsername);
		
		JLabel labelPassword = new JLabel("Password : ");
		labelPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelPassword.setForeground(UIManager.getColor("Button.highlight"));
		labelPassword.setBounds(100, 307, 100, 16);
		Connexion.add(labelPassword);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBackground(new Color(7, 73, 217));
		btnConnexion.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnConnexion.setForeground(Color.decode("#04224c"));
		btnConnexion.setBounds(200, 431, 120, 35);
		Connexion.add(btnConnexion);
	}
}
