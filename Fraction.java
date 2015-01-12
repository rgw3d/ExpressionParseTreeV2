import java.util.ArrayList;

/**
 * Fraction NumberStructure used to represent rational fractions.
 * Created by rgw3d on 10/9/2014.
 */
public class Fraction extends NumberStructure {

    public ArrayList<EquationNode> Top = new ArrayList<EquationNode>();
    public ArrayList<EquationNode> Bottom = new ArrayList<EquationNode>();

    /**
     * Default Constructor
     */
    @SuppressWarnings("UnusedDeclaration")
    public Fraction(){
    }

    /**
     * secondary constructor
     * @param top ArrayList to set as the top value
     * @param bottom ArrayList to set as bottom value
     */
    public Fraction(ArrayList<EquationNode> top, ArrayList<EquationNode> bottom) {

        //MathOperations.removeZeros(top);
        if(top.size() == 0){
            top.add(new Nominal(0,0));
        }
        Top = top;
        //MathOperations.removeZeros(bottom);
        if(bottom.size() ==0){//if it is zero, then the zero has been removed and it is a divide by zero error
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;

    }

    /**
     * Secondary Constructor
     * @param top ArrayList to set as the top value
     * @param bottom NumberStructure (fraction or nominal) to set as bottom
     */
    @SuppressWarnings("UnusedDeclaration")
    public Fraction(ArrayList<EquationNode> top, NumberStructure bottom) {
        Top = top;
        if(bottom.equals(new Nominal())){
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom.add(bottom);
    }

    /**
     * Secondary Constructor
     * @param top NumberStructure (fraction or nominal) to set as top
     * @param bottom ArrayList to set as bottom value
     */
    public Fraction(NumberStructure top, ArrayList<EquationNode> bottom) {
        Top.add(top);
        if(bottom.size() ==0){//if it is zero, then the zero has been removed and it is a divide by zero error
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;
    }

    /**
     * Secondary Constructor
     * @param top NumberStructure (fraction or nominal) to set as top
     * @param bottom NumberStructure (fraction or nominal) to set as bottom
     */
    public Fraction(NumberStructure top, NumberStructure bottom) {
        Top.add(top);
        if(bottom.equals(new Nominal())){
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom.add(bottom);
    }


    /**
     * returns the Top list
     * @return ArrayList of top
     */
    @Override
    public ArrayList<EquationNode> getList() {
        return getTop();
    }

    /**
     * Retuns the Top list
     * Also cleans the returned value by removing zeros
     * @return ArrayList of the top values
     */
    @Override
    public ArrayList<EquationNode> getTop() {
        return Top;
    }

    /**
     * returns the bottom list
     * @return ArrayList of the bottom values
     */
    @Override
    public ArrayList<EquationNode> getBottom() {
        return Bottom;
    }

    /**
     *
     * @return String output value
     */
    @Override
    public String toString(){
        String toReturn ="";
        String top = "";
        String bot = "";

        if(Bottom.size() ==1 && Bottom.get(0).equals(Nominal.One)) {//if denominator is 1
            for (EquationNode fract : Top) {
                if (top.equals("")) {
                    top += fract.toString();
                } else {
                    top += "+" + fract.toString();
                }
            }
        }
        else {
            top = "((";
            for (EquationNode fract : Top) {
                if(top.equals("((")){
                    top += fract.toString();
                }
                else{
                    top+= "+"+fract.toString();
                }
            }
            top+=")/(";
            for (EquationNode fract : Bottom) {
                if(bot.equals("")){
                    bot += fract.toString();
                }
                else{
                    bot+= "+"+fract.toString();
                }
            }
            bot += "))";

        }
        toReturn+=top;
        toReturn+=bot;
        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        return Bottom.equals(fraction.Bottom) && Top.equals(fraction.Top);

    }

    @Override
    public int hashCode() {
        int result = Top.hashCode();
        result = 31 * result + Bottom.hashCode();
        return result;
    }
}
