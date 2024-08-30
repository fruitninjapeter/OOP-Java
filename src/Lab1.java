import java.util.ArrayList;
import java.util.List;

public class Lab1 {
    /* lab1.java
    A simple program demonstrating basic language features.
    */

    // Function Definition
    public static boolean contains(List<String> sList, String s) {
        /* Return true if the list contains the desired item. */
        // Iteration on elements
        //for (int i = 0; i < sList.size(); i++) {
        for (String str : sList) {
            // Conditional
            if (str.equals(s)) {
                return true;    }
        }
        return false;
    }
    public static void main(String[] args) {
        // Variables
        int x = 3;
        double y = 4.0;
        int z = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        // String Concatenation
        System.out.println("x: " + x);

        // String Formatting
        String formatted = String.format("y: " + y);
        System.out.println(formatted);

        // Printing on Same Line
        System.out.print("z: ");
        System.out.print(z);

        // Strings and Characters
        String a = "hello";
        char b = 'j';
        String c = a.replace('h', b);

        // Iteration in Indices
        System.out.println();
        for (int i = 0; i < c.length(); i++) {
            System.out.print(c.charAt(i));  }   // Print on same line
        System.out.println();   // Print the line's end

        // List Usage
        List<String> cats = new ArrayList<>();
        cats.add("Mochi"); cats.add("harvest");
        cats.remove(0);
        cats.add("Pearl");

        // Function Call
        boolean has_mochi = contains(cats, "Mochi");
        if (!has_mochi) {
            System.out.println("Bye bye, Mochi! Farewell!");
        }
    }
}