package serviceLayer;

import databaseLayer.DatabaseConnection;
import model.Supplier;

import java.sql.ResultSet;

public class SupplierService {
    private DatabaseConnection singleConnection;

    public SupplierService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addSupplier(Supplier supplier) {
        try{
            String query = "INSERT INTO supplier(name, email) VALUES('"+supplier.getSupplier_name()+"','"+supplier.getSupplier_email()+"')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding supplier" + e.getMessage());
            return false;
        }
    }

    public boolean removeSupplier(int id) {
        try{
            String query = "DELETE FROM supplier WHERE supplier_id = "+id;
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in deleting supplier" + e.getMessage());
            return false;
        }
    }

    public ResultSet getSuppliers() {
        try{
            String query = "SELECT * FROM supplier";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();
        }
        catch(Exception e) {
            System.out.println("Error in getting suppliers" + e.getMessage());
            return null;
        }
    }

    public ResultSet sendSupplierEmail() {
        try{
            String query = "SELECT s.email FROM supplier s JOIN pharmacy_inventory p ON s.supplier_id = p.supplierID  WHERE quantity <= threshold AND s.supplier_id = p.supplierID";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();
        }
        catch(Exception e) {
            System.out.println("Error in getting supplier email" + e.getMessage());
            return null;
        }
    }
}
