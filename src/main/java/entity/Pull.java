package entity;

import java.util.Date;


public class Pull {
    private final Game game;
    private final float cost;
    private final float rate;
    private final Banner bannerType;
    private final Date timeStamp;
    /**
     * Creates a new pull object.
     * @param game the game object assigned to a given game
     * @param cost the cost of the pull in real world currency
     * @param rate the rate at which soft pity increases
     * @param bannerType the banner the player made the pull on.
     * @param timeStamp the Date object corresponding to the date and time the player made the pull
     * @throws IllegalArgumentException if the name is empty
     */
    public Pull(Game game, float cost, float rate, Banner bannerType, Date timeStamp){
        this.game = game;
        this.cost = cost;
        this.rate = rate;
        this.bannerType = bannerType;
        this.timeStamp = timeStamp;
    }
    public float getRate(){
        return this.rate;
    }
    public Banner getBanner(){
        return this.bannerType;
    }

}
