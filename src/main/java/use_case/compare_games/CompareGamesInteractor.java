package use_case.compare_games;

import entity.PitySystem;
import java.util.Map;

/**
 * Interactor for comparing pity probabilities across games.
 */
public class CompareGamesInteractor
        implements CompareGamesInputBoundary {

    private final Map<String, PitySystem> pitySystems;
    private final CompareGamesOutputBoundary outputBoundary;

    public CompareGamesInteractor(
            Map<String, PitySystem> pitySystems,
            CompareGamesOutputBoundary outputBoundary) {
        this.pitySystems = pitySystems;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CompareGamesInputData inputData) {

        String game1 = inputData.getGame1();
        String game2 = inputData.getGame2();
        int numberOfPulls = inputData.getNumberOfPulls();
        int pity1 = inputData.getPity1();
        int pity2 = inputData.getPity2();

        // Check that two different games are selected.
        if (game1 == null || game2 == null ||
                game1.equalsIgnoreCase(game2)) {
            throw new IllegalArgumentException(
                    "Please select two different games.");
        }

        // Check that the number of pulls is valid.
        if (numberOfPulls <= 0) {
            throw new IllegalArgumentException(
                    "Number of pulls must be positive.");
        }

        // Checks if pity counts are valid.
        if (pity1 < 0 || pity2 < 0 || pity1 >= 90 || pity2 >= 90) {
            throw new IllegalArgumentException(
                    "Current pity must be between 0 and 89.");
        }

        // Find each game's pity system.
        PitySystem system1 = pitySystems.get(game1);
        PitySystem system2 = pitySystems.get(game2);

        if (system1 == null || system2 == null) {
            throw new IllegalArgumentException(
                    "Pity rules are unavailable for a selected game.");
        }

        // Calculate the two probabilities.
        float probability1 = system1.probabilityWithin(pity1, numberOfPulls);

        float probability2 = system2.probabilityWithin(pity2, numberOfPulls);

        // Store the comparison results.
        CompareGamesOutputData outputData =
                new CompareGamesOutputData(
                        game1, game2,
                        probability1, probability2);

        // Send the results to the presenter.
        outputBoundary.prepareSuccessView(outputData);
    }
}
