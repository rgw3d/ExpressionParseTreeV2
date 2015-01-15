package Simplifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * All the math operations used in each Simplifier.Operator class
 * Created by rgw3d on 10/10/2014.
 */
public class MathOperations {

    /**
     * This method will do addition in a list that has two terms
     * @param terms all Simplifier.EquationNode objects to add.  size() must == 2
     */
    public static void additionControl(ArrayList<EquationNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        ArrayList<EquationNode> allTerms = terms.get(0).getList();
        allTerms.addAll(terms.get(1).getList());
        if(sameVariablePower(allTerms)){
            terms.clear();
            terms.add(variableIndependentAddition(allTerms));
        }
        else{
            ArrayList<EquationNode> complexAddition = complexAdditionControl(allTerms);
            terms.clear();//add the results back to the same list, so we clear the old list
            terms.addAll(complexAddition);

        }
    }

    /**
     * Used to control the complex addition (fractions and things with different variables)
     * this is a method because it is used after the multiplication method
     * @param terms list of all terms to be added
     * @return simplified list of all terms that were added
     */
    private static ArrayList<EquationNode> complexAdditionControl(ArrayList<EquationNode> terms) {
        ArrayList<EquationNode> nominalsOnly = new ArrayList<EquationNode>();
        ArrayList<EquationNode> others = new ArrayList<EquationNode>();


        for(EquationNode node: terms){
            if(node instanceof Nominal)
                nominalsOnly.add(node);
            else
                others.add(node);
        }

        if(nominalsOnly.size()>1){
            variableDependentAddition(nominalsOnly);
        }
        if(others.size()>1){
          mixedAddition(others);
        }

        terms.clear();
        terms.addAll(nominalsOnly);
        terms.addAll(others);

        return terms;
    }

    /**
     * Checks to see if the variables of a list of Simplifier.EquationNode objects have the same getVar() value.
     * this would mean that the terms are easy to add together, and simply summing the getNum() values is sufficient
     * @param terms all Simplifier.EquationNode objects to test
     * @return boolean if getVar() is the same in all terms objects.  if any term is not instanceOf Simplifier.Nominal, returns false
     */
    private static boolean sameVariablePower(ArrayList<EquationNode> terms){
        double variablePower = terms.get(0).getVar();
        for(EquationNode node: terms){
            if(!(node instanceof Nominal) || node.getVar()!=variablePower)
                return false;
        }
        return true;
    }

    /**
     * Sums the getNum() value of a list of terms.
     * Assumes sameVariablePower()==true;
     * @param terms all Simplifier.EquationNode objects to add
     * @return Simplifier.Nominal that contains summed value along with correct getVar() value.
     */
    private static Nominal variableIndependentAddition(ArrayList<EquationNode> terms){
        double total = 0;
        for(EquationNode node: terms){
            total+=node.getNum();
        }
        return new Nominal(total,terms.get(0).getVar());
    }

    /**
     * Run if sameVariablePower()==false;
     * Adds numbers if their variable exponents allow them to.
     * @param terms all Simplifier.EquationNode objects to add
     */
    private static void variableDependentAddition(ArrayList<EquationNode> terms) {
        Hashtable<Double, ArrayList<EquationNode>> sortedNominals = new Hashtable<Double, ArrayList<EquationNode>>();
        ArrayList<Double> varsAdded = new ArrayList<Double>();

        for (EquationNode nom : terms) {//add everyone to their respective groups
            Double nomBottom = nom.getVar();//the variable value of the nominal
            try {
                sortedNominals.get(nomBottom).add(nom);//use the key to get the ArrayList<Simplifier.EquationNode> and then add the nominal
            } catch (NullPointerException E) {//key was not mapped to a value
                ArrayList<EquationNode> tmp = new ArrayList<EquationNode>();
                tmp.add(nom);
                sortedNominals.put(nomBottom, tmp);
                varsAdded.add(nomBottom);
            }
        }

        terms.clear();//clear the original list so that we can add on to it
        Collections.sort(varsAdded);
        Collections.reverse(varsAdded);//descending order
        for (Double var : varsAdded) {
            Nominal simp = variableIndependentAddition(sortedNominals.get(var));
            if(simp.getNum()!=0)//num doesn't equal zero.  stops this situation--> 0.0x^2.0 from happening and screwing things up
                terms.add(simp);
        }
    }

    /**
     * This is run if there are things other than Simplifier.Nominal type objects to add together
     * @param terms all Simplifier.EquationNode objects to add
     */
    private static void mixedAddition(ArrayList<EquationNode> terms) {
        //could be a fraction, or could be an Equation Node that wasn't simplified;
        ArrayList<EquationNode> fractions = new ArrayList<EquationNode>();
        ArrayList<EquationNode> others = new ArrayList<EquationNode>();

        for(EquationNode node: terms){
            if(node instanceof Fraction)
                fractions.add(node);
            else
                others.add(node);
        }

        if(fractions.size()>1)
            fractionAddition(fractions);

        terms.clear();
        terms.addAll(fractions);
        terms.addAll(others);
    }

    /**
     * run if there are fractions to add together.
     * Will add fractions together if their bottoms are the same.  (common denominator)
     * @param terms all Simplifier.EquationNode objects to add
     */
    private static void fractionAddition(ArrayList<EquationNode> terms) {
        Hashtable<ArrayList<EquationNode>, ArrayList<EquationNode>> sortedFractions = new Hashtable<ArrayList<EquationNode>, ArrayList<EquationNode>>();
        ArrayList<ArrayList<EquationNode>> fractionBottoms = new ArrayList<ArrayList<EquationNode>>();

        for (EquationNode node : terms) {
            try {
                sortedFractions.get(node.getBottom()).add(node);//from the node.getBottom() arrayList key, add to it the fraction
            } catch (NullPointerException E) {//key was not mapped to a value
                ArrayList<EquationNode> tmp = new ArrayList<EquationNode>();
                tmp.add(node);
                sortedFractions.put(node.getBottom(),tmp);
                fractionBottoms.add(node.getBottom());
            }

        }

        terms.clear();
        for (ArrayList<EquationNode> x : fractionBottoms) {
            additionControl(sortedFractions.get(x));

            if(sortedFractions.get(x).size()!=0 && sortedFractions.get(x).get(0).getTop().size() ==1 && sortedFractions.get(x).get(0).getTop().get(0).getNum() != 0) //if the number does not equal zero
                //noinspection SuspiciousNameCombination
                terms.add(simplifyFractions(new Fraction(sortedFractions.get(x),x)));
            //the if is necessary because sometimes there can be this number--> 0.0x^2.0.  screws up later.  so just don't add it.
        }
    }


    /**
     * Multiplication Control.  This starts the multiplication simplification process
     * @param terms all Simplifier.EquationNode objects to multiply.  size() must == 2
     *
     */
    public static void multiplicationControl(ArrayList<EquationNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());

        if(onlyNumberStructures(terms)){
            NumberStructure product = nominalMultiplication(terms);
            terms.clear();
            terms.add(product);
        }
        else {
            ArrayList<EquationNode> products = multiplyLists(terms.get(0).getList(), terms.get(1).getList());
            terms.clear();
            terms.addAll(complexAdditionControl(products));
        }
    }

    /**
     * Multiplies two NumberStructures together.  treats them as fractions regardless of type, and simplifies after the multiplication
     * @param terms all Simplifier.NumberStructure objects to multiply.  Must be instanceOf Simplifier.NumberStructure
     * @return Simplifier.NumberStructure, either a fraction or nominal
     */
    private static NumberStructure nominalMultiplication(ArrayList<EquationNode> terms) {
        Fraction fraction = new Fraction(Nominal.One);
        for(EquationNode node : terms){
            fraction.Top = multiplyLists(fraction.getTop(), node.getTop());
            fraction.Bottom = multiplyLists(fraction.getBottom(), node.getBottom());
        }
        if(fraction.getTop().size()==1 && fraction.getBottom().size()==1 && fraction.getBottom().get(0).equals(Nominal.One))
            return new Nominal(fraction.getTop().get(0).getNum(), fraction.getTop().get(0).getVar());
        else
            return simplifyFractions(fraction);

    }

    /**
     * Multiplies two arrayLists together.  Left * Right = product
     * left and right could be arrayLists of any amount of EquationNodes
     * @param left the left arrayList (or parenthesis)
     * @param right the right arrayList
     * @return the multiplied product list
     */
    private static ArrayList<EquationNode> multiplyLists(ArrayList<EquationNode> left, ArrayList<EquationNode> right) {
        ArrayList<EquationNode> result = new ArrayList<EquationNode>();

        for(EquationNode leftTerm : left){
            for(EquationNode rightTerm : right){
                if(leftTerm instanceof Nominal && rightTerm instanceof Nominal)
                    result.add(new Nominal(leftTerm.getNum()*rightTerm.getNum(),leftTerm.getVar() + rightTerm.getVar()));
                else if( leftTerm instanceof NumberStructure && rightTerm instanceof NumberStructure) {
                    ArrayList<EquationNode> leftAndRightNodes = new ArrayList<EquationNode>();
                    leftAndRightNodes.add(leftTerm);
                    leftAndRightNodes.add(rightTerm);
                    result.add(nominalMultiplication(leftAndRightNodes));
                }
                else if( leftTerm instanceof Operator || rightTerm instanceof Operator){
                    ArrayList<EquationNode> tmpList = new ArrayList<EquationNode>();
                    tmpList.add(leftTerm);
                    tmpList.add(rightTerm);
                    result.add(new MultiplicationOperator(tmpList));
                }
            }
        }
        return result;
    }

    /**
     * Used to determine the path of the multiplication operation.
     * @param terms all Simplifier.EquationNode objects to test
     * @return boolean true if terms consists of just NumberStructures (fractions or Nominals)
     */
    private static boolean onlyNumberStructures(ArrayList<EquationNode> terms) {
        int numberStructures = 0;

        for(EquationNode node : terms){
            if(node instanceof NumberStructure)
                numberStructures++;
        }

        return numberStructures == 2;
    }

    /**
     * Starts the division process.  This returns a fraction or a nominal, depending on what can be divided
     * @param terms all Simplifier.EquationNode objects to divide.  size() must == 2
     */
    public static void divisionControl(ArrayList<EquationNode> terms) {
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        NumberStructure fraction = new Fraction(terms.get(0).getList(), terms.get(1).getList());
        fraction = simplifyFractions(fraction);
        terms.clear();
        terms.add(fraction);

    }


    /**
     * This function will simplify fractions.  it will take (3x/3) and return x  (new Simplifier.Nominal(1,1))
     * @param fraction the Simplifier.NumberStructure (a Simplifier.Nominal will just be returned) to be simplified
     * @return NumberStructure. the simplified fraction, or can be simplified into a nominal.
     */
    private static NumberStructure simplifyFractions(NumberStructure fraction) {
        if(fraction instanceof Nominal)//if a nominal is sent. cannot be simplified
            return fraction;

        for (EquationNode top : fraction.getTop()) {//simplify underlying fractions
            if (top instanceof Fraction) {//could be a fraction in a fraction
                int tmpIndx = fraction.getTop().indexOf(top);//get indx of fraction
                fraction.getTop().remove(top);//remove the object from the list
                fraction.getTop().add(tmpIndx, simplifyFractions((Fraction) top));//add the "simplified" object back in
            }

        }
        for (EquationNode bot : fraction.getBottom()) {//simplify underlying fractions
            if (bot instanceof Fraction) {//could be a fraction in a fraction
                int tmpIndx = fraction.getBottom().indexOf(bot);//get indx of fraction
                fraction.getBottom().remove(bot);//remove the object from the list
                fraction.getBottom().add(tmpIndx, simplifyFractions((Fraction) bot));//add the "simplified" object back in
            }

        }

        ArrayList<Integer> topDivisors = new ArrayList<Integer>();
        ArrayList<Integer> botDivisors = new ArrayList<Integer>();
        boolean canDivide = findListGCD(fraction.getTop(), topDivisors) && findListGCD(fraction.getBottom(), botDivisors);

        //now both of the lists have the GCD and can be used to find something to divide by.
        if (canDivide) {
            simplifyFractionByGCD(fraction, topDivisors, botDivisors);
        }


        /*
        above loops divided shit.  now its time to remove the variables.  yay dividing by variables.
        how to do this.  we will just get a int that says the lowest var exponent that each have.
        then compare the two and whichever is the smallest we will then just remove that many number of exponents from all of them
        negative exponents do not count.
        wait, we can just do Math.abs(and use those values.  maybe.  i will think about it.
        nope. no negative exponents so far
        */

            boolean canReduceVars = true;
            int topSmallestVar = -1;//non initialized values
            int botSmallestVar = -1;

            for (EquationNode x : fraction.getTop()) {
                if (x instanceof Nominal) {
                    if (topSmallestVar == -1) {
                        topSmallestVar = (int) x.getVar();
                    } else if (topSmallestVar > x.getVar() && x.getVar() >= 0) {
                        topSmallestVar = (int) x.getVar();
                    }
                } else {
                    canReduceVars = false;
                }
            }

            for (EquationNode x : fraction.getBottom()) {
                if (x instanceof Nominal) {
                    if (botSmallestVar == -1) {

                        botSmallestVar = (int) x.getVar();
                    } else if (botSmallestVar > x.getVar() && x.getVar() >= 0) {
                        botSmallestVar = (int) x.getVar();
                    }
                } else {
                    canReduceVars = false;
                }
            }

            if (canReduceVars) {
                int reduceValue;
                if (topSmallestVar >= botSmallestVar)
                    reduceValue = botSmallestVar;
                else
                    reduceValue = topSmallestVar;

                ArrayList<EquationNode> tmpTop = new ArrayList<EquationNode>();
                ArrayList<EquationNode> tmpBot = new ArrayList<EquationNode>();

                for (EquationNode node : fraction.getTop()) {
                    tmpTop.add(new Nominal((node.getNum()), node.getVar() - reduceValue));
                }
                for (EquationNode node : fraction.getBottom()) {
                    tmpBot.add(new Nominal((node.getNum()), node.getVar() - reduceValue));
                }

                fraction.getTop().clear();
                fraction.getTop().addAll(tmpTop);

                fraction.getBottom().clear();
                fraction.getBottom().addAll(tmpBot);
            }

            if (fraction.getTop().size() == 1 && fraction.getBottom().size() == 1 && fraction.getBottom().get(0).equals(Nominal.One)) //if the number does not equal zero
                return new Nominal(fraction.getTop().get(0).getNum(), fraction.getTop().get(0).getVar());
            else if(fraction.getTop().size() == 1 && fraction.getBottom().size() == 1//if only numbers are in the top and bottom.
                    && fraction.getTop().get(0).getVar() == 0 && fraction.getTop().get(0) instanceof Nominal
                    && fraction.getBottom().get(0).getVar() == 0 && fraction.getBottom().get(0) instanceof Nominal)
                return new Nominal(fraction.getTop().get(0).getNum()/fraction.getBottom().get(0).getNum(),0);//return the division of these numbers

            return fraction;


    }

    /**
     * Used to get the GCD list of factors for a list of NumberStructures. used by the simplifyFractions() method
     * @param list the list of NumberStructures to test for a list of GCDs
     * @param divisorList the list of GCDs to be added to.  This list will be cleared if the returned value of this function is false
     * @return boolean of if it was able to generate a list of GCDs
     */
    private static boolean findListGCD(ArrayList<EquationNode> list, ArrayList<Integer> divisorList) {
         /*
            This section either loops through the top of the fraction, or the bottom of it
            determining the greatest common divisor of all the numbers
            Determines that canDevide = false if there is fraction present in the top or bottom of the fraction.
                this also clears the list of Divisors
            Loops through each value while looping though each value.
                get(0) is tested against get(0-(size()-1)) to determine the GCD
         */
        boolean canDivide = true;
        outerLoop:
        for (EquationNode outside : list) {//these should all be nominals.  if not, clear list and break
            int possibleOutsideGCD = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof Nominal) {
                    double outsideNum = outside.getNum();
                    double insideNum = list.get(i).getNum();

                    if ((int)outsideNum == outsideNum && (int)insideNum == insideNum) {//if both are integers
                        if (possibleOutsideGCD == 0) {
                            possibleOutsideGCD = GCD((int) outsideNum, (int) insideNum);
                        } else {
                            if (!((int) insideNum % possibleOutsideGCD == 0)) {//so the factor doesn't work
                                possibleOutsideGCD = GCD((int) outsideNum, (int) insideNum);
                                i = 0;//resetting the loop so that this new gcd value is tested.

                            }//if false, then its good -- do nothing because this factor works.
                        }
                    } else {
                        canDivide = false;
                        divisorList.clear();
                        break outerLoop;
                    }

                } else {
                    canDivide = false;
                    divisorList.clear();
                    break outerLoop;
                }
            }
            //this is where we add the possibleGCD value to the ArrayList if it has survived
            if (possibleOutsideGCD != 1 && possibleOutsideGCD != 0) {
                divisorList.add(possibleOutsideGCD);
            }

        }
        return canDivide;
    }

    /**
     * Take the GCD of both the top and bottom of a fraction and see what simplification can be done
     * @param fraction the fraction to simplify
     * @param topDivisors list of the top
     * @param botDivisors list of the bottom
     */
    private static void simplifyFractionByGCD(NumberStructure fraction, ArrayList<Integer> topDivisors, ArrayList<Integer> botDivisors) {
        Collections.sort(topDivisors);
        Collections.reverse(topDivisors);//reverse because we start with the highest value.
        Collections.sort(botDivisors);
        Collections.reverse(botDivisors);

        botDivisorsLoop:
        for (Integer x : botDivisors) {
            for (Integer y : topDivisors) {
                if (y % x == 0) {//yay it actually works. this means that the greatest divisor has been found.
                    ArrayList<EquationNode> tmpTop = new ArrayList<EquationNode>();
                    ArrayList<EquationNode> tmpBot = new ArrayList<EquationNode>();

                    for (EquationNode node : fraction.getTop()) {
                        tmpTop.add(new Nominal((node.getNum() / x), node.getVar()));
                    }
                    for (EquationNode node : fraction.getBottom()) {
                        tmpBot.add(new Nominal((node.getNum() / x), node.getVar()));
                    }

                    fraction.getTop().clear();
                    fraction.getTop().addAll(tmpTop);

                    fraction.getBottom().clear();
                    fraction.getBottom().addAll(tmpBot);

                    break botDivisorsLoop;
                }
            }
        }
    }

    /**
     * used to find the greatest common divisor (GCD)
     * @param a one of the factors
     * @param b one of the factors
     * @return GCD of a and b
     */
    private static int GCD(int a, int b) {//greatest common divisor
        if (b==0) return a;
        return GCD(b,a%b);
    }


    /**
     * This starts the process of raising something to a power. terms.get(0) is the base.  terms.get(1) is the power
     * @param terms all Simplifier.EquationNode objects to be a part of this operation.  size() must == 2
     */
    public static void powerControl(ArrayList<EquationNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        if(canRaiseToPower(terms)){//if exponent is or can be simplified to one integer.
            if (terms.get(0) instanceof Nominal) {//meaning that the first term is actually something else. like a multiplication operator or something.
                baseIsNominal(terms);
            }
            else
            {
                baseIsOperator(terms);
            }
            
            
        }
        else{//special case. base is nominal.  exponent is real number without variables. but it could be something like 2.2 or 22/10
            if(terms.get(0) instanceof Nominal && terms.get(1).getList().size()==1
                    && terms.get(1).getList().get(0) instanceof NumberStructure
                    && terms.get(1).getList().get(0).getTop().size()==1
                    && terms.get(1).getList().get(0).getBottom().size()==1
                    && terms.get(1).getList().get(0).getTop().get(0).getVar()==0
                    && terms.get(1).getList().get(0).getBottom().get(0).getVar()==0)
                baseIsNominal(terms);
            else {
            PowerOperator powerOperator = new PowerOperator(terms);
                terms.clear();
                terms.add(powerOperator);
            }

        }

    }

    /**
     * Raises the returned list of the operator (the base, terms.get(0)) to the power (terms.get(1).getNum())
     * @param terms the base and the exponent to simplify
     */
    private static void baseIsOperator(ArrayList<EquationNode> terms) {
        int expnt = (int) terms.get(1).getList().get(0).getTop().get(0).getNum();
        //get exponent.  will be cast to an int.
        if(expnt == 0) {
            terms.clear();
            terms.add(Nominal.One);
        }
        else if(expnt <0){//negative exponent
            Fraction flipped = new Fraction(terms.get(0).getList());
            expnt = Math.abs(expnt);

            if(expnt-1==0){//not multiplied.  exponent of 1
                terms.clear();
                terms.add(flipped);

            }
            else {
                MultiplicationOperator[] raisedPowers = new MultiplicationOperator[expnt - 1];
                for (int i = raisedPowers.length - 1; i >= 0; i--) {
                    if (i == raisedPowers.length - 1) {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(flipped);
                        raisedPowers[i].addTerm(flipped);
                    } else {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(raisedPowers[i + 1]);
                        raisedPowers[i].addTerm(flipped);
                    }
                }
                terms.clear();
                terms.addAll(raisedPowers[0].getList());
            }

        }
        else
        {
            if(expnt-1==0){//not multiplied
                ArrayList<EquationNode> simplified = terms.get(0).getList();
                terms.clear();
                terms.addAll(simplified);
            }
            else {

                MultiplicationOperator[] raisedPowers = new MultiplicationOperator[expnt - 1];
                for (int i = raisedPowers.length - 1; i >= 0; i--) {
                    if (i == raisedPowers.length - 1) {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(terms.get(0));
                        raisedPowers[i].addTerm(terms.get(0));
                    } else {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(raisedPowers[i + 1]);
                        raisedPowers[i].addTerm(terms.get(0));
                    }
                }
                terms.clear();
                terms.addAll(raisedPowers[0].getList());
            }
        }
    }

    private static void baseIsNominal(ArrayList<EquationNode> terms) {
        if (terms.get(0).getVar() == 0) {//meaning that there is no variable in the base
            Nominal simplified = new Nominal(Math.pow(terms.get(0).getNum(),
                     terms.get(1).getList().get(0).getTop().get(0).getNum()/terms.get(1).getList().get(0).getBottom().get(0).getNum()), 0);
            terms.clear();
            terms.add(simplified);

        } else {//with a variable in the base
            Nominal simplified = new Nominal(terms.get(0).getNum(),
                    terms.get(0).getVar() * ( terms.get(1).getList().get(0).getTop().get(0).getNum()/terms.get(1).getList().get(0).getBottom().get(0).getNum()));
            terms.clear();
            terms.add(simplified);
        }
    }


    /**
     * This determines if there is a variable in the exponent and if the exponent is an int
     * if this is true, anything can be raised to this exponent
     * @return boolean true if there is no variable in exponent, if exponent is a nominal (or fraction like 3/1) and is an int.
     */
    private static boolean canRaiseToPower(ArrayList<EquationNode> terms){
        if (terms.get(1) instanceof Nominal && terms.get(1).getVar() == 0 && terms.get(1).getNum()==(int)terms.get(1).getNum()) {
            return true;
        } else {
            ArrayList<EquationNode> list = terms.get(1).getList();//get the list for the exponent
            if (list.size() == 1 && list.get(0) instanceof Nominal && list.get(0).getVar() == 0) {
                return true;//if it simplifies to a nominal without a variable
            } else if (list.size() == 1 && list.get(0) instanceof Fraction) {
                NumberStructure simplifiedFraction = simplifyFractions((NumberStructure) list.get(0));
                return (simplifiedFraction.getTop().size() == 1 && simplifiedFraction.getTop().get(0) instanceof Nominal && simplifiedFraction.getTop().get(0).getVar() == 0 &&
                        simplifiedFraction.getBottom().size() == 1 && simplifiedFraction.getBottom().get(0).equals(Nominal.One));
                //this returns true if the fraction is something like (3/1)
            }
            return false;
        }
    }

}
