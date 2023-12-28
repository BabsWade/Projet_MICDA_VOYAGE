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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import  Projet_MICDA_VOYAGE.ConnexionBD;
public class Connexion extends JFrame {
	private ConnexionBD connexionDB;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;

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
	private void openTrajetInterface() {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                Trajet frame = new Trajet();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

	// Function to handle user authentication
    private boolean authenticateUser(String username, String password) {
        // Connexion à la base de données
        try (Connection connection = connexionDB.getConnection()) {
            if (connection != null) {
                // Requête SQL pour la vérification des informations d'identification
                String query = "SELECT * FROM Users WHERE username=? AND password=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Paramètres de la requête
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    // Exécution de la requête
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Si une correspondance est trouvée, l'utilisateur est authentifié
                        return resultSet.next();
                    }
                }
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
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
		
		
		
		
		// Connexion
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Récupérer les informations saisies par l'utilisateur
		        String username = txtUser.getText();
		        String password = new String(txtPassword.getPassword());

		        // Vérifier les informations d'identification
		        if (authenticateUser(username, password)) {
		            // Utilisateur authentifié, redirigez vers la page Trajet
		            dispose(); // Ferme la fenêtre de connexion
		            openTrajetInterface(); // Ouvre la fenêtre Trajet
		        } else {
		            // Authentification échouée, afficher un message d'erreur
		            JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect. Veuillez entrer vos informations correctes.", "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		btnConnexion.setBackground(new Color(7, 73, 217));
		btnConnexion.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnConnexion.setForeground(Color.decode("#04224c"));
		btnConnexion.setBounds(200, 431, 120, 35);
		Connexion.add(btnConnexion);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(200, 303, 225, 30);
		Connexion.add(txtPassword);
		Connexion.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lableTitle, txtUser, labelUsername, labelPassword, btnConnexion, txtPassword}));
		
	}
}
