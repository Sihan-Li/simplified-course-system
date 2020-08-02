import java.util.ArrayList;

public class studentCoursePage implements loginCommand{
    Student student;
    ArrayList<Course>courseList;

    public studentCoursePage(Student student) {
        this.student = student;
    }

    databaseConnect dbConn = databaseConnect.getInstance();

    public ArrayList<Course> showEnrolledCourses(){
        courseList = dbConn.getStudentEnrolledCourse(this.student);
        for(Course course : courseList){
            System.out.println("courseInformation: " + course.courseID +" " + course.Name);
        };
        return courseList;
    }


    public void addCourse(Course course,Student student){
        dbConn.setStudentAddCourse(course,this.student);
    }

    public void dropCourse(Student student, Course course){
        dbConn.setStudentDropCourse(this.student,course);
    }

    public void swapCourse(Student student, Course course1, Course course2){
        dbConn.setStudentSwapCourse(this.student,course1, course2);
    }

    public String courseEnrollmentRequest(Student student, Course course){
        Faculty fau = dbConn.getFaculty(course.facultyID);
        FacultyCoursePage fpage = new FacultyCoursePage(fau);
        String decision = fpage.approveClassRegister(this.student,course);
        System.out.println(decision);
        return decision;
    }

    @Override
    public String loginExecute(int id, String password) {
        String userType = "student";
        boolean checkLogin = dbConn.login(userType,id,password);
        if(checkLogin){
            return "You have successfully log in";
        }else {
            return "Wrong information, input again";
        }
    }

}
