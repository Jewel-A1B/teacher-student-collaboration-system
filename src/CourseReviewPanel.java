import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseReviewPanel extends JPanel {
    private String studentUsername;
    private Course course;
    private String teacherName;
    private MainFrame mainFrame;
    private JPanel reviewsPanel;
    private JTextArea commentArea;
    private JSlider ratingSlider;
    
    public CourseReviewPanel(MainFrame mainFrame, String studentUsername, Course course, String teacherName) {
        this.mainFrame = mainFrame;
        this.studentUsername = studentUsername;
        this.course = course;
        this.teacherName = teacherName;
        
        initComponents();
        loadReviews();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(700, 80));
        headerPanel.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel courseLabel = new JLabel(course.getCourseCode() + " - " + course.getCourseName());
        courseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseLabel.setForeground(Color.WHITE);
        
        JLabel teacherLabel = new JLabel("Teacher: " + teacherName);
        teacherLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        teacherLabel.setForeground(Color.WHITE);
        
        titlePanel.add(courseLabel);
        titlePanel.add(teacherLabel);
        
        // Back button
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(41, 128, 185));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backButton.addActionListener(e -> mainFrame.goBack());
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(backButton);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add Review Panel
        JPanel addReviewPanel = createAddReviewPanel();
        
        // Reviews Display Panel
        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(680, 300));
        
        JLabel reviewsLabel = new JLabel("Previous Reviews:");
        reviewsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reviewsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JPanel reviewsContainer = new JPanel(new BorderLayout());
        reviewsContainer.add(reviewsLabel, BorderLayout.NORTH);
        reviewsContainer.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(addReviewPanel, BorderLayout.NORTH);
        mainPanel.add(reviewsContainer, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createAddReviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add New Review"));
        panel.setPreferredSize(new Dimension(680, 180));
        
        // Rating Panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPreferredSize(new Dimension(200, 50));
        
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingSlider);
        
        // Comment Panel
        JPanel commentPanel = new JPanel(new BorderLayout());
        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        commentArea = new JTextArea(4, 50);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        JScrollPane commentScrollPane = new JScrollPane(commentArea);
        
        commentPanel.add(commentLabel, BorderLayout.NORTH);
        commentPanel.add(commentScrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit Review");
        submitButton.setBackground(new Color(46, 204, 113));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setPreferredSize(new Dimension(130, 35));
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        
        submitButton.addActionListener(new SubmitReviewListener());
        
        buttonPanel.add(submitButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(ratingPanel, BorderLayout.NORTH);
        topPanel.add(commentPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadReviews() {
        reviewsPanel.removeAll();
        
        List<Review> reviews = DataManager.getCourseReviews(course.getCourseCode(), course.getTeacherUsername());
        
        if (reviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews yet. Be the first to review!");
            noReviewsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noReviewsLabel.setForeground(Color.GRAY);
            noReviewsLabel.setHorizontalAlignment(JLabel.CENTER);
            reviewsPanel.add(noReviewsLabel);
        } else {
            for (Review review : reviews) {
                JPanel reviewPanel = createReviewPanel(review);
                reviewsPanel.add(reviewPanel);
                reviewsPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }
    
    private JPanel createReviewPanel(Review review) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Header with student name, rating and date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        String studentName = DataManager.getUserFullName(review.getStudentUsername(), true);
        JLabel studentLabel = new JLabel(studentName);
        studentLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        String ratingText = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
        JLabel ratingLabel = new JLabel(ratingText + " (" + review.getRating() + "/5)");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        ratingLabel.setForeground(new Color(241, 196, 15));
        
        JLabel dateLabel = new JLabel(review.getFormattedDate());
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        dateLabel.setForeground(Color.GRAY);
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(studentLabel);
        leftPanel.add(Box.createHorizontalStrut(10));
        leftPanel.add(ratingLabel);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);
        
        // Comment
        JTextArea commentTextArea = new JTextArea(review.getComment());
        commentTextArea.setEditable(false);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setOpaque(false);
        commentTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
        contentPanel.add(commentTextArea, BorderLayout.SOUTH);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Teacher Reply
        if (review.hasReply()) {
            JPanel replyPanel = new JPanel(new BorderLayout());
            replyPanel.setBackground(new Color(236, 240, 241));
            replyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            
            JLabel replyLabel = new JLabel("Teacher Reply:");
            replyLabel.setFont(new Font("Arial", Font.BOLD, 11));
            replyLabel.setForeground(new Color(155, 89, 182));
            
            JTextArea replyTextArea = new JTextArea(review.getReply());
            replyTextArea.setEditable(false);
            replyTextArea.setLineWrap(true);
            replyTextArea.setWrapStyleWord(true);
            replyTextArea.setOpaque(false);
            replyTextArea.setFont(new Font("Arial", Font.PLAIN, 11));
            
            replyPanel.add(replyLabel, BorderLayout.NORTH);
            replyPanel.add(replyTextArea, BorderLayout.CENTER);
            
            panel.add(replyPanel, BorderLayout.SOUTH);
        }
        
        return panel;
    }
    
    private class SubmitReviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentArea.getText().trim();
            
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(CourseReviewPanel.this,
                    "Please write a comment!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int rating = ratingSlider.getValue();
            
            DataManager.addReview(studentUsername, course.getCourseCode(), 
                                course.getTeacherUsername(), comment, rating);
            
            JOptionPane.showMessageDialog(CourseReviewPanel.this,
                "Review submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            commentArea.setText("");
            ratingSlider.setValue(3);
            
            // Reload reviews
            loadReviews();
        }
    }
}
