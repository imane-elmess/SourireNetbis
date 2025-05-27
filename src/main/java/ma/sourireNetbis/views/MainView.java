package ma.sourireNetbis.views;

import ma.sourireNetbis.config.AppFactory;
import ma.sourireNetbis.views.Patient.CRUDPanelPatient;
import ma.sourireNetbis.views.Patient.TablePanelPatient;

import javax.swing.*;
import java.awt.*;


public class MainView extends JFrame {
    private JPanel pnl_main, pnl_navbar, pnl_sidebar;
    private TablePanelPatient pnl_table;

    public TablePanelPatient getPnl_table() {
        return pnl_table;
    }

    private void initPanels() {
        // CardLayout pour le panneau principal
        pnl_main = new JPanel(new CardLayout());
        add(pnl_main, BorderLayout.CENTER);
        // Navbar en haut
        pnl_navbar = new Navbar(this);
        add(pnl_navbar, BorderLayout.NORTH);
        // Sidebar à gauche
        pnl_sidebar = new Sidebar(pnl_main);  // Passer la référence de pnl_main
        add(pnl_sidebar, BorderLayout.WEST);

        // Panneau central
        JPanel pnl_center = new JPanel(new BorderLayout());
        // Initialisation de la table des patients
        pnl_table = new TablePanelPatient("ID", "CIN", "Nom", "Prénom", "Age", "Sexe", "Assurance");
        pnl_table.getTableModel().initPatients(AppFactory.getPatientController().showAll());
        // Ajout direct de la table
        pnl_center.add(pnl_table, BorderLayout.CENTER);
        // Panneau du bas pour les opérations CRUD
        CRUDPanelPatient pnl_crud_patient = new CRUDPanelPatient("Ajouter", "Chercher", "Modifier", "Supprimer");
        pnl_center.add(pnl_crud_patient, BorderLayout.SOUTH);
// Bouton Refresh placé dans le panneau du haut pour patients
        JPanel pnl_title = new JPanel(new BorderLayout());


        // Titre du panel
        JLabel title = new JLabel("Liste des Patients", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 18));  // Taille et style de police
        title.setForeground(new Color(0x37474F));          // Couleur du texte
        pnl_title.add(title, BorderLayout.CENTER);
        pnl_center.add(pnl_title, BorderLayout.NORTH); // Ajoute le panneau contenant le bouton en haut

        // Ajout du panneau des patients dans le CardLayout
        pnl_main.add(pnl_center, "patients");  // "patients" est le nom de la vue dans le CardLayout
    }
public MainView() {
            initPanels();
            setTitle("SOURIRE NET");
            setSize(600, 400);
            setLocation(0, 0);
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Fenêtre maximisée
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//quand je clique sur x, fermeture de l'exécution
            setVisible(true);
        }


    }