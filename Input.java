import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input class for EquationParseTree
 * Created by rgw3d on 10/9/2014.
 */
public class Input {
    public static void main(String[] args) {

        System.out.println("Enter an expression to see it simplified");
        System.out.println("\tType STOP to break the loop");
        while(true){
            System.out.print("Enter Expression:  ");
            String input = new Scanner(System.in).nextLine();
            System.out.println();
            if(input.equalsIgnoreCase("stop"))
                break;

            Date start = new Date();
            if (!isEquation(input)) {
                System.out.println("\nBad Expression. Please Revise\n");
                continue;
            }

            input = handSanitizer(input);

            ControlOperator controlOperator =new ControlOperator();
            Parser parser = new Parser();
            controlOperator.addTerm(parser.ParseEquation(input));//parse the expression

            printSimplifiedResult(controlOperator.getList());//get the result here
            System.out.println("It took " + (new Date().getTime() - start.getTime()) + " milliseconds");//print time
            System.out.println("");

        }
    }

    /**
     * This is used to print the output after recieving the list of results.
     * uses addition between every term
     * does not return anything, as it simply prints out something.
     * @param list must send a ArrayListEquationNode
     */
    private static void printSimplifiedResult(ArrayList<EquationNode> list) {
        String toPrint = "";
        for (EquationNode x : list) {
            toPrint += "+" + x.toString();
        }
        toPrint = toPrint.substring(1);//remove the first + sign
        System.out.println("Result: "+toPrint);
    }

    /**
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

        Pattern p = Pattern.compile("[^(0-9,*,/,\\-,\\.,+,x,X,\\^,\\s)]");
        Matcher m = p.matcher(input);
        if (m.find()) {//detects illegal character
            System.out.println("Illegal Character(s): " + m.group());
            return false;
        }


        Pattern n = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        Matcher o = n.matcher(input);
        if (o.find()) {
            System.out.print("Two or more of a kind");
            return false;
        }

        if(parenthesisCheck(input)){
            System.out.println("Uneven amount of parenthesis");
            return false;
        }

        //good syntax if
        System.out.print("Syntax Passed");
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
     * @param fix the string to reformat so that the parser can parse it
     * @return returns the "fixed"  string
     */
    private static String handSanitizer(String fix) {
        System.out.print("\n\tInput Equation: " + fix);
        fix = fix.replace(" ", "");//Geting rid of spaces

        fix = fix.replace("--", "+"); //minus a minus is addition.  make it simple

        fix = fix.replace("-", "+-");  //replace a negative with just plus a minus.

        fix = fix.replace("X", "x");//just cuz

        fix = fix.replace("^+-", "^-"); //common error that happens after one of the above methods run. negative exponents

        fix = fix.replace("*+-", "*-"); //common error that happens if multiplying by a negative

        fix = fix.replace("(+-", "(-"); //common error that happens if multiplying by a negative

        fix = fix.replace(")(",")*(");//multiply by parenthesis

        fix = fix.replace("++-","+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        if(fix.startsWith("+-"))
            fix = fix.substring(1);


        //this will be updated later as I fix all the syntax errors that come with exponents and parentheses
        System.out.println("\tReformatted Equation: " + fix);
        return fix;
    }
}
