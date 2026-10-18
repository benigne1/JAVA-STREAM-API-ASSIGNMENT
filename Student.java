import java.util.List;
import java.util.Map;

public class Student {

    private int id;
    private String studentName;
    private int age;
    private Map<String, Double> courses;
    private List<String> skills;

    public Student(int id, String studentName, int age,
                   Map<String, Double> courses, List<String> skills) {
        this.id = id;
        this.studentName = studentName;
        this.age = age;
        this.courses = courses;
        this.skills = skills;
    }

    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public int getAge() { return age; }
    public Map<String, Double> getCourses() { return courses; }
    public List<String> getSkills() { return skills; }

    public void setId(int id) { this.id = id; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setAge(int age) { this.age = age; }
    public void setCourses(Map<String, Double> courses) { this.courses = courses; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public double getAverage() {
        double total =  0;
        for (double mark : courses.values()) {
            total = total + mark;
        }
        return total / courses.size();
    }

    public String toString() {
        return "ID: " + id + ", Name: " + studentName + ", Age: " + age
                + ", Courses: " + courses + ", Skills: " + skills
                + ", Average: " + getAverage();
    }
}