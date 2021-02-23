package sample;

public class StudentRecord {
    private String studentID;
    private float assignments;
    private float midterm;
    private float finalExam;

    public StudentRecord(String studentID, float assignments, float midterm, float finalExam) {
        this.studentID = studentID;
        this.assignments = assignments;
        this.midterm = midterm;
        this.finalExam = finalExam;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public float getMidterm() {
        return this.midterm;
    }

    public float getAssignments() {
        return this.assignments;
    }

    public float getFinalExam() {
        return this.finalExam;
    }

    public double getFinalMark() {
        return (this.assignments * .2 + this.midterm * .3 + this.finalExam * .5);
    }

    public char getLetterGrade() {
        double finalMark = getFinalMark();
        if (finalMark <= 50) {
            return 'F';
        } else if (finalMark <= 60) {
            return 'D';
        } else if (finalMark <= 70) {
            return 'C';
        } else if (finalMark <= 80) {
            return 'B';
        } else {
            return 'A';
        }
    }
}
