package ma.sourireNetbis.views.User;

import ma.sourireNetbis.views.Patient.MyTableModelPatient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TablePanelUser extends JPanel {
    MyTableModelUser tableModel;
    JScrollPane tableScrollPane; //pour ajouter la ligne pour monter et descendre
    JTable table;//utilise un modèle de données


    //getters
    public MyTableModelUser getTableModel() {
        return tableModel;
    }
    public JScrollPane getTableScrollPane() {
        return tableScrollPane;
    }
    public JTable getTable() {
        return table;
    }


    private void initComponents(String ... columns) {

        tableModel = new MyTableModelUser(columns); //les columns seront données dans le frame pour etre utilisées
        // avec nptk table data(patients, dossiers..)
        table = new JTable(tableModel);
        table.setFont(new Font("Poppins",Font.PLAIN,15));
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//pour pouvoir sélectionner une seule fois
        //en cas de modif d'une entrée
        table.setRowHeight(40);

        //il faut personnaliser aussi le header qui n'est pas inclus dans la personnalisation de la table
        var header = table.getTableHeader();
        header.setFont(new Font("Poppins",Font.BOLD,15));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(140, 190, 243));
        table.setRowHeight(20);


        // Centrer le contenu des colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tableScrollPane = new JScrollPane(table);
        //on personnalise maintenant notre pannel
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(1,1));//ma table prend tout le layout
        add(tableScrollPane);
    }
    public TablePanelUser(String ... columns){
        initComponents(columns);

    }
    // Méthode pour mettre à jour la table
    public void updateTableData() {
        tableModel.fireTableDataChanged();
    }
}
