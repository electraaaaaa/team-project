package use_case.simulate_pulls;

/**
 * the output data for the simulate use case
 */
public class SimulateOutputData {
    private final float[] results;
    private final float mean;
    private final float q1;
    private final float q2;     //also the median
    private final float q3;

    public SimulateOutputData(float[] results, float mean, float q1, float q2, float q3){
        this.results = results;
        this.mean = mean;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    public float[] getResults() { return results; }

    public float getMean() { return mean; }

    public float getQ1() { return q1; }

    public float getQ2() { return q2; }

    public float getQ3() { return q3; }
}
