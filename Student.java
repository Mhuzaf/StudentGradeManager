public class Student {
    private String firstName;
    private String lastName;
    private long studentNumber;

    // Default constructor
    public Student() {
        this.firstName = "";
        this.lastName = "";
        this.studentNumber = 0;
    }

    // Parameterized constructor
    public Student(String firstName, String lastName, long studentNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    // Report grade method (to be overridden)
    public void reportGrade() {
        System.out.println("There is no grade here.");
    }

    // Equals method to compare student numbers
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
			return true;
		}	
        if (!(obj instanceof Student)) {
			return false;
		}	
        Student other = (Student) obj;
        return this.studentNumber == other.studentNumber;
    }
}