import java.util.ArrayList;

/**
 * Used to control everything. essentially it calls getList() starting everything on
 * Has the ability to solve equations and simplify exponents
 * Created by rgw3d on 10/9/2014.
 */
public class ControlOperator extends Operator {
    /**
     * Default constructor
     */
    public ControlOperator() {
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> getList() {
        return Terms.get(0).getList();
    }

    @Override
    public String toString() {
        String output = "";
        for(EquationNode node: getList()){
            output+=node.toString()+"+";
        }
        return output.substring(0,output.length()-1);//remove the last "+" sign then return
    }

}
