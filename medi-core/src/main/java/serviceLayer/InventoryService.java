package serviceLayer;

import databaseLayer.DatabaseConnection;
import model.Inventory;

import java.sql.ResultSet;

public class InventoryService {
    private DatabaseConnection singleConnection;

    public InventoryService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addInventory(Inventory inventory) {
        try {
            String query = "INSERT INTO pharmacy_inventory(supplierID, medicine_name, quantity, threshold, price ) VALUES('"+ inventory.getSupplier_id() +"','" +inventory.getMedicine_name()+"','" +inventory.getQuantity()+"','"+ inventory.getThreshold() +"','"+inventory.getPrice()+"')";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in adding inventory" + e.getMessage());
            return false;
        }
    }

    public boolean removeQuantityFromInventory(int id, int quantity) {
        try {
            String query = "UPDATE pharmacy_inventory SET quantity = " + quantity + " WHERE id = " + id;
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in removing quantity from inventory" + e.getMessage());
            return false;
        }
    }

    public boolean removeItem(int id) {
        try {
            String query = "DELETE FROM pharmacy_inventory WHERE id = " + id;
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in removing item from inventory" + e.getMessage());
            return false;
        }
    }

    public ResultSet getInventoryDetails() {
        try {
            singleConnection.setPreparedStatement("SELECT * FROM pharmacy_inventory");
            return singleConnection.ExecutePreparedStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
