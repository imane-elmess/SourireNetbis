package ma.sourireNetbis.presentation.controller.api;

import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.presentation.controller.IController;

public interface IUserController extends IController<User> {
    User findUserByid(Integer id);
}
