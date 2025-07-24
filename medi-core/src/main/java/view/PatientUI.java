package view;

import controller.PatientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientUI {
    private JPanel BackPanel;
    private JTextField textName;
    private JTextField textEmail;
    private JTextField textContactNumber;
    private JTextArea textAddress;
    private JButton addPatientBtn;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    // create a object using controller class to add patient details to the database
    PatientController objController;

    public PatientUI() {
        // create a object using controller class to add patient details to the database
        objController = new PatientController();
        int patientCount = 0;

        addPatientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pid = patientCount + 1;
                String name = textName.getText();
                String email = textEmail.getText();

                String gender = "";
                if (maleRadioButton.isSelected()) {
                    gender = "male";
                }
                if (femaleRadioButton.isSelected()) {
                    gender = "female";
                }

                String contactNumber = textContactNumber.getText();
                String address = textAddress.getText();

                if (name.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || address.isEmpty() || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    // add patient details to the object of model class using controller class
                    objController.addPatient(pid, name, gender, email, contactNumber, address);

                    // add patient details to the database using controller class
                    if (!objController.addPatientToDataBase()) {
                        JOptionPane.showMessageDialog(null, "Error in adding patient");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Patient added successfully!");
                }

                // clear the text fields after adding patient details
                textName.setText("");
                textEmail.setText("");
                textContactNumber.setText("");
                textAddress.setText("");
            }
        });
        maleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                femaleRadioButton.setSelected(false);
            }
        });
        femaleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maleRadioButton.setSelected(false);
            }
        });
    }

    public JPanel getPatientUI() {
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PatientUI");
        frame.setSize(300, 300);
        frame.setContentPane(new PatientUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        textName = new CreateUIComponentDashboard.RoundedJTextField(20);
        textName.setFont(new Font("Arial", Font.PLAIN, 15));

        textEmail = new CreateUIComponentDashboard.RoundedJTextField(20);
        textEmail.setFont(new Font("Arial", Font.PLAIN, 15));

        maleRadioButton = new CreateUIComponentDashboard.CustomRadioButton("Male");
        femaleRadioButton = new CreateUIComponentDashboard.CustomRadioButton("Female");

        textContactNumber = new CreateUIComponentDashboard.RoundedJTextField(20);
        textContactNumber.setFont(new Font("Arial", Font.PLAIN, 15));

        textAddress = new CreateUIComponentDashboard.CustomTextArea(1, 50);

        addPatientBtn = new CreateUIComponentDashboard.CustomButton("Add Patient");
        addPatientBtn.setPreferredSize(new Dimension(200, 35));

        ImageIcon addIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\heart.png");
        Image iconImage = addIcon.getImage();
        Image newAddIcon = iconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(newAddIcon);
        addPatientBtn.setIcon(addIcon);
    }
}
