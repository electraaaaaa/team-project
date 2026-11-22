package interface_adapter.compare_games;

import java.util.Locale;

import use_case.compare_games.CompareGamesOutputBoundary;
import use_case.compare_games.CompareGamesOutputData;

/**
 * Presenter for the Compare Across Games use case.
 */
public class CompareGamesPresenter implements CompareGamesOutputBoundary {

    private final CompareGamesViewModel viewModel;

    public CompareGamesPresenter(CompareGamesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CompareGamesOutputData outputData) {
        String probability1 = String.format(
                Locale.US, "%.2f%%", outputData.getProbability1() * 100);

        String probability2 = String.format(
                Locale.US, "%.2f%%", outputData.getProbability2() * 100);

        viewModel.setResults(
                outputData.getGame1(),
                outputData.getGame2(),
                probability1,
                probability2
        );
    }
}
