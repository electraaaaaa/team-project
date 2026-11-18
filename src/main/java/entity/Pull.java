package entity;

import java.util.Date;

public class Pull {
    private final Game game;
    private final float cost;
    private final float rate;
    private final Banner bannerType;
    private final Date timeStamp;
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
