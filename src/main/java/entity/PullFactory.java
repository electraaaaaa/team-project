package entity;

import java.util.Date;
import java.util.List;

public class PullFactory {
    public Pull create(Game game, float cost, float rate, Banner bannerType, Date timeStamp){
        return new Pull(game, cost, rate, bannerType, timeStamp);
    }
}
