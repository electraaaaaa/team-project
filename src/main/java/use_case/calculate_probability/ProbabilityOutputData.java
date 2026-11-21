package use_case.calculate_probability;

public class ProbabilityOutputData {
    private final double probability;
    // TODO (use case extension 4a): add whether a 5-star is guaranteed and the pulls left to hard pity.

    public ProbabilityOutputData(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
