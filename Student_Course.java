public class Student_Course extends Student {
    private String enrolmentType = "C";
    private Unit_Course unit;

    public Student_Course(String firstName, String lastName, long studentNumber, Unit_Course unit) {
        super(firstName, lastName, studentNumber);
        this.unit = unit;
    }

    public void reportGrade() {
        System.out.println("C, " + getFirstName() + " " + getLastName() + ", " + getStudentNumber() + ", " +
                unit.getUnitID() + ", " + unit.getOverallMark() + ", " + unit.getFinalGrade());
    }

    public Unit_Course getUnit() {
        return unit;
    }
}