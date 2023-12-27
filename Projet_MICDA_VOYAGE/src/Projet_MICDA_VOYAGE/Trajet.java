package Projet_MICDA_VOYAGE;
import  Projet_MICDA_VOYAGE.ConnexionBD;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JLocaleChooser;
import com.toedter.components.JLocaleChooserBeanInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.JTableHeader;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Trajet extends JFrame {
	private ConnexionBD connexionDB;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodeTrajet;
	private JTextField txtNomTrajet;
	private JTable tableTrajet;
	private JDateChooser dateDepart;
	private JTable table;
	private JTable table_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public Trajet() {
		//connexionDB = new ConnexionDB();
		setTitle("Trajet Bus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		
		contentPane.setBackground(Color.decode("#04224c"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Trajet Bus");
		lblNewLabel.setBounds(650, 15, 200, 50);
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		JLabel labelCodeTrajet = new JLabel("Code Trajet :");
		labelCodeTrajet.setBounds(50, 98, 100, 20);
		labelCodeTrajet.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelCodeTrajet.setForeground(UIManager.getColor("Button.highlight"));
		contentPane.add(labelCodeTrajet);
		
		txtCodeTrajet = new JTextField();
		txtCodeTrajet.setBounds(160, 96, 200, 30);
		contentPane.add(txtCodeTrajet);
		txtCodeTrajet.setColumns(10);
		
		JLabel labelVilleDepart = new JLabel("Ville Départ :");
		labelVilleDepart.setBounds(50, 174, 100, 20);
		labelVilleDepart.setForeground(Color.WHITE);
		labelVilleDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelVilleDepart);
		
		JLabel labelBusAssocie = new JLabel("Bus Associe :");
		labelBusAssocie.setBounds(390, 101, 100, 20);
		labelBusAssocie.setForeground(Color.WHITE);
		labelBusAssocie.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelBusAssocie);
		
		JLabel labelVilleArrivee = new JLabel("Ville Arrivée :");
		labelVilleArrivee.setBounds(390, 171, 100, 20);
		labelVilleArrivee.setForeground(Color.WHITE);
		labelVilleArrivee.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelVilleArrivee);
		
		JLabel labelNomTrajet = new JLabel("Nom Trajet :");
		labelNomTrajet.setBounds(740, 101, 100, 20);
		labelNomTrajet.setForeground(Color.WHITE);
		labelNomTrajet.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelNomTrajet);
		
		JLabel labelDateDepart = new JLabel("Date Départ :");
		labelDateDepart.setBounds(740, 171, 100, 20);
		labelDateDepart.setForeground(Color.WHITE);
		labelDateDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelDateDepart);
		
		txtNomTrajet = new JTextField();
		txtNomTrajet.setBounds(850, 96, 200, 30);
		txtNomTrajet.setColumns(10);
		contentPane.add(txtNomTrajet);
		
		JLabel labelHeureDepart = new JLabel("Heure Départ :");
		labelHeureDepart.setBounds(1100, 101, 105, 20);
		labelHeureDepart.setForeground(Color.WHITE);
		labelHeureDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(labelHeureDepart);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(411, 254, 117, 35);
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnUpdate.setForeground(UIManager.getColor("CheckBox.foreground"));
		contentPane.add(btnUpdate);
		
		
		//Bouton Ajout
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(570, 255, 117, 35);
		
		btnAdd.setForeground(UIManager.getColor("CheckBox.foreground"));
		btnAdd.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		contentPane.add(btnAdd);
		
		
		
		//Suppression des donnee  de la BD
		JButton btnDelete = new JButton("Delete");
		
		btnDelete.setBounds(721, 255, 117, 35);
		btnDelete.setForeground(UIManager.getColor("CheckBox.foreground"));
		btnDelete.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		contentPane.add(btnDelete);
		
		JComboBox comboBoxVilleArrivee = new JComboBox();
		comboBoxVilleArrivee.setBounds(500, 173, 200, 30);
		comboBoxVilleArrivee.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBoxVilleArrivee.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		contentPane.add(comboBoxVilleArrivee);
		
		JComboBox comboBoxBusAssocie = new JComboBox();
		comboBoxBusAssocie.setBounds(502, 97, 200, 30);
		comboBoxBusAssocie.setModel(new DefaultComboBoxModel(new String[] {"AA001-DK01", "AA002-DK01", "AA003-DK01", "AA004-DK02", "AA005-DK02", "AA006-TH01", "AA007-TH01", "AA008-TH01", "AA009-TH02", "AA010-TH02", "AA011-TH03", "AA012-TH03", "AA013-KL01", "AA014-KL01", "AA015-ZG01", "AA016-ZG01"}));
		comboBoxBusAssocie.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		contentPane.add(comboBoxBusAssocie);
		
		JComboBox comboBoxVilleDepart = new JComboBox();
		comboBoxVilleDepart.setBounds(160, 173, 200, 30);
		comboBoxVilleDepart.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBoxVilleDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		contentPane.add(comboBoxVilleDepart);
		
		
		//Date depart
		dateDepart = new JDateChooser();
		dateDepart.setBounds(850, 165, 200, 30);
		getContentPane().add(dateDepart);
		
		
		//heureDepart
		SimpleDateFormat heureDepart = new SimpleDateFormat("HH:MM");//Creer un objet SimpleDateFormat pour formatter l'heure si on formatte pas il affiche par defaut date et heure
		JFormattedTextField txtHeureDepart = new JFormattedTextField(new DateFormatter(heureDepart));
		txtHeureDepart.setBounds(1220, 96, 200, 30);
		txtHeureDepart.setValue(new Date()); // valeur par defaut date actuel
		getContentPane().add(txtHeureDepart);
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollpane.setBounds(50, 300, 1350, 450);
		getContentPane().add(scrollpane);
		
		table_1 = new JTable();
		table_1.setInheritsPopupMenu(true);
		table_1.setIgnoreRepaint(true);
		table_1.setAutoCreateRowSorter(true);
		table_1.setDragEnabled(true);
		table_1.setDoubleBuffered(true);
		table_1.setFocusCycleRoot(true);
		table_1.setFocusTraversalPolicyProvider(true);
		//selection d'une ligne du tableau pour le maj
		table_1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try (Connection connection = ConnexionBD.getConnection()) {
		            if (connection != null) {
		                // Obtenir la ligne sélectionnée
		                int selectedRow = table_1.getSelectedRow();

		                // Vérifier si une ligne est sélectionnée
		                if (selectedRow != -1) {
		                    // Récupérer les données de la ligne sélectionnée
		                    String codeTrajet = table_1.getValueAt(selectedRow, 0).toString();
		                    String busAssocie = table_1.getValueAt(selectedRow, 1).toString();
		                    String nomTrajet = table_1.getValueAt(selectedRow, 2).toString();
		                    String villeDepart = table_1.getValueAt(selectedRow, 3).toString();
		                    String villeArrivee = table_1.getValueAt(selectedRow, 4).toString();
		                    String dateDepart = table_1.getValueAt(selectedRow, 5).toString();
		                    String heureDepart = table_1.getValueAt(selectedRow, 6).toString();

		                    // Utiliser les données récupérées comme nécessaire
		                    txtCodeTrajet.setText(codeTrajet);
		                    comboBoxBusAssocie.setSelectedItem(busAssocie);
		                    txtNomTrajet.setText(nomTrajet);
		                    comboBoxVilleDepart.setSelectedItem(villeDepart);
		                    comboBoxVilleArrivee.setSelectedItem(villeArrivee);
		                    // dateDepart.setDate(/* convertir dateDepart en java.util.Date si nécessaire */);		                    
		                    txtHeureDepart.setText(heureDepart);
		                }
		            } else {
		                System.out.println("La connexion à la base de données a échoué.");
		            }
		        } catch (Exception evt) {
		            evt.printStackTrace();
		        }
		    }
		});

		
		scrollpane.setViewportView(table_1);
		//Create a separate component for column headers
		JTableHeader header = table_1.getTableHeader();
		scrollpane.setColumnHeaderView(header);
		table_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//table_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		header.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		table_1.setForeground(UIManager.getColor("Button.darkShadow"));
		table_1.setBackground(UIManager.getColor("Button.highlight"));
		table_1.setFillsViewportHeight(true);
		table_1.setColumnSelectionAllowed(true);
		table_1.setCellSelectionEnabled(true);
		table_1.setSurrendersFocusOnKeystroke(true);
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
						"Code Trajet", "Bus Associe", "Nom Trajet", "Ville Depart", "Ville Arrivee", "Date Depart", "Heure Depart"
				}
				));
		
		//Affichage 
		
	        try (Connection connection = ConnexionBD.getConnection()) {
	            if (connection != null) {
	                String query = "SELECT codeTrajet, BusAssocie, NomTrajet, VilleDepart, VilleArrivee, DateDepart, HeureDepart FROM Trajet ORDER BY DateDepart DESC";
	                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	                     ResultSet resultSet = preparedStatement.executeQuery()) {

	                    while (resultSet.next()) {
	                        Object[] row = new Object[8]; // Ajoutez une colonne supplémentaire pour le bouton "Éditer"
	                        row[0] = resultSet.getString("CodeTrajet");
	                        row[1] = resultSet.getString("BusAssocie");
	                        row[2] = resultSet.getString("NomTrajet");
	                        row[3] = resultSet.getString("VilleDepart");
	                        row[4] = resultSet.getString("VilleArrivee");
	                        row[5] = resultSet.getString("DateDepart");
	                        row[6] = resultSet.getString("HeureDepart");
	                        row[7] = "Éditer";

	                        ((DefaultTableModel) table_1.getModel()).addRow(row);
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
		
	        
	      
		
		//Fonction Ajout dans la BD
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Recuperation des donnees du formualaires
				 String codeTrajet = txtCodeTrajet.getText();
				 String nomTrajet = txtNomTrajet.getText();
				 String Depart = comboBoxVilleDepart.getSelectedItem().toString();
				 String Arrivee = comboBoxVilleArrivee.getSelectedItem().toString();
				 String Bus = comboBoxBusAssocie.getSelectedItem().toString();
				// Récupérer la date de départ
			    Date date = dateDepart.getDate();
			    java.sql.Date dateSql = new java.sql.Date(date.getTime());
			 // Récupérer l'heure de départ
			    Date heure = (Date) txtHeureDepart.getValue();
			    java.sql.Time heureSql = new java.sql.Time(heure.getTime());
			
			 // Connexion à la base de données
			    try (Connection connection = connexionDB.getConnection()) {
		            // Utilisez la connexion pour vos opérations sur la base de données
			    	 // Requête SQL pour l'insertion des données
			        String query = "INSERT INTO Trajet (codeTrajet, busAssocie, nomTrajet, villeDepart, villeArrivee, dateDepart, heureDepart ) VALUES (?, ?, ?, ?, ?, ?, ?)";
			        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			            // Définir les valeurs des paramètres
			            preparedStatement.setString(1, codeTrajet);
			            preparedStatement.setString(3, nomTrajet);
			            preparedStatement.setString(4, Depart);
			            preparedStatement.setString(5, Arrivee);
			            preparedStatement.setDate(6, dateSql);
			            preparedStatement.setTime(7, heureSql);
			            preparedStatement.setString(2, Bus);

			            // Exécuter la requête
			            int rowsAffected = preparedStatement.executeUpdate();
			            refreshTable();
			            // Vérifier si l'insertion a réussi
			            if (rowsAffected > 0) {
			            	JOptionPane.showMessageDialog(null, "Données ajoutées avec succès", null, 0);
			             			                
			                /*// Mettre à jour le modèle de la table
			                DefaultTableModel model = (DefaultTableModel) tableTrajet.getModel();
			                model.addRow(new Object[]{codeTrajet, nomTrajet, villeDepart, villeArrivee, dateSql, heureSql, });*/
			            } else {
			            	JOptionPane.showMessageDialog(null, "Échec de l'ajout des données", null, 0);
			            }
			        }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, 0);
		        }
			    
			    System.out.println(" Code Trajet :"+codeTrajet+"\n Bus Associe : "+Bus+"\n Nom Trajet :"+nomTrajet+"\n Ville Depart :"+Depart+"\n Ville Arrivee :"+Arrivee+"\n Date Depart :"+dateSql+"\n Heure Depart :"+heureSql);
			}
			
			  private void refreshTable() {
	                // Effacer toutes les lignes actuelles du modèle
	                DefaultTableModel model = (DefaultTableModel) table_1.getModel();
	                model.setRowCount(0);

	                // Remplir à nouveau le tableau avec les données mises à jour de la base de données
	                try (Connection connection = ConnexionBD.getConnection()) {
	    	            if (connection != null) {
	    	                String query = "SELECT codeTrajet, BusAssocie, NomTrajet, VilleDepart, VilleArrivee, DateDepart, HeureDepart FROM Trajet";
	    	                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	                     ResultSet resultSet = preparedStatement.executeQuery()) {

	    	                    while (resultSet.next()) {
	    	                        Object[] row = new Object[8]; // Ajoutez une colonne supplémentaire pour le bouton "Éditer"
	    	                        row[0] = resultSet.getString("CodeTrajet");
	    	                        row[1] = resultSet.getString("BusAssocie");
	    	                        row[2] = resultSet.getString("NomTrajet");
	    	                        row[3] = resultSet.getString("VilleDepart");
	    	                        row[4] = resultSet.getString("VilleArrivee");
	    	                        row[5] = resultSet.getString("DateDepart");
	    	                        row[6] = resultSet.getString("HeureDepart");
	    	                        row[7] = "Éditer";

	    	                        ((DefaultTableModel) table_1.getModel()).addRow(row);
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
			
		});
		
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Vérifier si une ligne est sélectionnée
		        int selectedRow = table_1.getSelectedRow();
		        if (selectedRow != -1) {
		            int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette ligne ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
		            
		            // Vérifier la réponse de l'utilisateur
		            if (confirmation == JOptionPane.YES_OPTION) {
		                String codeTrajet = table_1.getValueAt(selectedRow, 0).toString();
		                try (Connection connection = connexionDB.getConnection()) {
		                    // Utilisez la connexion pour vos opérations sur la base de données
		                    // Requête SQL pour la suppression des données
		                    String query = "DELETE FROM Trajet WHERE CodeTrajet=?";
		                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                        // Paramètre de la requête
		                        preparedStatement.setString(1, codeTrajet);

		                        // Exécuter la requête
		                        int rowsAffected = preparedStatement.executeUpdate();
		                        
		                        // Rafraîchir le tableau après la suppression
		                        // refreshTable();
		                        
		                        JOptionPane.showMessageDialog(null, "Ligne supprimée avec succès", null, JOptionPane.INFORMATION_MESSAGE);
		                    }
		                } catch (SQLException ev) {
		                    ev.printStackTrace();
		                    JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Aucune ligne sélectionnée", null, JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});


		
	
		
	}
}
