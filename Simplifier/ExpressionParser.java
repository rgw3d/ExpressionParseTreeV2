package Simplifier;

/**
 * Used to parse equation.  will return one Simplifier.EquationNode.  This means that
 * Created by rgw3d on 10/9/2014.
 */
public class ExpressionParser {

    public static String variable = "x";//can be any variable

    /**
     * Essentially this will be called recursively, until the only thing left to parse are
     * Nominals/variables which will end parsing.
     * The rules for parsing:
     * Find + outside of Parenthesis, then parse left side of the +, store it in an additionOperator, then store parsed right side of + into the same additionOperator
     * Find multiplication or division outside of Parenthesis, same as above
     * Find ^ operator, same as above
     * Find Parenthesis,
     * Find the actual numbers
     * This results in a tree of Operators, with an additionOperator containing a multiplication on the left, and division on the right
     * This would look like 3*2+4/4.  It is parsed into a tree
     * Parsing continues recursively until actual numbers are found
     *
     * @param input String input from the Input class to be parsed into something we can work with
     */
    public EquationNode ParseEquation(String input) {

        String[] operands = new String[]{"+", "*", "/", "^"};

        for (String op : operands) {//loop through each of the operands once
            //go backwards through the string to search for the operand
            //skip parenthesis

            boolean hasParen = false;

            for (int indx = input.length() - 1; indx > 0; indx--) {
                //loop through backwards
                String eqtIndx = "" + input.charAt(indx);
                //first check to see if it is a closed paren.
                if (eqtIndx.equals(")")) {
                    hasParen = true;
                    indx = skipParen(input, indx);//skips paren and sets indx to its proper "skipped" value
                    continue; //skip the rest of the statements so that indx can be again incremented
                                // this is to prevent indx from being equal to 0 and being decremented
                }
                if (eqtIndx.equals(op)) {//a hit

                    Operator operator;

                    if (op.equals("+"))
                        operator = new AdditionOperator();

                    else if (op.equals("*"))
                        operator = new MultiplicationOperator();

                    else if (op.equals("/"))
                        operator = new DivisionOperator();

                    else // if op.equals("^");
                        operator = new PowerOperator();


                    operator.addTerm(ParseEquation(input.substring(0, indx)));//left side
                    operator.addTerm(ParseEquation(input.substring(indx + 1)));//right side
                    return operator;//everything is recursive
                }
            }
            /*
            gone all the way through the first for loop.
            if hasParen is true, then we parse it
            if  hasParenthesis is false then we return the nominal
            op.equals(^) because it has to be the last iteration of the top for loop
            */
            if (hasParen && op.equals("^")) {//loop inside the parenthesis because otherwise it would have returned an operator
                return ParseEquation(input.substring(1, input.length() - 1));//trim the parenthesis

            } else if (!hasParen && op.equals("^")) {
                ParseNominal parseNominal = new ParseNominal(input);//subclass used here to parse Simplifier.Nominal
                return new Nominal(parseNominal.constantCount, parseNominal.varExponent);
            }

        }
        throw new UnsupportedOperationException("Something went horribly wrong in the parser, this should not happen");

    }

    /**
     * Used to skip over parenthesis -->
     * if input is 3+(3*x)+2
     * if indx is 6
     * then it goes through backwards through the string until it completes the original outside parenthesis.
     * returns 2.  This way parenthesis are the last things to be added
     *
     * @param input string current string being parsed
     * @param indx  int current indx
     * @return int index of the string where the parenthesis has been skipped
     */
    private int skipParen(String input, int indx) {
        int openCount = 0;
        int closedCount = 1;
        while (indx > 0) {
            indx--;
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count

            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count

            if (openCount == closedCount)
                return indx;
        }

        throw new UnsupportedOperationException("Missing Parenthesis Pair");
    }

    /**
     * Used to parse Nominals
     */
    public class ParseNominal {
        public double constantCount = 0;
        public double varExponent = 0;
        private String input;

        /**
         * @param input the string to be parsed
         */
        public ParseNominal(String input) {
            this.input = input;
            parseInput();
        }

        /**
         * Parses and then stores the values of the input string
         */
        private void parseInput() {
            if (input.contains(variable))//if there is a x in it
                varExponent = 1;

            if (input.equals(variable) || input.equals("-"+variable))// if it just a "x"
                input = input.replace(variable, "1"+variable);

            if (input.equals("-"))//another special case where it sends just a negative
                input = input.replace("-", "-1");

            input = input.replace(variable, "");
            constantCount = Double.parseDouble(input);

        }
    }
}

