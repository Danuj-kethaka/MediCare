package controller;

import serviceLayer.ReportService;

import java.sql.ResultSet;

public class ReportController {
    // create a object using service class
    ReportService reportService;

    // constructor to initialize the object of service class
    public ReportController() {
        reportService = new ReportService();
    }

    // method to get patient details from the database
    public ResultSet findPatientDetails() {
        return reportService.findPatientDetailsDB();
    }

    // method to get patient count from the database
    public ResultSet patientCount() {
        return reportService.patientCountDB();
    }

    // method to get appointment details from the database
    public ResultSet findAppointmentDetails() {
        return reportService.findAppointmentDetailsDB();
    }

    // method to get appointment count from the database
    public ResultSet appointmentCount() {
        return reportService.appointmentCountDB();
    }

    //  method to get doctor details from the database
    public ResultSet medicineCount(){
        return reportService.medicineCountDB();
    }

    // method to get doctor count from the database
    public ResultSet appointmentRevenue() {
        return reportService.revenueFromAppointmentsDB();
    }

    // method to get inventory details from the database
    public ResultSet findInventoryDetails() {
        return reportService.findInventoryDetailsDB();
    }

    // method to get inventory count from the database
    public ResultSet costForInventory() {
        return reportService.costOfInventoryDB();
    }

}
