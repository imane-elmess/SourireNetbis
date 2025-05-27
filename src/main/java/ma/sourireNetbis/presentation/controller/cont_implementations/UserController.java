package ma.sourireNetbis.presentation.controller.cont_implementations;

import ma.sourireNetbis.dao.filebase_implementations.PatientDao;
import ma.sourireNetbis.dao.filebase_implementations.UserDao;
import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.presentation.controller.api.IUserController;
import ma.sourireNetbis.service.PatientService;
import ma.sourireNetbis.service.UserService;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.views.MainView;
import ma.sourireNetbis.views.Patient.TablePanelPatient;
import ma.sourireNetbis.views.User.TablePanelUser;
import ma.sourireNetbis.views.UserView;

import javax.swing.*;
import java.util.List;

public class UserController implements IUserController {
      UserService userService;
      UserView userView;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User findUserByid(Integer id) {
        return userService.findById(id);

    }

    @Override
    public List<User> showAll() {
        return  userService.listAll();
    }

    @Override
    public void save(User newElement) {

        userService.create(newElement);
        List<User> list =userService.listAll();
        TablePanelUser pnl_table =userView.getPnl_table();
        pnl_table.getTableModel();
        pnl_table.revalidate();
        pnl_table.repaint();
        pnl_table.updateUI();
        pnl_table.updateTableData();
    }

    @Override
    public void update(User newValuesElement) {
           userService.update(newValuesElement);
    }

    @Override
    public void deletebyId(Integer id) {
  userService.delete(id);
    }


    public void showUserView(){
        try {
            SwingUtilities.invokeLater(() -> {
                userView = new UserView();
            });
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println(new UserController(new UserService(new UserDao())).findUserByid(1));
    }

}
