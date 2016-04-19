package test;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Implementation that calculates primes using the Sieve of Eratosthenes method.
 */
/*
 * This implementation can take advantage of up to three threads by evaluating two adjacent numbers
 * in parallel. Specifically, it filters out:
 * - all multiples of 2
 * - all multiples of 3 and 5 in parallel
 * - all multiples of 7 and 11 in parallel
 * - etc
 * This is based on the assumption that adjacent (sieved) odd numbers will never have a dependency
 * on each other (so it will never be possible to have a situation where, for example, 3 and 9 are
 * evaluated in parallel).
 *
 * It is probable that we can safely evaluate three numbers in parallel, and possibly more
 * by having more special cases (currently only 2 is a special case), but that's a future improvement.
 */
@Component("sieve")
class SievePrimeCalculator implements PrimeCalculator {
    private final ExecutorService executor;

    @Inject
    public SievePrimeCalculator(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public int[] calculate(int from, int to) throws Exception {
        if (from < 2) {
            throw new IllegalArgumentException(Integer.toString(from));
        }

        if (to < 2) {
            throw new IllegalArgumentException(Integer.toString(to));
        }

        if (from > to) {
            int i = from;
            from = to;
            to = i;
        }

        boolean[] values = new boolean[to + 1];

        sieve(2, values);
        int i = 2;
        while (true) {
            int j = next(i, values);
            Future<?> f1 = null, f2 = null;
            if (j != -1) {
                f1 = executor.submit(() -> sieve(j, values));
                int k = next(j, values);
                if (k != -1){
                    f2 = executor.submit(() -> sieve(k, values));
                }
                i = k;
            }

            if (f1 != null) {
                f1.get();
            }
            if (f2 != null) {
                f2.get();
            }

            if (f2 == null) {
                break;
            }
        }

        return populateResults(from, values);
    }

    /*
     * Returns the next candidate number that hasn't fallen through the sieve, or -1 if
     * there is no next number that's below the upper bound
     */
    private int next(int prev, boolean[] values) {
        for (int i = prev + 1; i < values.length; i++) {
            if (!values[i]) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Mark all multiples of the given value as not prime
     */
    private void sieve(int value, boolean[] values) {
        int i = 2;
        while (true) {
            int index = value * i;
            if (index < values.length) {
                values[value * i] = true;
                i++;
            } else {
                break;
            }
        }
    }

    private int[] populateResults(int from, boolean[] values) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = from; i < values.length; i++) {
            if (!values[i]) {
                results.add(Integer.valueOf(i));
            }
        }
        int[] ret = new int[results.size()];
        int i = 0;
        for (Integer result : results) {
            ret[i++] = result.intValue();
        }
        return ret;
    }
}
