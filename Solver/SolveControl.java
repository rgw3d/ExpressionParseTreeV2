package Solver;

import Simplifier.EquationNode;
import Simplifier.Fraction;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * Created by rgw3d on 1/13/2015.
 * This class will determine what action is required to solve an equation for its variable
 * Also contains utility functions used by both the Fraction and Polynomial Solver classes
 *  that do simple things like count the number of Fraction objects in given list, or find
 *  the highest exponent in a list of Nominals
 */
public class SolveControl {

    /**
     * Starts the process of trying to solve for the variable
     *
     * @param list the list of EquationNode objects that hold the variables/numerical values.  this list
     *             should just be NumberStructure objects, Nominal and Fraction
     */
    public static void startSolve(ArrayList<EquationNode> list){
        int length = list.size();
        int fractionCount = countFractions(list);

        if(fractionCount == length)
            FractionSolver.startFractionSolver(list);
        else if( fractionCount>0 )
            PolynomialSolver.startPolynomialSolverWithFractions(list);
        else
            PolynomialSolver.startPolynomialSolver(list);
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
