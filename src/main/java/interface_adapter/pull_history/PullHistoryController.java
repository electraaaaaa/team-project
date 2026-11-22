package interface_adapter.pull_history;

import entity.Game;
import use_case.pull_history.PullHistoryInputBoundary;
import use_case.pull_history.PullHistoryInputData;

/**
 * Controller for the Pull History Use Case.
 */
public class PullHistoryController {

    private final PullHistoryInputBoundary pullHistoryUseCase;

    public PullHistoryController(PullHistoryInputBoundary pullHistoryUseCase) {
        this.pullHistoryUseCase = pullHistoryUseCase;
    }

    /**
     * Executes the pull history use case.
     *
     * @param game the game whose pull history is being imported
     * @param warpHistoryUrl the user's Warp History URL
     */
    public void execute(Game game, String warpHistoryUrl) {

        PullHistoryInputData inputData =
                new PullHistoryInputData(game, warpHistoryUrl);

        pullHistoryUseCase.execute(inputData);
    }
}
