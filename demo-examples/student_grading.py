# Function to display the grading system menu
def display_menu():
    print "Student Grading System"
    print "1: Add Student Score"
    print "2: View All Students"
    print "3: Calculate Average Score"
    print "4: Assign Letter Grades"
    print "5: Exit"

# Function to add a student's score
def add_student(students):
    print "Enter student's name:"
    name = raw_input()
    print "Enter student's score (0-100):"
    score_input = raw_input()
    score = float(score_input)
    if score != None:
        print "Invalid score input"
        return students
    if score < 0 or score > 100:
        print "Score must be between 0 and 100"
        return students
    # Add student using list concatenation
    students = students + [[name, score, ""]]
    print "Student added successfully"
    return students

# Function to view all students
def view_students(students):
    if len(students) == 0:
        print "No student records available"
        return None
    print "Student Records:"
    for i in range(0, len(students)):
        print i + 1 + "." + students[i][0] + "- Score:" + students[i][1] + "- Grade:" + students[i][2]

# Function to calculate the average score
def calculate_average(students):
    if len(students) == 0:
        print "No student records to calculate average"
        return None
    total = 0
    for i in range(0, len(students)):
        total = total + students[i][1]
    average = total // len(students)
    print "Average Score:" + average

# Function to assign letter grades based on scores without using elif
def assign_grades(students):
    if len(students) == 0:
        print "No student records to assign grades"
        return students
    new_students = []
    for i in range(0, len(students)):
        score = students[i][1]
        grade = ""
        if score >= 90:
            grade = "A"
        else:
            if score >= 80:
                grade = "B"
            else:
                if score >= 70:
                    grade = "C"
                else:
                    if score >= 60:
                        grade = "D"
                    else:
                        grade = "F"
        # Update student with grade
        new_students = new_students + [[students[i][0], students[i][1], grade]]
    print "Letter grades assigned based on scores"
    return new_students

# Function to handle user choices
def handle_choice(choice, students):
    if choice == "1":
        students = add_student(students)
    else:
        if choice == "2":
            view_students(students)
        else:
            if choice == "3":
                calculate_average(students)
            else:
                if choice == "4":
                    students = assign_grades(students)
                else:
                    if choice == "5":
                        print "Exiting Student Grading System. Goodbye!"
                        return [False, students]
                    else:
                        print "Invalid choice, please try again"
    return [True, students]

# Main function to run the grading system without while loop
def main():
    students = []
    continue_program = True
    for _ in range(1000):  # Arbitrary large number to simulate loop
        if not continue_program:
            break
        display_menu()
        print "Choose an option (1-5):"
        choice = raw_input()
        user_choice = handle_choice(choice, students)
        continue_program = user_choice[0]
        students = user_choice[1]
        print ""  # Blank line for readability

# Start the program
main()
