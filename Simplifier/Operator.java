package Simplifier;

import java.util.ArrayList;

/**
 * Parent to all of the Simplifier.Operator classes
 * Deals with getVar() getNum() methods that are not used in the operator classes.
 * Provides addTerm()
 * Created by rgw3d on 10/9/2014.
 */
public abstract class Operator implements EquationNode {
    final ArrayList<EquationNode> Terms;
    boolean evaluated = false;

    /**
     * Default constructor
     */
    Operator(){
        Terms  = new ArrayList<EquationNode>();
    }

    /**
     * Secondary constructor
     *
     * @param terms ArrayList to set as the Terms
     */
    Operator(ArrayList<EquationNode> terms){
        Terms = terms;
    }

    /**
     * Extend by all of the Simplifier.Operator classes. default action for adding a term to the list
     * @param node Simplifier.EquationNode to add
     */
    public void addTerm(EquationNode node){
        Terms.add(node);
    }

    /**
     * By default, returns -1
     * @return double -1
     */
    public double getNum() {
        return -1;
    }

    /**
     * By default, just returns -1
     * @return double -1
     */
    public double getVar() {
        return -1;
    }

    /**
     * Default action of the getTop(), returns the list.  essentially just getList()
     * @return ArrayList that is also returned from getList()
     */
    public ArrayList<EquationNode> getTop(){
        return getList();
    }

    /**
     * Default action of the getBottom(), returns a nominal(1,) wrapped in an ArrayList
     * @return ArrayList that contains a Simplifier.Nominal(1,0)
     */
    public ArrayList<EquationNode> getBottom(){
        ArrayList<EquationNode> term = new ArrayList<EquationNode>();
        term.add(Nominal.One);
        return term;
    }

}
