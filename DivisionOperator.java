import java.util.ArrayList;

/**
 * Division Operator.
 * This operator handles all the division and makes fractions
 * Created by rgw3d on 10/9/2014.
 */
public class DivisionOperator extends Operator {
    /**
     * Default constructor
     */
    public DivisionOperator() {
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> getList() {
        if(!evaluated) {
            MathOperations.divisionControl(Terms);
            evaluated = true;
        }
        return Terms;
    }
}
