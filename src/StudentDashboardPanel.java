import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashboardPanel extends JPanel {
    private String studentUsername;
    private String studentFullName;
    private MainFrame mainFrame;
    private JPanel coursesPanel;
    
    public StudentDashboardPanel(MainFrame mainFrame, String username) {
        this.mainFrame = mainFrame;
        this.studentUsername = username;
        this.studentFullName = DataManager.getUserFullName(username, true);
        
        initComponents();
        loadCourses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome, " + studentFullName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutButton.addActionListener(e -> mainFrame.logout());
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(logoutButton);
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Your Courses & Teacher Connection");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadCourses() {
        // Clear existing courses first
        coursesPanel.removeAll();
        
        String studentSemester = DataManager.getUserSemester(studentUsername);
        List<Course> courses = DataManager.getStudentCoursesBySemester(studentUsername, studentSemester);
        
        for (Course course : courses) {
            String teacherName = DataManager.getTeacherNameByUsername(course.getTeacherUsername());
            
            JPanel coursePanel = createCoursePanel(course, teacherName);
            coursesPanel.add(coursePanel);
            coursesPanel.add(Box.createVerticalStrut(10));
        }
        
        if (courses.isEmpty()) {
            JLabel noCoursesLabel = new JLabel("No courses available for your semester!");
            noCoursesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noCoursesLabel.setForeground(Color.GRAY);
            coursesPanel.add(noCoursesLabel);
        }
        
        coursesPanel.revalidate();
        coursesPanel.repaint();
    }
    
    private JPanel createCoursePanel(Course course, String teacherName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Course Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        
        JLabel courseCodeLabel = new JLabel(course.getCourseCode());
        courseCodeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseCodeLabel.setForeground(new Color(52, 73, 94));
        
        JLabel courseNameLabel = new JLabel(course.getCourseName());
        courseNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        courseNameLabel.setForeground(new Color(52, 73, 94));
        
        JLabel teacherLabel = new JLabel("Teacher: " + teacherName);
        teacherLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        teacherLabel.setForeground(new Color(127, 140, 141));
        
        infoPanel.add(courseCodeLabel);
        infoPanel.add(courseNameLabel);
        infoPanel.add(teacherLabel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        JButton reviewButton = new JButton("View/Add Reviews");
        reviewButton.setBackground(new Color(46, 204, 113));
        reviewButton.setForeground(Color.WHITE);
        reviewButton.setFont(new Font("Arial", Font.BOLD, 12));
        reviewButton.setPreferredSize(new Dimension(150, 35));
        reviewButton.setOpaque(true);
        reviewButton.setBorderPainted(false);
        reviewButton.setFocusPainted(false);
        
        reviewButton.addActionListener(e -> {
            mainFrame.showCourseReview(studentUsername, course, teacherName);
        });
        
        buttonPanel.add(reviewButton);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }
}
