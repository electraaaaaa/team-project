package interface_adapter.compare_games;

import use_case.compare_games.CompareGamesInputBoundary;
import use_case.compare_games.CompareGamesInputData;

/**
 * Controller for the Compare Across Games use case.
 */
public class CompareGamesController {

    private final CompareGamesInputBoundary interactor;

    public CompareGamesController(
            CompareGamesInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Passes the player's selections to the use case.
     */
    public void execute(String game1, String game2,
                        int numberOfPulls, int pity1, int pity2) {

        CompareGamesInputData inputData =
                new CompareGamesInputData(
                        game1, game2, numberOfPulls, pity1, pity2);

        interactor.execute(inputData);
    }
}
