import Simplifier.InputException;
import Simplifier.ExpressionParser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input/ input sanitation class for EquationParseTree
 * Created by rgw3d on 10/9/2014.
 */
class ExpressionSanitizer {

    /**
     *
     * @return String containing input expression
     * @throws InputException
     */
    public static String readInput() throws InputException {
        System.out.print("Enter Expression:  ");
        String input = new Scanner(System.in).nextLine().toLowerCase();
        System.out.println();
        if(input.equalsIgnoreCase("stop"))
            throw new InputException("stop");

        input = extractConstants(input);
        if (!isEquation(input)) {
            throw new InputException("Bad Expression. Please Revise");
        }
        System.out.println("Syntax Passed!");
        System.out.println("\tParsing with respect to: " + ExpressionParser.variable);

        System.out.println("\tInput Equation: " + input);
        input = reformatInput(input);

        return input;
    }

    /**
     * Extract the actual mathematical constants PI and E
     * Call this before the other input sanitation methods to replace the constants
     * @param input the input string.
     * @return String of the updated input string
     */
    public static String extractConstants(String input){

        input = input.replace("pi",Math.PI+"");//change pi to the actual value

        input = input.replace("e",Math.E+"");//change e to the actual value

        return input;
    }

    /**
     * Determines if the input string is appropriate to parse
     *
     * @param input the string to be tested
     * @return boolean if the expression qualifies as an acceptable expression
     */
    public static boolean isEquation(String input) {
        if (!(input.length() >= 3) ) //to short
        {
            System.out.println("Too Short to be considered an expression");
            return false;
        }
        if(!(input.contains("+")||input.contains("*")||input.contains("/") || input.contains("^"))){
            System.out.println("Does not contain an operator");
            return false;
        }

        String endOfEq = input.substring(input.length() - 1);//ends bad
        if (endOfEq.equals("+") || endOfEq.equals("-") || endOfEq.equals("*") || endOfEq.equals("/")) {
            System.out.println("Ends with a +, -, * or /");
            return false;
        }

        String beginOfEq = "" + input.charAt(0); //starts bad
        if (beginOfEq.equals("+")  || beginOfEq.equals("*") || beginOfEq.equals("/")) {
            System.out.println("Starts with a +, * or /");
            return false;
        }

        Pattern p = Pattern.compile("[^(0-9,a-z*,/,\\-,\\.,+,\\^,\\s)]");
        Matcher m = p.matcher(input);
        if (m.find()) {//detects illegal character
            System.out.println("Illegal Character(s): " + m.group());
            return false;
        }

        String variable = "";//find the variable
        for(char indx: input.toCharArray()){
            p = Pattern.compile("[a-z]");
            m = p.matcher(indx+"");
            if(m.find()){
                if(variable.equals("")){
                    variable = m.group();
                }
                else if(!variable.equals(m.group())){
                    System.out.println("Mixing variables! Use only one variable.");
                    return false;
                }
            }
        }
        if(!variable.equals("")) {
            ExpressionParser.variable = variable;
        }

        p = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        m = p.matcher(input);
        if (m.find()) {
            System.out.println("Two or more of a kind: " + m.group());
            return false;
        }

        if(parenthesisCheck(input)){
            System.out.println("Uneven amount of parenthesis");
            return false;
        }

        //good syntax
        return true;
    }

    /**
     *
     * @param input the string to be tested
     * @return true if there is an inconsistency.
     */
    public static boolean parenthesisCheck(String input){
        int openCount = 0;
        int closedCount = 0;
        for(int indx = 0; indx<input.length(); indx++){
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count
            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count
        }

        if(!(openCount==closedCount))
        {
            System.out.println("Open: "+openCount);
            System.out.println("Closed: "+closedCount);
            return true;

        }
        return false;

    }

    /**
     *
     * @param input the string to reformat so that the parser can easily parse it
     * @return returns the "fixed"  string
     */
    public static String reformatInput(String input) {
        String orig = input;

        input = input.replace(" ", "");//Getting rid of spaces

        input = input.replace("--", "+"); //minus a minus is addition.  make it simple

        input = input.replace("-", "+-");  //replace a negative with just plus a minus.

        input = input.replace("^+-", "^-"); //common error that happens after one of the above methods run. negative exponents

        input = input.replace("*+-", "*-"); //common error that happens if multiplying by a negative

        input = input.replace("(+-", "(-"); //common error that happens if multiplying by a negative

        input = input.replace(")(",")*(");//multiply by parenthesis

        input = input.replace("++-","+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        input = input.replace(ExpressionParser.variable+"(", ExpressionParser.variable+"*("); //for situations like: x(x+3).  before the fix, that would not work

        input = input.replace(")"+ ExpressionParser.variable,")*"+ ExpressionParser.variable); //same as above

        if(input.startsWith("+-"))
            input = input.substring(1);//can't start with a +  happens of above replacements

        if(!orig.equals(input))
            System.out.println("\tReformatted Equation: " + input);//only if it has changed print the reformatted equation
        return input;
    }


}