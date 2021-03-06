package Solver;

import Simplifier.EquationNode;
import Simplifier.MathOperations;
import Simplifier.Nominal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class will store the solutionSet of an equation when it is solved
 * It will have a list of what values the variable could be, and f
 * Created by rgw3d on 1/13/2015.
 */
public class SolvedEquation {



    private Set<EquationNode> solutionSet = new HashSet<EquationNode>();//the solutionSet
    private ArrayList<EquationNode> initEquation = new ArrayList<EquationNode>();//initEquation equation
    private ArrayList<ArrayList<ArrayList<EquationNode>>> factors = new ArrayList<ArrayList<ArrayList<EquationNode>>>();
        //list holds different factors,and the bottom level list holds the actual factors. like this: TopLevel[Group[Factor[x-2],Factor[x+2]], Group[]]

    public SolvedEquation(){

    }

    public SolvedEquation(Set<EquationNode> solutionSet, ArrayList<EquationNode> initEquation){

        this.solutionSet = solutionSet;
        this.initEquation = initEquation;

    }

    public SolvedEquation(Nominal solutionSet, ArrayList<EquationNode> initEquation){

        Set<EquationNode> tmp = new HashSet<EquationNode>();
        tmp.add(solutionSet);
        this.solutionSet = tmp;
        this.initEquation = initEquation;

    }

    public void addSolution(EquationNode solution){
        solutionSet.add(solution);
    }

    public void addSolutions(SolvedEquation solvedEquation){
        for(EquationNode solution: solvedEquation.getSolutionSet()){
            solutionSet.add(solution);
        }
    }

    public void addFactor(ArrayList<ArrayList<EquationNode>> factorGroup){
        factors.add(factorGroup);
    }

    public ArrayList<EquationNode> getInitEquation() {
        return initEquation;
    }

    public Set<EquationNode> getSolutionSet() {
        return solutionSet;
    }

    public ArrayList<ArrayList<ArrayList<EquationNode>>> getFactors(){
        return factors;
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
