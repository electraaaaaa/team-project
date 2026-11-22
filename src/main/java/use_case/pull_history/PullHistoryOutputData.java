package use_case.pull_history;

import entity.GamePullHistories;

/**
 * The Output Data for the Pull History Use Case.
 */
public class PullHistoryOutputData {

    private final GamePullHistories gamePullHistories;

    public PullHistoryOutputData(GamePullHistories gamePullHistories) {
        this.gamePullHistories = gamePullHistories;
    }

    public GamePullHistories getGamePullHistories() {
        return gamePullHistories;
    }
}
