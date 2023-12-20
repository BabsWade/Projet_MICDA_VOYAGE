package bus;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    public static void main(String[] args) {
        // Test de la connexion à la base de données
        getConnection();
    }

   
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL de connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/micda_voyages?user=root&password=";

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
}

