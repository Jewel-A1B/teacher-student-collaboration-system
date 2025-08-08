import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, JPanel> panels;
    private Stack<String> navigationStack;
    
    // Current user info
    private String currentUsername;
    private boolean isCurrentUserStudent;
    
    public MainFrame() {
        setTitle("Teacher-Student Collaboration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panels = new HashMap<>();
        navigationStack = new Stack<>();
        
        initializePanels();
        
        add(mainPanel);
        showPanel("login");
    }
    
    private void initializePanels() {
        // Add Login Panel
        LoginPanel loginPanel = new LoginPanel(this);
        panels.put("login", loginPanel);
        mainPanel.add(loginPanel, "login");
        
        // Add Registration Panel
        RegistrationPanel registrationPanel = new RegistrationPanel(this);
        panels.put("registration", registrationPanel);
        mainPanel.add(registrationPanel, "registration");
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        
        // Add to navigation stack (but not if it's the same as current top)
        if (navigationStack.isEmpty() || !navigationStack.peek().equals(panelName)) {
            navigationStack.push(panelName);
        }
    }
    
    public void goBack() {
        if (navigationStack.size() > 1) {
            navigationStack.pop(); // Remove current panel
            String previousPanel = navigationStack.peek();
            cardLayout.show(mainPanel, previousPanel);
        }
    }
    
    public void login(String username, boolean isStudent) {
        this.currentUsername = username;
        this.isCurrentUserStudent = isStudent;
        
        if (isStudent) {
            // Create and add student dashboard if not exists
            if (!panels.containsKey("studentDashboard")) {
                StudentDashboardPanel studentPanel = new StudentDashboardPanel(this, username);
                panels.put("studentDashboard", studentPanel);
                mainPanel.add(studentPanel, "studentDashboard");
            }
            showPanel("studentDashboard");
        } else {
            // Create and add teacher dashboard if not exists
            if (!panels.containsKey("teacherDashboard")) {
                TeacherDashboardPanel teacherPanel = new TeacherDashboardPanel(this, username);
                panels.put("teacherDashboard", teacherPanel);
                mainPanel.add(teacherPanel, "teacherDashboard");
            }
            showPanel("teacherDashboard");
        }
    }
    
    public void logout() {
        // Clear user info
        currentUsername = null;
        isCurrentUserStudent = false;
        
        // Clear navigation stack
        navigationStack.clear();
        
        // Go to login
        showPanel("login");
    }
    
    public void showLogin() {
        showPanel("login");
    }
    
    public void showRegistration() {
        showPanel("registration");
    }
    
    public void showCourseReview(String studentUsername, Course course, String teacherName) {
        String panelKey = "courseReview_" + course.getCourseCode();
        
        if (!panels.containsKey(panelKey)) {
            CourseReviewPanel reviewPanel = new CourseReviewPanel(this, studentUsername, course, teacherName);
            panels.put(panelKey, reviewPanel);
            mainPanel.add(reviewPanel, panelKey);
        }
        showPanel(panelKey);
    }
    
    public void showTeacherReview(String teacherUsername, Course course) {
        String panelKey = "teacherReview_" + course.getCourseCode();
        
        if (!panels.containsKey(panelKey)) {
            TeacherReviewPanel reviewPanel = new TeacherReviewPanel(this, teacherUsername, course);
            panels.put(panelKey, reviewPanel);
            mainPanel.add(reviewPanel, panelKey);
        }
        showPanel(panelKey);
    }
    
    public void showCreateCourse(String teacherUsername) {
        if (!panels.containsKey("createCourse")) {
            CreateCoursePanel createPanel = new CreateCoursePanel(this, teacherUsername);
            panels.put("createCourse", createPanel);
            mainPanel.add(createPanel, "createCourse");
        }
        showPanel("createCourse");
    }
    
    public String getCurrentUsername() {
        return currentUsername;
    }
    
    public boolean isCurrentUserStudent() {
        return isCurrentUserStudent;
    }
    
    public void refreshTeacherDashboard() {
        TeacherDashboardPanel teacherPanel = (TeacherDashboardPanel) panels.get("teacherDashboard");
        if (teacherPanel != null) {
            teacherPanel.refreshCourses();
        }
    }
}
