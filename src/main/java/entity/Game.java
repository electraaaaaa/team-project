package entity;

import java.util.List;
import java.util.Map;

public class Game {
  private final String name;
  private final String gameCurrency;
  private final Map<Float, Float> moneyPackages;
  private final List<Collectable> collectables;
  private final List<Banner> banners;


  /**
   * Creates a new user with the given non-empty name and non-empty password.
   * @param name the Game name
   * @param gameCurrency the name of the game Currency
   * @param moneyPackages a mapping of game currency to the real life currency cost from packages in the game
   * @param collectables a list of collectables in the game
   * @throws IllegalArgumentException if the name is empty
   * @throws IllegalArgumentException if the password or name are empty
   */
  public Game(String name, String gameCurrency, Map<Float, Float> moneyPackages, List<Collectable> collectables, List<Banner> banners) {
    if ("".equals(name)) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    this.name = name;
    this.gameCurrency = gameCurrency;
    this.moneyPackages = moneyPackages;
    this.collectables = collectables;
    this.banners = banners;
  }

  public String getName() {
    return name;
  }

  public String getGameCurrency() {
    return gameCurrency;
  }

  public PitySystem getPityRule(Banner banner) {
    // TODO: implement this function
    return new PitySystem( 0, 0, 0, 0);
  }

  public int getCostOfPulls(int pulls) {
    // TODO: implement this function
    return -1;
  }

  public List<Banner> getAllBanners() {
    return banners;
  }
}
