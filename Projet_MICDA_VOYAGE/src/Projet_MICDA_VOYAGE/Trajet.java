package Projet_MICDA_VOYAGE;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JLocaleChooser;
import com.toedter.components.JLocaleChooserBeanInfo;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private JTextField txtHeureDepart;
	private JTable tableTrajet;
	private JDateChooser dateDepart;
	private JLocaleChooser heureDepart;

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
		
		/*txtHeureDepart = new JTextField();
		txtHeureDepart.setColumns(10);
		txtHeureDepart.setBounds(1220, 96, 200, 30);
		contentPane.add(txtHeureDepart);*/
		
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
		
		JComboBox comboBoxVilleArrivee = new JComboBox();
		comboBoxVilleArrivee.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBoxVilleArrivee.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBoxVilleArrivee.setBounds(500, 173, 200, 30);
		contentPane.add(comboBoxVilleArrivee);
		
		JComboBox comboBoxBusAssocie = new JComboBox();
		comboBoxBusAssocie.setModel(new DefaultComboBoxModel(new String[] {"AA001-DK01", "AA002-DK01", "AA003-DK01", "AA004-DK02", "AA005-DK02", "AA006-TH01", "AA007-TH01", "AA008-TH01", "AA009-TH02", "AA010-TH02", "AA011-TH03", "AA012-TH03", "AA013-KL01", "AA014-KL01", "AA015-ZG01", "AA016-ZG01"}));
		comboBoxBusAssocie.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBoxBusAssocie.setBounds(502, 97, 200, 30);
		contentPane.add(comboBoxBusAssocie);
		
		JComboBox comboBoxVilleDepart = new JComboBox();
		comboBoxVilleDepart.setModel(new DefaultComboBoxModel(new String[] {"Dakar", "Kaolack", "Fatick", "Thies", "Faffrine", "Tambacounda", "Kedougou", "Sédhiou", "Louga", "Saint-Louis", "Diourbel", "Kolda", "Ziguinchor", "Matam"}));
		comboBoxVilleDepart.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		comboBoxVilleDepart.setBounds(160, 173, 200, 30);
		contentPane.add(comboBoxVilleDepart);
		
		
		//Date depart
		dateDepart = new JDateChooser();
		dateDepart.setBounds(850, 165, 200, 30);
		add(dateDepart);
		
		//heureDepart
		SimpleDateFormat heureDepart = new SimpleDateFormat("HH:MM");//Creer un objet SimpleDateFormat pour formatter l'heure si on formatte pas il affiche par defaut date et heure
		JFormattedTextField txtHeureDepart = new JFormattedTextField(new DateFormatter(heureDepart));//Creer JFormattedTextField avec le DateFormatter
		txtHeureDepart.setBounds(1220, 96, 200, 30);
		txtHeureDepart.setValue(new Date()); // valeur par defaut date actuel
		add(txtHeureDepart);
	}
}
