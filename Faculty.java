public class Faculty {
    int id;
    String name;
    String password;

    private Faculty instance = new Faculty(this.id,this.name,this.password);

    public Faculty(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    public void login(){
        loginCommand loginPage = new FacultyCoursePage(instance);
        String result = loginPage.loginExecute(instance.id,instance.password);
        System.out.println(result);
    }
}
