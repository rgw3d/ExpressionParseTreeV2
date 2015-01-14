package Solver;

import Simplifier.EquationNode;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * This class will store the results of an equation when it is solved
 * It will have a list of what values the variable could be, and f
 * Created by rgw3d on 1/13/2015.
 */
public class SolvedEquation {

    public ArrayList<EquationNode> results;//the results
    public ArrayList<EquationNode> original;//original equation

    public SolvedEquation(){

    }

    public SolvedEquation(ArrayList<EquationNode> results, ArrayList<EquationNode> original){

        this.results = results;
        this.original = original;

    }

    public SolvedEquation(Nominal results, ArrayList<EquationNode> original){

        ArrayList<EquationNode> tmp = new ArrayList<EquationNode>();
        tmp.add(results);
        this.results = tmp;
        this.original = original;

    }

    public int getLength(){
        return original.size();
    }

    public int getFractionCount(){
        return SolveControl.countFractions(original);
    }

    public double getHighestExponent(){
        return SolveControl.findHighestExponent(original);
    }


}
