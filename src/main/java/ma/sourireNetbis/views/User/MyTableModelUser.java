package ma.sourireNetbis.views.User;

import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.model.entities.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTableModelUser extends AbstractTableModel {


    private String[] columnsNames;
    private Object[][] data; //car on peut avoir différents types dans nos lignes

    public MyTableModelUser(String...columns) { //... pour spécifier tableau
        this.columnsNames = columns;
    }
    public void initUser(List<User> userist){
        //on va remplir le tableau
        data = new Object[userist.size()][columnsNames.length];
        int i =0;
        for(User user: userist){
            //ligne par ligne
            data[i][0]= user.getId();
            data[i][1]=user.getUsername();
            data[i][2]=user.getPassword();
            data[i][3]=user.getRole();
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
