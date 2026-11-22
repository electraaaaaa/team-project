package entity;

import java.util.HashMap;
import java.util.Map;

public class GamePullHistories {

    private final Game game;
    private final Map<String, PullHistory> histories;

    public GamePullHistories(Game game, Map<String, PullHistory> histories) {
        this.game = game;
        this.histories = histories;
    }

    public void addHistory(PullHistory history) {
        histories.put(history.getType(), history);
    }

    public PullHistory getHistory(String type) {
        return histories.get(type);
    }

    public Map<String, PullHistory> getAllHistories() {
        return new HashMap<>(histories);
    }

    public Game getGame() { return game; }
}
