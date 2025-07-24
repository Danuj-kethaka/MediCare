package model;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private DoctorSchedule doctorSchedule;
    private Double fee;

    public Appointment(int appointmentId, Patient patient, DoctorSchedule doctorSchedule, Double fee) {
        this.appointmentId = appointmentId;
        this.patient =  patient;
        this.doctorSchedule = doctorSchedule;
        this.fee = fee;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getPatientName() {
        return patient.getFullName();
    }

    public String getDoctorSchedule() {
        return doctorSchedule.getDoctorName();
    }

    public String getDate() {
        return doctorSchedule.getDate();
    }

    public String getTime() {
        return doctorSchedule.getTime();
    }

    public Double getFee() {
        return fee;
    }
}
