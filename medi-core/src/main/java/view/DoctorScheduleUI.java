package view;

import controller.DoctorScheduleController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DoctorScheduleUI {
    private JPanel BackPanel;
    private JTextField textDoctorName;
    private JTextField textDoctorSpecialization;
    private JTextField textDate;
    private JTextField textTime;
    private JButton addScheduleButton;
    private JTable scheduleTable;
    private JButton updateScheduleButton;
    private JButton removeOldSchedulesButton;
    private int selectedRow = -1;

    DoctorScheduleController ObjDoctorScheduleController;

    public DoctorScheduleUI(){
        updateScheduleTable();
        ObjDoctorScheduleController = new DoctorScheduleController();
        int scheduleCount = 0;

        addScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sid = scheduleCount + 1;
                String doctorName = textDoctorName.getText();
                String doctorSpecialization = textDoctorSpecialization.getText();
                String date = textDate.getText();
                String time = textTime.getText();

                ObjDoctorScheduleController.addDoctorSchedule(sid, doctorName, doctorSpecialization, date, time);

                if (!ObjDoctorScheduleController.addDoctorScheduleToDataBase()) {
                    JOptionPane.showMessageDialog(null, "Error in adding schedule");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Schedule added successfully!");
                updateScheduleTable();

                textDoctorName.setText("");
                textDoctorSpecialization.setText("");
                textDate.setText("");
                textTime.setText("");
            }
        });
        textDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (textDate.getText().isEmpty()) {
                    textDate.setText("yyyy-mm-dd");
                }
            }
        });
        textTime.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (textTime.getText().isEmpty()) {
                    textTime.setText("hh:mm:ss");
                }
            }
        });

        textDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (textDate.getText().equals("yyyy-mm-dd")) {
                    textDate.setText("");
                }
            }
        });
        textTime.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (textTime.getText().equals("hh:mm:ss")) {
                    textTime.setText("");
                }
            }
        });
        scheduleTable.addComponentListener(new ComponentAdapter() {
        });
        scheduleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = scheduleTable.getSelectedRow();
                if(row >= 0){
                    selectedRow =  Integer.parseInt(scheduleTable.getModel().getValueAt(row, 0).toString());
                    textDoctorName.setText(scheduleTable.getModel().getValueAt(row, 1).toString());
                    textDoctorSpecialization.setText(scheduleTable.getModel().getValueAt(row, 2).toString());
                    textDate.setText(scheduleTable.getModel().getValueAt(row, 3).toString());
                    textTime.setText(scheduleTable.getModel().getValueAt(row, 4).toString());
                }
            }
        });
        updateScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedRow >= 0){
                    int sid = selectedRow;
                    String doctorName = textDoctorName.getText();
                    String doctorSpecialization = textDoctorSpecialization.getText();
                    String date = textDate.getText();
                    String time = textTime.getText();

                    ObjDoctorScheduleController.addDoctorSchedule(sid, doctorName, doctorSpecialization, date, time);

                    if (!ObjDoctorScheduleController.updateDoctorSchedule(ObjDoctorScheduleController.ObjDoctorSchedule)) {
                        JOptionPane.showMessageDialog(null, "Error in updating schedule");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Schedule updated successfully!");

                    updateScheduleTable();
                    textDoctorName.setText("");
                    textDoctorSpecialization.setText("");
                    textDate.setText("");
                    textTime.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please select a schedule to update");
                }
            }
        });
        removeOldSchedulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ObjDoctorScheduleController.removeDoctorSchedule(selectedRow)){
                    JOptionPane.showMessageDialog(null, "Old schedules removed successfully!");
                    updateScheduleTable();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error in removing old schedules");
                }
            }
        });

        scheduleTable.setDefaultRenderer(Object.class, new CreateUIComponentDashboard.CustomTableCellRenderer());
        scheduleTable.setRowHeight(30);
        scheduleTable.setPreferredSize(new Dimension(700, 50));
        scheduleTable.setMinimumSize(new Dimension(700, 50));
        scheduleTable.setMaximumSize(new Dimension(700, 50));
        scheduleTable.setShowVerticalLines(false);
        scheduleTable.setFont(new Font("Arial", Font.PLAIN, 12));
        scheduleTable.setGridColor(Color.WHITE);
    }

    public JPanel getDoctorScheduleUI(){
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DoctorScheduleUI");
        frame.setContentPane(new DoctorScheduleUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void updateScheduleTable(){
        ObjDoctorScheduleController = new DoctorScheduleController();
        try{
            ResultSet resultSet = ObjDoctorScheduleController.getAllSchedule();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
            model.setRowCount(0);

            model.setColumnIdentifiers(new Object[]{"SID", "Doctor Name", "Doctor Specialization", "Date", "Time"});
            model.addRow(new Object[]{"SID", "Doctor Name", "Doctor Specialization", "Date", "Time"});
            model.addRow(new Object[]{resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)});

            while(resultSet.next()){
                Object[] row = new Object[columnCount];
                for(int i = 1; i <= columnCount; i++){
                    row[i-1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
        }
        catch(Exception e){
            System.out.println("Error in updating schedule table" + e.getMessage());
        }
    }

    private void createUIComponents() {
        textDoctorName = new CreateUIComponentDashboard.RoundedJTextField(20);
        textDoctorName.setFont(new Font("Arial", Font.PLAIN, 15));

        textDoctorSpecialization = new CreateUIComponentDashboard.RoundedJTextField(20);
        textDoctorSpecialization.setFont(new Font("Arial", Font.PLAIN, 15));

        textDate = new CreateUIComponentDashboard.RoundedJTextField(20);
        textDate.setFont(new Font("Arial", Font.PLAIN, 15));
        textDate.setText("yyyy-mm-dd");

        textTime = new CreateUIComponentDashboard.RoundedJTextField(20);
        textTime.setFont(new Font("Arial", Font.PLAIN, 15));
        textTime.setText("hh:mm:ss");

        addScheduleButton = new CreateUIComponentDashboard.CustomButton("Add Schedule");
        addScheduleButton.setMinimumSize(new Dimension(200, 35));
        updateScheduleButton = new CreateUIComponentDashboard.CustomButton("Update Schedule");
        updateScheduleButton.setMinimumSize(new Dimension(200, 35));
        removeOldSchedulesButton = new CreateUIComponentDashboard.CustomButton("Remove Old Schedules");
        removeOldSchedulesButton.setMinimumSize(new Dimension(250, 35));

        ImageIcon addIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\heart.png");
        Image iconImage = addIcon.getImage();
        Image newAddIcon = iconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(newAddIcon);
        addScheduleButton.setIcon(addIcon);

        ImageIcon updateIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\updated.png");
        Image updateImage = updateIcon.getImage();
        Image newUpdateIcon = updateImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        updateIcon = new ImageIcon(newUpdateIcon);
        updateScheduleButton.setIcon(updateIcon);

        ImageIcon removeIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\remove.png");
        Image removeImage = removeIcon.getImage();
        Image newRemoveIcon = removeImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        removeIcon = new ImageIcon(newRemoveIcon);
        removeOldSchedulesButton.setIcon(removeIcon);
    }
}
