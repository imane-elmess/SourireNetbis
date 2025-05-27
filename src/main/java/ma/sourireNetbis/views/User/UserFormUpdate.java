package ma.sourireNetbis.views.User;

import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.model.enums.RoleUser;
import ma.sourireNetbis.presentation.controller.cont_implementations.UserController;
import ma.sourireNetbis.service.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UserFormUpdate extends JDialog {
    private JTextField idField, usernameField, passwordField, emailField;
    private JComboBox<RoleUser> roleComboBox;
    private JButton saveButton, resetButton;
    private UserController userController;
    private User user;

    public UserFormUpdate(JFrame parent, UserController userController, User user) {
        super(parent, "Modifier un utilisateur", true); // Fenêtre modale
        this.userController = userController;
        this.user = user;

        setLayout(new BorderLayout());

        // Partie 1 : Formulaire avec les champs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10)); // Grille pour organiser les champs

        // Style des labels
        Font labelFont = new Font("Poppins", Font.BOLD, 14);
        Color labelColor = new Color(0, 51, 153);

        // Style des textfields
        Border blueBorder = BorderFactory.createLineBorder(new Color(0, 102, 204), 2);
        Font textFieldFont = new Font("Poppins", Font.PLAIN, 14);

        // Initialisation des champs
        idField = new JTextField(20);
        idField.setBorder(blueBorder);
        idField.setFont(textFieldFont);
        idField.setEditable(false); // L'ID ne doit pas être modifiable
        usernameField = new JTextField(20);
        usernameField.setBorder(blueBorder);
        usernameField.setFont(textFieldFont);

        passwordField = new JTextField(20); // Champ pour le mot de passe
        passwordField.setBorder(blueBorder);
        passwordField.setFont(textFieldFont);

        roleComboBox = new JComboBox<>(RoleUser.values()); // Enum des rôles

        // Pré-remplir les champs avec les données de l'utilisateur
        idField.setText(String.valueOf(user.getId()));
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        roleComboBox.setSelectedItem(user.getRole());

        // Ajouter les labels et champs texte au panneau
        formPanel.add(new JLabel("ID:", SwingConstants.RIGHT));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nom d'utilisateur:", SwingConstants.RIGHT));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Mot de passe:", SwingConstants.RIGHT));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Rôle:", SwingConstants.RIGHT));
        formPanel.add(roleComboBox);

        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Marges autour du panneau

        // Partie 2 : Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        saveButton = new JButton("Sauvegarder");
        resetButton = new JButton("Réinitialiser");

        // Action : sauvegarder
        saveButton.addActionListener(e -> {
            try {
                // Mise à jour des informations de l'utilisateur
                User updatedUser = new User();
                updatedUser.setId(user.getId());
                updatedUser.setUsername(usernameField.getText());
                updatedUser.setPassword(passwordField.getText());
                updatedUser.setRole((RoleUser) roleComboBox.getSelectedItem());

                // Appel au contrôleur pour sauvegarder
                userController.update(updatedUser);
                JOptionPane.showMessageDialog(UserFormUpdate.this, "Utilisateur modifié avec succès !");
                dispose(); // Fermer la fenêtre après la sauvegarde
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(UserFormUpdate.this, "Erreur : " + ex.getMessage());
            }
        });

        // Action : réinitialiser
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            roleComboBox.setSelectedIndex(0);
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);

        // Ajouter le formulaire et les boutons
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Réglages de la fenêtre
        setSize(400, 400);
        setLocationRelativeTo(parent); // Centrer la fenêtre
    }

}
