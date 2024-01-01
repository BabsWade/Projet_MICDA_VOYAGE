package bus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ListeBus extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7638335444232733266L;
	private JTable table;

    public ListeBus() {
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
        model.addColumn("Action");
        

        fillTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton deleteButton = new JButton("Supprimer");

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
                        String query = "INSERT INTO bus (CodeBus, NomBus, Description, NombreSiege, EtatBus, SiegeRestant) " +
                                "VALUES (?, ?, ?, ?, ?, 0)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, codeField.getText());
                            preparedStatement.setString(2, nomField.getText());
                            preparedStatement.setString(3, descriptionField.getText());
                            preparedStatement.setInt(4, Integer.parseInt(nombreSiegeField.getText()));
                            preparedStatement.setString(5, etatBusField.getText());

                            preparedStatement.executeUpdate();

                            // Rafraîchir le tableau après ajout
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

                // Récupérer le nombre d'enregistrements dans la table "ticket"
                int nombreEnregistrements = getNombreEnregistrementsTicket();
                System.out.println("Nombre d'enregistrements dans la table 'ticket': " + nombreEnregistrements);

                // Mettre à jour la colonne SiegeReserve dans la table bus
                updateSiegeReserve(nombreEnregistrements);
            }
            private void updateSiegeReserve(int nombreEnregistrements) {
                try (Connection connection = ConnexionBD.getConnection()) {
                    if (connection != null) {
                        String updateQuery = "UPDATE bus SET SiegeReserve = ?, SiegeRestant = (NombreSiege - ?)";
                    	//String updateQuery = "UPDATE bus SET NomBus = ?, Description = ?, SiegeReserve = ?, SiegeRestant = (NombreSiege - ?), EtatBus = ? WHERE CodeBus = ?";

                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                            preparedStatement.setInt(1, nombreEnregistrements);
                            preparedStatement.setInt(2, nombreEnregistrements);
                           // preparedStatement.setInt(3, nombreEnregistrements);
                            preparedStatement.executeUpdate();
                        	//preparedStatement.setInt(1, nombreEnregistrements);
                        	//preparedStatement.setInt(2, nombreEnregistrements);
                        	//preparedStatement.setInt(3, nombreEnregistrements);
                        	//preparedStatement.setInt(4, nombreEnregistrements);  // Utilisation de nombreSiege à deux endroits différents, ajustez si nécessaire
                        	//preparedStatement.setInt(5, nombreEnregistrements);
                        	//preparedStatement.setInt(6, nombreEnregistrements);
                        	//preparedStatement.executeUpdate();
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
            
            private int getNombreEnregistrementsTicket() {
                int nombreEnregistrements = 0;
                try (Connection connection = ConnexionBD.getConnection()) {
                    if (connection != null) {
                        String query = "SELECT COUNT(*) FROM ticket";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                             ResultSet resultSet = preparedStatement.executeQuery()) {

                            if (resultSet.next()) {
                                nombreEnregistrements = resultSet.getInt(1);
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
                return nombreEnregistrements;
            }

        });


        
        
        /**deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour supprimer un bus
                // ...
            }
        });**/
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lorsque le bouton est cliqué, instanciez la classe AutreFenetre
                SuppBus suppBus = new SuppBus();
                
                // Affichez la nouvelle fenêtre
                suppBus.setVisible(true);
            }
        });

        panel.add(addButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        // Ajouter le bouton "Éditer" à chaque ligne
        addEditButtonToTable();
    }
    class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ListeBus.this.table.getSelectedRow();
            String codeBus = (String) ListeBus.this.table.getValueAt(selectedRow, 0);
            String nomBus = (String) ListeBus.this.table.getValueAt(selectedRow, 1);
            String description = (String) ListeBus.this.table.getValueAt(selectedRow, 2);
            int nombreSiege = (int) ListeBus.this.table.getValueAt(selectedRow, 3);
            String etatBus = (String) ListeBus.this.table.getValueAt(selectedRow, 4);

            // Instancier la boîte de dialogue d'édition avec les données actuelles du bus
            EditBusDialog editDialog = new EditBusDialog(ListeBus.this, codeBus, nomBus, description, nombreSiege, etatBus);

            // Afficher la boîte de dialogue et récupérer les données éditées
            if (editDialog.showDialog()) {
                String newNom = editDialog.getNom();
                String newDescription = editDialog.getDescription();
                int newNombreSiege = editDialog.getNombreSiege();
                String newEtatBus = editDialog.getEtatBus();

                // Mettre à jour les données dans le tableau
                ListeBus.this.table.setValueAt(newNom, selectedRow, 1);
                ListeBus.this.table.setValueAt(newDescription, selectedRow, 2);
                ListeBus.this.table.setValueAt(newNombreSiege, selectedRow, 3);
                ListeBus.this.table.setValueAt(newEtatBus, selectedRow, 4);

                // Mettre à jour les données dans la base de données
                ListeBus.this.editerBus(codeBus, newNom, newDescription, newNombreSiege, newEtatBus);
            }
        }
    }

    private void addEditButtonToTable() {
        TableColumn editColumn = table.getColumnModel().getColumn(5);
        editColumn.setCellRenderer(new ButtonRenderer());
        editColumn.setCellEditor(new ButtonEditor(new JCheckBox(), new EditButtonListener()));
    }

    private void fillTable() {
        try (Connection connection = ConnexionBD.getConnection()) {
            if (connection != null) {
                String query = "SELECT CodeBus, NomBus, Description, NombreSiege, EtatBus FROM bus";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        Object[] row = new Object[7]; // Ajoutez une colonne supplémentaire pour le bouton "Éditer"
                        row[0] = resultSet.getString("CodeBus");
                        row[1] = resultSet.getString("NomBus");
                        row[2] = resultSet.getString("Description");
                        row[3] = resultSet.getInt("NombreSiege");
                        row[4] = resultSet.getString("EtatBus");
                        row[5] = "Éditer";
                        row[6]= 0;
                    

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

    /**public void editerBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        
        //String updateQuery = "UPDATE bus SET SiegeReserve = ?, SiegeRestant = (NombreSiege - ?)";
    	String updateQuery = "UPDATE bus SET NomBus = ?, Description = ?, SiegeReserve = ?, SiegeRestant = (NombreSiege - ?), EtatBus = ? WHERE CodeBus = ?";

        System.out.println("Query: " + updateQuery);
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, nomBus);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, nombreSiege);
            preparedStatement.setString(4, etatBus);
            preparedStatement.setString(5, codeBus);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }**/
    
    public void editerBus(String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        String updateQuery = "UPDATE bus SET NomBus = ?, Description = ?, SiegeReserve = ?, SiegeRestant = (NombreSiege - ?), EtatBus = ? WHERE CodeBus = ?";
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, nomBus);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, nombreSiege);
            preparedStatement.setInt(4, nombreSiege);  // Utilisation de nombreSiege à deux endroits différents, ajustez si nécessaire
            preparedStatement.setString(5, etatBus);
            preparedStatement.setString(6, codeBus);

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

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private ActionListener listener;

    public ButtonEditor(JCheckBox checkBox, ActionListener listener) {
        super(checkBox);
        this.listener = listener;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                listener.actionPerformed(e);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

class EditBusDialog extends JDialog {
    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField nombreSiegeField;
    private JTextField etatBusField;

    private boolean confirmed;

    public EditBusDialog(JFrame parent, String codeBus, String nomBus, String description, int nombreSiege, String etatBus) {
        super(parent, "Éditer le bus", true);
        setLayout(new BorderLayout());
        setSize(300, 200);

        nomField = new JTextField(nomBus);
        descriptionField = new JTextField(description);
        nombreSiegeField = new JTextField(Integer.toString(nombreSiege));
        etatBusField = new JTextField(etatBus);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("NomBus:"));
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("NombreSiege:"));
        inputPanel.add(nombreSiegeField);
        inputPanel.add(new JLabel("EtatBus:"));
        inputPanel.add(etatBusField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);
    }

    public boolean showDialog() {
        confirmed = false;
        setVisible(true);
        return confirmed;
    }

    public String getNom() {
        return nomField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public int getNombreSiege() {
        try {
            return Integer.parseInt(nombreSiegeField.getText());
        } catch (NumberFormatException e) {
            return 0; // Gestion de l'erreur si la conversion échoue
        }
    }

    public String getEtatBus() {
        return etatBusField.getText();
    }
}
