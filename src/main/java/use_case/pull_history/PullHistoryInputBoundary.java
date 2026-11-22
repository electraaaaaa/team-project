package use_case.pull_history;

/**
 * Input Boundary for the Pull History Use Case.
 */
public interface PullHistoryInputBoundary {

    /**
     * Executes the pull history use case.
     * @param inputData the input data
     */
    void execute(PullHistoryInputData inputData);
}
