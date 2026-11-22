package use_case.compare_games;

/**
 * Output boundary for the Compare Across Games use case.
 */
public interface CompareGamesOutputBoundary {

    /**
     * Presents the results of comparing two games.
     *
     * @param outputData the calculated probabilities
     *                   for both games
     */
    void prepareSuccessView(CompareGamesOutputData outputData);
}
