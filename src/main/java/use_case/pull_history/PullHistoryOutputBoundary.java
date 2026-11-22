package use_case.pull_history;

import entity.Game;

/**
 * Output Boundary for the Pull History Use Case.
 */
public interface PullHistoryOutputBoundary {

    /**
     * Prepares the success view for the Pull History Use Case.
     *
     * @param outputData the output data
     */
    void prepareSuccessView(PullHistoryOutputData outputData);

    /**
     * Prepares the failure view for the Pull History Use Case.
     *
     * @param game the game whose import failed
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(Game game, String errorMessage);
}
