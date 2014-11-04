import java.util.ArrayList;

/**
 * Addition Operator with all the necesary actions to deal with addition
 * Created by rgw3d on 10/9/2014.
 */
public class AdditionOperator extends Operator {
    /**
     * Default constructor
     */
    public AdditionOperator() {
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> getList() {
        if(!evaluated) {
            MathOperations.additionControl(Terms);
            evaluated = true;
        }
        return Terms;
    }

    @Override
    public String toString() {
        return Terms.get(0).toString()+"+"+Terms.get(1).toString();
    }
}
