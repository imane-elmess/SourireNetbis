package ma.sourireNetbis.presentation.controller;

import java.util.List;

public interface IController <T>{
    List<T> showAll();
   // void showNewElementForm();
   // void showUpdateForm();
    void save(T newElement);
    void update(T newValuesElement);
    void deletebyId(Integer id);
}
