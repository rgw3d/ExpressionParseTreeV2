package Solver;

import Simplifier.EquationNode;

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
        switch (length){
            case 0:
                System.out.print("Nothing to solve");
                break;
            case 1:
                if(1 >0){
                    //do something with 1 fraction
                }
                else{
                    //should be able to solve anything at this point.
                }
                break;
            case 2:
                if(2 >0){
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

        return new SolvedEquation();
    }

    public static SolvedEquation startPolynomialSolverWithFractions(ArrayList<EquationNode> list){


        return new SolvedEquation();
    }

}
