package interface_adapter.pull_history;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for the Pull History Use Case.
 */
public class PullHistoryViewModel {

    public static final String VIEW_NAME = "pull history";

    private List<GamePullHistoryState> states = new ArrayList<>();

    private final List<PullHistoryViewModelListener> listeners =
            new ArrayList<>();

    public List<GamePullHistoryState> getStates() {
        return new ArrayList<>(states);
    }

    public void setStates(List<GamePullHistoryState> states) {
        this.states = new ArrayList<>(states);
    }

    /**
     * Updates the pull history state for a game.
     * If the game is not already present, adds it.
     *
     * @param newState the updated game state
     */
    public void updateGameState(GamePullHistoryState newState) {

        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getGameName().equals(newState.getGameName())) {
                states.set(i, newState);
                return;
            }
        }

        states.add(newState);
    }

    public void addListener(PullHistoryViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners that the state has changed.
     */
    public void fireStateChanged() {
        for (PullHistoryViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
