package use_case.calculate_probability;

public class ProbabilityOutputData {
    private final double probability;
    private final boolean guaranteed;
    private final int pullsToHardPity;

    public ProbabilityOutputData(double probability, boolean guaranteed, int pullsToHardPity) {
        this.probability = probability;
        this.guaranteed = guaranteed;
        this.pullsToHardPity = pullsToHardPity;
    }

    public double getProbability() {
        return probability;
    }

    public boolean isGuaranteed() {
        return guaranteed;
    }

    public int getPullsToHardPity() {
        return pullsToHardPity;
    }
}
