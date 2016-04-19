package test;

import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class SievePrimeCalculatorTest {
    private SievePrimeCalculator calculator = new SievePrimeCalculator(Executors.newFixedThreadPool(10));

    @Test
    public void testNegatives() throws Exception {
        try {
            calculator.calculate(-1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testZero() throws Exception {
        try {
            calculator.calculate(0, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testOne() throws Exception {
        try {
            calculator.calculate(1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testTwo() throws Exception {
        int[] res = calculator.calculate(2, 2);
        assertArrayEquals(new int[] { 2 }, res);
    }

    @Test
    public void testLow() throws Exception {
        int[] res1 = calculator.calculate(5, 19);
        int[] res2 = calculator.calculate(19, 5);
        assertArrayEquals(new int[] { 5, 7, 11, 13, 17, 19 }, res1);
        assertArrayEquals(res1, res2);
    }

    @Test
    public void testStartWithEvenNumber() throws Exception {
        int[] res = calculator.calculate(4, 6);
        assertArrayEquals(new int[] { 5 }, res);
    }

    @Test
    public void testNoResults() throws Exception {
        int[] res = calculator.calculate(8, 10);
        assertArrayEquals(new int[0], res);
    }

    @Test
    public void testHigh() throws Exception {
        // No idea how to verify each figure
        long l1 = System.currentTimeMillis();
        int[] res = calculator.calculate(5, 10000000);
        long l2 = System.currentTimeMillis();
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
            System.out.println(", ");
        }
        System.out.println("Completed in " + (l2 - l1) + " milliseconds");
    }
}
