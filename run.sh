#!/bin/bash

echo "🎓 Teacher-Student Collaboration System 🎓"
echo "==========================================="
echo ""
echo "📚 Compiling Java files..."

# Compile Java files
javac -d . src/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🚀 Starting application..."
    echo ""
    echo "📋 Sample Login Credentials:"
    echo "   Students: student1/pass123, student2/pass123, student3/pass123, etc."
    echo "   Teachers: teacher1/pass123, teacher2/pass123, teacher3/pass123, etc."
    echo ""
    
    # Run the application
    java TeacherStudentCollaborationApp
else
    echo "❌ Compilation failed!"
    echo "Please check for errors and try again."
fi
