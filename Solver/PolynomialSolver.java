package Solver;

import Simplifier.EquationNode;
import Simplifier.MathOperations;
import Simplifier.Nominal;
import static Simplifier.MathOperations.*;

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

        double highestExponent = MathOperations.findHighestVariableExponent(list);

        if((int)highestExponent!=highestExponent){
            System.out.print("Cannot Solve, exponent value is not a whole number");
        }
        else {

            switch ((int) highestExponent) {
                case 0:
                    System.out.print("Nothing to solve");
                    break;
                case 1:
                    return solvePowerOneEquation(list);
                case 2:
                    // return solveLengthTwoPolynomial(list, highestExponent,varNominalCount);
                case 3:
                    //return solveLengthThreePolynomial(list, highestExponent,varNominalCount);
                case 4:
                    //return solveLengthFourPolynomial(list, highestExponent,varNominalCount);
                case 5:
                    // return solveLengthFivePolynomial(list, highestExponent,varNominalCount);
                default:

                    break;
            }
        }

        return new SolvedEquation();
    }

    private static SolvedEquation solvePowerOneEquation(ArrayList<EquationNode> list){
        return  new SolvedEquation();

    }

    private static SolvedEquation solvePowerTwoEquation(ArrayList<EquationNode> list){



        return new SolvedEquation();
    }

    private static SolvedEquation solveLengthTwoPolynomial(ArrayList<EquationNode> list, double highestExponent, int varNominalCount) {
        if(varNominalCount==2){//there are two variables in a list of length two

            ArrayList<EquationNode> solutions = new ArrayList<EquationNode>();
            solutions.add(new Nominal(0,0));//zero must be a solution

            Nominal lowest = findNthDegreeNominal(findSmallestVariableExponent(list), list);
            Nominal highest = findNthDegreeNominal(highestExponent,list);

            ArrayList<EquationNode> insideEquation = new ArrayList<EquationNode>();
            insideEquation.add(new Nominal(highest.getNum(),highest.getVar()-lowest.getVar()));//add both nominals
            insideEquation.add(new Nominal(lowest.getNum(),0));
            solutions.addAll(solveLengthTwoPolynomial(insideEquation,findHighestVariableExponent(insideEquation),countNominalsWithVars(insideEquation)).getSolutionSet());

            return new SolvedEquation(solutions,list);

        }
        else if(varNominalCount ==1){

            Nominal lowest = findNthDegreeNominal(0,list);
            Nominal highest = findNthDegreeNominal(highestExponent,list);

            ArrayList<EquationNode> solutions = new ArrayList<EquationNode>();
            solutions.add(new Nominal(Math.pow((-1*lowest.getNum())/(highest.getNum()),(1.0/highest.getVar())),0));

            if(highest.getVar()%2==0) //get both factors
                solutions.add(new Nominal( -1 * Math.pow((-1*lowest.getNum())/(highest.getNum()),(1.0/highest.getVar())),0));

            return new SolvedEquation(solutions,list);

        }
        else//having no variables should not be possible, but here it is anyway to print an error message
            throw new UnsupportedOperationException("Having two Nominals without variables should not be possible. List: "+list.get(0)+ " " + list.get(1));

    }














    public static SolvedEquation startPolynomialSolverWithFractions(ArrayList<EquationNode> list){


        return new SolvedEquation();
    }

}
