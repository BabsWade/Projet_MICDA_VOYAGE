package reservation;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bus.ConnexionBD;

public class Reservation extends JFrame {
    private static final long serialVersionUID = -2526523836189223084L;
    private JPanel contentPane;
    private JComboBox<String> seatNumberDropdown;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField idNumberField;
    private JTextField phoneField;
    private JTextField reservationCodeField;
    private JComboBox<String> trajetAssocieDropdown;
    private JComboBox<String> statutReservationDropdown;
    private JComboBox<String> paymentTypeDropdown;
    private JButton reserveButton;

    public Reservation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(10, 2));

        // Seat Number Dropdown
        contentPane.add(new JLabel("Numéro de siège:"));
        String[] seatNumberOptions = getSeatNumberOptionsFromDatabase();
        seatNumberDropdown = new JComboBox<>(seatNumberOptions);
        contentPane.add(seatNumberDropdown);

        // Other Fields
        contentPane.add(new JLabel("Prénom:"));
        firstNameField = new JTextField();
        contentPane.add(firstNameField);

        contentPane.add(new JLabel("Nom:"));
        lastNameField = new JTextField();
        contentPane.add(lastNameField);

        contentPane.add(new JLabel("Numéro d'identité:"));
        idNumberField = new JTextField();
        contentPane.add(idNumberField);

        contentPane.add(new JLabel("Téléphone:"));
        phoneField = new JTextField();
        contentPane.add(phoneField);

        contentPane.add(new JLabel("Code de réservation:"));
        reservationCodeField = new JTextField();
        reservationCodeField.setEditable(false);
        contentPane.add(reservationCodeField);

        contentPane.add(new JLabel("Trajet associé:"));
        String[] trajetOptions = {"Option1", "Option2", "Option3"}; // Replace with actual options
        trajetAssocieDropdown = new JComboBox<>(trajetOptions);
        contentPane.add(trajetAssocieDropdown);

        // StatutReservation Dropdown
        contentPane.add(new JLabel("Statut de la réservation:"));
        String[] statutOptions = {"payé", "enregistré"};
        statutReservationDropdown = new JComboBox<>(statutOptions);
        contentPane.add(statutReservationDropdown);

        // Payment Type Dropdown
        contentPane.add(new JLabel("Type de paiement:"));
        String[] paymentTypeOptions = {"Cash", "Mobile"};
        paymentTypeDropdown = new JComboBox<>(paymentTypeOptions);
        contentPane.add(paymentTypeDropdown);

        reserveButton = new JButton("Réserver");
        contentPane.add(reserveButton);

        
        
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String seatNumber = (String) seatNumberDropdown.getSelectedItem();

                    // Vérifier si le siège est toujours disponible
                    if (isSeatAvailable(seatNumber)) {
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String idNumber = idNumberField.getText();
                        String phone = phoneField.getText();

                        reservationCodeField.setText(generateReservationCode());

                        trajetAssocieDropdown.getSelectedItem();
                        String selectedStatut = (String) statutReservationDropdown.getSelectedItem();
                        String selectedPaymentType = (String) paymentTypeDropdown.getSelectedItem();

                        String paymentCode = generatePaymentCode(selectedPaymentType);
                        makeReservation(seatNumber, firstName, lastName, idNumber, phone, paymentCode, selectedPaymentType, selectedStatut);

                        JOptionPane.showMessageDialog(Reservation.this, "Réservation effectuée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Reservation.this, "Le siège sélectionné n'est plus disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Reservation.this, "Erreur lors de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

        // Méthode pour vérifier si le siège est disponible
 // Méthode pour vérifier si le siège est disponible
    private boolean isSeatAvailable(String seatNumber) throws SQLException {
        try (Connection connection = ConnexionBD.getConnection()) {
            String availabilityQuery = "SELECT NumeroSiege FROM ticket WHERE NumeroSiege = ?";
            try (PreparedStatement availabilityStatement = connection.prepareStatement(availabilityQuery)) {
                availabilityStatement.setString(1, seatNumber);
                ResultSet resultSet = availabilityStatement.executeQuery();
                return !resultSet.next(); // Si le siège est disponible, il ne devrait y avoir aucune entrée dans le résultat
            }
        }
    }




    private String[] getSeatNumberOptionsFromDatabase() {
        try (Connection connection = ConnexionBD.getConnection()) {
            String seatQuery = "SELECT SiegeRestant FROM bus WHERE SiegeRestant > 0"; // Ne récupère que les sièges restants
            try (PreparedStatement seatStatement = connection.prepareStatement(seatQuery)) {
                ResultSet resultSet = seatStatement.executeQuery();
                if (resultSet.next()) {
                    int totalSeats = resultSet.getInt("SiegeRestant");
                    return generateSeatNumbers(totalSeats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }


    String[] generateSeatNumbers(int totalSeats) {
        String[] seatNumberOptions = new String[totalSeats];
        int seatCounter = 1;

        for (int i = 0; i < totalSeats; i++) {
            seatNumberOptions[i] = String.valueOf(seatCounter);
            seatCounter++;
        }

        return seatNumberOptions;
    }

    private void makeReservation(String seatNumber, String firstName, String lastName, String idNumber, String phone, String paymentCode, String modePaiement, String statutReservation) throws SQLException {
        try (Connection connection = ConnexionBD.getConnection()) {
            String reservationQuery = "INSERT INTO ticket (NumeroSiege, Prenom, Nom, CniPasseport, Telephone, CodePaiement, ModePaiement, StatutReservation, CodeReservation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery)) {

                reservationStatement.setString(1, seatNumber);
                reservationStatement.setString(2, firstName);
                reservationStatement.setString(3, lastName);
                reservationStatement.setString(4, idNumber);
                reservationStatement.setString(5, phone);
                reservationStatement.setString(6, paymentCode);
                reservationStatement.setString(7, modePaiement);
                reservationStatement.setString(8, statutReservation);
                reservationStatement.setString(9, generateReservationCode());

                // Tronquer le code de réservation si nécessaire
                String reservationCode = generateReservationCode();
                int maxLength = 5; // Remplacez par la longueur maximale autorisée
                reservationCode = reservationCode.substring(0, Math.min(reservationCode.length(), maxLength));
                reservationStatement.setString(9, reservationCode);

                reservationStatement.executeUpdate();
            }

            // Après la réservation, mettez à jour le nombre de sièges restants
            updateRemainingSeats(connection);
        }
    }

    private void updateRemainingSeats(Connection connection) throws SQLException {
        try (Statement updateStatement = connection.createStatement()) {
            // Exécutez la requête SQL pour mettre à jour le nombre de sièges restants
            String updateQuery = "UPDATE bus SET SiegeRestant = NombreSiege - SiegeReserve";
            updateStatement.executeUpdate(updateQuery);
        }
    }

    private String generateReservationCode() {
        return "cr_" + UUID.randomUUID().toString();
    }

    private String generatePaymentCode(String paymentType) {
        if ("Cash".equals(paymentType)) {
            return "cp_" + UUID.randomUUID().toString();
        } else if ("Mobile".equals(paymentType)) {
            return "mp_" + UUID.randomUUID().toString();
        } else {
            throw new IllegalArgumentException("Invalid payment type");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Reservation frame = new Reservation();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
