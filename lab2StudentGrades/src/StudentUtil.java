public class StudentUtil {
    public static char getLetterGrade(Student McJeebers) {
        double theGrade = McJeebers.getGrade();
        if (theGrade >= 90) {
            return 'A';
        } else if (theGrade >= 80) {
            return 'B';
        } else if (theGrade >= 70) {
            return 'C';
        } else if (theGrade >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}