package test;

/**
 * Implementations of this interface calculate prime numbers
 */
public interface PrimeCalculator {
    /**
     * Calculates all prime numbers that are between the two given values
     * @param from the lower bound (inclusive), which must be greater than 1
     * @param to the upper bound (inclusive), which must be greater than 1
     * @return the array of zero or more prime numbers that fall between the two values
     * @throws IllegalArgumentException if either the lower or upper bounds are invalid
     * @throws Exception if anything else went wrong
     */
    public int[] calculate(int from, int to) throws Exception;
}
