package Projet_MICDA_VOYAGE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class ConnexionBD {

	public static Connection getConnection() {
		// TODO Auto-generated constructor stub
        Connection connection = null;
        try {
            // JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL de connexion à la base de données
            String url = "jdbc:mysql://localhost:8889/micda_voyage?user=root&password=root";

            // Établir la connexion
            connection = DriverManager.getConnection(url);

            // Afficher un message de succès
            System.out.println("Connexion à la base de données réussie.");

        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du pilote JDBC : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }

        return connection;
    }

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Test de la connexion à la base de données
        getConnection();
	}

}

/*
 * 
 private void addDataToDatabase() {
				// TODO Auto-generated method stub
				comboBoxBusAssocie = new JComboBox<String>();
				comboBoxVilleArrivee = new JComboBox<String>();
				comboBoxVilleDepart = new JComboBox<String>();
				
			        try {
			            // Chargez le pilote JDBC
			            Class.forName("com.mysql.cj.jdbc.Driver");

			            // Modifiez les paramètres suivants en fonction de votre configuration MySQL
			            String url = "jdbc:mysql://localhost:8889/micda_voyage";
			            String username = "root";
			            String password = "root";

			            // Établissez la connexion à la base de données
			            try (Connection connection = DriverManager.getConnection(url, username, password)) {
			                // Préparez la requête d'insertion
			                String sql = "INSERT INTO Trajet (CodeTrajet, BusAssocie, NomTrajet, VilleDepart, VilleArrivee, DateDepart, HeureDepart) VALUES (?, ?, ?, ?, ?, ?, ?)";
			                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			                    // Remplacez ces méthodes par les getters appropriés de vos composants de formulaire
			                    preparedStatement.setString(1, txtCodeTrajet.getText());
			                   
			                    preparedStatement.setString(3, txtNomTrajet.getText());
			                 // Remplacer ces méthodes par les getters appropriés de vos composants de formulaire
			                    String villeDepart = comboBoxVilleDepart.getSelectedItem() != null ? comboBoxVilleDepart.getSelectedItem().toString() : "";
			                    String villeArrivee = comboBoxVilleArrivee.getSelectedItem() != null ? comboBoxVilleArrivee.getSelectedItem().toString() : "";
			                    String busAssocie = comboBoxBusAssocie.getSelectedItem() != null ? comboBoxBusAssocie.getSelectedItem().toString() : "";

			                    preparedStatement.setString(4, villeDepart);
			                    preparedStatement.setString(5, villeArrivee);
			                    preparedStatement.setString(2, busAssocie);

			                    // Récupérer la date sélectionnée dans le JDateChooser
			                    java.util.Date selectedDate = dateDepart.getDate();
			                    if (selectedDate != null) {
			                        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
			                        preparedStatement.setDate(6, sqlDate);
			                    } else {
			                        // Gérer le cas où la date n'est pas sélectionnée
			                        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une date.");
			                        return;  // Sortir de la méthode pour éviter d'exécuter la requête avec une date null
			                    }

			                    // Récupérer l'heure sélectionnée dans le JFormattedTextField
			                    SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
			                    java.util.Date heureSelected = (java.util.Date) txtHeureDepart.getValue();
			                    preparedStatement.setTimestamp(7, new java.sql.Timestamp(heureSelected.getTime()));


			                    // Exécuter la requête
			                    preparedStatement.executeUpdate();
			                    
			                    // Formattez la date selon le format de votre base de données
			                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String formattedDate = sdf.format(dateDepart.getDate());
			                    preparedStatement.setString(6, formattedDate);
			                    
								
			                    // Exécutez la requête
			                    preparedStatement.executeUpdate();
			                    JOptionPane.showMessageDialog(null, "Données ajoutées avec succès !");
			                }
			            }
			        } catch (ClassNotFoundException | SQLException ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout des données à la base de données.");
			        }
			}
*/
