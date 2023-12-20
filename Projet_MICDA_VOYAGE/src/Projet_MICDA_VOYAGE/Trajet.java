package Projet_MICDA_VOYAGE;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;

public class Trajet extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodeTrajet;
	private JTextField txtNomTrajet;
	private JTextField textField_5;
	private JTable tableTrajet;

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
		setTitle("Trajet Bus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#04224c"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Trajet Bus");
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		lblNewLabel.setBounds(650, 15, 200, 50);
		contentPane.add(lblNewLabel);
		
		JLabel labelCodeTrajet = new JLabel("Code Trajet :");
		labelCodeTrajet.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelCodeTrajet.setForeground(UIManager.getColor("Button.highlight"));
		labelCodeTrajet.setBounds(50, 98, 100, 20);
		contentPane.add(labelCodeTrajet);
		
		txtCodeTrajet = new JTextField();
		txtCodeTrajet.setBounds(160, 96, 200, 30);
		contentPane.add(txtCodeTrajet);
		txtCodeTrajet.setColumns(10);
		
		JLabel labelVilleDepart = new JLabel("Ville Départ :");
		labelVilleDepart.setForeground(Color.WHITE);
		labelVilleDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelVilleDepart.setBounds(50, 174, 100, 20);
		contentPane.add(labelVilleDepart);
		
		JLabel labelBusAssocie = new JLabel("Bus Associe :");
		labelBusAssocie.setForeground(Color.WHITE);
		labelBusAssocie.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelBusAssocie.setBounds(390, 101, 100, 20);
		contentPane.add(labelBusAssocie);
		
		JLabel labelVilleArrivee = new JLabel("Ville Arrivée :");
		labelVilleArrivee.setForeground(Color.WHITE);
		labelVilleArrivee.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelVilleArrivee.setBounds(390, 171, 100, 20);
		contentPane.add(labelVilleArrivee);
		
		JLabel labelNomTrajet = new JLabel("Nom Trajet :");
		labelNomTrajet.setForeground(Color.WHITE);
		labelNomTrajet.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelNomTrajet.setBounds(740, 101, 100, 20);
		contentPane.add(labelNomTrajet);
		
		JLabel labelDateDepart = new JLabel("Date Départ :");
		labelDateDepart.setForeground(Color.WHITE);
		labelDateDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelDateDepart.setBounds(740, 171, 100, 20);
		contentPane.add(labelDateDepart);
		
		txtNomTrajet = new JTextField();
		txtNomTrajet.setColumns(10);
		txtNomTrajet.setBounds(850, 96, 200, 30);
		contentPane.add(txtNomTrajet);
		
		JLabel labelHeureDepart = new JLabel("Heure Départ :");
		labelHeureDepart.setForeground(Color.WHITE);
		labelHeureDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labelHeureDepart.setBounds(1100, 101, 105, 20);
		contentPane.add(labelHeureDepart);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(1220, 96, 200, 30);
		contentPane.add(textField_5);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnUpdate.setForeground(UIManager.getColor("CheckBox.foreground"));
		btnUpdate.setBounds(411, 254, 117, 35);
		contentPane.add(btnUpdate);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(UIManager.getColor("CheckBox.foreground"));
		btnAdd.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnAdd.setBounds(570, 255, 117, 35);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(UIManager.getColor("CheckBox.foreground"));
		btnDelete.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnDelete.setBounds(721, 255, 117, 35);
		contentPane.add(btnDelete);
		
		//Tableau		
        tableTrajet = new JTable();
		tableTrajet.setSurrendersFocusOnKeystroke(true);
		tableTrajet.setFillsViewportHeight(true);
		tableTrajet.setColumnSelectionAllowed(true);
		tableTrajet.setCellSelectionEnabled(true);
		tableTrajet.setBounds(8, 320, 1420, 420);
		contentPane.add(tableTrajet);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBox.setBounds(500, 173, 200, 30);
		contentPane.add(comboBox);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"AA001-DK01", "AA002-DK01", "AA003-DK01", "AA004-DK02", "AA005-DK02", "AA006-TH01", "AA007-TH01", "AA008-TH01", "AA009-TH02", "AA010-TH02", "AA011-TH03", "AA012-TH03", "AA013-KL01", "AA014-KL01", "AA015-ZG01", "AA016-ZG01"}));
		comboBox_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBox_2.setBounds(502, 97, 200, 30);
		contentPane.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBox_3.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBox_3.setBounds(160, 173, 200, 30);
		contentPane.add(comboBox_3);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(852, 172, 200, 30);
		contentPane.add(formattedTextField);
	}
}
