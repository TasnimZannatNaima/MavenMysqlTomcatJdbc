public class Student {
    private int id;
    private String name;
    private String email;
    private String course;
    private String semester;

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getCourse() {
        return course;
    }
    public String getSemester() {
        return semester;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Student{id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}
