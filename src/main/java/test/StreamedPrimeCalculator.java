package test;

import java.util.function.Consumer;

/**
 * An implementation that calls the given callback function as soon as a prime number is found,
 * waiting for all calculations to be completed.
 * <br>
 * Each callback invocation may be performed on a different thread, so the callback handler must
 * be thread-safe.
 */
public class StreamedPrimeCalculator implements PrimeCalculator {
    private Consumer<Integer> callback;

    @Override
    // TODO
    public int[] calculate(int from, int to) throws Exception {
        throw new UnsupportedOperationException();
    }
}
