package use_case.simulate_pulls;

/**
 * The output boundary for the Simulate Use Case
 */
public interface SimulateOutputBoundary {
    void prepareSuccessView(SimulateOutputData simulateOutputData);

    void prepareFailView(String errormesage);
}
