package Simplifier;

import java.util.ArrayList;
/**
 * Parent to the Number Structures, Simplifier.Fraction and Simplifier.Nominal
 * Created by rgw3d on 10/9/2014.
 */
public abstract class NumberStructure implements EquationNode {

    /**
     * Treats everything as a fraction
     * @return ArrayList of bottom values
     */
    @Override
    public ArrayList<EquationNode> getBottom(){
        ArrayList<EquationNode> toReturn = new ArrayList<EquationNode>();
        toReturn.add(Nominal.One);
        return toReturn;
    }

    /**
     * Treats everything as a fraction
     * @return ArrayList of top values
     */
    @Override
    public ArrayList<EquationNode> getTop(){
        ArrayList<EquationNode> toReturn = new ArrayList<EquationNode>();
        toReturn.add(Nominal.One);
        return toReturn;
    }

    /**
     * Sets default action
     * @return double -1
     */
    @Override
    public double getNum() {
        return -1;
    }

    /**
     * sets default value
     * @return double -1
     */
    @Override
    public double getVar() {
        return -1;
    }
}
