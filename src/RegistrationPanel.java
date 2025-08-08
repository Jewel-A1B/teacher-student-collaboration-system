import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField extraInfoField;
    private JComboBox<String> semesterCombo;
    private JRadioButton studentRadio, teacherRadio;
    private ButtonGroup userTypeGroup;
    private boolean isStudent;
    private MainFrame mainFrame;
    private JPanel formPanel;
    
    public RegistrationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.isStudent = true; // Default to student
        
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        fullNameField = new JTextField(15);
        extraInfoField = new JTextField(15);
        
        // User Type Selection
        studentRadio = new JRadioButton("Student", true);
        teacherRadio = new JRadioButton("Teacher", false);
        userTypeGroup = new ButtonGroup();
        userTypeGroup.add(studentRadio);
        userTypeGroup.add(teacherRadio);
        
        // Semester combo for students
        String[] semesters = {
            "1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
            "5th Semester", "6th Semester", "7th Semester", "8th Semester"
        };
        semesterCombo = new JComboBox<>(semesters);
        
        // Add listeners for user type change
        studentRadio.addActionListener(e -> {
            isStudent = true;
            updateFormForUserType();
        });
        
        teacherRadio.addActionListener(e -> {
            isStudent = false;
            updateFormForUserType();
        });
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        Color headerColor = isStudent ? new Color(52, 152, 219) : new Color(155, 89, 182);
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(450, 80));
        
        String userType = isStudent ? "Student" : "Teacher";
        JLabel titleLabel = new JLabel("New " + userType + " Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        
        // Create form panel
        createFormPanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void createFormPanel() {
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // User Type Selection
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel userTypePanel = new JPanel(new FlowLayout());
        userTypePanel.add(new JLabel("Register as: "));
        userTypePanel.add(studentRadio);
        userTypePanel.add(teacherRadio);
        formPanel.add(userTypePanel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(confirmPasswordField, gbc);
        
        // Full Name
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Full Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(fullNameField, gbc);
        
        // Extra Info (Dynamic based on user type)
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        String extraLabel = isStudent ? "Semester:" : "Designation:";
        formPanel.add(new JLabel(extraLabel), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (isStudent) {
            formPanel.add(semesterCombo, gbc);
        } else {
            formPanel.add(extraInfoField, gbc);
        }
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        Color headerColor = isStudent ? new Color(52, 152, 219) : new Color(155, 89, 182);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(headerColor);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(110, 40));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(110, 40));
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);
        
        // Add action listeners
        registerButton.addActionListener(new RegisterActionListener());
        cancelButton.addActionListener(e -> mainFrame.showLogin());
    }
    
    private void updateFormForUserType() {
        // Remove current form and recreate
        if (formPanel != null) {
            remove(formPanel);
        }
        
        // Update header color and title
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getBackground() != null) {
                    Color headerColor = isStudent ? new Color(52, 152, 219) : new Color(155, 89, 182);
                    panel.setBackground(headerColor);
                    
                    // Update title
                    Component[] panelComps = panel.getComponents();
                    for (Component panelComp : panelComps) {
                        if (panelComp instanceof JLabel) {
                            JLabel titleLabel = (JLabel) panelComp;
                            String userType = isStudent ? "Student" : "Teacher";
                            titleLabel.setText("New " + userType + " Registration");
                            break;
                        }
                    }
                    break;
                }
            }
        }
        
        // Recreate form
        createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // Refresh UI
        revalidate();
        repaint();
    }
    
    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String fullName = fullNameField.getText().trim();
            String extraInfo;
            
            if (isStudent) {
                extraInfo = (String) semesterCombo.getSelectedItem();
            } else {
                extraInfo = extraInfoField.getText().trim();
            }
            
            // Validation
            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || extraInfo.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if username already exists
            if (DataManager.usernameExists(username, isStudent)) {
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    "Username already exists! Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Register user
            boolean success = DataManager.registerUser(username, password, fullName, extraInfo, isStudent);
            
            if (success) {
                String userType = isStudent ? "Student" : "Teacher";
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    userType + " registration successful!\\nYou can now login with your credentials.",
                    "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.showLogin();
            } else {
                JOptionPane.showMessageDialog(RegistrationPanel.this,
                    "Registration failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
