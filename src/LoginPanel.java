import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton studentRadio, teacherRadio;
    private ButtonGroup roleGroup;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
       
        
        initComponents();
        layoutComponents();
    }    private void initComponents() {
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        studentRadio = new JRadioButton("Student", true);
        teacherRadio = new JRadioButton("Teacher");
        roleGroup = new ButtonGroup();
        roleGroup.add(studentRadio);
        roleGroup.add(teacherRadio);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(450, 80));

        JLabel titleLabel = new JLabel("Teacher-Student Collaboration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);

        // Role Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Login as:"), gbc);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.add(studentRadio);
        rolePanel.add(teacherRadio);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(rolePanel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // ✅ Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(150, 35));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(true);
        loginButton.setFocusPainted(false);

        // ✅ Register Button
        JButton registerButton = new JButton("Register New Account");
        registerButton.setBackground(new Color(52, 152, 219));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(150, 35));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(true);
        registerButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        loginButton.addActionListener(new LoginActionListener());
        registerButton.addActionListener(e -> mainFrame.showRegistration());

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Please enter both username and password!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isStudent = studentRadio.isSelected();

            // Validate login
            if (DataManager.validateLogin(username, password, isStudent)) {
                mainFrame.login(username, isStudent);
            } else {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Invalid username or password!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}