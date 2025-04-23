public class Unit_Course extends Unit {
    private String unitID;
    private int level;
    private double[] assignments = new double[4];
    private double finalExam;

    public Unit_Course(String unitID, int level, double[] assignments, double finalExam) {
        super("C");
        this.unitID = unitID;
        this.level = level;
        for (int i = 0; i < 4; i++) {
            this.assignments[i] = assignments[i];
        }
        this.finalExam = finalExam;
    }

    public String getUnitID() {
        return unitID;
    }

    public double getOverallMark() {
        double assignmentAvg = 0;
        for (double mark : assignments) {
            assignmentAvg += mark;
        }
        assignmentAvg /= 4;
        return (assignmentAvg * 0.6) + (finalExam * 0.4); // 60% assignments, 40% exam
    }

    @Override
    public String getFinalGrade() {
        double mark = getOverallMark();
        if (mark >= 80) return "HD";
        else if (mark >= 70) return "D";
        else if (mark >= 60) return "C";
        else if (mark >= 50) return "P";
        else return "N";
    }
}