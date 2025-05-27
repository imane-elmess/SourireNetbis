package ma.sourireNetbis.views.Patient;

import ma.sourireNetbis.config.AppFactory;
import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.model.enums.Assurance;
import ma.sourireNetbis.model.enums.Sexe;
import ma.sourireNetbis.presentation.controller.cont_implementations.PatientController;
import ma.sourireNetbis.service.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientForm extends JDialog {
    private JTextField nomField, prenomField, cinField, ageField, assuranceField, sexeField;
    private JComboBox<Sexe> sexeComboBox;
    private JComboBox<Assurance> assuranceComboBox;
    private JButton saveButton, resetButton;
    private PatientController patientController;
    public PatientForm(JFrame parentFrame, PatientController patientController) {
        super(parentFrame,"Ajouter un patient",true); // true signifie que c'est une fenêtre modale
        this.patientController=patientController;
        setLayout(new BorderLayout());
        // Partie 1: Formulaire avec les champs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 10, 10)); // Espacement entre les champs
        // Style des labels
        Font labelFont = new Font("Poppins", Font.BOLD, 14); // Police personnalisée
        Color labelColor = new Color(0, 51, 153); // Bleu foncé

        // Style des textfields
        Border blueBorder = BorderFactory.createLineBorder(new Color(0, 102, 204), 2); // Bordure bleue
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);


        //Initialisation des champs
        cinField= new JTextField(20);
        cinField.setBorder(blueBorder);
        cinField.setFont(textFieldFont);
        nomField= new JTextField(20);
        nomField.setBorder(blueBorder);
        nomField.setFont(textFieldFont);
        prenomField= new JTextField(20);
        prenomField.setBorder(blueBorder);
        prenomField.setFont(textFieldFont);
        ageField= new JTextField(20);
        ageField.setBorder(blueBorder);
        ageField.setFont(textFieldFont);
        sexeComboBox = new JComboBox<>(Sexe.values());  // Enum Sexe
        assuranceComboBox = new JComboBox<>(Assurance.values());  // Enum

        // Ajout des composants au formulaire
        // Ajouter les labels et champs texte au panneau
        JLabel cinLabel = new JLabel("CIN Patient:");
        cinLabel.setFont(labelFont);
        cinLabel.setForeground(labelColor);
        cinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setFont(labelFont);
        nomLabel.setForeground(labelColor);
        nomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel prenomLabel = new JLabel("Prénom:");
        prenomLabel.setFont(labelFont);
        prenomLabel.setForeground(labelColor);
        prenomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        ageLabel.setForeground(labelColor);
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel sexeLabel = new JLabel("Sexe:");
        sexeLabel.setFont(labelFont);
        sexeLabel.setForeground(labelColor);
        sexeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel assuranceLabel = new JLabel("Assurance:");
       assuranceLabel.setFont(labelFont);
       assuranceLabel.setForeground(labelColor);
       assuranceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        formPanel.add(cinLabel);
        formPanel.add(cinField);

        formPanel.add(nomLabel);
        formPanel.add(nomField);

        formPanel.add(prenomLabel);
        formPanel.add(prenomField);

        formPanel.add(ageLabel);
        formPanel.add(ageField);

        formPanel.add(sexeLabel);
        formPanel.add(sexeComboBox);

        formPanel.add(assuranceLabel);
        formPanel.add(assuranceComboBox);

        // espacement pour les boutons
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Marges autour du panneau


        // Partie 2: Panneau pour les boutons CRUD
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        saveButton = new JButton("Sauvegarder");
        resetButton = new JButton("Réinitialiser");


        saveButton.setBackground(new Color(0x536DFE));
        saveButton.setFont(new Font("Poppins",Font.BOLD,12));
        saveButton.setFocusPainted(false);  // Pas de bordure lors de la sélection
      //  saveButton.setIcon(PatientForm.class.getResource("/ressources/images/sticky-note-add-line.png"));
        saveButton.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\"));
        saveButton.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        saveButton.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône

        resetButton.setBackground(new Color(255, 51, 51)); // Couleur de fond rouge
        resetButton.setFont(new Font("Poppins",Font.BOLD,12));
        resetButton.setFocusPainted(false);  // Pas de bordure lors de la sélection
        resetButton.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\reset-left-line.png"));
        resetButton.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        resetButton.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône


// Ajouter le listener sur le bouton Sauvegarder
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Création du de la consultation avec les données du formulaire
                    Patient patient = new Patient();
                    patient.setCin(cinField.getText());
                    patient.setNom(nomField.getText());
                    patient.setPrenom(prenomField.getText());
                    patient.setSexe((Sexe) sexeComboBox.getSelectedItem());
                    patient.setAssurance((Assurance) assuranceComboBox.getSelectedItem());
                    patient.setAge(Integer.valueOf(ageField.getText()));
                    System.out.println(patient);
                    // Appel à la méthode create du contrôleur pour enregistrer la consultation
                    patientController.save(patient);
                    JOptionPane.showMessageDialog(PatientForm.this, "Patient créé avec succès !");
                    dispose(); // Fermer la fenêtre après la sauvegarde
                    //new PatientView();
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(PatientForm.this, "Erreur lors de la création du patient : " + ex.getMessage());
                }
            }
        });

        // Ajout d'un listener pour réinitialiser les champs
        resetButton.addActionListener(e -> {
            cinField.setText("");
            nomField.setText("");
            prenomField.setText("");
            ageField.setText("");
        });

        // Ajouter les boutons
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);

        // Ajouter le formulaire et les boutons au JPanel
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Réglages de la fenêtre modale
        setSize(400, 500); // Taille plus adaptée
        setLocationRelativeTo(parentFrame);  // Centrer la fenêtre par rapport à la fenêtre parente

    }
}
