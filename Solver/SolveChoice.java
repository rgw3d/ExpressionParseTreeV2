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
        boolean hasFractions = findFractions(list);//if true, then it is not a true polynomial
        double highestExponent = findHighestExponent(list);

    }

    /**
     * Determine if there is a fraction or not
     *
     * @param list the list of EquationNodes
     * @return boolean if there is one or more instances of a fraction
     */
    private static boolean findFractions(ArrayList<EquationNode> list){
        for(EquationNode node: list){
            if(node instanceof Fraction){
                return true;//if there is one fraction, return true;
            }
        }
        return false;
    }

    /**
     * Find the highest exponent, assuming no fractions, in a polynomial.
     * Fractions are ignored!
     *
     * @param list the list of EquationNodes
     * @return double of the highestExponent
     */
    private static double findHighestExponent(ArrayList<EquationNode> list){
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
