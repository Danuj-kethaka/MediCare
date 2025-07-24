package controller;

import model.Appointment;
import model.DoctorSchedule;
import model.Patient;
import serviceLayer.AppointmentService;

public class AppointmentController {

    // Object of Appointment, AppointmentService
    Appointment ObjAppointment;
    AppointmentService ObjAppointmentService;

    // Constructor
    public AppointmentController() {
        ObjAppointmentService = new AppointmentService();
    }

    // Method to add Appointment
    public Appointment addAppointment(int appointmentId, Patient patient, DoctorSchedule doctorSchedule, Double fee) {
        ObjAppointment = new Appointment(appointmentId, patient, doctorSchedule, fee);
        return ObjAppointment;
    }

    // Method to add Appointment to Database
    public boolean addAppointmentToDataBase() {
        return ObjAppointmentService.addAppointment(ObjAppointment);
    }
}
