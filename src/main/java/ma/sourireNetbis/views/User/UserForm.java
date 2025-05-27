package ma.sourireNetbis.views.User;

import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.model.enums.RoleUser;
import ma.sourireNetbis.presentation.controller.cont_implementations.UserController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserForm extends JDialog {
    private JTextField idField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton, resetButton;
    private UserController userController;



    public UserForm(JFrame parentFrame, UserController userController) {
        super(parentFrame, "Ajouter un utilisateur", true); // Fenêtre modale
        this.userController = userController;
        setLayout(new BorderLayout());

        // Partie 1 : Formulaire avec les champs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10)); // Espacement entre les champs

        // Style des labels
        Font labelFont = new Font("Poppins", Font.BOLD, 14);
        Color labelColor = new Color(0, 51, 153);

        // Style des champs texte
        Border blueBorder = BorderFactory.createLineBorder(new Color(0, 102, 204), 2);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Initialisation des champs
        idField = new JTextField(20);
        idField.setBorder(blueBorder);
        idField.setFont(textFieldFont);

        usernameField = new JTextField(20);
        usernameField.setBorder(blueBorder);
        usernameField.setFont(textFieldFont);

        passwordField = new JPasswordField(20);
        passwordField.setBorder(blueBorder);
        passwordField.setFont(textFieldFont);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "User", "Manager"});

        // Ajouter les labels et champs texte au panneau
        JLabel idLabel = new JLabel("ID Utilisateur:");
        idLabel.setFont(labelFont);
        idLabel.setForeground(labelColor);
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel roleLabel = new JLabel("Rôle Utilisateur:");
        roleLabel.setFont(labelFont);
        roleLabel.setForeground(labelColor);
        roleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        formPanel.add(idLabel);
        formPanel.add(idField);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        // Espacement pour les boutons
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Partie 2 : Panneau pour les boutons CRUD
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        saveButton = new JButton("Sauvegarder");
        resetButton = new JButton("Réinitialiser");

        saveButton.setBackground(new Color(0x536DFE));
        saveButton.setFont(new Font("Poppins", Font.BOLD, 12));
        saveButton.setFocusPainted(false);
        saveButton.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\save-icon.png"));
        saveButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        saveButton.setVerticalTextPosition(SwingConstants.CENTER);

        resetButton.setBackground(new Color(255, 51, 51));
        resetButton.setFont(new Font("Poppins", Font.BOLD, 12));
        resetButton.setFocusPainted(false);
        resetButton.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\reset-icon.png"));
        resetButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        resetButton.setVerticalTextPosition(SwingConstants.CENTER);

        // Listener pour le bouton Sauvegarder
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Création de l'utilisateur avec les données du formulaire
                    User user = new User();
                    user.setId(Integer.parseInt(idField.getText()));
                    user.setUsername(usernameField.getText());
                    user.setPassword(new String(passwordField.getPassword()));
                    user.setRole(RoleUser.valueOf(roleComboBox.getSelectedItem().toString()));
                    // Appel à la méthode create du contrôleur pour enregistrer l'utilisateur
                    userController.save(user);
                    JOptionPane.showMessageDialog(UserForm.this, "Utilisateur créé avec succès !");
                    dispose(); // Fermer la fenêtre après la sauvegarde
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserForm.this, "Erreur lors de la création de l'utilisateur : " + ex.getMessage());
                }
            }
        });

        // Listener pour réinitialiser les champs
        resetButton.addActionListener(e -> {
            idField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        });

        // Ajouter les boutons au panneau des boutons
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);

        // Ajouter le formulaire et les boutons au JPanel principal
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Réglages de la fenêtre modale
        setSize(400, 500); // Taille adaptée
        setLocationRelativeTo(parentFrame); // Centrer la fenêtre par rapport à la fenêtre parente
    }
}
