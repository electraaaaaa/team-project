
package use_case.pull_history;

import entity.Game;

/**
 * The Input Data for the Pull History Use Case.
 */
public class PullHistoryInputData {

    private final Game game;
    private final String warpHistoryUrl;

    public PullHistoryInputData(Game game, String warpHistoryUrl) {
        this.game = game;
        this.warpHistoryUrl = warpHistoryUrl;
    }

    public Game getGame() {
        return game;
    }

    public String getWarpHistoryUrl() {
        return warpHistoryUrl;
    }
}
