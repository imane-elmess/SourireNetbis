package ma.sourireNetbis.views.Patient;

import ma.sourireNetbis.config.AppFactory;
import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.presentation.controller.cont_implementations.PatientController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CRUDPanelPatient extends JPanel {
    private JButton btn_create, btn_update, btn_delete, btn_read;
    private JTextField txt_read; //pour le read où on va saisir ce qu'on veut chercher selon un motif

    public CRUDPanelPatient(String txtC, String txtR, String txtU, String txtD) {
        initComponents(txtC, txtR, txtU, txtD);
    }

    private JFrame parentFrame;

    public CRUDPanelPatient(JFrame parentFrame, String txtC, String txtR, String txtU, String txtD) {
        this.parentFrame = parentFrame;
        initComponents(txtC, txtR, txtU, txtD);
    }

    private void updateTable(Patient patient) {
        String[] columnNames = {"ID", "CIN", "Nom", "Prenom"}; // Colonnes de la table
        Object[][] data = new Object[1][columnNames.length];

            data[0][0] = patient.getId();
            data[0][1] = patient.getCin();
            data[0][2] = patient.getNom();
            data[0][3] = patient.getPrenom();
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        // Afficher la table dans une nouvelle fenêtre
        JOptionPane.showMessageDialog(null, scrollPane, "Résultats de recherche par CIN", JOptionPane.INFORMATION_MESSAGE);
    }



    private void initComponents(String txtC, String txtR, String txtU, String txtD) {

        //text field
        txt_read = new JTextField();
        txt_read = new JTextField();
        txt_read.setPreferredSize(new Dimension(120,50));
        txt_read.setBackground(Color.WHITE);
        txt_read.setForeground(Color.BLACK);
        txt_read.setHorizontalAlignment(JTextField.CENTER);//pour écrire au milieu
        txt_read.setFont(new Font("Poppins",Font.BOLD,12));
        //bouton create
        btn_create = new JButton(txtC);
        btn_create.setPreferredSize(new Dimension(130,50));//je ne veux pas qu'il lui donne une taille par défaut
        btn_create.setMinimumSize(new Dimension(130,50));
        btn_create.setForeground(new Color(0x536DFE));
        btn_create.setFont(new Font("Poppins",Font.BOLD,12));
        btn_create.setBackground(Color.WHITE);
        btn_create.setFocusPainted(false);  // Pas de bordure lors de la sélection
        //btn_create.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/user-add-line.png")));
        btn_create.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\user-add-line.png"));
        btn_create.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        btn_create.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône
        btn_create.addActionListener(e -> {
//             Création d'une nouvelle instance de PatientForm
//             Création du contrôleur et de la fenêtre
            PatientController patientController=  AppFactory.getPatientController();
            PatientForm patientForm = new PatientForm(parentFrame, patientController);
            patientForm.setVisible(true);
        });
        btn_create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_create.setForeground(Color.WHITE);
                btn_create.setBackground(new Color(0x536DFE));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_create.setForeground(new Color(0x536DFE));
                btn_create.setBackground(Color.WHITE);
            }
        });
        //btn_read
        btn_read = new JButton(txtR);
        btn_read.setPreferredSize(new Dimension(130,50));//je ne veux pas qu'il lui donne une taille par défaut
        btn_read.setMinimumSize(new Dimension(130,50));
        btn_read.setForeground(new Color(0x536DFE));
        btn_read.setFont(new Font("Poppins",Font.BOLD,12));
        btn_read.setBackground(Color.WHITE);
        btn_read.setFocusPainted(false);  // Pas de bordure lors de la sélection
        btn_read.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\user-search-line.png"));
        btn_read.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        btn_read.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône
        btn_read.addActionListener(e -> {
                PatientController controller = AppFactory.getPatientController();
            try {
                String keyword = txt_read.getText();
                Patient patient = controller.findPatientByCin(keyword);
                updateTable(patient);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn_read.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_read.setForeground(Color.WHITE);
                btn_read.setBackground(new Color(0x536DFE));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_read.setForeground(new Color(0x536DFE));
                btn_read.setBackground(Color.WHITE);
            }
        });
//btn_delete
                btn_delete = new JButton(txtD);
        btn_delete.setPreferredSize(new Dimension(130,50));//je ne veux pas qu'il lui donne une taille par défaut
        btn_delete.setMinimumSize(new Dimension(130,50));
        btn_delete.setForeground(new Color(0x536DFE));
        btn_delete.setFont(new Font("Poppins",Font.BOLD,12));
        btn_delete.setBackground(Color.WHITE);
        btn_delete.setFocusPainted(false);  // Pas de bordure lors de la sélection
        btn_delete.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\delete-bin-2-line.png"));
        btn_delete.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        btn_delete.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône
        btn_delete.addActionListener(e -> {
            PatientController controller = AppFactory.getPatientController();
            try {
                Integer Id = Integer.valueOf(txt_read.getText());
                controller.deletebyId(Id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_delete.setForeground(Color.WHITE);
                btn_delete.setBackground(new Color(0x536DFE));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_delete.setForeground(new Color(0x536DFE));
                btn_delete.setBackground(Color.WHITE);
            }
        });
//btn_update
        btn_update = new JButton(txtU);
        btn_update.setPreferredSize(new Dimension(130,50));//je ne veux pas qu'il lui donne une taille par défaut
        btn_update.setMinimumSize(new Dimension(130,50));
        btn_update.setForeground(new Color(0x536DFE));
        btn_update.setFont(new Font("Poppins",Font.BOLD,12));
        btn_update.setBackground(Color.WHITE);
        btn_update.setFocusPainted(false);  // Pas de bordure lors de la sélection
        btn_update.setIcon(new ImageIcon("C:\\Users\\qq\\Desktop\\PROJET JAVA EMSI\\Icons\\edit-line.png"));
        btn_update.setHorizontalTextPosition(SwingConstants.RIGHT);  // Texte à droite de l'icône
        btn_update.setVerticalTextPosition(SwingConstants.CENTER);  // Texte centré verticalement par rapport à l'icône
        btn_update.addActionListener(e -> {
            PatientController controller = AppFactory.getPatientController();
            try {
                String keyword = txt_read.getText();
                Patient patient = controller.findPatientByCin(keyword);
                PatientFormUpdate patientUpdateForm = new PatientFormUpdate(parentFrame, controller,patient);
                patientUpdateForm.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn_update.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_update.setForeground(Color.WHITE);
                btn_update.setBackground(new Color(0x536DFE));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_update.setForeground(new Color(0x536DFE));
                btn_update.setBackground(Color.WHITE);
            }
        });
        this.setBorder(new EmptyBorder(20,20,20,20));
        var centerPanel = new JPanel();//où mettre mes boutons
        //centerPanel.setBackground(bgColor);
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15,0)); //gap pour séparer entre les boutons
        centerPanel.add(btn_create);
        centerPanel.add(btn_update);
        centerPanel.add(btn_delete);
        var eastPanel = new JPanel();//où mettre mes boutons
        //eastPanel.setBackground(bgColor);
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15,0)); //gap pour séparer entre les boutons
        eastPanel.add(txt_read);
        eastPanel.add(btn_read);
        this.add(centerPanel,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
    }


}

