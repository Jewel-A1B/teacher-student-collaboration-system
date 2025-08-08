import java.io.*;
import java.util.*;

public class DataManager {
    private static final String DATA_DIR = "./data/";
    private static final String STUDENTS_FILE = DATA_DIR + "students.txt";
    private static final String TEACHERS_FILE = DATA_DIR + "teachers.txt";
    private static final String COURSES_FILE = DATA_DIR + "courses.txt";
    private static final String STUDENT_COURSES_FILE = DATA_DIR + "student_courses.txt";
    private static final String REVIEWS_FILE = DATA_DIR + "reviews.txt";
    
    static {
        initializeDataFiles();
    }
    
    private static void initializeDataFiles() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Create sample data if files don't exist
        if (!new File(STUDENTS_FILE).exists()) {
            createSampleStudents();
        }
        if (!new File(TEACHERS_FILE).exists()) {
            createSampleTeachers();
        }
        if (!new File(COURSES_FILE).exists()) {
            createSampleCourses();
        }
        if (!new File(STUDENT_COURSES_FILE).exists()) {
            createSampleStudentCourses();
        }
        if (!new File(REVIEWS_FILE).exists()) {
            createEmptyReviewsFile();
        }
    }
    
    private static void createSampleStudents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            writer.println("student1,password1,Rahul Islam,CSE 7th Semester");
            writer.println("student2,password2,Fatima Khan,CSE 7th Semester");
            writer.println("student3,password3,Shakib Ahmed,CSE 7th Semester");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createSampleTeachers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEACHERS_FILE))) {
            writer.println("teacher1,password1,Dr. Mohammad Ali,Associate Professor");
            writer.println("teacher2,password2,Prof. Rashida Begum,Professor");
            writer.println("teacher3,password3,Dr. Karim Ahmed,Assistant Professor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createSampleCourses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            writer.println("CSE401,Database Management Systems,teacher1");
            writer.println("CSE402,Software Engineering,teacher2");
            writer.println("CSE403,Computer Networks,teacher3");
            writer.println("CSE404,Web Programming,teacher1");
            writer.println("CSE405,Machine Learning,teacher2");
            writer.println("CSE406,Mobile App Development,teacher3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createSampleStudentCourses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_COURSES_FILE))) {
            // All students enrolled in all courses for simplicity
            String[] students = {"student1", "student2", "student3"};
            String[] courses = {"CSE401", "CSE402", "CSE403", "CSE404", "CSE405", "CSE406"};
            
            for (String student : students) {
                for (String course : courses) {
                    writer.println(student + "," + course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createEmptyReviewsFile() {
        try {
            new File(REVIEWS_FILE).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean validateLogin(String username, String password, boolean isStudent) {
        String filename = isStudent ? STUDENTS_FILE : TEACHERS_FILE;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getUserFullName(String username, boolean isStudent) {
        String filename = isStudent ? STUDENTS_FILE : TEACHERS_FILE;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }
    
    public static String getUserSemester(String studentUsername) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(studentUsername)) {
                    return parts[3]; // semester is 4th column
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1st Semester"; // default
    }
    
    public static List<Course> getStudentCourses(String studentUsername) {
        List<Course> courses = new ArrayList<>();
        Map<String, Course> allCourses = getAllCourses();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(studentUsername)) {
                    Course course = allCourses.get(parts[1]);
                    if (course != null) {
                        courses.add(course);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    public static List<Course> getTeacherCourses(String teacherUsername) {
        List<Course> courses = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(teacherUsername)) {
                    Course course = new Course(parts[0], parts[1], parts[2]);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    private static Map<String, Course> getAllCourses() {
        Map<String, Course> courses = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Course course = new Course(parts[0], parts[1], parts[2]);
                    courses.put(parts[0], course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    private static Map<String, Course> getAllCoursesWithDetails() {
        Map<String, Course> courses = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Course course = new Course(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    courses.put(parts[0], course);
                } else if (parts.length >= 3) {
                    Course course = new Course(parts[0], parts[1], parts[2]);
                    courses.put(parts[0], course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    public static String getTeacherNameByUsername(String teacherUsername) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(teacherUsername)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacherUsername;
    }
    
    public static void addReview(String studentUsername, String courseCode, String teacherUsername, 
                                String comment, int rating) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REVIEWS_FILE, true))) {
            long timestamp = System.currentTimeMillis();
            writer.println(timestamp + "," + studentUsername + "," + courseCode + "," + 
                          teacherUsername + "," + rating + "," + comment + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Review> getCourseReviews(String courseCode, String teacherUsername) {
        List<Review> reviews = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(REVIEWS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length >= 6 && parts[2].equals(courseCode) && parts[3].equals(teacherUsername)) {
                    Review review = new Review(
                        Long.parseLong(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        parts.length > 6 ? parts[6] : ""
                    );
                    reviews.add(review);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        reviews.sort((r1, r2) -> Long.compare(r2.getTimestamp(), r1.getTimestamp()));
        return reviews;
    }
    
    public static void addReply(long reviewTimestamp, String reply) {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(REVIEWS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length >= 6 && Long.parseLong(parts[0]) == reviewTimestamp) {
                    // Add reply to this review
                    line = parts[0] + "," + parts[1] + "," + parts[2] + "," + 
                           parts[3] + "," + parts[4] + "," + parts[5] + "," + reply;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Write back all lines
        try (PrintWriter writer = new PrintWriter(new FileWriter(REVIEWS_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean usernameExists(String username, boolean isStudent) {
        String filename = isStudent ? STUDENTS_FILE : TEACHERS_FILE;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean registerUser(String username, String password, String fullName, String extraInfo, boolean isStudent) {
        // Check if username already exists
        if (usernameExists(username, isStudent)) {
            return false;
        }
        
        String filename = isStudent ? STUDENTS_FILE : TEACHERS_FILE;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(username + "," + password + "," + fullName + "," + extraInfo);
            
            // If it's a student, also add course enrollments based on semester
            if (isStudent) {
                addSemesterBasedCourseEnrollments(username, extraInfo);
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void addSemesterBasedCourseEnrollments(String studentUsername, String semester) {
        Map<String, Course> allCourses = getAllCoursesWithDetails();
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_COURSES_FILE, true))) {
            for (Course course : allCourses.values()) {
                // Enroll student in courses from same semester
                if (course.getSemester().equals(semester)) {
                    writer.println(studentUsername + "," + course.getCourseCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean createCourse(String courseCode, String courseName, String teacherUsername, 
                                     String semester, String department) {
        // Check if course code already exists
        if (courseExists(courseCode)) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE, true))) {
            writer.println(courseCode + "," + courseName + "," + teacherUsername + "," + semester + "," + department);
            
            // Auto-assign students from the same semester to this course
            autoAssignStudentsToCourse(courseCode, semester);
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean courseExists(String courseCode) {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(courseCode)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static void autoAssignStudentsToCourse(String courseCode, String semester) {
        List<String> studentsToAssign = new ArrayList<>();
        
        // Find all students from the same semester
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[3].equals(semester)) {
                    studentsToAssign.add(parts[0]); // Add student username
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        // Assign these students to the course
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_COURSES_FILE, true))) {
            for (String studentUsername : studentsToAssign) {
                writer.println(studentUsername + "," + courseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Course> getStudentCoursesBySemester(String studentUsername, String studentSemester) {
        List<Course> courses = new ArrayList<>();
        Map<String, Course> allCourses = getAllCoursesWithDetails();
        
        // Get courses both from assignments AND directly by semester match
        Set<String> courseIds = new HashSet<>();
        
        // First, get assigned courses
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(studentUsername)) {
                    courseIds.add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Then, get all courses that match student's semester (newly created courses)
        for (Course course : allCourses.values()) {
            if (course.getSemester().equals(studentSemester)) {
                courseIds.add(course.getCourseCode());
            }
        }
        
        // Convert course IDs to Course objects
        for (String courseId : courseIds) {
            Course course = allCourses.get(courseId);
            if (course != null && course.getSemester().equals(studentSemester)) {
                courses.add(course);
            }
        }
        
        return courses;
    }
}
