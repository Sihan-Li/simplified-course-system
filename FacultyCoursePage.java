import java.util.ArrayList;

public class FacultyCoursePage implements loginCommand{
    Faculty faculty;
    ArrayList<Course> courseList;

    public FacultyCoursePage(Faculty faculty) {
        this.faculty = faculty;
    }

    databaseConnect dbConn = databaseConnect.getInstance();

    public ArrayList<Course> showRelatedCourses(){
        //search the CourseFaculty table and find courseIDs
        courseList = dbConn.getFacultyRelatedCourse(this.faculty);
        for(Course course : courseList){
            System.out.println("courseInformation: " + course.courseID +" " + course.Name);
        }
        return courseList;
    }


    public void addCourse(Course course){
        dbConn.setFacultyCreateCourse(course);
    }
    public void deleteCourse(Course course){
        dbConn.setFacultyDropCourse(this.faculty,course);
    }
    public void modifyCourse(Course oldCourse,Course newCourse){
        dbConn.setFacultyModifyCourse(oldCourse.courseID,newCourse.courseID,newCourse.Name, newCourse.time,newCourse.facultyID,newCourse.classroomID,newCourse.prerequisite);
    }
    public String approveClassRegister(Student s, Course course){
        ArrayList<Course> studentCourseList = dbConn.getStudentEnrolledCourse(s);
        Course prerequisite = dbConn.getCourse(course.courseID);
        if(studentCourseList.contains(prerequisite)){
            course.registeredStudentList.add(s);
            return "You are approved";
        }
        return "You are not approved";
    }


    @Override
    public String loginExecute(int id, String password) {
        String userType = "Faculty";
        boolean checkLogin = dbConn.login(userType,id,password);
        if(checkLogin){
            return "You have successfully log in";
        }else {
            return "Wrong information, input again";
        }
    }
}
