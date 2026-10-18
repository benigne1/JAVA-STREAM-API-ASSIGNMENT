import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static List<Student> students = new ArrayList<>();
    static Map<Integer, Double> originalAverages = new HashMap<>(); 
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            showMenu();
            choice = readNumber("Enter your choice: ");

            switch (choice) {
                case 1: registerStudent(); break;
                case 2: displayAll(); break;
                case 3: searchById(); break;
                case 4: showPassing(); break;
                case 5: showFailing(); break;
                case 6: searchByName(); break;
                case 7: sortByName(); break;
                case 8: sortByAverage(); break;
                case 9: showAllCourses(); break;
                case 10: showUniqueCourses(); break;
                case 11: searchByCourse(); break;
                case 12: showUniqueSkills(); break;
                case 13: groupByCourse(); break;
                case 14: showStatistics(); break;
                case 15: showBestStudent(); break;
                case 16: showTopThree(); break;
                case 17: applyBonus(); break;
                case 18: showFinalRanking(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option!");
            }
        }
    }

    static void showMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Register New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Filter Passing Students");
        System.out.println("5. Filter Failing Students");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Sort Students by Name");
        System.out.println("8. Sort Students by Average Marks");
        System.out.println("9. Display All Courses");
        System.out.println("10. Display Unique Courses");
        System.out.println("11. Search Students by Course");
        System.out.println("12. Display Unique Skills");
        System.out.println("13. Group Students by Course");
        System.out.println("14. View Student Performance Statistics");
        System.out.println("15. View Best Performing Student");
        System.out.println("16. View Top 3 Students");
        System.out.println("17. Apply Bonus Marks");
        System.out.println("18. View Final Student Ranking");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println();
        System.out.println("==========================================");
    }

    // Reads a number. If the user types letters, it asks again.
    static int readNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please type a number.");
            }
        }
    }
    static boolean isEmpty() {
        if (students.isEmpty()) {
            System.out.println("No students yet. Register a student first (option 1).");
            return true;
        }
        return false;
    }


    static void registerStudent() {
        int id = readNumber("Enter student ID: ");
        if (id <= 0) {
            System.out.println("ID must be more than 0.");
            return;
        }

        boolean exists = students.stream().anyMatch(s -> s.getId() == id);
        if (exists) {
            System.out.println("This ID already exists.");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        int age = readNumber("Enter age: ");
        if (age <= 0) {
            System.out.println("Age must be more than 0.");
            return;
        }

        int count = readNumber("Enter number of courses: ");
        if (count <= 0) {
            System.out.println("Number of courses must be more than 0.");
            return;
        }

        Map<String, Double> courses = new HashMap<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Enter course " + i + " name: ");
            String course = scanner.nextLine().trim();

            int mark = readNumber("Enter marks for " + course + " (0-100): ");
            while (mark < 0 || mark > 100) {
                mark = readNumber("Marks must be 0-100. Try again: ");
            }
            courses.put(course, (double) mark);
        }

        System.out.print("Enter skills separated by commas (example: Java,SQL): ");
        String line = scanner.nextLine();
        List<String> skills = new ArrayList<>();
        for (String skill : line.split(",")) {
            if (!skill.trim().isEmpty()) {
                skills.add(skill.trim());
            }
        }

        students.add(new Student(id, name, age, courses, skills));
        System.out.println("Student registered!");
    }

    static void displayAll() {
        if (isEmpty()) return;
        students.stream().forEach(s -> System.out.println(s));
    }

    static void searchById() {
        if (isEmpty()) return;
        int id = readNumber("Enter ID to search: ");

        // filter keeps matching students, findFirst takes the first one
        Student found = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(found);
        }
    }

    // ---------- Task 2 ----------

    static void showPassing() {
        if (isEmpty()) return;
        List<Student> list = students.stream()
                .filter(s -> s.getAverage() >= 65)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            System.out.println("No passing students.");
        }
        list.forEach(s -> System.out.println(s));
    }

    static void showFailing() {
        if (isEmpty()) return;
        List<Student> list = students.stream()
                .filter(s -> s.getAverage() < 65)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            System.out.println("No failing students.");
        }
        list.forEach(s -> System.out.println(s));
    }

    static void searchByName() {
        if (isEmpty()) return;
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim().toLowerCase();

        List<Student> list = students.stream()
                .filter(s -> s.getStudentName().toLowerCase().contains(name))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            System.out.println("No student found.");
        }
        list.forEach(s -> System.out.println(s));
    }

    static void sortByName() {
        if (isEmpty()) return;
        students.stream()
                .sorted(Comparator.comparing(Student::getStudentName))
                .forEach(s -> System.out.println(s));
    }

    static void sortByAverage() {
        if (isEmpty()) return;
    
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .forEach(s -> System.out.println(s));
    }



    static void showAllCourses() {
        if (isEmpty()) return;
        students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .sorted()
                .forEach(c -> System.out.println(c));
    }

    static void showUniqueCourses() {
        if (isEmpty()) return;
        students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .distinct()
                .sorted()
                .forEach(c -> System.out.println(c));
    }

    static void searchByCourse() {
        if (isEmpty()) return;
        System.out.print("Enter course name: ");
        String course = scanner.nextLine().trim();

        List<Student> list = students.stream()
                .filter(s -> s.getCourses().containsKey(course))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            System.out.println("No student takes this course.");
        }
        list.forEach(s -> System.out.println(s));
    }

    static void showUniqueSkills() {
        if (isEmpty()) return;
        students.stream()
                .flatMap(s -> s.getSkills().stream())
                .distinct()
                .sorted()
                .forEach(skill -> System.out.println(skill));
    }

    static void groupByCourse() {
        if (isEmpty()) return;

        List<String> courseNames = students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (String course : courseNames) {
            List<String> names = students.stream()
                    .filter(s -> s.getCourses().containsKey(course))
                    .map(s -> s.getStudentName())
                    .collect(Collectors.toList());
            System.out.println(course + ": " + names);
        }
    }

    static void showStatistics() {
        if (isEmpty()) return;

        for (Student s : students) {
        
            DoubleSummaryStatistics stats = s.getCourses().values().stream()
                    .mapToDouble(mark -> mark)
                    .summaryStatistics();

            System.out.println(s.getStudentName());
            System.out.println("  Average: " + stats.getAverage());
            System.out.println("  Highest: " + stats.getMax());
            System.out.println("  Lowest: " + stats.getMin());
            System.out.println("  Sum: " + stats.getSum());
            System.out.println("  Courses: " + stats.getCount());
        }

        long passed = students.stream().filter(s -> s.getAverage() >= 65).count();
        System.out.println("Students who passed: " + passed);
    }

    static void showBestStudent() {
        if (isEmpty()) return;
        Student best = students.stream()
                .max(Comparator.comparingDouble(Student::getAverage))
                .get();
        System.out.println("Best student: " + best.getStudentName()
                + " (" + best.getAverage() + ")");
    }

    static void showTopThree() {
        if (isEmpty()) return;
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .limit(3)
                .forEach(s -> System.out.println(s.getStudentName() + " - " + s.getAverage()));
    }


    static void applyBonus() {
        if (isEmpty()) return;

        List<Student> eligible = students.stream()
                .filter(s -> s.getAverage() > 50)
                .filter(s -> !originalAverages.containsKey(s.getId()))
                .collect(Collectors.toList());

        if (eligible.isEmpty()) {
            System.out.println("No student qualifies for the bonus.");
            return;
        }

        for (Student s : eligible) {
            originalAverages.put(s.getId(), s.getAverage());

            // Add 10 to each course, but not more than 100
            for (String course : s.getCourses().keySet()) {
                double newMark = s.getCourses().get(course) + 10;
                if (newMark > 100) {
                    newMark = 100;
                }
                s.getCourses().put(course, newMark);
            }

            System.out.println(s.getStudentName() + ": original "
                    + originalAverages.get(s.getId()) + ", final " + s.getAverage());
        }
    }

    static void showFinalRanking() {
        if (isEmpty()) return;

        List<Student> ranking = students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverage).reversed())
                .collect(Collectors.toList());

        int position = 1;
        for (Student s : ranking) {
            double original = originalAverages.getOrDefault(s.getId(), s.getAverage());
            System.out.println(position + ". " + s.getStudentName()
                    + " | original: " + original + " | final: " + s.getAverage());
            position++;
        }
        double total = ranking.stream()
                .map(s -> s.getAverage())
                .reduce(0.0, (a, b) -> a + b);
        System.out.println("Class average: " + total / ranking.size());
    }
}