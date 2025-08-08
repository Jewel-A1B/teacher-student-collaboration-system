import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    private long timestamp;
    private String studentUsername;
    private String courseCode;
    private String teacherUsername;
    private int rating;
    private String comment;
    private String reply;
    
    public Review(long timestamp, String studentUsername, String courseCode, 
                  String teacherUsername, int rating, String comment, String reply) {
        this.timestamp = timestamp;
        this.studentUsername = studentUsername;
        this.courseCode = courseCode;
        this.teacherUsername = teacherUsername;
        this.rating = rating;
        this.comment = comment;
        this.reply = reply;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStudentUsername() {
        return studentUsername;
    }
    
    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getTeacherUsername() {
        return teacherUsername;
    }
    
    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getReply() {
        return reply;
    }
    
    public void setReply(String reply) {
        this.reply = reply;
    }
    
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(timestamp));
    }
    
    public boolean hasReply() {
        return reply != null && !reply.trim().isEmpty();
    }
}
