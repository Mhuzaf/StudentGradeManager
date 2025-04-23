public class Research extends Unit {
    private double proposal;
    private double dissertation;

    public Research(double proposal, double dissertation) {
        super("R");
        this.proposal = proposal;
        this.dissertation = dissertation;
    }

    @Override
    public double getOverallMark() {
        return (proposal * 0.4) + (dissertation * 0.6); // 40% proposal, 60% dissertation
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