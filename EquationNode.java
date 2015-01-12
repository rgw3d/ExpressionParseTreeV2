import java.util.ArrayList;

/**
 * Parent interface to everything in this project
 * provides methods for all subclasses
 * Created by rgw3d on 10/9/2014.
 */
public interface EquationNode {
    /**
     * gets the number value for the EquationNode
     * @return double number value
     */
    public double getNum();//returns the number from the operation

    /**
     * gets the variable value for the EquationNode
     * @return double Var value
     */
    public double getVar();//returns the variable from the operation

    /**
     * Get the list of simplified terms,
     * @return simplified list of operation in Nominals and Fractions
     */
    public ArrayList<EquationNode> getList();

    /**
     * Treats everything as a fraction. Gets the "top".
     * Usually the top is just the getList() method.
     * @return ArrayList of simplified "top" terms
     */
    public ArrayList<EquationNode> getTop();

    /**
     * Treats everything as a fraction. Gets the "bottom".
     * Usually the bottom is just a nominal with getNum()=1 and getVar()=0
     * @return ArrayList of simplified "bottom" terms
     */
    public ArrayList<EquationNode> getBottom();
}
