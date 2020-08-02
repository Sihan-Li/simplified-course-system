// username: root  host name: yy   password : 12345678

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class databaseConnect{
    private Connection connection;
    private String URL = "jdbc:mysql://localhost:3306/" +  "regie";
    private String userName = "root";
    private String password = "12345678";

    private static String addStudent = "INSERT INTO Student(id,name,password) VALUES(?,?,?)";

    private static String studentEnrolledCourse = "SELECT * FROM Course " +
                                                    "LEFT JOIN CourseStudent " +
                                                    "on Course.courseID = CourseStudent.CourseID" +
                                                    "WHERE CourseStudent.StudentID = ";

    private static String studentAddCourse = "INSERT INTO CourseStudent(id,CourseID,StudentID) VALUES(?,?,?)";
    private static String studentDropCourse = "DELETE FROM CourseStudent";
    private static String studentSwapCourse = "UPDATE CourseStudent SET";
    private static boolean False;

    private static databaseConnect instance = new databaseConnect();

    //private constructor of singleton
    private databaseConnect(){

    }
    //get singleton instance
    public static databaseConnect getInstance(){
        return instance;
    }

    public void connect(){
        try{
            connection = DriverManager.getConnection(URL + "?user=" + userName + "useSSL=false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

    public boolean login(String userType,int id, String password) {
        int idFromDB = 0;
        String passwordFromDB = null;
        try{
            String login = "SELECT * FROM "+ userType + " WHERE id = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(login);
            stmt.setInt(1, id);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery(login);
            if (res.next()){
                idFromDB = res.getInt("id");
                passwordFromDB = res.getString("password");
            }
            if(idFromDB == id && passwordFromDB.equals(password)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Firstly we add all the students information into the database.
    public void setAddStudent(Student student){
        try{
            PreparedStatement stmt = connection.prepareStatement(addStudent);
            stmt.setInt(1,student.id);
            stmt.setString(2,student.name);
            stmt.setString(3,student.password);
            stmt.executeUpdate();
            System.out.println("Successfully added a student: studentID " + student.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(int studentID){
        String getStudentSQL = "SELECT * FROM Student WHERE id = " + studentID;
        ResultSet res = null;
        int id = 0;
        String name = null;
        String password = null;
        try{
            PreparedStatement stmt = connection.prepareStatement(getStudentSQL);
            res = stmt.executeQuery(getStudentSQL);
            while (res.next()){
                id = res.getInt("id");
                name = res.getString("name");
                password = res.getString("password");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Student s = new Student(id,name,password);
        return s;
    }

    public Course getCourse(int courseID){
        String getCourseSQL = "SELECT * FROM Course WHERE CourseID = " + courseID;
        ResultSet res = null;
        Course course = null;
        try{
            PreparedStatement stmt = connection.prepareStatement(getCourseSQL);
            res = stmt.executeQuery(getCourseSQL);
            while (res.next()){
                int courseid = res.getInt("courseID");
                String Name = res.getString("Name");
                String time = res.getString("time");
                int facultyID = res.getInt("facultyID");
                int ClassroomID = res.getInt("ClassroomID");
                int prerequisite = res.getInt("prerequisite");
                course = new Course(courseid,Name,time,facultyID,ClassroomID,prerequisite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public ArrayList<Course> getStudentEnrolledCourse(Student student){
        String findStudentEnrolledCourseSQL = studentEnrolledCourse + student.id;
        ResultSet res = null;
        ArrayList<Course> courseList = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(findStudentEnrolledCourseSQL);
            res = stmt.executeQuery(findStudentEnrolledCourseSQL);
            while (res.next()){
                int courseID = res.getInt("courseID");
                String Name = res.getString("Name");
                String time = res.getString("time");
                int facultyID = res.getInt("facultyID");
                int ClassroomID = res.getInt("ClassroomID");
                int prerequisite = res.getInt("prerequisite");
                Course course = new Course(courseID,Name,time,facultyID,ClassroomID,prerequisite);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }


    public void setStudentAddCourse(Course course, Student student){
        int id = new Random().nextInt(100000000);
        try{
            PreparedStatement stmt = connection.prepareStatement(studentAddCourse);
            stmt.setInt(1,id);
            stmt.setInt(2,course.courseID);
            stmt.setInt(3,student.id);
            stmt.executeUpdate();
            System.out.println("Student with studentID: " + student.id + " has been added to this course " + course.courseID + " enrollment list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStudentDropCourse(Student stu, Course course){
        String dropCourseSQL = studentDropCourse + " where CourseID = " + course.courseID + " and StudentID = " + stu.id;
        try{
            PreparedStatement stmt = connection.prepareStatement(dropCourseSQL);
            stmt.executeUpdate();
            System.out.println("successfully drop a course: id" + course.courseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStudentSwapCourse(Student stu, Course course1, Course course2){
        String swapCourseSQL = studentSwapCourse + " CourseID = " + course2.courseID + " where CourseID = " + course1.courseID;
        try{
            PreparedStatement stmt = connection.prepareStatement(swapCourseSQL);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAddFaculty(Faculty faculty){
        String addFaculty = "INSERT INTO Faculty(id,name,password) VALUES(?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(addFaculty);
            stmt.setInt(1,faculty.id);
            stmt.setString(2,faculty.name);
            stmt.setString(3,faculty.password);
            stmt.executeUpdate();
            System.out.println("Successfully added a student: studentID " + faculty.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Faculty getFaculty(int facultyID){
        String getFacultySQL = "SELECT * FROM faculty WHERE id = " + facultyID;
        ResultSet res = null;
        int id = 0;
        String name = null;
        String password = null;
        try{
            PreparedStatement stmt = connection.prepareStatement(getFacultySQL);
            res = stmt.executeQuery(getFacultySQL);
            while (res.next()){
                id = res.getInt("id");
                name = res.getString("name");
                password = res.getString("password");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Faculty f = new Faculty(id,name,password);
        return f;
    }

    public ArrayList<Course> getFacultyRelatedCourse(Faculty faculty){
        String findFacultyRelatedCourseSQL = "SELECT * FROM Course " +
                "LEFT JOIN CourseFaculty " +
                "on Course.courseID = CourseFaculty.CourseID" +
                "WHERE CourseStudent.FacultyID = " + faculty.id;
        ResultSet res = null;
        ArrayList<Course> courseList = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(findFacultyRelatedCourseSQL);
            res = stmt.executeQuery(findFacultyRelatedCourseSQL);
            while (res.next()){
                int courseID = res.getInt("courseID");
                String Name = res.getString("Name");
                String time = res.getString("time");
                int facultyID = res.getInt("facultyID");
                int ClassroomID = res.getInt("ClassroomID");
                int prerequisite = res.getInt("prerequisite");
                Course course = new Course(courseID,Name,time,facultyID,ClassroomID,prerequisite);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public void setFacultyCreateCourse(Course course){
        String facultyAddCourseSQL = "INSERT INTO Course(courseID,Name,time,facultyID,ClassroomID,prerequisite) VALUES(?,?,?,?,?,?)";
        int courseID = course.courseID ;
        String Name = course.Name;
        String time = course.time;
        int facultyID = course.courseID;
        int ClassroomID = course.classroomID ;
        int prerequisite = course.prerequisite;
        try{
            PreparedStatement stmt = connection.prepareStatement(facultyAddCourseSQL);
            stmt.setInt(1,courseID);
            stmt.setString(2,Name);
            stmt.setString(3,time);
            stmt.setInt(4,facultyID);
            stmt.setInt(5,ClassroomID);
            stmt.setInt(6,prerequisite);
            stmt.executeUpdate();
            System.out.println("Successfully added a new course : "+ courseID + " "+ Name + " into the course list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFacultyDropCourse(Faculty faculty, Course course){
        String facultyDropCourseSQL = "DELETE FROM CourseFaculty WHERE courseID = "+ course.courseID + " and facultyID = " + faculty.id;
        try{
            PreparedStatement stmt = connection.prepareStatement(facultyDropCourseSQL);
            stmt.executeUpdate();
            System.out.println("successfully drop a course: id" + course.courseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFacultyModifyCourse(int OldID, int newCourseID, String newName, String newTime, int newFacultyID, int newClassroomID, int newPrerequisite){
        String chooseCourse = "UPDATE Course SET courseID=" + newCourseID +", Name = " + newName + ",time=" + newTime + ", facultyID=" + newFacultyID + ", ClassroomID = " + newClassroomID + ",prerequisite=" + newPrerequisite +
                "WHERE courseID = OldID";
        ResultSet res = null;
        try{
            PreparedStatement stmt = connection.prepareStatement(chooseCourse);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
