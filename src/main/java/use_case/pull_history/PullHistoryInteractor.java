package use_case.pull_history;

import entity.GamePullHistories;

/**
 * The Pull History Interactor.
 */
public class PullHistoryInteractor implements PullHistoryInputBoundary {

    private final PullHistoryDataAccessInterface pullHistoryDataAccessObject;
    private final PullHistoryOutputBoundary pullHistoryPresenter;

    public PullHistoryInteractor(
            PullHistoryDataAccessInterface pullHistoryDataAccessObject,
            PullHistoryOutputBoundary pullHistoryPresenter) {

        this.pullHistoryDataAccessObject = pullHistoryDataAccessObject;
        this.pullHistoryPresenter = pullHistoryPresenter;
    }

    @Override
    public void execute(PullHistoryInputData inputData) {

        try {
            GamePullHistories histories =
                    pullHistoryDataAccessObject.getGamePullHistory(
                            inputData.getGame(),
                            inputData.getWarpHistoryUrl()
                    );

            PullHistoryOutputData outputData =
                    new PullHistoryOutputData(histories);

            pullHistoryPresenter.prepareSuccessView(outputData);

        }
        catch (RuntimeException exception) {
            pullHistoryPresenter.prepareFailView(
                    inputData.getGame(),
                    "Failed to import pull history: " + exception.getMessage()
            );
        }

    }
}
