package model;

public class Supplier {
    private int id;
    private String supplier_name;
    private String supplier_email;

    public Supplier(int id, String supplier_name, String supplier_email) {
        this.id = id;
        this.supplier_name = supplier_name;
        this.supplier_email = supplier_email;
    }

    public int getSupplierId() {
        return id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getSupplier_email() {
        return supplier_email;
    }
}
