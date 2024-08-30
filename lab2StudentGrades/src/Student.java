import java.math.BigDecimal;
import java.math.RoundingMode;

public class Student {
    private String name;
    private double grade;

    public Student(String name, double grade)   {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public char getLetterGrade() {
        if (grade >= 90) {
            return 'A';
        } else if (grade >= 80) {
            return 'B';
        } else if (grade >= 70) {
            return 'C';
        } else if (grade >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public String toString() { // round to 2 decimal places
        BigDecimal rounded = new BigDecimal(grade).setScale(2, RoundingMode.HALF_UP);
        return name + " (" + rounded + ")";
    }

    public boolean equals(Object mystery) {
        if (mystery instanceof Student actualStudent) {
            return (name == null ? actualStudent.name == null : name.equals(actualStudent.name)
                    && grade == actualStudent.grade);
        }
        return false;
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (int)(grade*10000.0);
        return hash;
    }
}
