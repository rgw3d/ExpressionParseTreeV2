package Solver;

import Simplifier.*;

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
        SolvedEquation solvedEquation;

        long startTime = System.currentTimeMillis();
        if(fractionCount == length)
            solvedEquation = FractionSolver.startFractionSolver(list);
        else if( fractionCount>0 )
            solvedEquation = PolynomialSolver.startPolynomialSolverWithFractions(list);
        else
            solvedEquation = PolynomialSolver.startPolynomialSolver(list);

        long endTime = System.currentTimeMillis();

        if(solvedEquation.getSolutionSet().size()!=0) {
            printSolutionSet(solvedEquation);
            System.out.println();
            System.out.println("It took " + (endTime - startTime) + " milliseconds to solve");//print time
            System.out.println();
        }
        else {
            System.out.println("No solution found, or no solution exists");
            System.out.println();
        }
    }

    public static void printSolutionSet(SolvedEquation solvedEquation){
        System.out.println();
        System.out.println("Assuming Expression is set to zero... ");
        System.out.println("Solution Set: ");
        for(EquationNode nominal: solvedEquation.getSolutionSet()){
            System.out.println("\t"+ Parser.variable+" = "+ nominal.getNum());
        }

        if(solvedEquation.getFactors().size()>0){
            System.out.println();
            System.out.println("Factors: (May be an approximation)");
            for(ArrayList<ArrayList<EquationNode>> factorGroups: solvedEquation.getFactors()){
                System.out.print("\t");
                for(ArrayList<EquationNode> factor: factorGroups){
                    String out = "(";
                    for(EquationNode node: factor){
                        out+=node;
                        out+="+";
                    }
                    out = out.substring(0,out.length()-1);
                    out+=")";
                    System.out.print(out);
                }
                System.out.println();
            }
        }
    }






}
