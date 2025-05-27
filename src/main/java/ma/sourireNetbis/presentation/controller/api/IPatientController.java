package ma.sourireNetbis.presentation.controller.api;

import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.presentation.controller.IController;

public interface IPatientController extends IController<Patient> {
    Patient findPatientByCin(String CIN);
}
