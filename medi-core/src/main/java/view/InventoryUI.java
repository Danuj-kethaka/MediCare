package view;

import controller.InventoryController;
import controller.SupplierController;
import model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

public class InventoryUI {
    private JPanel panel1;
    private JPanel Header;
    private JPanel HeaderContent;
    private JPanel InventoryView;
    private JPanel AddData;
    private JTable Inventory;
    private JTextField textMedicine;
    private JTextField textQty;
    private JTextField textPrice;
    private JButton addItemButton;
    private JTextField textThreshold;
    private JTextField textNewQuantity;
    private JButton changeButton;
    private JButton logoutButton;
    private JTextField textmedicineID;
    private JTextField textRemoveMedicine;
    private JButton removeButton;
    private JComboBox comboSupplierID;
    private JButton manageSuppliersButton;

    InventoryController inventoryController;
    SupplierController supplierController;

    List<Supplier> supplierList;

    public InventoryUI() {

        int id = 0;
        updateInventoryTable();
        updateSupplierComboBox();


        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mid = id + 1;
                String name = textMedicine.getText();
                String qty = textQty.getText();
                String threshold = textThreshold.getText();
                String price = textPrice.getText();
                int index = comboSupplierID.getSelectedIndex();

                if(name.isEmpty() || qty.isEmpty() || threshold.isEmpty() || price.isEmpty() || index == 0){
                    JOptionPane.showMessageDialog(panel1, "Please fill all the fields");
                } else {
                    Supplier supplier = supplierList.get(index-1);
                    inventoryController = new InventoryController();
                    inventoryController.addInventoryToDB(mid, name, Integer.parseInt(qty), Integer.parseInt(threshold), Double.parseDouble(price), supplier.getSupplierId());

                    if(inventoryController.addInventoryToDB()){
                        JOptionPane.showMessageDialog(panel1, "Item added successfully");
                        updateInventoryTable();
                    } else {
                        JOptionPane.showMessageDialog(panel1, "Error in adding Item");
                    }
                }

                textMedicine.setText("");
                textQty.setText("");
                textThreshold.setText("");
                textPrice.setText("");
                comboSupplierID.setSelectedIndex(0);
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textmedicineID.getText());
                int quantity = Integer.parseInt(textNewQuantity.getText());
                inventoryController = new InventoryController();
                if(inventoryController.removeQuantityFromInventory(id, quantity)){
                    JOptionPane.showMessageDialog(panel1, "Item updated successfully");
                    updateInventoryTable();
                } else {
                    JOptionPane.showMessageDialog(panel1, "Error in updating Item");
                }

                textmedicineID.setText("");
                textNewQuantity.setText("");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textRemoveMedicine.getText());
                inventoryController = new InventoryController();
                if(inventoryController.removeItem(id)){
                    JOptionPane.showMessageDialog(panel1, "Item removed successfully");
                    updateInventoryTable();
                } else {
                    JOptionPane.showMessageDialog(panel1, "Error in removing Item");
                }

                textRemoveMedicine.setText("");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginUI loginUI = new LoginUI();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.setContentPane(loginUI.getLoginUI());
                frame.revalidate();
            }
        });

        manageSuppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("SupplierUI");
                frame.setContentPane(new SupplierUI().getBackPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateSupplierComboBox();
                    }
                });
                frame.setSize(800, 400);
                frame.setVisible(true);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InventoryUI");
        frame.setContentPane(new InventoryUI().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    public void updateInventoryTable() {
        inventoryController = new InventoryController();
        try{
            ResultSet resultSet = inventoryController.getInventoryDetails();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();

            DefaultTableModel model = (DefaultTableModel) Inventory.getModel();
            model.setRowCount(0);

            model.addRow(new Object[]{"ID","Supplier ID", "Medicine", "Quantity", "Threshold" ,"Price"});
            model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});

            while (resultSet.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(resultSet.getObject(i));
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panel1, "Error in fetching data");
            throw new RuntimeException(e);
        }
    }

    public void updateSupplierComboBox(){
        supplierController = new SupplierController();
        supplierList = new ArrayList<>();

        try{
            ResultSet resultSet = supplierController.getSuppliers();
            Supplier sup1 = new Supplier(resultSet.getInt("supplier_id"), resultSet.getString("name"), resultSet.getString("email"));
            supplierList.add(sup1);

            while (resultSet.next()) {
                Supplier supplier = new Supplier(resultSet.getInt("supplier_id"), resultSet.getString("name"), resultSet.getString("email"));
                supplierList.add(supplier);
            }

            for(int i = 0; i < supplierList.size(); i++) {
                comboSupplierID.addItem(supplierList.get(i).getSupplier_name());
            }

        }catch (Exception e) {
            JOptionPane.showMessageDialog(panel1, "Error in fetching supplier list");
            throw new RuntimeException(e);
        }
    }

    private void createUIComponents() {

        HeaderContent = new JPanel();
        HeaderContent.setBackground(new Color(0, 0, 0, 0));
        HeaderContent.setOpaque(false);
        Header = new javax.swing.JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {

                    Paint p = new GradientPaint(0.0f, 0.0f, new Color(48, 203, 48, 255), getWidth(), getHeight(), new Color(148, 228, 148,255 ), true);

                    Graphics2D g2d = (Graphics2D)g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };

        DefaultTableModel model = new DefaultTableModel();
        Inventory = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Supplier ID");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Threshold");
        model.addColumn("Price");

        Inventory.setRowHeight(30);
        Inventory.setShowGrid(true);
        Inventory.setShowVerticalLines(false);
        Inventory.setGridColor(new Color(0, 0, 0, 0));
        Inventory.setIntercellSpacing(new Dimension(0, 0));


        textMedicine = new CreateUIComponentDashboard.RoundedJTextField(10);
        textQty = new CreateUIComponentDashboard.RoundedJTextField(10);
        textThreshold = new CreateUIComponentDashboard.RoundedJTextField(10);
        textPrice = new CreateUIComponentDashboard.RoundedJTextField(10);
        textRemoveMedicine = new CreateUIComponentDashboard.RoundedJTextField(10);

        comboSupplierID = new CreateUIComponentDashboard.CustomComboBox();

        textmedicineID = new CreateUIComponentDashboard.RoundedJTextField(10);
        textNewQuantity = new CreateUIComponentDashboard.RoundedJTextField(10);


        addItemButton = new CreateUIComponentDashboard.CustomButton("Add Item");
        addItemButton.setPreferredSize(new Dimension(150, 35));
        changeButton = new CreateUIComponentDashboard.CustomButton("Change Quantity");
        changeButton.setPreferredSize(new Dimension(150, 35));
        removeButton = new CreateUIComponentDashboard.CustomButton("Remove Item");
        removeButton.setPreferredSize(new Dimension(150, 35));
        manageSuppliersButton = new CreateUIComponentDashboard.CustomButton("Manage Suppliers");
        manageSuppliersButton.setPreferredSize(new Dimension(200, 35));



        logoutButton = new CreateUIComponent.CustomButton("Sign Out");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));

        ImageIcon addItem = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\add.png");
        Image addItemImage = addItem.getImage();
        Image newAddItem = addItemImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        addItem = new ImageIcon(newAddItem);
        addItemButton.setIcon(addItem);

        ImageIcon changeItem = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\updated.png");
        Image changeItemImage = changeItem.getImage();
        Image newChangeItem = changeItemImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        changeItem = new ImageIcon(newChangeItem);
        changeButton.setIcon(changeItem);

        ImageIcon removeItem = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\remove.png");
        Image removeItemImage = removeItem.getImage();
        Image newRemoveItem = removeItemImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        removeItem = new ImageIcon(newRemoveItem);
        removeButton.setIcon(removeItem);

        ImageIcon manageSuppliers = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\manage.png");
        Image manageSuppliersImage = manageSuppliers.getImage();
        Image newManageSuppliers = manageSuppliersImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        manageSuppliers = new ImageIcon(newManageSuppliers);
        manageSuppliersButton.setIcon(manageSuppliers);

        ImageIcon signoutIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\exit.png");
        Image signoutIconImage = signoutIcon.getImage();
        Image newSignoutIcon = signoutIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        signoutIcon = new ImageIcon(newSignoutIcon);
        logoutButton.setIcon(signoutIcon);
    }
}
