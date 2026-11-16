package entity;

import java.util.List;
import java.util.Map;
/**
 * Factory for creating Game objects.
 */
public class GameFactory {

  public Game create(String name, String gameCurrency, Map<Float, Float> moneyPackages,
                     List collectables, List<Banner> banners) {
    return new Game(name, gameCurrency, moneyPackages,collectables, banners);
  }
}
