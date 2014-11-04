import java.util.ArrayList;

/**
 * Simplifies all exponents
 * Created by rgw3d on 10/9/2014.
 */
public class PowerOperator extends Operator {
    /**
     * Default constructor
     */
    public PowerOperator() {
    }

    /**
     * Secondary constructor
     *
     * @param terms ArrayList to set as the Terms
     */
    public PowerOperator(ArrayList<EquationNode> terms) {
        super(terms);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> getList() {
        if(!evaluated){
            MathOperations.powerControl(Terms);
            evaluated = true;
        }
            return Terms;

    }
}
