package Solver;

import Simplifier.EquationNode;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * This is the base solver for Polynomials, no fractions
 * Created by rgw3d on 1/13/2015.
 */
public class PolynomialSolver {


    /**
     * @param list of Nominals to be solved
     * @return SolvedEquation in form of the SolvedEquation object
     */
    public static SolvedEquation startPolynomialSolver(ArrayList<EquationNode> list){

        int length = list.size();
        double highestExponent = SolveControl.findHighestExponent(list);
        int varNominalCount = SolveControl.countNominalsWithVars(list);

        switch (length){
            case 0:
                System.out.print("Nothing to solve");
                break;
            case 1:
                if(highestExponent == 0)//no variable, no answer
                    return new SolvedEquation(new Nominal(Double.NaN,0),list);
                else
                    return new SolvedEquation(new Nominal(0,0),list);//variable must be zero
            case 2:
                return solveLengthTwoPolynomial(list, highestExponent,varNominalCount);
            case 3:
                return solveLengthThreePolynomial(list, highestExponent,varNominalCount);
            case 4:
                return solveLengthFourPolynomial(list, highestExponent,varNominalCount);
            case 5:
                return solveLengthFivePolynomial(list, highestExponent,varNominalCount);
            default:

                break;
        }

        return new SolvedEquation();
    }

    private static SolvedEquation solveLengthTwoPolynomial(ArrayList<EquationNode> list, double highestExponent, int varNominalCount) {
        if(varNominalCount==2){//there are two variables in a list of length two

        }
        else if(varNominalCount ==1){

        }
        else{//having no

        }
        return  new SolvedEquation();
    }

    private static SolvedEquation solveLengthThreePolynomial(ArrayList<EquationNode> list, double highestExponent, int varNominalCount) {

        return new SolvedEquation();
    }

    private static SolvedEquation solveLengthFourPolynomial(ArrayList<EquationNode> list, double highestExponent, int varNominalCount) {

        return  new SolvedEquation();
    }

    private static SolvedEquation solveLengthFivePolynomial(ArrayList<EquationNode> list, double highestExponent, int varNominalCount) {
        return null;
    }












    public static SolvedEquation startPolynomialSolverWithFractions(ArrayList<EquationNode> list){


        return new SolvedEquation();
    }

}
