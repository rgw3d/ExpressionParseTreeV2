package Solver;

import Simplifier.EquationNode;

import java.util.ArrayList;

/**
 * This class will store the results of an equation when it is solved
 * It will have a list of what values the variable could be, and f
 * Created by rgw3d on 1/13/2015.
 */
public class SolvedEquation {

    public ArrayList<EquationNode> results;//the results
    public ArrayList<EquationNode> original;//original equation

    public SolvedEquation(ArrayList<EquationNode> results, ArrayList<EquationNode> original){

        this.results = results;
        this.original = original;

    }

    public int getLength(){
        return original.size();
    }

    public int getFractionCount(){
        return SolveChoice.countFractions(original);
    }

    public double getHighestExponent(){
        return SolveChoice.findHighestExponent(original);
    }


}
