package ma.sourireNetbis.config;

import ma.sourireNetbis.dao.filebase_implementations.PatientDao;
import ma.sourireNetbis.dao.filebase_implementations.UserDao;
import ma.sourireNetbis.presentation.controller.cont_implementations.PatientController;
import ma.sourireNetbis.presentation.controller.cont_implementations.UserController;
import ma.sourireNetbis.service.PatientService;
import ma.sourireNetbis.service.UserService;

public class AppFactory {
    static UserDao userDao;
    static UserController userController;
    static UserService userService;
    static PatientDao patientDao;
    static PatientService patientService;
    static PatientController patientController;
    static {
        patientDao = new PatientDao();
        patientService = new PatientService(patientDao);
        patientController = new PatientController(patientService);
    }
    static {
        userDao = new UserDao();
        userService = new UserService(userDao);
        userController = new UserController(userService);

    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        AppFactory.userDao = userDao;
    }

    public static UserController getUserController() {
        return userController;
    }

    public static void setUserController(UserController userController) {
        AppFactory.userController = userController;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static void setUserService(UserService userService) {
        AppFactory.userService = userService;
    }

    public static PatientDao getPatientDao() {
        return patientDao;
    }

    public static void setPatientDao(PatientDao patientDao) {
        AppFactory.patientDao = patientDao;
    }

    public static PatientService getPatientService() {
        return patientService;
    }

    public static void setPatientService(PatientService patientService) {
        AppFactory.patientService = patientService;
    }

    public static PatientController getPatientController() {
        return patientController;
    }

    public static void setPatientController(PatientController patientController) {
        AppFactory.patientController = patientController;
    }
}
