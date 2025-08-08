# 🎓 Teacher-Student Collaboration System

A comprehensive Java Swing application that facilitates seamless interaction between teachers and students for course management and review systems.

## ✨ Features

### 🔐 Authentication System

- **Student & Teacher Login**: Secure authentication for both user types
- **Registration System**: New users can register as students or teachers
- **Session Management**: Maintains user sessions throughout the application

### 👨‍🎓 Student Features

- **Course Dashboard**: View enrolled courses with detailed information
- **Course Reviews**: Submit reviews and ratings for courses
- **Review History**: View previously submitted reviews
- **Interactive UI**: Clean and intuitive interface

### 👨‍🏫 Teacher Features

- **Course Management**: Create and manage courses
- **Student Reviews**: View all student reviews for their courses
- **Review Responses**: Reply to student reviews and feedback
- **Course Analytics**: See review statistics and ratings

### 🎨 Single-Window Architecture

- **Unified Interface**: All operations happen in a single main window
- **Panel Switching**: Smooth transitions between different sections
- **Back Navigation**: Easy navigation with back button functionality
- **Consistent Design**: Modern and professional UI throughout

## 🛠️ Technical Details

### Architecture

- **Single JFrame Design**: MainFrame acts as the central container
- **CardLayout**: Efficient panel switching for different views
- **Navigation Stack**: Maintains history for back button functionality
- **MVC Pattern**: Clean separation of data management and UI

### Panel Components

- `LoginPanel`: User authentication interface
- `RegistrationPanel`: New user registration
- `StudentDashboardPanel`: Student main interface
- `TeacherDashboardPanel`: Teacher main interface
- `CourseReviewPanel`: Course review submission and viewing
- `CreateCoursePanel`: Course creation interface
- `TeacherReviewPanel`: Teacher review management

## 🚀 How to Run

### Using the Run Script (Recommended)

```bash
bash run.sh
```

### Manual Compilation and Execution

```bash
# Compile
javac -d . src/*.java

# Run
java TeacherStudentCollaborationApp
```

## 👤 Login Credentials

### Students

- **Username**: student1, **Password**: password1
- **Username**: student2, **Password**: password2
- **Username**: student3, **Password**: password3

### Teachers

- **Username**: teacher1, **Password**: password1
- **Username**: teacher2, **Password**: password2
- **Username**: teacher3, **Password**: password3

## 📁 Project Structure

```
TSC 2/
├── src/                          # Source code directory
│   ├── MainFrame.java           # Central application frame with CardLayout
│   ├── LoginPanel.java          # Login interface
│   ├── RegistrationPanel.java   # Registration interface
│   ├── StudentDashboardPanel.java # Student main interface
│   ├── TeacherDashboardPanel.java # Teacher main interface
│   ├── CourseReviewPanel.java   # Course review interface
│   ├── CreateCoursePanel.java   # Course creation interface
│   ├── TeacherReviewPanel.java  # Teacher review management
│   ├── DataManager.java         # Data management and file operations
│   ├── Course.java              # Course data model
│   ├── Review.java              # Review data model
│   └── TeacherStudentCollaborationApp.java # Main application entry point
├── data/                        # Data storage directory
│   ├── students.txt            # Student account information
│   ├── teachers.txt            # Teacher account information
│   ├── courses.txt             # Course information
│   ├── reviews.txt             # Student reviews
│   └── student_courses.txt     # Student-course enrollment data
├── run.sh                      # Application launcher script
└── README.md                   # Project documentation
```

## 🎯 Key Improvements

### Single-Window Design

- **Eliminated Multiple Windows**: Converted from multi-window to single-window architecture
- **Better User Experience**: No more window management issues
- **Consistent Navigation**: Unified back button and navigation system
- **Memory Efficient**: Reduced resource usage with panel reuse

### Enhanced UI/UX

- **Improved Button Visibility**: Fixed text color issues across all interfaces
- **Professional Styling**: Consistent color scheme and modern design
- **Responsive Layout**: Better component sizing and spacing
- **Visual Feedback**: Clear status messages and confirmations

### Code Architecture

- **Panel-Based Design**: All Frame classes converted to Panel classes
- **Centralized Management**: MainFrame handles all navigation and state
- **Clean Separation**: Better separation of concerns between UI and data
- **Maintainable Code**: Easier to extend and modify functionality

## 🔧 System Requirements

- **Java**: JDK 8 or higher
- **OS**: Windows, macOS, or Linux
- **Memory**: Minimum 256MB RAM
- **Storage**: 50MB free space

## 🎉 Usage Instructions

1. **Start Application**: Run using `bash run.sh` or the manual compilation method
2. **Login**: Use provided credentials or register as a new user
3. **Navigate**: Use the intuitive interface to access different features
4. **Back Navigation**: Click the "← Back" button to return to previous screens
5. **Logout**: Use the logout button to return to the login screen

## 🏆 Features Showcase

### For Students

1. **Login** → View your enrolled courses
2. **Select Course** → Submit reviews and ratings
3. **View History** → Check your previous reviews

### For Teachers

1. **Login** → View your created courses
2. **Create Course** → Add new courses for students
3. **View Reviews** → Read student feedback and respond

---

**Developed with ❤️ using Java Swing - Single-Window Architecture**



Perfect!


👥 Students (17 জন):
student1 to student17
Password: pass123 (সবার জন্য)
বিভিন্ন semester: 1st থেকে 8th পর্যন্ত
👨‍🏫 Teachers (6 জন):
teacher1 to teacher6
Password: pass123 (সবার জন্য)
বিভিন্ন designation: Professor, Associate Professor, Assistant Professor
📚 Courses (23টি কোর্স):
1st Semester: ENG101, MATH101, CSE101, PHY101
2nd Semester: ENG201, MATH201, CSE201, PHY201
3rd Semester: CSE301, CSE302, EEE301, MATH301
4th-8th Semester: Advanced CSE courses
⭐ Reviews (16টি sample reviews):
Realistic comments and ratings (4-5 stars)
Different courses এর জন্য meaningful feedback
🔗 Student-Course Assignments:
Semester wise properly assigned
Each semester এর students তাদের relevant courses পাবে
Test করার জন্য:

Student login: student1/pass123 (1st semester courses দেখবে)
Teacher login: teacher1/pass123 (তার courses এবং reviews দেখবে)
Application ready with realistic dummy data! 🎉