
package interface_adapter.pull_history;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the pull histories of one game.
 */
public class GamePullHistoryState {

    private String gameName = "";
    private List<PullHistoryState> pullHistories = new ArrayList<>();
    private String errorMessage = "";

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<PullHistoryState> getPullHistories() {
        return pullHistories;
    }

    public void setPullHistories(List<PullHistoryState> pullHistories) {
        this.pullHistories = new ArrayList<>(pullHistories);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
