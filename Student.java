public class Student {
    int id;
    String name;
    String password;

    private Student instance = new Student(this.id,this.name,this.password);


    public Student(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    public void login(){
        loginCommand loginPage = new studentCoursePage(instance);
        String result = loginPage.loginExecute(instance.id,instance.password);
        System.out.println(result);
    }
}

/*
the logic is that we first manually insert students' instances for all the student into the database,
and then do the add/drop courses functions for a student.
 */