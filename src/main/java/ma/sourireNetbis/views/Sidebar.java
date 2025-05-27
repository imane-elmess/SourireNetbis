package ma.sourireNetbis.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sidebar extends JPanel {
    private JPanel pnl_main;  // Ajouter une variable pour stocker la référence de pnl_main

    public Sidebar(JPanel pnl_main) {
        this.pnl_main = pnl_main;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // Rangée verticale
        setPreferredSize(new Dimension(200, 0));  // Largeur fixe pour la sidebar
        setBackground(Color.WHITE);  // Couleur de fond

        // Boutons de la Sidebar avec icônes
        add(createSidebarButton("Profil", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\user-settings-line.png"));
        add(createSidebarButton("Dashboard", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\dashboard-line.png"));
        add(createSidebarButton("Agenda", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\calendar-schedule-line.png"));
        add(createSidebarButton("Patients", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\group-3-line.png"));
        add(createSidebarButton("Fiches", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\draft-line.png"));
        add(createSidebarButton("Consultations", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\tooth-line.png"));
        add(createSidebarButton("Stock", "C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\medicine-bottle-line.png"));

        // Espace flexible pour remplir la sidebar
        add(Box.createVerticalGlue());
    }

    // Fonction utilitaire pour créer des boutons stylisés avec icônes
    private JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 70));  // Taille des boutons
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // Largeur maximale
        button.setHorizontalAlignment(SwingConstants.CENTER);  // Texte aligné au centre
        Font font_button = new Font("Poppins", Font.BOLD, 16);
        button.setFont(font_button);

        // Charger l'icône et l'ajouter au bouton
        ImageIcon icon = new ImageIcon(iconPath);  // Remplacez par votre chemin d'icône
        icon = new ImageIcon(icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));  // Redimensionner l'icône
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        button.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône

        // Stylisation de plus de mon boutton
        button.setFocusPainted(false);  // Pas de bordure lors de la sélection
        button.setOpaque(true);  // Permet de changer le background pour mes mouseListener
        button.setBackground(Color.WHITE);  // Couleur initiale du bouton
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));  // Espacement intérieur
        button.setForeground(new Color(0x536DFE));  // Couleur du texte

        // Ajouter un ActionListener pour chaque bouton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) pnl_main.getLayout();

                switch (text) {
                    case "Patients":
                        cardLayout.show(pnl_main, "patients");
                        break;
                    case "Dashboard":
                        cardLayout.show(pnl_main, "dashboard");
                        break;
                    case "Agenda":
                        cardLayout.show(pnl_main, "agenda");
                        break;
                    case "Fiches":
                        cardLayout.show(pnl_main, "fiches");
                        break;
                    case "Consultations":
                        cardLayout.show(pnl_main, "consultations");
                        break;
                    case "Stock":
                        cardLayout.show(pnl_main, "stock");
                        break;
                    // Ajoutez ici des cases pour d'autres vues
                }
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(0x536DFE));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0x536DFE));
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }
}
