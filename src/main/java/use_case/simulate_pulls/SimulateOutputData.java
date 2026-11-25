package use_case.simulate_pulls;

import org.jfree.chart.JFreeChart;

/**
 * the output data for the simulate use case
 */
public class SimulateOutputData {
    private final JFreeChart chart;
    private final double mean;
    private final double q1;
    private final double q2;     //also the median
    private final double q3;

    public SimulateOutputData(JFreeChart chart, double mean, double q1, double q2, double q3){
        this.chart = chart;
        this.mean = mean;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    public JFreeChart getChart() { return chart; }

    public double getMean() { return mean; }

    public double getQ1() { return q1; }

    public double getQ2() { return q2; }

    public double getQ3() { return q3; }
}
