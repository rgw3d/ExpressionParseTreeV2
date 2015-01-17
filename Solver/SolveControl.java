package Solver;

import Simplifier.EquationNode;
import Simplifier.Fraction;
import Simplifier.MathOperations;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * Created by rgw3d on 1/13/2015.
 * This class will determine what action is required to solve an equation for its variable
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
        int fractionCount = MathOperations.countFractions(list);

        if(fractionCount == length)
            FractionSolver.startFractionSolver(list);
        else if( fractionCount>0 )
            PolynomialSolver.startPolynomialSolverWithFractions(list);
        else
            PolynomialSolver.startPolynomialSolver(list);
    }




}
