
package interface_adapter.pull_history;

import entity.Game;
import entity.GamePullHistories;
import entity.PullHistory;
import use_case.pull_history.PullHistoryOutputBoundary;
import use_case.pull_history.PullHistoryOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the Pull History Use Case.
 */
public class PullHistoryPresenter implements PullHistoryOutputBoundary {

    private final PullHistoryViewModel viewModel;

    public PullHistoryPresenter(PullHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(PullHistoryOutputData outputData) {

        GamePullHistories gameHistories =
                outputData.getGamePullHistories();

        GamePullHistoryState gameState = new GamePullHistoryState();

        gameState.setGameName(gameHistories.getGame().getName());

        List<PullHistoryState> pullHistoryStates = new ArrayList<>();

        for (PullHistory history : gameHistories.getAllHistories().values()) {

            PullHistoryState pullHistoryState = new PullHistoryState();

            pullHistoryState.setType(history.getType());
            pullHistoryState.setTotalPulls(
                    String.valueOf(history.getTotalPulls()));
            pullHistoryState.setCurrentPity(
                    String.valueOf(history.getCurrentPity()));

            pullHistoryStates.add(pullHistoryState);
        }

        gameState.setPullHistories(pullHistoryStates);

        // Clear any previous error for this game.
        gameState.setErrorMessage("");

        viewModel.updateGameState(gameState);
        viewModel.fireStateChanged();
    }

    @Override
    public void prepareFailView(Game game, String errorMessage) {

        GamePullHistoryState gameState = findGameState(game);

        if (gameState == null) {
            gameState = new GamePullHistoryState();
            gameState.setGameName(game.getName());
        }

        // Preserve existing pull histories and update only the error.
        gameState.setErrorMessage(errorMessage);

        viewModel.updateGameState(gameState);
        viewModel.fireStateChanged();
    }

    /**
     * Finds the existing state for a game.
     *
     * @param game the game to find
     * @return the existing state, or null if none exists
     */
    private GamePullHistoryState findGameState(Game game) {

        for (GamePullHistoryState state : viewModel.getStates()) {
            if (state.getGameName().equals(game.getName())) {
                return state;
            }
        }

        return null;
    }
}
