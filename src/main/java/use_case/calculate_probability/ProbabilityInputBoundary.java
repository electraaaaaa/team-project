package use_case.calculate_probability;

public interface ProbabilityInputBoundary {
    /**
     * Executes the calculate probability use case.
     * @param probabilityInputData the input data
     */
    void execute(ProbabilityInputData probabilityInputData);
}
