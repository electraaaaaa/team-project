package use_case.simulate_pulls;

import java.util.Arrays;

/**
 * A statistics handler to find the quartiles of the
 */
public class StatisticsHandler {

    public double[] findQuartiles(double[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        // Clone and sort to avoid modifying the original array
        double[] sorted = data.clone();
        Arrays.sort(sorted);

        int n = sorted.length;
        double q2 = median(sorted, 0, n);
        double q1 = median(sorted, 0, n / 2);
        // If n is odd, skip the middle element for the upper half
        double q3 = median(sorted, (n + 1) / 2, n);

        return new double[]{q1, q2, q3};
    }

    private double median(double[] sorted, int start, int end) {
        int length = end - start;
        int mid = start + length / 2;
        if (length % 2 == 0) {
            return (sorted[mid - 1] + sorted[mid]) / 2.0;
        } else {
            return sorted[mid];
        }
    }
    public double mean(double[] data){
        double sum = 0;
        for (double datanum : data) {
            sum += datanum;
        }
        return sum/data.length;
    }

}
