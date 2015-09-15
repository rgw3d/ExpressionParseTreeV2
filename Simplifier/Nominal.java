package Simplifier;

import java.util.ArrayList;

/**
 * Simplifier.Nominal Simplifier.NumberStructure
 * Most basic form of number storage
 * Created by rgw3d on 10/9/2014.
 */
public class Nominal extends NumberStructure {

    private final double Num;
    private final double Var;

    public static final Nominal Zero = new Nominal(0,0);
    public static final Nominal One = new Nominal(1,0);

    /**
     * Default constructor.
     * Sets nominal to have a value of zero
     */
    public Nominal(){
        Num =0;
        Var =0;
    }

    /**
     * Secondary constructor
     * @param num numerical value
     * @param var variable exponent
     */
    public Nominal(double num, double var){
        Num = num;
        Var = var;
    }

    /**
     * gets the number
     * @return double number
     */
    @Override
    public double getNum() {
        return Num;
    }

    /**
     * gets the variable exponent
     * @return double variable exponent value
     */
    @Override
    public double getVar() {
        return Var;
    }

    /**
     * return getList() value
     * @return ArrayList that has the nominal value.
     */
    @Override
    public ArrayList<EquationNode> getTop() {
        return getList();
    }

    /**
     * Return the nominal wrapped in an ArrayList
     * @return ArrayList that contains the Simplifier.Nominal.
     */
    @Override
    public ArrayList<EquationNode> getList() {
        ArrayList<EquationNode> term = new ArrayList<EquationNode>();
        term.add(this);
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Nominal))
            return false;

        Nominal nominal = (Nominal) o;

        return Double.compare(nominal.Num, Num) == 0 && Double.compare(nominal.Var, Var) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(Num);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Var);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString(){
        String toReturn = "";

        if((int)getNum()==getNum())
            toReturn+=""+(int)getNum();
        else
            toReturn+=""+getNum();

        if(getVar()!=0){
            if((int)getVar()==getVar())

                if(this.equals(new Nominal(1,1)))
                    toReturn = ExpressionParser.variable;

                else if(getVar()==1)
                    toReturn+= ExpressionParser.variable;

                else
                    toReturn+= ExpressionParser.variable+"^"+(int)getVar();
            else
                toReturn+= ExpressionParser.variable+"^"+getVar();
        }

        return toReturn;
    }
}
