import java.util.List;

public class Course {
    int courseID;
    String Name;
    String time;
    int facultyID;
    int classroomID;
    int prerequisite;
    List<Student> registeredStudentList;

    public Course() {
    }

    public Course(int courseID, String name, String time, int facultyID, int classroomID, int prerequisite) {
        this.courseID = courseID;
        Name = name;
        this.time = time;
        this.facultyID = facultyID;
        this.classroomID = classroomID;
        this.prerequisite = prerequisite;
    }

    public static String showInformation(int courseID, String Name, Faculty faculty, String time, Classroom classroom){
        String info = "courseID = " + courseID + "Name = " + Name + "Faculty =" + faculty + "time = " + time + "classroom = " + classroom;
        return info;
    }
}
