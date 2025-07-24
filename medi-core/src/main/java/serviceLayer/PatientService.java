package serviceLayer;

import databaseLayer.DatabaseConnection;
import model.Patient;
import java.sql.ResultSet;

public class PatientService {
    // create a object using database class to establish connection with database
    private DatabaseConnection singleConnection;

    // constructor to initialize the object of database class using singleton pattern
    public PatientService() {
        // create a object using database class to establish connection with database using singleton pattern
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    // method to add patient details to the database
    public boolean addPatient(Patient patient) {
        try{
            // SQL query to insert patient details into the database table patients
            String query = "INSERT INTO patients(fullname, gender, email, contact, address) VALUES('"+patient.getFullName()+ "','"+ patient.getGender() +"','" +patient.getEmail()+ "','"+patient.getPhone()+"','"+patient.getAddress()+"')";
            // ExecuteSQL method is called to execute the SQL query
            return singleConnection.ExecuteSQL(query); // return the result of the query execution
        }
        catch(Exception e) {
            System.out.println("Error in adding patient" + e.getMessage());
            return false;
        }
    }

    // method to get patient details by email from the database
    public ResultSet getPatientByEmail(String email) {
        try {
            String query = "SELECT * FROM patients WHERE email = '" + email + "'";
            singleConnection.setPreparedStatement(query); // Set prepared statement for SQL query execution

            // Execute prepared statement for SQL query execution and store the result in ResultSet object
            // ExecutePreparedStatement method is called to execute the SQL query
            //System.out.println(result.getString("fullname")); <-- This line is not needed used for testing purpose

            return singleConnection.ExecutePreparedStatement(); // return the result of the query execution
        } catch (Exception e) {
            System.out.println("Error in getting patient by email" + e.getMessage());
            return null;
        }
    }

}
