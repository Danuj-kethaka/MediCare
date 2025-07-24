package view;

import controller.ReportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ReportUI {
    private JPanel BackPanel;
    private JTable table1;
    private JButton patientReportButton;
    private JButton revenueReportButton;
    private JButton pharmacyInventoryButton;
    private JLabel countLabel;
    private JButton appointmentReportButton;
    private JLabel totalLabel;

    ReportController reportController;
    
    public ReportUI(){
        patientReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalLabel.setText("");
                reportController = new ReportController();
                int rowCount = table1.getRowCount();
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                model.setColumnCount(6);
                try{
                    ResultSet patientDetails = reportController.findPatientDetails();
                    ResultSetMetaData metaData = patientDetails.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    model.setRowCount(0);

                    model.addRow(new Object[]{"Patient ID", "Patient Name", "Gender", "Email", "Contact", "Address", "Date"});
                    model.addRow(new Object[]{patientDetails.getString(1), patientDetails.getString(2), patientDetails.getString(3), patientDetails.getString(4), patientDetails.getString(5), patientDetails.getString(6)});

                    while (patientDetails.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = patientDetails.getObject(i);
                        }
                        model.addRow(row);
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(BackPanel, "Error in fetching data");
                    throw new RuntimeException(ex);
                }

                try {
                    ResultSet patientCount = reportController.patientCount();
                    System.out.println(patientCount.getObject(1));
                    countLabel.setText(patientCount.getString(1));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        appointmentReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalLabel.setText("");
                reportController = new ReportController();
                int rowCount = table1.getRowCount();
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                model.setColumnCount(4);
                try{
                    ResultSet appointmentDetails = reportController.findAppointmentDetails();
                    ResultSetMetaData metaData = appointmentDetails.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    model.setRowCount(0);

                    model.addRow(new Object[]{"Appointment ID", "Patient Name", "Doctor Name", "Date"});
                    model.addRow(new Object[]{appointmentDetails.getString(1), appointmentDetails.getString(2), appointmentDetails.getString(3), appointmentDetails.getString(4)});

                    while (appointmentDetails.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = appointmentDetails.getObject(i);
                        }
                        model.addRow(row);
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(BackPanel, "Error in fetching data");
                    throw new RuntimeException(ex);
                }

                try {
                    ResultSet appointmentCount = reportController.appointmentCount();
                    ResultSetMetaData metaData = appointmentCount.getMetaData();
                    countLabel.setText(appointmentCount.getString(1));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try{
                    ResultSet revenueDetails = reportController.appointmentRevenue();
                    ResultSetMetaData metaData = revenueDetails.getMetaData();
                    totalLabel.setText("Rs."+revenueDetails.getString(1));
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        revenueReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countLabel.setText("");
                reportController = new ReportController();
                int rowCount = table1.getRowCount();
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                model.setColumnCount(2);
                try{
                    ResultSet revenueDetails = reportController.appointmentRevenue();
                    ResultSet inventoryDetails = reportController.costForInventory();
                    ResultSetMetaData metaData = revenueDetails.getMetaData();

                    model.setRowCount(0);
                    Double totalRevenue = Double.parseDouble(revenueDetails.getString(1))-Double.parseDouble(inventoryDetails.getString(1));
                    model.addRow(new Object[]{"Revenue from Appointments","Rs."+revenueDetails.getString(1)});
                    model.addRow(new Object[]{"Cost for Inventory","Rs."+inventoryDetails.getString(1)});
                    model.addRow(new Object[]{"---------------------------","-------------------"});
                    model.addRow(new Object[]{"Total Revenue","Rs."+totalRevenue});
                    totalLabel.setText("Rs."+totalRevenue);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(BackPanel, "Error in fetching data");
                    throw new RuntimeException(ex);
                }
            }
        });
        pharmacyInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countLabel.setText("");
                totalLabel.setText("");
                reportController = new ReportController();
                int rowCount = table1.getRowCount();
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                model.setColumnCount(4);
                try{
                    ResultSet inventoryDetails = reportController.findInventoryDetails();
                    ResultSetMetaData metaData = inventoryDetails.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    model.setRowCount(0);

                    model.addRow(new Object[]{"Inventory ID", "Medicine Name", "Quantity", "Price"});
                    model.addRow(new Object[]{inventoryDetails.getString(1), inventoryDetails.getString(3), inventoryDetails.getString(4), inventoryDetails.getString(5)});

                    while (inventoryDetails.next()) {
                        Object[] row = new Object[columnCount];
                        int index = 0;

                        for (int i = 1; i <= columnCount; i++) {
                            if (i == 2) continue; // Skip column index 2
                            row[index++] = inventoryDetails.getObject(i);
                        }
                        model.addRow(row);
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(BackPanel, "Error in fetching data");
                    throw new RuntimeException(ex);
                }

                try {
                    ResultSet inventoryCost = reportController.costForInventory();
                    ResultSetMetaData metaData = inventoryCost.getMetaData();
                    totalLabel.setText("Rs."+inventoryCost.getString(1));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try{
                    ResultSet medicineCount = reportController.medicineCount();
                    ResultSetMetaData metaData = medicineCount.getMetaData();
                    countLabel.setText(medicineCount.getString(1));
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        table1.setRowHeight(30);
        table1.setPreferredSize(new Dimension(700, 50));
        table1.setMinimumSize(new Dimension(700, 50));
        table1.setMaximumSize(new Dimension(700, 50));
        table1.setShowVerticalLines(false);
        table1.setFont(new Font("Arial", Font.PLAIN, 12));
        table1.setGridColor(Color.WHITE);
        table1.setDefaultRenderer(Object.class, new CreateUIComponentDashboard.CustomTableCellRenderer());
    }

    public JPanel getReportUI() {
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ReportUI");
        frame.setContentPane(new ReportUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        appointmentReportButton = new CreateUIComponentDashboard.CustomButton("Appointment Report");
        appointmentReportButton.setPreferredSize(new Dimension(50, 50));

        patientReportButton = new CreateUIComponentDashboard.CustomButton("Patient Report");
        patientReportButton.setPreferredSize(new Dimension(50, 50));

        revenueReportButton = new CreateUIComponentDashboard.CustomButton("Revenue Report");
        revenueReportButton.setPreferredSize(new Dimension(50, 50));

        pharmacyInventoryButton = new CreateUIComponentDashboard.CustomButton("Pharmacy Inventory");
        pharmacyInventoryButton.setPreferredSize(new Dimension(50, 50));
    }
}
