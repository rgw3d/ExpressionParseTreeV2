package Solver;

import Simplifier.EquationNode;
import Simplifier.Fraction;
import Simplifier.Nominal;

import java.util.ArrayList;

/**
 * This class will store the results of an equation when it is solved
 * It will have a list of what values the variable could be, and f
 * Created by rgw3d on 1/13/2015.
 */
public class SolvedEquation {

    public ArrayList<EquationNode> results;
    public ArrayList<EquationNode> eqt;

    public SolvedEquation(ArrayList<EquationNode> results, ArrayList<EquationNode> eqt){

        this.results = results;
        this.eqt = eqt;

    }

    public int getLength(){
        return eqt.size();
    }

    public int getFractionCount(){
        return SolveChoice.countFractions(eqt);
    }

    public double getHighestExponent(){
        return SolveChoice.findHighestExponent(eqt);
    }


}
