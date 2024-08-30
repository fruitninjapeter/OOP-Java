import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StudentTests {

    Student student1 = new Student("Peter", 33);
    Student student1Clone = new Student("Peter", 33);
    String Santa = "Ho! Ho! Ho!";
    @Test
    public void testStudent1() {
        assertEquals(student1.getName(), "Peter");
        assertEquals(student1.getGrade(), 33);
        assertEquals(student1.getLetterGrade(), 'F');
        assertEquals(student1.toString(), "Peter (33.00)");
        assertEquals(student1.getLetterGrade(), StudentUtil.getLetterGrade(student1));
        assertTrue(student1.equals(student1Clone));
        assertFalse(student1.equals(Santa));
    }

    Student student2 = new Student("Amy", 89.999);
    Student student3 = new Student("Jessica", 75.2);
    @Test
    public void testStudents() {
        assertEquals(student2.getName(), "Amy");
        assertEquals(student2.getGrade(), 89.999);
        assertEquals(student3.getGrade(), 75.2);
        assertEquals(student2.getLetterGrade(), 'B');
        assertEquals(student2.getLetterGrade(), StudentUtil.getLetterGrade(student2));
        assertEquals(student3.getLetterGrade(), 'C');
        assertEquals(student3.getLetterGrade(), StudentUtil.getLetterGrade(student3));
        assertEquals(student2.toString(), "Amy (90.00)");
        assertEquals(student3.toString(), "Jessica (75.20)");
        assertNotEquals(student2.hashCode(),student3.hashCode());
    }

    Student student4 = new Student("Jeremers", 69.6969696969);
    Student student5 = new Student("Jenna", 101.101);

    @Test
    public void testMoreStudents() {
        assertEquals(student4.getGrade(), 69.6969696969);
        assertEquals(student4.getLetterGrade(), StudentUtil.getLetterGrade(student4));
        assertEquals(student5.getGrade(), 101.101);
        assertEquals(student5.getLetterGrade(), StudentUtil.getLetterGrade(student5));
        assertNotEquals(student5.hashCode(), Double.hashCode(101.101));
        assertEquals(student4.toString(), "Jeremers (69.70)");
        assertEquals(student5.toString(), "Jenna (101.10)");
    }
}
