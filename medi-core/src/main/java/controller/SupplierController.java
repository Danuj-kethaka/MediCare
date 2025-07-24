package controller;

import model.Supplier;
import serviceLayer.SupplierService;

import java.sql.ResultSet;

public class SupplierController {

    // create a object using model class
    Supplier ObjSupplier;

    // create a object using service class
    SupplierService ObjSupplierService;

    // constructor to initialize the object of service class
    public SupplierController(){
        ObjSupplierService = new SupplierService();
    }

    // method to add supplier details to the object of model class
    public void addSupplier(int id, String supplier_name, String supplier_email) {
        ObjSupplier = new Supplier(id, supplier_name, supplier_email);
    }

    // method to add supplier details to the database
    public boolean addSupplierToDB() {
        return ObjSupplierService.addSupplier(ObjSupplier);
    }

    // method to get supplier details by email from the database
    public boolean removeSupplier(int id){
        return ObjSupplierService.removeSupplier(id);
    }

    // method to get supplier details by email from the database
    public ResultSet getSuppliers() {
        return ObjSupplierService.getSuppliers();
    }

    // method to get supplier details by email from the database
    public ResultSet sendSupplierEmail() {
        return ObjSupplierService.sendSupplierEmail();
    }
}
