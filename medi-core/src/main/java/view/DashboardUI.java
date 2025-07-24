package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardUI {
    private JButton patientsButton;
    private JButton doctorScheduleButton;
    private JButton newAppointmentButton;
    private JPanel ViewPanel;
    private JPanel BackPanel;
    private JLabel logoArea;
    private JButton signoutButton;
    private JPanel sidePanel2;
    private JPanel sidepanel1;
    private JPanel logoutArea;
    private JButton reportsButton;

    public DashboardUI() {
        CardLayout cardLayout = new CardLayout();
        ViewPanel.setLayout(cardLayout);

        logoArea.setOpaque(false);
        sidepanel1.setOpaque(false);
        logoutArea.setOpaque(false);

        ViewPanel.setSize(600, 500);

        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientUI patientUI = new PatientUI();
                ViewPanel.add(patientUI.getPatientUI(), "patientUI");
                cardLayout.show(ViewPanel, "patientUI");
            }
        });

        doctorScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ViewPanel.removeAll();
                DoctorScheduleUI doctorScheduleUI = new DoctorScheduleUI();
                ViewPanel.add(doctorScheduleUI.getDoctorScheduleUI(), "doctorScheduleUI");
                cardLayout.show(ViewPanel, "doctorScheduleUI");
            }
        });

        newAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentUI appointmentUI = new AppointmentUI();
                ViewPanel.add(appointmentUI.getAppointmentUI(), "appointmentUI");
                cardLayout.show(ViewPanel, "appointmentUI");
            }
        });

        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportUI reportUI = new ReportUI();
                ViewPanel.add(reportUI.getReportUI(), "reportUI");
                cardLayout.show(ViewPanel, "reportUI");
            }
        });

        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginUI loginUI = new LoginUI();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(BackPanel);
                frame.setContentPane(loginUI.getLoginUI());
                frame.revalidate();
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MediCore");
        frame.setContentPane(new DashboardUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public JPanel getDashboardUI() {
        return BackPanel;
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        sidePanel2 = new javax.swing.JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {

                    // Define the start and end points of the gradient
                    float startX = 0.0f;
                    float startY = 0.0f;
                    float endX = 0.1f;
                    float endY = getHeight();


                    // Define the positions and colors for the gradient
                    float[] fractions = {0.0f, 0.5f, 1.0f}; // Positions for each color (0%, 50%, 100%)
                    Color[] colors = {
                            new Color(202, 240, 248, 255),  // First color
                            new Color(144, 224, 239, 255), // Middle color
                            new Color(0, 180, 216, 255)   // Last color
                    };

                    // Create a LinearGradientPaint object
                    LinearGradientPaint p = new LinearGradientPaint(startX, startY, endX, endY, fractions, colors);

                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        patientsButton = new CreateUIComponent.CustomButton("Patients");
        patientsButton.setMinimumSize(new Dimension(0, 35));

        doctorScheduleButton = new CreateUIComponent.CustomButton("Doctor Schedule");
        doctorScheduleButton.setMinimumSize(new Dimension(0, 35));

        newAppointmentButton = new CreateUIComponent.CustomButton("New Appointment");
        reportsButton = new CreateUIComponent.CustomButton("Reports");

        reportsButton.setMinimumSize(new Dimension(0, 35));
        newAppointmentButton.setMinimumSize(new Dimension(0, 35));

        signoutButton = new CreateUIComponent.CustomButton("Sign Out");

        patientsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        doctorScheduleButton.setFont(new Font("Arial", Font.PLAIN, 20));
        newAppointmentButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signoutButton.setFont(new Font("Arial", Font.PLAIN, 18));

        ImageIcon logo = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\iconnew.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);

        ImageIcon patientIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\patient.png");
        Image patientIconImage = patientIcon.getImage();
        Image newPatientIcon = patientIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        patientIcon = new ImageIcon(newPatientIcon);
        patientsButton.setIcon(patientIcon);

        ImageIcon doctorIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\doctor-appointment.png");
        Image doctorIconImage = doctorIcon.getImage();
        Image newDoctorIcon = doctorIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        doctorIcon = new ImageIcon(newDoctorIcon);
        doctorScheduleButton.setIcon(doctorIcon);

        ImageIcon appointmentIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\medical.png");
        Image appointmentIconImage = appointmentIcon.getImage();
        Image newAppointmentIcon = appointmentIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        appointmentIcon = new ImageIcon(newAppointmentIcon);
        newAppointmentButton.setIcon(appointmentIcon);

        ImageIcon reportIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\report.png");
        Image reportIconImage = reportIcon.getImage();
        Image newReportIcon = reportIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        reportIcon = new ImageIcon(newReportIcon);
        reportsButton.setIcon(reportIcon);

        ImageIcon signoutIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\exit.png");
        Image signoutIconImage = signoutIcon.getImage();
        Image newSignoutIcon = signoutIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        signoutIcon = new ImageIcon(newSignoutIcon);
        signoutButton.setIcon(signoutIcon);
    }
}
