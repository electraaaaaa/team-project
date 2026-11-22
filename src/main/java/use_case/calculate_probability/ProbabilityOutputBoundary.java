package use_case.calculate_probability;

public interface ProbabilityOutputBoundary {
    /**
     * Prepares the success view for the Calculate Probability Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ProbabilityOutputData outputData);

    /**
     * Prepares the failure view for the Calculate Probability Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
