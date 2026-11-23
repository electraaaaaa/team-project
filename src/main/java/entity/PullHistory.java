package entity;

import java.util.List;

public class PullHistory {
    private final String type;
    private final int totalPulls;
    private final int currentPity;
    private final List<Collectable> pullList;
    /**
     * Creates a new pull history object.
     * @param type the game String assigned to a given game
     * @param totalPulls the total amount of pulls in the pullList (equivalent to pullList.length())
     * @param currentPity The current pity number
     * @param pullList a list of pull objects in a collection/List
     * @throws IllegalArgumentException if the name is empty
     */
    public PullHistory(String type, int totalPulls, int currentPity, List<Collectable> pullList){
        this.type = type;
        this.totalPulls = totalPulls;
        this.currentPity = currentPity;
        this.pullList = pullList;
    }
    public void addPull(Collectable pull){
        this.pullList.add(pull);
    }

//    public int getCurrentPity(Banner bannerType){
//        //TODO implement get current Pity
//    }

    public int countByRarity(int rarity){
        int count = 0;
        for (int i=0; i< pullList.size();i++){
            if(pullList.get(i).getRarity() == rarity) {
                count += 1;
            }
        }
        return count;
    }

    public int getTotalPulls() {
        return totalPulls;
    }

    public int getCurrentPity() { return currentPity; };

    public String getType() {
        return type;
    }
}
