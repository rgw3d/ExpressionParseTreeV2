import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Used to test methods in ExpressionSanitizer
 * Created by rgw3d on 9/19/2015.
 */
public class ExpressionSanitizerTest extends TestCase {
    @Test
    public void testInferMultiplication() {
        String[] testInputs = {"2(x-1)", "(4+3)5(5+1)"};
        String[] outputs = {"2*(x-1)","(4+3)*5*(5+1)"};
        for(int i = 0; i<testInputs.length; i++){
            String functionReturn = ExpressionSanitizer.inferMultiplication(testInputs[i]);
            System.out.println("@Test inferMultiplication(): " + testInputs[i] + " = " + functionReturn);
            assertEquals(outputs[i], functionReturn);
        }

    }

    @Test
    public void testReformatInput() throws Exception {

        String[] testInputs = {"2  *3(x-1)", "4*5", "5(5(5(5(5(5)5)5)5)5)5"};
        String[] outputs = {"2*3*(x+-1)","4*5", "5*(5*(5*(5*(5*(5)*5)*5)*5)*5)*5"};


        for(int i = 0; i<testInputs.length; i++){
            String functionReturn = ExpressionSanitizer.reformatInput(testInputs[i]);
            System.out.println("@Test reformatInput(): " + testInputs[i] + " = " + functionReturn);
            assertEquals(outputs[i], functionReturn);
        }
    }

}