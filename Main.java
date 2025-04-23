import java.io.*;
import java.util.*;

public class Main {
    private static ArrayList<Student> students = new ArrayList<>();
    private static boolean isSorted = false;

    public static void main(String[] args) {
        loadStudents("student.csv");
        if (students.isEmpty()) {
            System.out.println("No students loaded.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("""
                    
                    Menu:
                    1. Quit
                    2. Remove student by ID
                    3. Display all students
                    4. Analyze course students
                    5. Report grade by student ID
                    6. Sort students by ID
                    7. Save sorted list to CSV
                    Enter choice: """);

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                case 2 -> removeStudent(scanner);
                case 3 -> displayAllStudents();
                case 4 -> analyzeCourseStudents();
                case 5 -> reportGradeByID(scanner);
                case 6 -> sortStudents();
                case 7 -> saveToCSV();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void loadStudents(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("^\"|\"$", "");
                String[] d = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                if (d.length == 0 || d[0].isEmpty()) continue;

                if (d[0].equals("C") && d.length == 11) {
                    Unit_Course unit = new Unit_Course(d[3], Integer.parseInt(d[4]),
                            new double[]{Double.parseDouble(d[5]), Double.parseDouble(d[6]), Double.parseDouble(d[7]), Double.parseDouble(d[8])},
                            Double.parseDouble(d[9]));
                    students.add(new Student_Course(d[1], d[2], Long.parseLong(d[10]), unit));
                } else if (d[0].equals("R") && d.length == 6) {
                    Research research = new Research(Double.parseDouble(d[3]), Double.parseDouble(d[4]));
                    students.add(new Research_Student(d[1], d[2], Long.parseLong(d[5]), research));
                }
            }
            System.out.println("Loaded " + students.size() + " students.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter student ID to remove: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume leftover newline

        Student toRemove = null;
        for (Student s : students) {
            if (s.getStudentNumber() == id) {
                System.out.print("Confirm removal of " + s.getFirstName() + " " + s.getLastName() + " (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    toRemove = s;
                }
                break;
            }
        }
        if (toRemove != null) {
            students.remove(toRemove);
            System.out.println("Student removed.");
        } else {
            System.out.println("Student not removed or not found.");
        }
    }

    private static void displayAllStudents() {
        if (students.isEmpty()) System.out.println("No students to display.");
        else students.forEach(Student::reportGrade);
    }

    private static void analyzeCourseStudents() {
        List<Student_Course> courseList = students.stream()
                .filter(s -> s instanceof Student_Course)
                .map(s -> (Student_Course) s)
                .toList();

        if (courseList.isEmpty()) {
            System.out.println("No course students.");
            return;
        }

        double avg = courseList.stream().mapToDouble(sc -> sc.getUnit().getOverallMark()).average().orElse(0);
        long above = courseList.stream().filter(sc -> sc.getUnit().getOverallMark() >= avg).count();
        System.out.println("Average: " + avg);
        System.out.println("Above average: " + above);
        System.out.println("Below average: " + (courseList.size() - above));
    }

    private static void reportGradeByID(Scanner scanner) {
        System.out.print("Enter student ID: ");
        long id = scanner.nextLong();
        students.stream()
                .filter(s -> s.getStudentNumber() == id)
                .findFirst()
                .ifPresentOrElse(Student::reportGrade, () -> System.out.println("Student not found."));
    }

    private static void sortStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to sort.");
            return;
        }

        students.sort(Comparator.comparingLong(Student::getStudentNumber));
        isSorted = true;
        System.out.println("Students sorted.");
        displayAllStudents();
    }

    private static void saveToCSV() {
        if (!isSorted) {
            System.out.println("Please sort before saving.");
            return;
        }

        try (PrintWriter pw = new PrintWriter("sorted_students.csv")) {
            for (Student s : students) {
                if (s instanceof Student_Course sc) {
                    pw.println("C," + sc.getFirstName() + "," + sc.getLastName() + "," +
                            sc.getUnit().getUnitID() + "," + sc.getStudentNumber());
                } else if (s instanceof Research_Student rs) {
                    pw.println("R," + rs.getFirstName() + "," + rs.getLastName() + "," + rs.getStudentNumber());
                }
            }
            System.out.println("Saved to sorted_students.csv");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
}
