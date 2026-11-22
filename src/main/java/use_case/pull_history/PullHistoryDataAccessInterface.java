package use_case.pull_history;

import entity.Game;
import entity.GamePullHistories;

/**
 * DAO interface for the Pull History Use Case.
 */
public interface PullHistoryDataAccessInterface {

    /**
     * Retrieves the pull histories for the given game.
     *
     * @param game the game whose pull histories are being retrieved
     * @param pullHistoryUrl the user's pull History URL
     * @return the retrieved pull histories
     */
    GamePullHistories getGamePullHistory(Game game, String pullHistoryUrl);
}
