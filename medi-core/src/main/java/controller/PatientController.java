package controller;

import model.Patient;
import serviceLayer.PatientService;

import java.sql.ResultSet;

public class PatientController {
    // create a object using model class
    Patient ObjPatient;

    // create a object using service class
    PatientService ObjPatientService;

    // constructor to initialize the object of service class
    public PatientController() {
        ObjPatientService = new PatientService();
    }

    // method to add patient details to the object of model class
    public Patient addPatient(int pid, String fullName, String email, String gender, String phone, String address) {
        ObjPatient = new Patient(pid, fullName, gender, email, phone, address);
        return ObjPatient;
    }

    // method to add patient details to the database
    public boolean addPatientToDataBase() {
        return ObjPatientService.addPatient(ObjPatient);
    }

    // method to get patient details by email from the database
    public ResultSet getPatientByEmail(String email) {
        return ObjPatientService.getPatientByEmail(email);
    }
}
