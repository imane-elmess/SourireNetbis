package ma.sourireNetbis.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navbar extends JPanel {
    public Navbar(JFrame parentFrame) {
        // Configuration du JPanel Navbar
        setLayout(new BorderLayout());
        // Style de la navbar
        setBackground(Color.WHITE);  // Fond blanc
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0x536DFE)));  // Trait bleu en bas

        // Logo à gauche
        // Charger et redimensionner l'image
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Maquettes des interfaces\\logo.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));


        // Panneau à droite pour les informations utilisateur
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        // Nom utilisateur avec icone et bouton déconnexion avec image
        JLabel userLabel = new JLabel("Dr. Imane EL MESSAOUDI");
        userLabel.setForeground(new Color(0x536DFE));
        JLabel userIcon = new JLabel(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\user-line.png"));

        JButton logoutButton = new JButton(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\logout.png"));
        logoutButton.setToolTipText("Déconnexion");
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentFrame, "Déconnexion réussie !");
                parentFrame.dispose();
            }
        });

        // Ajout des composants au panneau droit
        rightPanel.add(userIcon);
        rightPanel.add(userLabel);
        rightPanel.add(logoutButton);

        // Ajout des composants à la navbar
        add(logoLabel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }
}