package Solver;

import Simplifier.EquationNode;
import Simplifier.MathOperations;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * This class will store the solutionSet of an equation when it is solved
 * It will have a list of what values the variable could be, and f
 * Created by rgw3d on 1/13/2015.
 */
public class SolvedEquation {



    private ArrayList<EquationNode> solutionSet;//the solutionSet
    private ArrayList<EquationNode> initEquation;//initEquation equation

    public SolvedEquation(){

    }

    public SolvedEquation(ArrayList<EquationNode> solutionSet, ArrayList<EquationNode> initEquation){

        this.solutionSet = solutionSet;
        this.initEquation = initEquation;

    }

    public SolvedEquation(Nominal solutionSet, ArrayList<EquationNode> initEquation){

        ArrayList<EquationNode> tmp = new ArrayList<EquationNode>();
        tmp.add(solutionSet);
        this.solutionSet = tmp;
        this.initEquation = initEquation;

    }

    public ArrayList<EquationNode> getInitEquation() {
        return initEquation;
    }
    public ArrayList<EquationNode> getSolutionSet() {
        return solutionSet;
    }

    public int getLength(){
        return initEquation.size();
    }

    public int getFractionCount(){
        return MathOperations.countFractions(initEquation);
    }

    public double getHighestExponent(){
        return MathOperations.findHighestVariableExponent(initEquation);
    }


}
