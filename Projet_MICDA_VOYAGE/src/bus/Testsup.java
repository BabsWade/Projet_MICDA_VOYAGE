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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Testsup extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5697902934882215541L;
	private JTable table;

    public Testsup() {
        setTitle("Tableau des Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("CodeBus");
        model.addColumn("NomBus");
        model.addColumn("Description");
        model.addColumn("NombreSiege");
        model.addColumn("EtatBus");
        model.addColumn("Éditer");
        model.addColumn("Supprimer");

        fillTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Ajouter");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                int result = JOptionPane.showConfirmDialog(Testsup.this, panel, "Ajouter un Bus",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String codeBus = codeField.getText();
                    String nomBus = nomField.getText();
                    String description = descriptionField.getText();
                    int nombreSiege = Integer.parseInt(nombreSiegeField.getText());
                    String etatBus = etatBusField.getText();

                    ajouterBus(codeBus, nomBus, description, nombreSiege, etatBus);
                    refreshTable();
                }
            }
        });

        panel.add(addButton);

        add(panel, BorderLayout.SOUTH);

        // Ajouter le bouton "Éditer" et "Supprimer" à chaque ligne
        addEditAndDeleteButtonsToTable();
    }
    
    
 // Ajouter une nouvelle classe pour la fenêtre d'édition
    class EditBusWindow extends JFrame {
        /**
		 * 
		 */
		private static final long serialVersionUID = 4691300486702745762L;
		private JTextField codeField;
        private JTextField nomField;
        private JTextField descriptionField;
        private JTextField nombreSiegeField;
        private JTextField etatBusField;

        public EditBusWindow(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
            setTitle("Édition de Bus");
            setSize(400, 300);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            codeField = new JTextField(codeBus);
            nomField = new JTextField(nomBus);
            descriptionField = new JTextField(description);
            nombreSiegeField = new JTextField(String.valueOf(nombreSiege));
            etatBusField = new JTextField(etatBus);

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

            JButton saveButton = new JButton("Enregistrer");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Récupérer les valeurs modifiées
                    String newCodeBus = codeField.getText();
                    String newNomBus = nomField.getText();
                    String newDescription = descriptionField.getText();
                    int newNombreSiege = Integer.parseInt(nombreSiegeField.getText());
                    String newEtatBus = etatBusField.getText();

                    // Appeler la méthode pour éditer le bus
                    Testsup parentFrame = (Testsup) SwingUtilities.getWindowAncestor(EditBusWindow.this);
                    parentFrame.editerBus(newCodeBus, newNomBus, newDescription, newNombreSiege, newEtatBus);
                    parentFrame.refreshTable();

                    // Fermer la fenêtre d'édition
                    dispose();
                }
            });

            panel.add(saveButton);

            add(panel, BorderLayout.CENTER);
        }
    }

    // Modifier la classe EditButtonListener pour utiliser la nouvelle fenêtre d'édition
    class EditButtonListener implements ActionListener {
        private int editedRow;

        public void setEditedRow(int editedRow) {
            this.editedRow = editedRow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Récupérer les valeurs de la ligne sélectionnée
            String codeBus = table.getValueAt(editedRow, 0).toString();
            String nomBus = table.getValueAt(editedRow, 1).toString();
            String description = table.getValueAt(editedRow, 2).toString();
            int nombreSiege = Integer.parseInt(table.getValueAt(editedRow, 3).toString());
            String etatBus = table.getValueAt(editedRow, 4).toString();

            // Ouvrir la fenêtre d'édition avec les valeurs actuelles
            EditBusWindow editWindow = new EditBusWindow(codeBus, nomBus, description, nombreSiege, etatBus);
            editWindow.setVisible(true);
        }
    }


    private void addEditAndDeleteButtonsToTable() {
        TableColumn editColumn = table.getColumnModel().getColumn(5);
        editColumn.setCellRenderer(new ButtonRenderer());
        editColumn.setCellEditor(new ButtonEditor(new JCheckBox(), new EditButtonListener()));

        TableColumn deleteColumn = table.getColumnModel().getColumn(6);
        deleteColumn.setCellRenderer(new ButtonRenderer());
        deleteColumn.setCellEditor(new ButtonEditor(new JCheckBox(), new DeleteButtonListener(table)));
    }

    private void fillTable() {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "SELECT CodeBus, NomBus, Description, NombreSiege, EtatBus FROM bus";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        Object[] row = new Object[7];
                        row[0] = resultSet.getString("CodeBus");
                        row[1] = resultSet.getString("NomBus");
                        row[2] = resultSet.getString("Description");
                        row[3] = resultSet.getInt("NombreSiege");
                        row[4] = resultSet.getString("EtatBus");
                        row[5] = "Éditer";
                        row[6] = "Supprimer";

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

    void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        fillTable();
    }

    public void ajouterBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO bus (CodeBus, NomBus, Description, NombreSiege, EtatBus) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, codeBus);
                    preparedStatement.setString(2, nomBus);
                    preparedStatement.setString(3, description);
                    preparedStatement.setInt(4, nombreSiege);
                    preparedStatement.setString(5, etatBus);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du bus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerBus(String codeBus) {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "DELETE FROM bus WHERE CodeBus = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, codeBus);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du bus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editerBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "UPDATE bus SET NomBus = ?, Description = ?, NombreSiege = ?, EtatBus = ? WHERE CodeBus = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, nomBus);
                    preparedStatement.setString(2, description);
                    preparedStatement.setInt(3, nombreSiege);
                    preparedStatement.setString(4, etatBus);
                    preparedStatement.setString(5, codeBus);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du bus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Testsup frame = new Testsup();
            frame.setVisible(true);
        });
    }
}


/**class DeleteButtonListener implements ActionListener {
	private int editedRow;

	public void setEditedRow(int editedRow) {
	    this.editedRow = editedRow;
	}
    private JTable table;  // Ajoutez la référence à la table

    public DeleteButtonListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce bus ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
        	
            // Récupérer le codeBus de la ligne sélectionnée
            String codeBus = table.getValueAt(editedRow, 0).toString();

            // Appeler la méthode de suppression
            Testsup parentFrame = (Testsup) SwingUtilities.getWindowAncestor(table);
            parentFrame.supprimerBus(codeBus);
            parentFrame.refreshTable();
        }
        
    }
}**/
