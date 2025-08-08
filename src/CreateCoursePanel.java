import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCoursePanel extends JPanel {
    private String teacherUsername;
    private MainFrame mainFrame;
    private JTextField courseCodeField;
    private JTextField courseNameField;
    private JComboBox<String> semesterCombo;
    private JComboBox<String> departmentCombo;
    
    public CreateCoursePanel(MainFrame mainFrame, String teacherUsername) {
        this.mainFrame = mainFrame;
        this.teacherUsername = teacherUsername;
        
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        courseCodeField = new JTextField(15);
        courseNameField = new JTextField(15);
        
        // Semester dropdown
        String[] semesters = {
            "1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
            "5th Semester", "6th Semester", "7th Semester", "8th Semester"
        };
        semesterCombo = new JComboBox<>(semesters);
        
        // Department dropdown
        String[] departments = {
            "CSE", "EEE", "CE", "ME", "BBA", "MBA", "English", "Mathematics"
        };
        departmentCombo = new JComboBox<>(departments);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setPreferredSize(new Dimension(400, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Create New Course");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        // Back button
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(142, 68, 173));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backButton.addActionListener(e -> mainFrame.goBack());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Course Code
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(new JLabel("Course Code:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(courseCodeField, gbc);
        
        // Course Name
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Course Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(courseNameField, gbc);
        
        // Semester
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Semester:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(semesterCombo, gbc);
        
        // Department
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Department:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(departmentCombo, gbc);
        
        // Info Label
        JLabel infoLabel = new JLabel("<html><i>Students from same semester & department will see this course</i></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5);
        mainPanel.add(infoLabel, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton createButton = new JButton("Create Course");
        createButton.setBackground(new Color(46, 204, 113));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Arial", Font.BOLD, 12));
        createButton.setPreferredSize(new Dimension(130, 35));
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setPreferredSize(new Dimension(90, 35));
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);
        
        // Add action listeners
        createButton.addActionListener(new CreateCourseListener());
        cancelButton.addActionListener(e -> mainFrame.goBack());
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private class CreateCourseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String courseCode = courseCodeField.getText().trim().toUpperCase();
            String courseName = courseNameField.getText().trim();
            String semester = (String) semesterCombo.getSelectedItem();
            String department = (String) departmentCombo.getSelectedItem();
            
            // Validation
            if (courseCode.isEmpty() || courseName.isEmpty()) {
                JOptionPane.showMessageDialog(CreateCoursePanel.this,
                    "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (courseCode.length() < 3) {
                JOptionPane.showMessageDialog(CreateCoursePanel.this,
                    "Course code must be at least 3 characters!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create course
            boolean success = DataManager.createCourse(courseCode, courseName, teacherUsername, semester, department);
            
            if (success) {
                JOptionPane.showMessageDialog(CreateCoursePanel.this,
                    "Course created successfully!\\nStudents from " + semester + " (" + department + ") can now see this course.",
                    "Course Created", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh teacher dashboard before going back
                mainFrame.refreshTeacherDashboard();
                
                // Go back to teacher dashboard
                mainFrame.goBack();
            } else {
                JOptionPane.showMessageDialog(CreateCoursePanel.this,
                    "Course code already exists! Please choose a different code.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
