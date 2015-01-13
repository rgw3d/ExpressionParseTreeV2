package Simplifier;

import java.util.ArrayList;

/**
 * Multiplication operator with all the actions to deal with Multiplication
 * Created by rgw3d on 10/9/2014.
 */
public class MultiplicationOperator extends Operator {
    /**
     * Default constructor
     */
    public MultiplicationOperator() {
    }

    /**
     * Secondary constructor
     *
     * @param terms list of terms to simplify
     */
    public MultiplicationOperator(ArrayList<EquationNode> terms) {
        super(terms);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> getList() {
        if (!evaluated) {
            MathOperations.multiplicationControl(Terms);
            evaluated = true;
        }
        return Terms;
    }
}
