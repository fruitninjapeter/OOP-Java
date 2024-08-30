import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Contains file processing behavior for the project.
 * You will modify only the following methods:
 * - parseLineUsingLittleNumber()
 * - parseLineUsingBigNumber()
 * You do not need to run this class directly.
 *
 * @author Vanessa Rivera
 */
public class FileProcessor {
    /**
     * Automatically called multiple times when parsing an input text file, once for each line.
     * The included tests control whether the LittleNumber or BigNumber version is used.
     *
     * @param line The line from the input file, including any newline characters
     */
    public static void parseLineUsingLittleNumber(String line) {
        String[] lineArray = line.split(" +");
        if (lineArray.length < 3) { return; }
        LittleNumber a = LittleNumber.fromString(lineArray[0]); // first number
        LittleNumber b = LittleNumber.fromString(lineArray[2]); // second number
        String op = lineArray[1]; // operator
        LittleNumber result;    // declare result variable
        if (op.equals("+")) {   // addition
            result = a.add(b);
        } else if (op.equals("*")) {    // multiplication
            result = LittleNumber.multiply(a, b);
        } else {    // exponentiation
            int p = Integer.parseInt(lineArray[2]);
            result = LittleNumber.exponentiate(a, p);
        }
        System.out.printf(a + " " + op + " " + b + " = " + result + "%n");
    }

    /**
     * Automatically called multiple times when parsing an input text file, once for each line.
     * The included tests control whether the LittleNumber or BigNumber version is used.
     *
     * @param line The line from the input file, including any newline characters
     */
    public static void parseLineUsingBigNumber(String line) {
        String[] LineArray = line.split(" +");
        if (LineArray.length < 3) { return; }
        BigNumber a = BigNumber.fromString(LineArray[0]);   // First Number
        BigNumber b = BigNumber.fromString(LineArray[2]);   // Second Number
        String OP = LineArray[1];   // operator
        BigNumber BigChungus; // declare result variable
        switch (OP) {
            case "+": BigChungus = a.add(b); break; // addition
            case "*": BigChungus = BigNumber.multiply(a, b); break; // multiplication
            default: // exponentiation
                int smolNumber = Integer.parseInt(LineArray[2]);
                BigChungus = BigNumber.exponentiate(a, smolNumber);
        }
        System.out.printf(a.toString() + " " + OP + " " + b.toString() + " = " + BigChungus.toString() + "%n");
    }

    /**
     * Called by the included test file. You do not need to run this method directly.
     * <strong>Do not</strong> modify this method.
     *
     * @param args Command-line arguments
     *             0: The input file path. (Required)
     *             1: Option to use "little" or "big" number parsing. (Required)
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    switch (args[1]) {
                        case "little":
                            parseLineUsingLittleNumber(line);
                            break;
                        case "big":
                            parseLineUsingBigNumber(line);
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("Option %s is invalid, must be 'little' or 'big'.%n", args[1]));
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.printf("Expected 2 command-line arguments, got %d.%n", args.length);
        } catch (IOException e) {
            System.out.printf("Failed to read file %s.%n", args[0]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
