import Simplifier.InputException;
import Simplifier.ControlOperator;
import Simplifier.EquationNode;
import Simplifier.Parser;
import Solver.SolveControl;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input class for EquationParseTree
 * Created by rgw3d on 10/9/2014.
 */
class Input {

    private static String readInput() throws InputException {
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
        System.out.println("\tParsing with respect to: " + Parser.variable);

        System.out.println("\tInput Equation: " + input);
        input = handSanitizer(input);

        return input;
    }

    /**
     * This is used to get the properly formated output after receiving the list of results.
     * uses addition between every term
     *
     * @param list must send a ArrayListEquationNode
     */
    private static String resultToString(ArrayList<EquationNode> list) {
        StringBuilder result = new StringBuilder();
        for (EquationNode x : list) {
            result.append("+");
            result.append(x.toString());
        }
        return result.toString().substring(1);//remove the first + sign
    }

    /**
     * Extract the actual mathematical constants PI and E
     * Call this before the other input sanitation methods to replace the constants
     * @param input the input string.
     * @return String of the updated input string
     */
    private static String extractConstants(String input){

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
    private static boolean isEquation(String input) {
        if (!(input.length() >= 3) ) //to short
        {
            System.out.print("Too Short to be considered an expression");
            return false;
        }
        if(!(input.contains("+")||input.contains("*")||input.contains("/") || input.contains("^"))){
            System.out.print("Does not contain an operator");
            return false;
        }

        String endOfEq = input.substring(input.length() - 1);//ends bad
        if (endOfEq.equals("+") || endOfEq.equals("-") || endOfEq.equals("*") || endOfEq.equals("/")) {
            System.out.print("Ends with a +, -, * or /");
            return false;
        }

        String beginOfEq = "" + input.charAt(0); //starts bad
        if (beginOfEq.equals("+")  || beginOfEq.equals("*") || beginOfEq.equals("/")) {
            System.out.print("Starts with a +, * or /");
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
                    System.out.print("Mixing variables! Use only one variable.");
                    return false;
                }
            }
        }
        if(!variable.equals("")) {
            Parser.variable = variable;
        }

        p = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        m = p.matcher(input);
        if (m.find()) {
            System.out.print("Two or more of a kind: "+m.group());
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
    private static boolean parenthesisCheck(String input){
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
     * @param fix the string to reformat so that the parser can parse it
     * @return returns the "fixed"  string
     */
    private static String handSanitizer(String fix) {
        String orig = fix;

        fix = fix.replace(" ", "");//Getting rid of spaces

        fix = fix.replace("--", "+"); //minus a minus is addition.  make it simple

        fix = fix.replace("-", "+-");  //replace a negative with just plus a minus.

        fix = fix.replace("^+-", "^-"); //common error that happens after one of the above methods run. negative exponents

        fix = fix.replace("*+-", "*-"); //common error that happens if multiplying by a negative

        fix = fix.replace("(+-", "(-"); //common error that happens if multiplying by a negative

        fix = fix.replace(")(",")*(");//multiply by parenthesis

        fix = fix.replace("++-","+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        fix = fix.replace(Parser.variable+"(",Parser.variable+"*("); //for situations like: x(x+3).  before the fix, that would not work

        fix = fix.replace(")"+Parser.variable,")*"+Parser.variable); //same as above

        if(fix.startsWith("+-"))
            fix = fix.substring(1);//can't start with a +  happens of above replacements

        if(!orig.equals(fix))
            System.out.println("\tReformatted Equation: " + fix);//only if it has changed print the reformatted equation
        return fix;
    }


    public static void main(String[] args) {

        System.out.println("Enter an expression to see it simplified");
        System.out.println("\tPI and E can be approximated.  Type pi for PI and e for E");
        System.out.println("\tType \"stop\" to break the loop");

        while(true){
            String input = "";
            try {
                input = readInput();//read input
            }
            catch(InputException ie) {
                if(ie.getMessage()=="stop")//error message to stop the loop
                    break;
                else {//if we are not breaking, then there is an actual exception
                    System.out.println(ie.getMessage());
                    continue;
                }
            }



            //do math

            ControlOperator controlOperator = new ControlOperator();
            Parser parser = new Parser();
            long startTime = System.currentTimeMillis();

            controlOperator.addTerm(parser.ParseEquation(input));//parse the expression
            ArrayList<EquationNode> result = controlOperator.getList();
            System.out.print(resultToString(result));//get the result here
            long endTime = System.currentTimeMillis();

            System.out.println();
            System.out.println("It took " + (endTime - startTime) + " milliseconds to simplify");//print time
            System.out.println();

            SolveControl.startSolve(result);


            Timer obj = new Timer () {
                public void abstractMethod(){
                    System.out.println("wo");
                }
            };
            obj.abstractMethod();




        }
    }
}