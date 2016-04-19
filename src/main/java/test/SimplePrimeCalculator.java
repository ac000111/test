package test;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;

/**
 * An implementation that simply tests each number in the range by testing whether it is
 * - even (and not two)
 * - odd and not divisible by any number from 3 to its square root
 *
 * Each number in the range may be tested within its own thread. The threading policy is
 * dictated by the Executor passed in to the constructor.
 */
@Component("simple")
class SimplePrimeCalculator implements PrimeCalculator {
    private final Executor executor;

    /**
     * Constructs a new instance
     * @param executor governs the threading policy used for calculations
     */
    @Inject
    public SimplePrimeCalculator(Executor executor) {
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
            // flip them around so that from is less than to
            int i = from;
            from = to;
            to = i;
        } else if (from == to) {
            if (returnIfPrime(from) != null) {
                return new int[] { from };
            } else {
                return new int[0];
            }
        }

        ArrayList<Integer> results = new ArrayList<>();
        // we only want to evaluate odd numbers, so by ensuring we start on an odd number we can continually increment by 2
        if (from == 2) {
            results.add(Integer.valueOf(2));
            from = 3;
        } else if (from % 2 == 0) {
            from++;
        }

        int numFutures = 0;

        ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<>(executor);
        for (int i = from; i <= to; i += 2) {
            final int j = i;
            ecs.submit(() -> returnIfPrime(j));
            numFutures++;
        }

        for (int i = 0; i < numFutures; i++) {
            try {
                Integer result = ecs.take().get();
                if (result != null) {
                    results.add(result);
                }
            } catch (ExecutionException e) {
                if (e.getCause() instanceof Exception) {
                    throw (Exception) e.getCause();
                } else {
                    throw (Error) e.getCause();
                }
            }
        }

        int[] ret = toArray(results);
        Arrays.sort(ret);
        return ret;
    }

    private int[] toArray(ArrayList<Integer> results) {
        int[] ret = new int[results.size()];
        int i = 0;
        for (Integer result : results) {
            ret[i++] = result.intValue();
        }
        return ret;
    }

    private Integer returnIfPrime(int n) {
        if (n == 2) {
            return Integer.valueOf(2);
        }

        //check if n is a multiple of 2
        if (n % 2 == 0) {
            return null;
        }
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return null;
            }
        }
        return Integer.valueOf(n);
    }
}
