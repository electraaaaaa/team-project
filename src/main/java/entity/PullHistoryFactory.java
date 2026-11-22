package entity;

import java.util.List;

public class PullHistoryFactory {
    public PullHistory create(String type, int totalPulls, int currentPity, List<Collectable> pulllist){
        return new PullHistory(type, totalPulls, currentPity, pulllist);
    }
}
