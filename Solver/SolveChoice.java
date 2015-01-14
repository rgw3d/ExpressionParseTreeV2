package Solver;

import Simplifier.EquationNode;
import Simplifier.Fraction;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * Created by rgw3d on 1/13/2015.
 * This class will determine what action is required to solve an equation for its variable
 */
public class SolveChoice {

    /**
     * Starts the process of trying to solve for the variable
     *
     * @param list the list of EquationNode objects that hold the variables/numerical values.  this list
     *             should just be NumberStructure objects, Nominal and Fraction
     */
    public static void startSolve(ArrayList<EquationNode> list){
        int length = list.size();
        int fractionCount = countFractions(list);
        double highestExponent = findHighestExponent(list);

        if(fractionCount == length){

        }

        switch (length){
            case 0:
                System.out.print("Nothing to solve");
                break;
            case 1:
                if(fractionCount >0){
                    //do something with 1 fraction
                }
                else{
                    //should be able to solve anything at this point.
                }
                break;
            case 2:
                if(fractionCount >0){
                    //do something with the fractions
                }
                else{
                    //should be able to either factor or solve
                }
                break;
            case 3:
                //do something with a 3 long list;
                break;
            case 4:
                //do something with a 4 long list;
                break;
            case 5:
                //do something with a 5 long list;
                //this is probably the most that we can do with a polynomial
                break;
            default:
                //catch anything else of a length.
                //go from here and determine if it is possible to solve.
                break;
        }

    }

    /**
     * Count the number of fractions
     *
     * @param list the list of EquationNodes
     * @return count of the fractions in the list
     */
    public static int countFractions(ArrayList<EquationNode> list){
        int count = 0;
        for(EquationNode node: list){
            if(node instanceof Fraction){
               count ++;
            }
        }
        return count;
    }

    /**
     * Find the highest exponent, assuming no fractions, in a polynomial.
     * Fractions are ignored!
     *
     * @param list the list of EquationNodes
     * @return double of the highestExponent
     */
    public static double findHighestExponent(ArrayList<EquationNode> list){
        double highestExponent = 0;
        for(EquationNode node: list){
            if(node instanceof Nominal){
                if(node.getVar()>highestExponent){
                    highestExponent = node.getVar();
                }
            }
        }
        return highestExponent;
    }


}
