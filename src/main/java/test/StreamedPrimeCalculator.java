package test;

import java.io.PipedWriter;

/**
 * An implementation that writes values as soon as they're calculated to the given writer, without
 * waiting for all calculations to be completed.
 */
public class StreamedPrimeCalculator implements PrimeCalculator {
    private PipedWriter writer;

    @Override
    // TODO
    public int[] calculate(int from, int to) throws Exception {
        throw new UnsupportedOperationException();
    }
}
