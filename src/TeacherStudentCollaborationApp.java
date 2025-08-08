/**
 * Teacher-Student Collaboration System
 * Single-Window Architecture Application
 * 
 * Entry point for the application that provides a unified interface
 * for teachers and students to collaborate on course management and reviews.
 * 
 * @author Developed with single-window architecture
 * @version 2.0 - Single Window Edition
 */
import javax.swing.*;

public class TeacherStudentCollaborationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
