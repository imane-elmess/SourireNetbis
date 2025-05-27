package ma.sourireNetbis.presentation.controller.cont_implementations;

import ma.sourireNetbis.dao.filebase_implementations.PatientDao;
import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.presentation.controller.api.IPatientController;
import ma.sourireNetbis.service.PatientService;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IPatientService;
import ma.sourireNetbis.views.MainView;
import ma.sourireNetbis.views.Patient.TablePanelPatient;

import javax.swing.*;
import java.util.List;

public class PatientController implements IPatientController {
    IPatientService service;
    MainView mainView;
    public PatientController(IPatientService service) {
        this.service = service;
    }

    public IPatientService getService() {
        return service;
    }

    public void setService(IPatientService service) {
        this.service = service;
    }

    @Override
    public Patient findPatientByCin(String CIN) {
       return service.findPatientByCin(CIN);
    }

    @Override
    public List<Patient> showAll() {
        return  service.listAll();
    }

    @Override
    public void save(Patient newElement) {
        service.create(newElement);
        List<Patient>patients = service.listAll();
        // Accéder à la table existante via MainView
        TablePanelPatient pnl_table = mainView.getPnl_table();
        pnl_table.getTableModel().initPatients(patients);
        pnl_table.revalidate();
        pnl_table.repaint();
        pnl_table.updateUI();
        pnl_table.updateTableData();

    }

    @Override
    public void update(Patient newValuesElement) {
        service.update(newValuesElement);
        List<Patient>patients = showAll();
        // Accéder à la table existante via MainView
        TablePanelPatient pnl_table = mainView.getPnl_table();
        pnl_table.getTableModel().initPatients(patients);
        pnl_table.revalidate();
        pnl_table.repaint();
        pnl_table.updateUI();
        pnl_table.updateTableData();
    }

    @Override
    public void deletebyId(Integer id) {
        service.delete(id);
        List<Patient>patients = showAll();
        // Accéder à la table existante via MainView
        TablePanelPatient pnl_table = mainView.getPnl_table();
        pnl_table.getTableModel().initPatients(patients);
        pnl_table.revalidate();
        pnl_table.repaint();
        pnl_table.updateUI();
        pnl_table.updateTableData();
    }

    public void showMainView(){
        try {
            SwingUtilities.invokeLater(() -> {
                mainView = new MainView();
            });
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new PatientController(new PatientService(new PatientDao())).showAll());
    }
}
