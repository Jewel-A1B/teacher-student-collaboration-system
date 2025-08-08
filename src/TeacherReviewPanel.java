import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeacherReviewPanel extends JPanel {
    private String teacherUsername;
    private Course course;
    private MainFrame mainFrame;
    private JPanel reviewsPanel;
    
    public TeacherReviewPanel(MainFrame mainFrame, String teacherUsername, Course course) {
        this.mainFrame = mainFrame;
        this.teacherUsername = teacherUsername;
        this.course = course;
        
        initComponents();
        loadReviews();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel courseLabel = new JLabel(course.getCourseCode() + " - " + course.getCourseName());
        courseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseLabel.setForeground(Color.WHITE);
        
        JLabel infoLabel = new JLabel("Student Reviews & Feedback");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(Color.WHITE);
        
        titlePanel.add(courseLabel);
        titlePanel.add(infoLabel);
        
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
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(backButton);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        // Main Content Panel
        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadReviews() {
        reviewsPanel.removeAll();
        
        List<Review> reviews = DataManager.getCourseReviews(course.getCourseCode(), teacherUsername);
        
        if (reviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews received yet.");
            noReviewsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noReviewsLabel.setForeground(Color.GRAY);
            noReviewsLabel.setHorizontalAlignment(JLabel.CENTER);
            reviewsPanel.add(Box.createVerticalGlue());
            reviewsPanel.add(noReviewsLabel);
            reviewsPanel.add(Box.createVerticalGlue());
        } else {
            // Calculate average rating
            double avgRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            
            JPanel statsPanel = createStatsPanel(reviews.size(), avgRating);
            reviewsPanel.add(statsPanel);
            reviewsPanel.add(Box.createVerticalStrut(20));
            
            for (Review review : reviews) {
                JPanel reviewPanel = createReviewPanel(review);
                reviewsPanel.add(reviewPanel);
                reviewsPanel.add(Box.createVerticalStrut(15));
            }
        }
        
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }
    
    private JPanel createStatsPanel(int totalReviews, double avgRating) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(new Color(245, 245, 245));
        
        JLabel statsLabel = new JLabel(String.format("Total Reviews: %d | Average Rating: %.1f/5.0", 
                                                     totalReviews, avgRating));
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsLabel.setForeground(new Color(155, 89, 182));
        
        panel.add(statsLabel);
        return panel;
    }
    
    private JPanel createReviewPanel(Review review) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        // Header with anonymous label, rating and date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel studentLabel = new JLabel("Anonymous Student");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        studentLabel.setForeground(new Color(52, 73, 94));
        
        String ratingText = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
        JLabel ratingLabel = new JLabel(ratingText + " (" + review.getRating() + "/5)");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ratingLabel.setForeground(new Color(241, 196, 15));
        
        JLabel dateLabel = new JLabel(review.getFormattedDate());
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        dateLabel.setForeground(Color.GRAY);
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        leftPanel.add(studentLabel);
        leftPanel.add(dateLabel);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(ratingLabel, BorderLayout.EAST);
        
        // Comment
        JTextArea commentTextArea = new JTextArea(review.getComment());
        commentTextArea.setEditable(false);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setOpaque(false);
        commentTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
        commentTextArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Reply Section
        JPanel replySection = createReplySection(review);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(commentTextArea, BorderLayout.CENTER);
        panel.add(replySection, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReplySection(Review review) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(189, 195, 199)));
        panel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        
        if (review.hasReply()) {
            // Show existing reply
            JPanel replyPanel = new JPanel(new BorderLayout());
            replyPanel.setOpaque(false);
            
            JLabel replyLabel = new JLabel("Your Reply:");
            replyLabel.setFont(new Font("Arial", Font.BOLD, 12));
            replyLabel.setForeground(new Color(155, 89, 182));
            
            JTextArea replyTextArea = new JTextArea(review.getReply());
            replyTextArea.setEditable(false);
            replyTextArea.setLineWrap(true);
            replyTextArea.setWrapStyleWord(true);
            replyTextArea.setOpaque(false);
            replyTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
            replyTextArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            
            replyPanel.add(replyLabel, BorderLayout.NORTH);
            replyPanel.add(replyTextArea, BorderLayout.CENTER);
            
            panel.add(replyPanel, BorderLayout.CENTER);
        } else {
            // Show reply form
            JPanel replyFormPanel = new JPanel(new BorderLayout());
            replyFormPanel.setOpaque(false);
            
            JLabel replyLabel = new JLabel("Reply to this review:");
            replyLabel.setFont(new Font("Arial", Font.BOLD, 12));
            replyLabel.setForeground(new Color(155, 89, 182));
            
            JTextArea replyTextArea = new JTextArea(2, 30);
            replyTextArea.setLineWrap(true);
            replyTextArea.setWrapStyleWord(true);
            replyTextArea.setBorder(BorderFactory.createLoweredBevelBorder());
            replyTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
            
            JButton replyButton = new JButton("Send Reply");
            replyButton.setBackground(new Color(155, 89, 182));
            replyButton.setForeground(Color.WHITE);
            replyButton.setFont(new Font("Arial", Font.BOLD, 11));
            replyButton.setPreferredSize(new Dimension(110, 30));
            replyButton.setOpaque(true);
            replyButton.setBorderPainted(false);
            replyButton.setFocusPainted(false);
            
            replyButton.addActionListener(e -> {
                String replyText = replyTextArea.getText().trim();
                if (replyText.isEmpty()) {
                    JOptionPane.showMessageDialog(TeacherReviewPanel.this,
                        "Please write a reply!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                DataManager.addReply(review.getTimestamp(), replyText);
                
                JOptionPane.showMessageDialog(TeacherReviewPanel.this,
                    "Reply sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                loadReviews(); // Refresh the display
            });
            
            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.setOpaque(false);
            inputPanel.add(replyTextArea, BorderLayout.CENTER);
            inputPanel.add(replyButton, BorderLayout.EAST);
            
            replyFormPanel.add(replyLabel, BorderLayout.NORTH);
            replyFormPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
            replyFormPanel.add(inputPanel, BorderLayout.SOUTH);
            
            panel.add(replyFormPanel, BorderLayout.CENTER);
        }
        
        return panel;
    }
}
