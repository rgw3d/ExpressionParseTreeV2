import Simplifier.ExpressionParser;
import Simplifier.InputException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input/ input sanitation class for EquationParseTree
 * Created by rgw3d on 10/9/2014.
 */
public class ExpressionSanitizer {

    public final static String QUIT_KEYWORD = "quit";
    private final static String ERROR_PREFIX = "Bad Expression. Please Revise\n";

    /**
     * @return String containing input expression
     * @throws InputException
     */
    public static String readInput() throws InputException {
        System.out.print("Enter Expression:  ");
        String input = new Scanner(System.in).nextLine().toLowerCase();
        System.out.println();
        if (input.equalsIgnoreCase(QUIT_KEYWORD))
            throw new InputException(QUIT_KEYWORD);

        input = extractConstants(input);
        isEquation(input);//checks for input errors
        System.out.println("Syntax Passed!");
        System.out.println("\tParsing with respect to: " + ExpressionParser.variable);

        System.out.println("\tInput Equation: " + input);
        input = reformatInput(input);
        System.out.println("\tReformatted Equation: " + input);

        return input;
    }

    /**
     * Extract the actual mathematical constants PI and E
     * Call this before the other input sanitation methods to replace the constants
     *
     * @param input the input string.
     * @return String of the updated input string
     */
    public static String extractConstants(String input) {

        input = input.replace("pi", Math.PI + "");//change pi to the actual value
        input = input.replace("e", Math.E + "");//change e to the actual value

        return input;
    }

    /**
     * Determines if the input string is appropriate to parse
     * If there is a problem, throws an InputException error.
     *
     * @param input the string to be tested
     */
    public static void isEquation(String input) throws InputException {
        if (!(input.length() >= 3)) { //to short
            throw new InputException(ERROR_PREFIX + "Too Short to be considered an expression");
        }
        if (!(input.contains("+") || input.contains("*") || input.contains("/") || input.contains("^"))) {
            throw new InputException(ERROR_PREFIX + "Does not contain an operator");
        }

        String endOfEq = input.charAt(input.length() - 1) + "";//ends bad
        if (endOfEq.equals("+") || endOfEq.equals("-") || endOfEq.equals("*") || endOfEq.equals("/")) {
            throw new InputException(ERROR_PREFIX + "Ends with: " + endOfEq);
        }

        String beginOfEq = input.charAt(0) + ""; //starts bad
        if (beginOfEq.equals("+") || beginOfEq.equals("*") || beginOfEq.equals("/")) {
            throw new InputException(ERROR_PREFIX + "Starts with " + beginOfEq);
        }

        Pattern p = Pattern.compile("[^(0-9,a-z*,/,\\-,\\.,+,\\^,\\s)]");
        Matcher m = p.matcher(input);
        if (m.find()) {//detects illegal character
            throw new InputException(ERROR_PREFIX + "Illegal Character: " + m.group());
        }

        String variable = "";//find the variable
        p = Pattern.compile("[a-z]");
        for (char indx : input.toCharArray()) {
            m = p.matcher(indx + "");
            if (m.find()) {
                if (variable.equals("")) {
                    variable = m.group();
                } else if (!variable.equals(m.group())) {
                    throw new InputException(ERROR_PREFIX + "Mixing variables! Use only one variable.");
                }
            }
        }
        if (!variable.equals("")) {
            ExpressionParser.variable = variable;
        }

        p = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        m = p.matcher(input);
        if (m.find()) {
            throw new InputException(ERROR_PREFIX + "Two or more of an operator: " + m.group());
        }

        parenthesisCheck(input);

        //good syntax if no errors are thrown.
    }

    /**
     * If parenthesis line up, then no error is thrown.
     * If there is an problem, an error is thrown describing the problem.
     *
     * @param input the string to be tested
     */
    public static void parenthesisCheck(String input) throws InputException {
        int openCount = 0;
        int closedCount = 0;
        for (int indx = 0; indx < input.length(); indx++) {
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count
            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count
        }

        if (!(openCount == closedCount)) {
            throw new InputException(ERROR_PREFIX + "Uneven amount of parenthesis\n" +
                    "\tOpen: " + openCount + "\n" +
                    "\tClosded: " + closedCount);
        }

    }

    /**
     * @param input the string to reformat so that the parser can easily parse it
     * @return returns the "fixed"  string
     */
    public static String reformatInput(String input) {

        input = input.replace(" ", "");//Getting rid of spaces

        input = input.replace("--", "+"); //minus a minus is addition.  make it simple

        input = input.replace("-", "+-");  //replace a negative with just plus a minus.

        input = input.replace("^+-", "^-"); //common error that happens after one of the above methods run. negative exponents

        input = input.replace("*+-", "*-"); //common error that happens if multiplying by a negative

        input = input.replace("(+-", "(-"); //common error that happens if multiplying by a negative

        input = input.replace(")(", ")*(");//multiply by parenthesis

        input = inferMultiplication(input);//for situations like this:  3(x+1) or (x^2-1)33, where there are parentheses and numbers touching

        input = input.replace("++-", "+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        input = input.replace(ExpressionParser.variable + "(", ExpressionParser.variable + "*("); //for situations like: x(x+3).  before the fix, that would not work

        input = input.replace(")" + ExpressionParser.variable, ")*" + ExpressionParser.variable); //same as above

        if (input.startsWith("+-"))
            input = input.substring(1);//can't start with a +. Happens because of above replacements

        return input;
    }

    public static String inferMultiplication(String input) {
        String result = "";
        Pattern p = Pattern.compile("[(0-9)]");
        for (int i = 0; i + 1 < input.length(); i++) {
            result += input.charAt(i);
            if ((p.matcher(input.charAt(i) + "").find() && input.charAt(i + 1) == '(') ||
                    (input.charAt(i) == ')' && p.matcher(input.charAt(i + 1) + "").find())) {
                result += "*";
            }
        }
        result += input.charAt(input.length() - 1);

        return result;
    }
}