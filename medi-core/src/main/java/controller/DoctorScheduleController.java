package controller;

import model.DoctorSchedule;
import serviceLayer.DoctorScheduleService;

import java.sql.ResultSet;

public class DoctorScheduleController {
    public DoctorSchedule ObjDoctorSchedule;
    DoctorScheduleService ObjDoctorScheduleService;

    // Constructor
    public DoctorScheduleController(){
        ObjDoctorScheduleService = new DoctorScheduleService();
    }

    // Method to add Doctor Schedule
    public void addDoctorSchedule(int did, String name, String specialization, String day, String time) {
        ObjDoctorSchedule = new DoctorSchedule(did, name, specialization, day, time);
    }

    // Method to add Doctor Schedule to Database
    public boolean addDoctorScheduleToDataBase() {
        return ObjDoctorScheduleService.addDoctorSchedule(ObjDoctorSchedule);
    }

    // Method to get all Doctor Schedule
    public ResultSet getAllSchedule() {
        return ObjDoctorScheduleService.getAllSchedule();
    }

    // Method to get Doctor Schedule by ID
    public boolean removeDoctorSchedule(int sid) {
        return ObjDoctorScheduleService.removeDoctorSchedule(sid);
    }

    // Method to update Doctor Schedule
    public boolean updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        return ObjDoctorScheduleService.updateDoctorSchedule(doctorSchedule);
    }

}
