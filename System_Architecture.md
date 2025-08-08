# Teacher-Student Collaboration System - UML Documentation

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                   Teacher-Student Collaboration System          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │   LoginPanel    │    │ RegistrationPanel│    │   MainFrame  │ │
│  │                 │    │                 │    │              │ │
│  │ - username      │    │ - student/teacher│    │ - cardLayout │ │
│  │ - password      │    │ - validation    │    │ - panels     │ │
│  │ - user type     │    │ - form fields   │    │ - navigation │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
│           │                       │                     │       │
│           └───────────────────────┼─────────────────────┘       │
│                                   │                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │Student Dashboard│    │Teacher Dashboard│    │ DataManager  │ │
│  │                 │    │                 │    │              │ │
│  │ - view courses  │    │ - view courses  │    │ - file I/O   │ │
│  │ - submit reviews│    │ - create courses│    │ - validation │ │
│  │ - view reviews  │    │ - view reviews  │    │ - user mgmt  │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
│           │                       │                     │       │
│           └───────────────────────┼─────────────────────┘       │
│                                   │                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │CourseReviewPanel│    │CreateCoursePanel│    │    Course    │ │
│  │                 │    │                 │    │    Review    │ │
│  │ - rating slider │    │ - course details│    │  Data Models │ │
│  │ - comment area  │    │ - semester/dept │    │              │ │
│  │ - review list   │    │ - validation    │    │              │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Core Classes and Relationships

### 1. Data Model Classes
```
Course                           Review
├── courseCode: String          ├── timestamp: long
├── courseName: String          ├── studentUsername: String
├── teacherUsername: String     ├── courseCode: String
├── semester: String            ├── teacherUsername: String
├── department: String          ├── rating: int (1-5)
└── [getters/setters]           └── comment: String
```

### 2. Main Application Framework
```
MainFrame (JFrame)
├── manages all panels using CardLayout
├── handles navigation and user sessions
├── coordinates between different views
└── maintains user state (student/teacher)
```

### 3. User Interface Panels
```
LoginPanel                      RegistrationPanel
├── username/password fields    ├── dynamic form (student/teacher)
├── student/teacher radio       ├── validation and registration
└── authentication             └── account creation

StudentDashboardPanel           TeacherDashboardPanel
├── enrolled courses list       ├── assigned courses list
├── course review access        ├── create course functionality
└── semester-based view         └── view student reviews

CourseReviewPanel              CreateCoursePanel
├── existing reviews display    ├── course information form
├── rating submission           ├── semester/department selection
└── comment submission          └── auto-student enrollment
```

### 4. Data Management Layer
```
DataManager (Static Utility Class)
├── File Operations
│   ├── students.txt (user accounts)
│   ├── teachers.txt (teacher accounts)
│   ├── courses.txt (course catalog)
│   ├── student_courses.txt (enrollments)
│   └── reviews.txt (course reviews)
├── Authentication Methods
│   ├── validateLogin()
│   ├── registerUser()
│   └── usernameExists()
├── Course Management
│   ├── createCourse()
│   ├── getTeacherCourses()
│   └── getStudentCoursesBySemester()
└── Review System
    ├── addReview()
    └── getReviewsForCourse()
```

## Use Cases by Actor

### Student Use Cases:
1. **Register Account** - Create new student account
2. **Login to System** - Authenticate as student
3. **View Enrolled Courses** - See semester-wise courses
4. **Submit Course Review** - Rate and comment on courses
5. **View Course Reviews** - Read other students' reviews
6. **Logout** - End session

### Teacher Use Cases:
1. **Register Account** - Create new teacher account
2. **Login to System** - Authenticate as teacher
3. **View Assigned Courses** - See courses they teach
4. **Create New Course** - Add course to system
5. **View Student Reviews** - Read feedback from students
6. **Logout** - End session

## Data Flow Architecture

```
User Interface Layer
        ↓
   MainFrame (Controller)
        ↓
   Panel Components
        ↓
   DataManager (Service)
        ↓
   File System (Data)
```

## Key Features:

✅ **Single Window Architecture** - All functionality in one frame
✅ **Role-Based Access** - Different interfaces for students/teachers
✅ **Dynamic Registration** - Toggle between student/teacher signup
✅ **Auto-Enrollment** - Students automatically get semester courses
✅ **Real-time Updates** - Dashboard refreshes after course creation
✅ **Review System** - Rating and commenting on courses
✅ **Data Persistence** - File-based storage system
✅ **Input Validation** - Form validation and duplicate prevention

## System Requirements:
- Java 8+ with Swing GUI framework
- File system access for data storage
- No external database required
- Cross-platform compatible
