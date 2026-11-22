package entity;

import java.util.List;

public class PullHistoryFactory {
    public PullHistory create(Game game, int totalPulls, int currentPity, List<Collectable> pulllist){
        return new PullHistory(game, totalPulls, currentPity, pulllist);
    }
}
