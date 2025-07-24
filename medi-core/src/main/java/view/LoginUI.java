package view;

import controller.LoginController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginUI {
    private JTextField textUsername;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel BackPanel;
    private JLabel status;
    private JLabel username;
    private JLabel psw;
    private JLabel usrIcon;
    private JPanel usrbg;
    private JLabel passwordIcon;
    private JPanel pswbg;
    private JLabel logoArea;


    public LoginUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textUsername.getText();
                char[] password = passwordField.getPassword();
                String pass = new String(password);

                User user = new User(username, pass);
                LoginController loginController = new LoginController();
                User validUser = loginController.validateUser(user);

                if(validUser != null) {
                    switch (validUser.getUsername()) {
                        case "admin":
                            JFrame homeFrame = new JFrame("HomeUI");
                            homeFrame.setContentPane(new DashboardUI().getDashboardUI());
                            homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            homeFrame.setSize(1366, 768);
                            homeFrame.setVisible(true);
                            Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
                            homeFrame.setLocation(dim1.width/2-homeFrame.getSize().width/2, dim1.height/2-homeFrame.getSize().height/2);

                            // Dispose the current login frame
                            SwingUtilities.getWindowAncestor(BackPanel).dispose();
                            break;
                        case "pharmacist":
                            JFrame pharmacistFrame = new JFrame("PharmacistUI");
                            pharmacistFrame.setContentPane(new InventoryUI().getPanel1());
                            pharmacistFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            pharmacistFrame.setSize(1366, 768);
                            pharmacistFrame.setVisible(true);
                            Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
                            pharmacistFrame.setLocation(dim2.width/2-pharmacistFrame.getSize().width/2, dim2.height/2-pharmacistFrame.getSize().height/2);

                            // Dispose the current login frame
                            SwingUtilities.getWindowAncestor(BackPanel).dispose();
                            break;
                    }
                }
                else {
                    status.setText("Wrong username or password");
                }
            }
        });

        // Mouse hover effect for login button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setForeground(Color.BLACK);
            }
        });

        // Enter key listener for password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        usrbg.setBackground(new Color(0, 0, 0, 0));
        pswbg.setBackground(new Color(0, 0, 0, 0));

        // username field
        Font currentFont = textUsername.getFont();
        textUsername.setFont(currentFont.deriveFont(currentFont.getSize()*1.5f));
        textUsername.setHorizontalAlignment(JTextField.CENTER);

        // password field
        Font currentFont1 = passwordField.getFont();
        passwordField.setFont(currentFont1.deriveFont(currentFont1.getSize()*1.5f));
        passwordField.setHorizontalAlignment(JPasswordField.CENTER);

        // login button
        loginButton.setFont(loginButton.getFont().deriveFont(18.f));

        // username label
        username.setFont(username.getFont().deriveFont(15.f));
        username.setForeground(Color.WHITE);

        // password label
        psw.setFont(psw.getFont().deriveFont(15.f));
        psw.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MediCore");
        frame.setUndecorated(false);
        frame.setIconImage(new ImageIcon("src/main/java/assets/icon.png").getImage());
        frame.setContentPane(new LoginUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        passwordField = new CreateUIComponent.RoundedJPasswordField(20);
        textUsername = new CreateUIComponent.RoundedJTextField(20);
        loginButton = new CreateUIComponent.CustomButton("Login");

        BackPanel = new javax.swing.JPanel() {
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

        ImageIcon logo = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\iconnew.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);

        ImageIcon userIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\user.png");
        Image userIconImage = userIcon.getImage();
        Image newUserIcon = userIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserIcon);
        usrIcon = new JLabel(userIcon);

        ImageIcon pswIcon = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\padlock.png");
        Image pswIconImage = pswIcon.getImage();
        Image newPswIcon = pswIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        pswIcon = new ImageIcon(newPswIcon);
        passwordIcon = new JLabel(pswIcon);

        ImageIcon login = new ImageIcon("D:\\y1s2\\OOP\\group assignment\\Project\\medi-core\\src\\main\\java\\assets\\icons\\enter.png");
        Image loginImage = login.getImage();
        Image newLogin = loginImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        login = new ImageIcon(newLogin);
        loginButton.setIcon(login);
    }

    public JPanel getLoginUI() {
        return BackPanel;
    }
}
