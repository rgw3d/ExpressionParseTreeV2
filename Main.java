import Simplifier.*;
import Solver.SolveControl;
import java.util.ArrayList;

/**
 * Contains main method, along with readInput method
 * Created by rgw3d on 9/14/2015.
 */
public class Main {
    /**
     * Parses input, and returns ArrayList of simplified nodes
     * @param input String containing expression
     * @return ArrayList containing the simplified expression
     */
    public static ArrayList<EquationNode> simplifyExpression(String input) {
        ControlOperator controlOperator = new ControlOperator();//root object
        controlOperator.addTerm(new ExpressionParser().ParseEquation(input));//parse the expression
        return controlOperator.getList();
    }

    /**
     * This is used to get the properly formated output after receiving the list of results.
     * uses addition between every term
     * @param list must send a ArrayListEquationNode
     */
    public static String resultToString(ArrayList<EquationNode> list) {
        StringBuilder result = new StringBuilder();
        for (EquationNode x : list) {
            result.append("+");
            result.append(x.toString());
        }
        return result.toString().substring(1);//remove the first + sign
    }

    /**
     *
     * Main method. contains input/ simplifier loop
     * @param args System in
     */
    public static void main(String[] args) {

        System.out.println("Enter an expression to see it simplified");
        System.out.println("\tPI and E can be approximated.  Type pi for PI and e for E");
        System.out.println("\tType \"stop\" to exit");

        String input;
        do {
            input = "";
            try {
                input = ExpressionSanitizer.readInput();//read input
            } catch (InputException ie) {
                if (ie.getMessage().equals("stop"))//error message to stop the loop
                    input = null;
                else //the exception message if we are not stopping
                    System.out.println(ie.getMessage()+"\n");
                continue;//after the error message jump to the end of the loop
            }

            long startTime = System.currentTimeMillis();

            ArrayList<EquationNode> result = simplifyExpression(input);
            System.out.println("\tResult: " + resultToString(result));//get the formatted result here

            SolveControl.startSolve(result);


            long endTime = System.currentTimeMillis();
            System.out.println();
            System.out.println("It took " + (endTime - startTime) + " milliseconds");//print time
            System.out.println();


        } while(input!=null);
    }


}
