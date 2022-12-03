package integration;

import org.junit.Test;
import org.mariuszgromada.math.mxparser.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MXparserIntegrationTest {

    @Test
    public void userDefinedBinaryFunction_evaluatesToCorrectValue() {
        Function f = new Function("f(x,y) = x+y");
        final double DELTA = 0;
        assertEquals(3, f.calculate(1, 2), DELTA);
        assertEquals(3, f.calculate(2, 1), DELTA);
        assertEquals(1, f.calculate(0, 1), DELTA);
        assertEquals(1, f.calculate(1, 0), DELTA);
        assertEquals(0, f.calculate(0, 0), DELTA);
    }

    @Test
    public void exponentialFunction_evaluatesToCorrectValue() {
        Function f = new Function("f(x) = exp(x)");
        assertEquals(1, f.calculate(0), 0);
        assertEquals(2.71828182845904523536028747, f.calculate(1), 0.000001);
    }

    @Test
    public void logarithmFunction_evaluatesToCorrectValue() {
        Function f = new Function("f(x) = log(2,x)");
        final double DELTA = 0;
        assertEquals(3, f.calculate(8), DELTA);
        assertEquals(4, f.calculate(16), DELTA);
    }

    @Test
    public void testIffKeyword_evaluatesToCorrectValue() {
        // signum
        Function f = new Function("f(x) = iff(x<0, -1; x==0, 0; x>0, 1)");
        assertEquals(-1, f.calculate(-3), 0);
        assertEquals(0, f.calculate(0), 0);
        assertEquals(1, f.calculate(3), 0);
    }

    @Test
    public void testInvalidSyntax_shouldRecognizeInvalidSyntax() {
        Function f = new Function("f(x = y");
        assertFalse(f.checkSyntax());
    }

    @Test
    public void testUserDefinedFunctionIsBinary_shouldRecognizeBinaryFunction() {
        Function f = new Function("f(x,y) = x+y");
        assertEquals(2, f.getArgumentsNumber());
    }

    @Test
    public void testUndefinedArgumentInUserDefinedFunction_shouldRecognizeUndefinedArgument() {
        Function f = new Function("f(x,y) = x+z");
        assertFalse(f.checkSyntax());
    }

}
