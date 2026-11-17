package entity;

import java.util.List;

public class PullHistory {
    private final Game game;
    private final int totalPulls;
    private final int currentPity;
    private final List<Collectable> pullList;
    public PullHistory(Game game, int totalPulls, int currentPity, List<Collectable> pullList){
        this.game = game;
        this.totalPulls = totalPulls;
        this.currentPity = currentPity;
        this.pullList = pullList;
    }
    public void addPull(Collectable pull){
        this.pullList.add(pull);
    }
    public int getCurrentPity(Banner bannerType){
        //TODO implement get current Pity
    }

    public int countByRarity(int rarity){
        int count = 0;
        for (int i=0; i< pullList.size();i++){
            if(pullList.get(i).getRarity() == rarity) {
                count += 1;
            }
        }
        return count;
    }
}
