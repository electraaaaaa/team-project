package use_case.simulate_pulls;

public interface SimulateOutputBoundary {
    void prepareSuccessView(SimulateOutputData simulateOutputData);

    void prepareFailView(String errormesage);
}
