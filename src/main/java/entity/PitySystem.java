package entity;

public class PitySystem {
  private final float baseRate;
  private final int softPityStart;
  private final float softPityIncrease;
  private final int hardPity;

  /**
   * Creates a new user with the given non-empty name and non-empty password.
   * @param baseRate the base rate of the banner for a 5 star.
   * @param softPityStart the exact number when soft pity starts
   * @param softPityIncrease the rate at which soft pity increases
   * @param hardPity the hard pity for this banner.
   * @throws IllegalArgumentException if the name is empty
   */
  public PitySystem(float baseRate, int softPityStart, float softPityIncrease, int hardPity) {
    this.baseRate = baseRate;
    this.softPityStart = softPityStart;
    this.softPityIncrease = softPityIncrease;
    this.hardPity = hardPity;
  }

  public float getRateAt(int pullNumber) {
    // TODO: implement this function
    return -1;
  }

  public float probabilityWithin(int currentPity, int n_pulls) {
    // TODO: implement this function
    return -1;
  }
}
