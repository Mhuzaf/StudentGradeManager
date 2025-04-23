public class Research_Student extends Student {
    private String enrolmentType = "R";
    private Research research;

    public Research_Student(String firstName, String lastName, long studentNumber, Research research) {
        super(firstName, lastName, studentNumber);
        this.research = research;
    }

    public void reportGrade() {
        System.out.println("R, " + getFirstName() + " " + getLastName() + ", " + getStudentNumber() + ", " +
                research.getOverallMark() + ", " + research.getFinalGrade());
    }

    public Research getResearch() {
        return research;
    }
}