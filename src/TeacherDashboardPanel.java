import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeacherDashboardPanel extends JPanel {
    private String teacherUsername;
    private String teacherFullName;
    private MainFrame mainFrame;
    private JPanel coursesPanel;
    
    public TeacherDashboardPanel(MainFrame mainFrame, String username) {
        this.mainFrame = mainFrame;
        this.teacherUsername = username;
        this.teacherFullName = DataManager.getUserFullName(username, false);
        
        initComponents();
        loadCourses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome, " + teacherFullName);
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
        
        // Top Panel with title and create button
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Courses & Student Reviews");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JButton createCourseButton = new JButton("Create New Course");
        createCourseButton.setBackground(new Color(46, 204, 113));
        createCourseButton.setForeground(Color.WHITE);
        createCourseButton.setFont(new Font("Arial", Font.BOLD, 12));
        createCourseButton.setPreferredSize(new Dimension(160, 35));
        createCourseButton.setOpaque(true);
        createCourseButton.setBorderPainted(false);
        createCourseButton.setFocusPainted(false);
        createCourseButton.addActionListener(e -> {
            mainFrame.showCreateCourse(teacherUsername);
        });
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(createCourseButton, BorderLayout.EAST);
        
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadCourses() {
        // Clear existing courses first
        coursesPanel.removeAll();
        
        List<Course> courses = DataManager.getTeacherCourses(teacherUsername);
        
        for (Course course : courses) {
            JPanel coursePanel = createCoursePanel(course);
            coursesPanel.add(coursePanel);
            coursesPanel.add(Box.createVerticalStrut(10));
        }
        
        if (courses.isEmpty()) {
            JLabel noCoursesLabel = new JLabel("No courses assigned!");
            noCoursesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noCoursesLabel.setForeground(Color.GRAY);
            coursesPanel.add(noCoursesLabel);
        }
        
        coursesPanel.revalidate();
        coursesPanel.repaint();
    }
    
    private JPanel createCoursePanel(Course course) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Course Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        
        JLabel courseCodeLabel = new JLabel(course.getCourseCode());
        courseCodeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseCodeLabel.setForeground(new Color(52, 73, 94));
        
        JLabel courseNameLabel = new JLabel(course.getCourseName());
        courseNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        courseNameLabel.setForeground(new Color(52, 73, 94));
        
        infoPanel.add(courseCodeLabel);
        infoPanel.add(courseNameLabel);
        
        // Review count and button panel
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setOpaque(false);
        
        List<Review> reviews = DataManager.getCourseReviews(course.getCourseCode(), teacherUsername);
        JLabel reviewCountLabel = new JLabel("Reviews: " + reviews.size());
        reviewCountLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        reviewCountLabel.setForeground(new Color(127, 140, 141));
        reviewCountLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        JButton viewButton = new JButton("View Reviews");
        viewButton.setBackground(new Color(142, 68, 173));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewButton.setPreferredSize(new Dimension(130, 35));
        viewButton.setOpaque(true);
        viewButton.setBorderPainted(false);
        viewButton.setFocusPainted(false);
        
        viewButton.addActionListener(e -> {
            mainFrame.showTeacherReview(teacherUsername, course);
        });
        
        rightPanel.add(reviewCountLabel);
        rightPanel.add(viewButton);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    public void refreshCourses() {
        loadCourses();
    }
}
