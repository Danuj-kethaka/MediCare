package serviceLayer;

import databaseLayer.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportService {
    private DatabaseConnection singleConnection;

    private PreparedStatement preparedStatement;

    private ResultSet result;

    public ReportService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public ResultSet findPatientDetailsDB() {
        try {
            singleConnection.setPreparedStatement("SELECT * FROM patients WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet patientCountDB() {
        try {
            singleConnection.setPreparedStatement("SELECT COUNT(*) AS patient_count FROM patients WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet findAppointmentDetailsDB() {
        try {
            singleConnection.setPreparedStatement("SELECT id, patientName, doctorName, date FROM appointment WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet appointmentCountDB() {
        try {
            singleConnection.setPreparedStatement("SELECT COUNT(*) AS appointment_count FROM appointment WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet medicineCountDB() {
        try {
            singleConnection.setPreparedStatement("SELECT COUNT(*) AS medicine_count FROM pharmacy_inventory WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet revenueFromAppointmentsDB() {
        try {
            singleConnection.setPreparedStatement("SELECT SUM(fee) AS revenue FROM appointment WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet findInventoryDetailsDB() {
        try {
            singleConnection.setPreparedStatement("SELECT * FROM pharmacy_inventory WHERE MONTH(date) = MONTH(NOW()) AND YEAR(date) = YEAR(NOW());");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ResultSet costOfInventoryDB() {
        try {
            singleConnection.setPreparedStatement("SELECT SUM(quantity * price) AS total_inventory_value FROM pharmacy_inventory WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
            result = singleConnection.ExecutePreparedStatement();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
