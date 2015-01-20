package Solver;

import Simplifier.EquationNode;
import Simplifier.MathOperations;
import Simplifier.Nominal;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionConversionException;

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
                    return solvePowerTwoEquation(list);
                case 3:
                    return solvePowerThreeEquation(list);
                case 4:
                    return solvePowerFourEquation(list);
                case 5:
                    return solvePowerFiveEquation(list);
                default:
                    return solveHighPowerEquation(list);
            }
        }

        return new SolvedEquation();
    }

    private static SolvedEquation solvePowerOneEquation(ArrayList<EquationNode> list){
        if(list.size() == 1)
            return new SolvedEquation(Nominal.Zero,list);

        else if(list.size() == 2){
            Nominal powerOne = findNthDegreeNominal(1,list);
            Nominal powerZero = findNthDegreeNominal(0,list);
            Nominal resultant = new Nominal(-1 * powerZero.getNum() / powerOne.getNum(),0);
            return new SolvedEquation(resultant,list);
        }

        else
            throw new UnsupportedOperationException("Having a list greater than size 2 with a power 1 equation is not possible.  ");

    }

    private static SolvedEquation solvePowerTwoEquation(ArrayList<EquationNode> list){
        if(list.size() == 1)
            return new SolvedEquation(Nominal.Zero,list);

        else if(list.size() == 2){
            if(countNominalsWithVars(list) == 2){//if they are both variables
                Nominal powerTwo = findNthDegreeNominal(2,list);
                Nominal powerOne = findNthDegreeNominal(1,list);

                SolvedEquation solvedEquation = new SolvedEquation();
                solvedEquation.addSolution(Nominal.One);

                ArrayList<ArrayList<EquationNode>> factorGroup = new ArrayList<ArrayList<EquationNode>>();
                ArrayList<EquationNode> leftFactor = new ArrayList<EquationNode>();
                leftFactor.add(new Nominal(1,1));
                ArrayList<EquationNode> rightFactor = new ArrayList<EquationNode>();
                rightFactor.add(new Nominal(powerTwo.getNum(),powerTwo.getVar() - 1));
                rightFactor.add(new Nominal(powerOne.getNum(),powerOne.getVar() - 1));
                factorGroup.add(leftFactor);
                factorGroup.add(rightFactor);
                solvedEquation.addFactor(factorGroup);

                solvedEquation.addSolutions(solvePowerOneEquation(rightFactor));

                return solvedEquation;

            }
            else{//if it is one variable and one number
                Nominal powerTwo = findNthDegreeNominal(2,list);
                Nominal powerZero = findNthDegreeNominal(0,list);

                SolvedEquation solvedEquation = new SolvedEquation();
                Nominal resultant = new Nominal(Math.pow((-1*powerZero.getNum())/(powerTwo.getNum()),(1.0/powerTwo.getVar())),0);
                solvedEquation.addSolution(resultant);

                return solvedEquation;

            }
        }
        else if(list.size() == 3){ //full quadratic
            Nominal powerTwo = findNthDegreeNominal(2,list);
            Nominal powerOne = findNthDegreeNominal(1,list);
            Nominal powerZero = findNthDegreeNominal(0,list);

            SolvedEquation solvedEquation = new SolvedEquation();
            if(( -4 * powerTwo.getNum() * powerZero.getNum())>=0) {
                Nominal resultant = new Nominal((-1 * powerOne.getNum() + Math.sqrt(Math.pow(powerOne.getNum(), 2) + (-4 * powerTwo.getNum() * powerZero.getNum()))) / (2 * powerTwo.getNum()), 0);
                solvedEquation.addSolution(resultant);
                solvedEquation.addSolution(new Nominal(-1 * resultant.getNum(), 0));

                ArrayList<ArrayList<EquationNode>> factorGroup = new ArrayList<ArrayList<EquationNode>>();
                ArrayList<EquationNode> leftFactor = new ArrayList<EquationNode>();
                ArrayList<EquationNode> rightFactor = new ArrayList<EquationNode>();

                try{
                    Fraction f = new Fraction(resultant.getNum());//get the left side
                    int coeff = f.getDenominator();
                    int term = f.getNumerator();
                    leftFactor.add(new Nominal(coeff,1));
                    leftFactor.add(new Nominal(term,0));

                    f = new Fraction(resultant.getNum());//get the right side
                    coeff = f.getDenominator();
                    term = f.getNumerator();
                    rightFactor.add(new Nominal(coeff,1));
                    rightFactor.add(new Nominal(term,0));
                }
                catch (FractionConversionException e){
                }

                factorGroup.add(leftFactor);
                factorGroup.add(rightFactor);
                solvedEquation.addFactor(factorGroup);

            }
            else
                solvedEquation.addSolution(new Nominal(Double.NaN,0));

            return solvedEquation;
        }
        else
            throw new UnsupportedOperationException("Having a list greater than size 3 with a power 2 equation is not possible.");

    }

    private static SolvedEquation solvePowerThreeEquation(ArrayList<EquationNode> list){

        return new SolvedEquation();
    }

    private static SolvedEquation solvePowerFourEquation(ArrayList<EquationNode> list){

        return new SolvedEquation();
    }

    private  static SolvedEquation solvePowerFiveEquation(ArrayList<EquationNode> list){

        return new SolvedEquation();
    }

    private static SolvedEquation solveHighPowerEquation(ArrayList<EquationNode> list){

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

            //return new SolvedEquation(solutions,list);
            return new SolvedEquation();

        }
        else if(varNominalCount ==1){

            Nominal lowest = findNthDegreeNominal(0,list);
            Nominal highest = findNthDegreeNominal(highestExponent,list);

            ArrayList<EquationNode> solutions = new ArrayList<EquationNode>();
            solutions.add(new Nominal(Math.pow((-1*lowest.getNum())/(highest.getNum()),(1.0/highest.getVar())),0));

            if(highest.getVar()%2==0) //get both factors
                solutions.add(new Nominal( -1 * Math.pow((-1*lowest.getNum())/(highest.getNum()),(1.0/highest.getVar())),0));

            return new SolvedEquation();
            //return new SolvedEquation(solutions,list);

        }
        else//having no variables should not be possible, but here it is anyway to print an error message
            throw new UnsupportedOperationException("Having two Nominals without variables should not be possible. List: "+list.get(0)+ " " + list.get(1));

    }















    public static SolvedEquation startPolynomialSolverWithFractions(ArrayList<EquationNode> list){


        return new SolvedEquation();
    }

}
