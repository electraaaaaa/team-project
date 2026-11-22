package use_case.calculate_probability;

public class ProbabilityOutputData {
    private final double probability;
    // TODO (use case extension 4a)

    public ProbabilityOutputData(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
