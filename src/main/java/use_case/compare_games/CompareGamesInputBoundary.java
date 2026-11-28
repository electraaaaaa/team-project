package use_case.compare_games;

/**
 * Input boundary for the Compare Across Games use case.
 */
public interface CompareGamesInputBoundary {

    /**
     * Compares the pity probabilities of two selected games.
     *
     * @param inputData the player's selected games,
     *                  number of pulls, and current pity counts
     */
    void execute(CompareGamesInputData inputData);
}
