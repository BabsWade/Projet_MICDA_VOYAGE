package dashboard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PageDashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7745232805227892169L;
	private JButton busButton;
    private JButton trajetsButton;
    private JButton reservationsButton;
    private JTable table;

    public PageDashboard() {
        getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setForeground(new Color(255, 255, 255));
        topPanel.setBackground(new Color(0, 64, 128));
        topPanel.setPreferredSize(new Dimension(200, 100));

        // Utilisation d'un BoxLayout pour centrer le label verticalement
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Dashboard MICDA_VOYAGE");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Centrer horizontalement
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        // Ajouter une boîte invisible pour centrer verticalement
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalGlue());

        JPanel centerPanel = new JPanel();

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Utilisation d'un FlowLayout pour centrer horizontalement
        FlowLayout fl_centerPanel = new FlowLayout(FlowLayout.CENTER);
        fl_centerPanel.setHgap(50);
        centerPanel.setLayout(fl_centerPanel);

        // Augmenter la taille des boutons
        int buttonWidth = 200;
        int buttonHeight = 100;

        JButton btnBus = new JButton("Bus");
        btnBus.setFont(new Font("Rubik", Font.BOLD, 20));
        btnBus.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\bus.png"));
        btnBus.setBackground(new Color(255, 255, 255));
        btnBus.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // Utilisation d'un BorderLayout pour ajouter du texte en bas à droite
        btnBus.setLayout(new BorderLayout());
        JLabel label = new JLabel("16");
        label.setFont(new Font("Rubik", Font.BOLD, 14));
        btnBus.add(label, BorderLayout.PAGE_END);

        centerPanel.add(btnBus);

        JButton btnTrajets = new JButton("Trajets");
        btnTrajets.setFont(new Font("Rubik", Font.BOLD, 20));
        btnTrajets.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\distance.png"));
        btnTrajets.setBackground(new Color(255, 255, 255));
        btnTrajets.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        btnTrajets.setLayout(new BorderLayout());
        JLabel label_1 = new JLabel("8");
        label_1.setFont(new Font("Rubik", Font.BOLD, 14));
        btnTrajets.add(label_1, BorderLayout.PAGE_END);
        centerPanel.add(btnTrajets);

        JButton btnReservation = new JButton("Réservation");
        btnReservation.setFont(new Font("Rubik", Font.BOLD, 20));
        btnReservation.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ticket.png"));
        btnReservation.setBackground(new Color(255, 255, 255));
        btnReservation.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        btnReservation.setLayout(new BorderLayout());
        JLabel label_2 = new JLabel("32");
        label_2.setFont(new Font("Rubik", Font.BOLD, 14));
        btnReservation.add(label_2, BorderLayout.PAGE_END);
        centerPanel.add(btnReservation);

        table = new JTable();
        getContentPane().add(table, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajuster la taille de la fenêtre
        setSize(800, 400);

        // Centrer la fenêtre
        setLocationRelativeTo(null);

        setVisible(true);
    }

    // Méthodes pour accéder aux boutons depuis l'extérieur
    public JButton getBusButton() {
        return busButton;
    }

    public JButton getTrajetsButton() {
        return trajetsButton;
    }

    public JButton getReservationsButton() {
        return reservationsButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PageDashboard::new);
    }
}


