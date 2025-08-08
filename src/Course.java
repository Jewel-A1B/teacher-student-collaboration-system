public class Course {
    private String courseCode;
    private String courseName;
    private String teacherUsername;
    private String semester;
    private String department;
    
    public Course(String courseCode, String courseName, String teacherUsername) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherUsername = teacherUsername;
        this.semester = "";
        this.department = "";
    }
    
    public Course(String courseCode, String courseName, String teacherUsername, String semester, String department) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherUsername = teacherUsername;
        this.semester = semester;
        this.department = department;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getTeacherUsername() {
        return teacherUsername;
    }
    
    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return courseCode + " - " + courseName;
    }
}
