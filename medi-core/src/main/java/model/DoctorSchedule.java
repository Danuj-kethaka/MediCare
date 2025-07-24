package model;

public class DoctorSchedule {
    private int doctorID;
    private String doctorName;
    private String doctorSpecialization;
    private String date;
    private String time;

    public DoctorSchedule(int doctorID, String doctorName, String doctorSpecialization, String date, String time) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.doctorSpecialization = doctorSpecialization;
        this.date = date;
        this.time = time;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
