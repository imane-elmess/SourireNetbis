package ma.sourireNetbis.views.Patient;

import ma.sourireNetbis.model.entities.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTableModelPatient extends AbstractTableModel {
    private String[] columnsNames;
    private Object[][] data; //car on peut avoir différents types dans nos lignes

    public MyTableModelPatient(String...columns) { //... pour spécifier tableau
        this.columnsNames = columns;
    }
    public void initPatients(List<Patient> patientList){
        //on va remplir le tableau
        data = new Object[patientList.size()][columnsNames.length];
        int i =0;
        for(Patient patient: patientList){
            //ligne par ligne
            data[i][0]= patient.getId();
            data[i][1]=patient.getCin();
            data[i][2]=patient.getNom();
            data[i][3]=patient.getPrenom();
            data[i][4]=patient.getAge();
            data[i][5]=patient.getSexe();
            data[i][6]=patient.getAssurance();
            //data[i][7]=patient.getFiche().getId();
            i++;
        }
    }

    @Override
    public int getRowCount() {
        return data!=null ? data.length : 0;//nombre de lignes
    }

    public String[] getColumnsNames() {
        return columnsNames;
    }

    public void setColumnsNames(String[] columnsNames) {
        this.columnsNames = columnsNames;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    @Override
    public int getColumnCount() {

        return columnsNames!= null ? columnsNames.length : 0; //ou bien data[0].length
    }

    @Override
    //on doit la redéfinir pour avoir le nom de la colonne comment est-elle définie sinon, A B C
    public String getColumnName(int col) {
        return columnsNames[col];
    }

    @Override
    public Object getValueAt(int row , int col) {
        //donne la valeur d'une colonne dans une ligne
        return data[row][col];
    }
}
