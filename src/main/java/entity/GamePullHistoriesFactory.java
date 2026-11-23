package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePullHistoriesFactory {
    public GamePullHistories create(Game game) {
        return new GamePullHistories(game, new HashMap<>());
    }
}
