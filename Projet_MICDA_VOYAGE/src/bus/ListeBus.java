package bus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ListeBus extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6856259460021850295L;
	private JTable table;

    public ListeBus() {
        setTitle("Tableau des Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Création du modèle de tableau
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Ajout des colonnes
        model.addColumn("CodeBus");
        model.addColumn("NomBus");
        model.addColumn("Description");
        model.addColumn("NombreSiege");
        model.addColumn("EtatBus");

        // Remplissage du tableau avec les données de la base de données
        fillTable();

        // Ajout du tableau à la fenêtre
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void fillTable() {
        // Obtenir une connexion à la base de données
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                // Exécuter une requête pour récupérer les données de la table "bus"
                String query = "SELECT CodeBus, NomBus, Description, NombreSiege, EtatBus FROM bus";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Ajouter les lignes au modèle de tableau
                    while (resultSet.next()) {
                        Object[] row = new Object[5];
                        row[0] = resultSet.getString("CodeBus");
                        row[1] = resultSet.getString("NomBus");
                        row[2] = resultSet.getString("Description");
                        row[3] = resultSet.getInt("NombreSiege");
                        row[4] = resultSet.getString("EtatBus");

                        ((DefaultTableModel) table.getModel()).addRow(row);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ListeBus frame = new ListeBus();
            frame.setVisible(true);
        });
    }
}
