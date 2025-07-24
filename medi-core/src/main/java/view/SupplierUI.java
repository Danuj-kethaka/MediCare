package view;

import controller.SupplierController;
import model.Email;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class SupplierUI {
    private JTextField textSupplierName;
    private JTextField textSupplierEmail;
    private JButton addSupplierButton;
    private JTextField textRemoveSupplier;
    private JButton removeButton;
    private JButton notifyButton;
    private JTable SupplierTable;
    private JPanel BackPanel;
    private JPanel header;

    SupplierController objController;

    public SupplierUI() {
        updateSupplierTable();
        objController = new SupplierController();

        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textSupplierName.getText();
                String email = textSupplierEmail.getText();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {

                    // add supplier details to the object of model class using controller class
                    objController.addSupplier(1, name, email);
                    if(!objController.addSupplierToDB()) {
                        JOptionPane.showMessageDialog(BackPanel, "Error in adding supplier");
                        return;
                    }
                    // add supplier details to the database using controller class
                    JOptionPane.showMessageDialog(BackPanel, "Supplier added successfully!");
                    updateSupplierTable();
                }

                textSupplierName.setText("");
                textSupplierEmail.setText("");
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string_id = textRemoveSupplier.getText();
                if(textRemoveSupplier.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt(string_id);
                    if(!objController.removeSupplier(id)) {
                        JOptionPane.showMessageDialog(BackPanel, "Error in deleting supplier");
                        return;
                    }
                    updateSupplierTable();
                    JOptionPane.showMessageDialog(BackPanel, "Supplier deleted successfully!");
                }
                textRemoveSupplier.setText("");
            }
        });
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet resultSet = objController.sendSupplierEmail();
                    if(resultSet == null){
                        JOptionPane.showMessageDialog(BackPanel, "Low stock items not found");
                        return;
                    }
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int emailColumnIndex = 1; // Update this based on your actual email column index
                    Email email = new Email();

                    if(!resultSet.isBeforeFirst()){
                        email.sendEmail(resultSet.getString(emailColumnIndex), "final test", "Please restock the inventory.");
                        JOptionPane.showMessageDialog(BackPanel, "Email sent to " + resultSet.getString(emailColumnIndex), "Email Sent Successfully", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        // Process every row in the result set
                        while (resultSet.next()) {
                            String recipientEmail = resultSet.getString(emailColumnIndex); // Fetch email from current row

                            if (recipientEmail != null && !recipientEmail.trim().isEmpty()) {
                                email.sendEmail(recipientEmail, "Restock Remainder", "Please restock the inventory.");
                                JOptionPane.showMessageDialog(BackPanel, "Email sent to " + recipientEmail);
                            } else {
                                System.out.println("Skipping empty email address.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(BackPanel, "Error in sending email");
                    throw new RuntimeException(ex);
                }
            }

        });
    }

    public void updateSupplierTable(){
        objController = new SupplierController();
        try{
            ResultSet resultSet = objController.getSuppliers();
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columns = metaData.getColumnCount();

            DefaultTableModel model = (DefaultTableModel) SupplierTable.getModel();
            model.setRowCount(0);

            model.addRow(new Object[]{"ID", "Name", "Email"});
            model.addRow(new Object[]{resultSet.getObject(1), resultSet.getObject(2), resultSet.getObject(3)});
            while (resultSet.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(resultSet.getObject(i));
                    //System.out.println(resultSet.getObject(i));
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in fetching data from database");

        }
    }

    public JPanel getBackPanel() {
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SupplierUI");
        frame.setContentPane(new SupplierUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        // Custom header creation
        header = new javax.swing.JPanel(){
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
        // Custom table creation
        DefaultTableModel model = new DefaultTableModel();
        SupplierTable = new JTable(model);

        model.addColumn("Supplier ID");
        model.addColumn("Supplier Name");
        model.addColumn("Supplier Email");


        SupplierTable.setRowHeight(30);
        SupplierTable.setShowGrid(true);
        SupplierTable.setShowVerticalLines(false);
        SupplierTable.setGridColor(new Color(0, 0, 0, 0));
        SupplierTable.setIntercellSpacing(new Dimension(0, 0));

        textSupplierName = new CreateUIComponentDashboard.RoundedJTextField(10);
        textSupplierEmail = new CreateUIComponentDashboard.RoundedJTextField(10);
        textRemoveSupplier = new CreateUIComponentDashboard.RoundedJTextField(10);

        addSupplierButton = new CreateUIComponentDashboard.CustomButton("Add Supplier");
        removeButton = new CreateUIComponentDashboard.CustomButton("Remove Supplier");
        removeButton.setMaximumSize(new Dimension(150, 30));
        removeButton.setMinimumSize(new Dimension(150, 30));
        removeButton.setPreferredSize(new Dimension(150, 30));
        notifyButton = new CreateUIComponentDashboard.CustomButton("Notify Supplier");
        notifyButton.setPreferredSize(new Dimension(100, 30));
        notifyButton.setMaximumSize(new Dimension(100, 30));
        notifyButton.setMinimumSize(new Dimension(100, 30));

        ImageIcon addSupplier = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\supplier.png");
        Image addSupplierImage = addSupplier.getImage();
        Image newAddSupplier = addSupplierImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        addSupplier = new ImageIcon(newAddSupplier);
        addSupplierButton.setIcon(addSupplier);

        ImageIcon removeSupplier = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\remove.png");
        Image removeSupplierImage = removeSupplier.getImage();
        Image newRemoveSupplier = removeSupplierImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        removeSupplier = new ImageIcon(newRemoveSupplier);
        removeButton.setIcon(removeSupplier);

        ImageIcon notifySupplier = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\Pharmacist\\notification.png");
        Image notifySupplierImage = notifySupplier.getImage();
        Image newNotifySupplier = notifySupplierImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        notifySupplier = new ImageIcon(newNotifySupplier);
        notifyButton.setIcon(notifySupplier);
    }
}
