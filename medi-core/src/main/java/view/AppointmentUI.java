package view;

import controller.AppointmentController;
import controller.DoctorScheduleController;
import controller.PatientController;
import model.Appointment;
import model.DoctorSchedule;
import model.Email;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentUI {
    private JPanel BackPanel;
    private JTextField textPatientEmail;
    private JTextField textFee;
    private JComboBox comboDoctor;
    private JButton addAppointment;
    private JTextField textDate;
    private JTextField textTime;

    // class members
    Appointment appointmentObj; // appointment object to store the appointment details to be added to the database later on

    // controller definitions
    AppointmentController appointmentControllerObj; // appointment controller object to add the appointment to the database later on
    PatientController patientControllerObj; // patient controller object to get the patient object using email to add to the appointment object later on
    DoctorScheduleController doctorScheduleControllerObj; // doctor schedule controller object to get the doctor schedule list to set in the combo box later on

    List<DoctorSchedule> doctorScheduleList;// list to store the doctor schedule list to set in the combo box later on


    public AppointmentUI() {

        // fetch doctor schedule list start and set them in the combo box
        doctorScheduleControllerObj = new DoctorScheduleController();
        doctorScheduleList = new ArrayList<>();
        try {
            ResultSet resultSet = doctorScheduleControllerObj.getAllSchedule();

            DoctorSchedule doc1 = new DoctorSchedule(resultSet.getInt("did"), resultSet.getString("doctorName"), resultSet.getString("doctorSpecialization"), resultSet.getString("date"), resultSet.getString("time"));
            doctorScheduleList.add(doc1);

            while (resultSet.next()) {
                DoctorSchedule doctorSchedule = new DoctorSchedule(resultSet.getInt("did"), resultSet.getString("doctorName"), resultSet.getString("doctorSpecialization"), resultSet.getString("date"), resultSet.getString("time"));
                doctorScheduleList.add(doctorSchedule);
            }

            for(int i = 0; i < doctorScheduleList.size(); i++) {
                comboDoctor.addItem(doctorScheduleList.get(i).getDoctorName());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in fetching doctor schedule list");
            throw new RuntimeException(e);
        }

        // initialize the controller objects to add the appointment to the database later on
        appointmentControllerObj = new AppointmentController();
        patientControllerObj = new PatientController();

        addAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientEmail = textPatientEmail.getText();
                Double fee = Double.parseDouble(textFee.getText());

                // get the patient object using email and add it to the appointment object and add the appointment to the database later on
                ResultSet patientResult = patientControllerObj.getPatientByEmail(patientEmail);

                try {
                    // get the patient object using email and add it to the appointment object and add the appointment to the database later on
                    int pid = patientResult.getInt("pid");
                    String fullName = patientResult.getString("fullname");
                    String gender = patientResult.getString("gender");
                    String email = patientResult.getString("email");
                    String phone = patientResult.getString("contact");
                    String address = patientResult.getString("address");
                    int index = comboDoctor.getSelectedIndex();

                    Patient patient = patientControllerObj.addPatient(pid, fullName, gender, email, phone, address);
                    DoctorSchedule doctorSchedule = doctorScheduleList.get(index-1);

                    appointmentObj = appointmentControllerObj.addAppointment(1, patient, doctorSchedule , fee);
                    if (!appointmentControllerObj.addAppointmentToDataBase()) {
                        JOptionPane.showMessageDialog(null, "Error in adding appointment");
                    }

                    JOptionPane.showMessageDialog(null, "Appointment added successfully");

                    try{
                        Email emailObj = new Email();
                        emailObj.sendEmail(patient.getEmail(), "Appointment Confirmation", "Your appointment has been confirmed with " + doctorSchedule.getDoctorName() + " on " + doctorSchedule.getDate() + " at " + doctorSchedule.getTime() + ". Please be on time.");
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error in sending email");
                        throw new RuntimeException(ex);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error in adding appointment");
                    throw new RuntimeException(ex);
                }

                textPatientEmail.setText("");
                textFee.setText("");
                textDate.setText("");
                textTime.setText("");
                comboDoctor.setSelectedIndex(0);
            }
        });
        comboDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboDoctor.getSelectedIndex();
                DoctorSchedule doctorSchedule = doctorScheduleList.get(index-1);
                textDate.setText(doctorSchedule.getDate());
                textTime.setText(doctorSchedule.getTime());
            }
        });
    }

    public JPanel getAppointmentUI() {
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AppointmentUI");
        frame.setContentPane(new AppointmentUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        textPatientEmail = new CreateUIComponentDashboard.RoundedJTextField(20);
        textPatientEmail.setFont(new Font("Arial", Font.PLAIN, 15));

        comboDoctor = new CreateUIComponentDashboard.CustomComboBox();

        textFee = new CreateUIComponentDashboard.RoundedJTextField(20);
        textFee.setFont(new Font("Arial", Font.PLAIN, 15));

        textDate = new CreateUIComponentDashboard.RoundedJTextField(20);
        textDate.setFont(new Font("Arial", Font.PLAIN, 15));

        textTime = new CreateUIComponentDashboard.RoundedJTextField(20);
        textTime.setFont(new Font("Arial", Font.PLAIN, 15));

        addAppointment = new CreateUIComponentDashboard.CustomButton("Add Appointment");
        addAppointment.setMinimumSize(new Dimension(200, 35));

        ImageIcon addIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\heart.png");
        Image iconImage = addIcon.getImage();
        Image newAddIcon = iconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(newAddIcon);
        addAppointment.setIcon(addIcon);

    }
}
