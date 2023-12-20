package bus;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ListeBus extends JFrame {
    private static final long serialVersionUID = -6856259460021850295L;
    private JTable table;

    public ListeBus() {
        setTitle("Tableau des Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("CodeBus");
        model.addColumn("NomBus");
        model.addColumn("Description");
        model.addColumn("NombreSiege");
        model.addColumn("EtatBus");

        fillTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton deleteButton = new JButton("Supprimer");
        JButton editButton = new JButton("Éditer");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer une boîte de dialogue pour saisir les informations du nouvel enregistrement
                JTextField codeField = new JTextField();
                JTextField nomField = new JTextField();
                JTextField descriptionField = new JTextField();
                JTextField nombreSiegeField = new JTextField();
                JTextField etatBusField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(5, 2));
                panel.add(new JLabel("CodeBus:"));
                panel.add(codeField);
                panel.add(new JLabel("NomBus:"));
                panel.add(nomField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("NombreSiege:"));
                panel.add(nombreSiegeField);
                panel.add(new JLabel("EtatBus:"));
                panel.add(etatBusField);

                int result = JOptionPane.showConfirmDialog(ListeBus.this, panel, "Ajouter un Bus",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Ajouter les données à la base de données
                    try (Connection connection = ConnexionBD.getConnection()) {
                        String query = "INSERT INTO bus (CodeBus, NomBus, Description, NombreSiege, EtatBus) " +
                                "VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, codeField.getText());
                            preparedStatement.setString(2, nomField.getText());
                            preparedStatement.setString(3, descriptionField.getText());
                            preparedStatement.setInt(4, Integer.parseInt(nombreSiegeField.getText()));
                            preparedStatement.setString(5, etatBusField.getText());

                            preparedStatement.executeUpdate();

                            // Rafraîchir le tableau après l'ajout
                            refreshTable();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(ListeBus.this, "Erreur lors de l'ajout du bus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ListeBus.this, "Erreur lors de la connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            private void refreshTable() {
                // Effacer toutes les lignes actuelles du modèle
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                // Remplir à nouveau le tableau avec les données mises à jour de la base de données
                fillTable();
            }

        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour supprimer un bus
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour éditer un bus
            }
        });

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(editButton);

        add(panel, BorderLayout.SOUTH);
    }

    private void fillTable() {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "SELECT CodeBus, NomBus, Description, NombreSiege, EtatBus FROM bus";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

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

    public void ajouterBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        String query = "INSERT INTO bus (CodeBus, NomBus, Description, NombreSiege, EtatBus) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, codeBus);
            preparedStatement.setString(2, nomBus);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, nombreSiege);
            preparedStatement.setString(5, etatBus);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerBus(String codeBus) {
        String query = "DELETE FROM bus WHERE CodeBus = ?";
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, codeBus);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editerBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        String query = "UPDATE bus SET NomBus = ?, Description = ?, NombreSiege = ?, EtatBus = ? WHERE CodeBus = ?";
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nomBus);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, nombreSiege);
            preparedStatement.setString(4, etatBus);
            preparedStatement.setString(5, codeBus);

            preparedStatement.executeUpdate();
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