package controller;

import model.Inventory;
import serviceLayer.InventoryService;

import java.sql.ResultSet;

public class InventoryController {
    Inventory ObjInventory;
    InventoryService ObjInventoryService;

    public void addInventoryToDB(int id, String medicine_name, int quantity, int threshold , double price, int supplier_id) {
        ObjInventory = new Inventory(id, medicine_name, quantity,threshold ,price, supplier_id);
    }

    public boolean addInventoryToDB() {
        ObjInventoryService = new InventoryService();
        return ObjInventoryService.addInventory(ObjInventory);
    }

    public boolean removeItem(int id){
        ObjInventoryService = new InventoryService();
        return ObjInventoryService.removeItem(id);
    }

    public boolean removeQuantityFromInventory(int id, int quantity) {
        ObjInventoryService = new InventoryService();
        return ObjInventoryService.removeQuantityFromInventory(id, quantity);
    }

    public ResultSet getInventoryDetails() {
        ObjInventoryService = new InventoryService();
        return ObjInventoryService.getInventoryDetails();
    }
}
