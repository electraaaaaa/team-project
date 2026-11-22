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

  public int getHardPity() {
    return hardPity;
  }

  /**
   * Returns the 5 star rate for the given pull.
   * @param pullNumber pulls since the last 5 star (starts at 1)
   * @return the 5 star rate
   */
  public float getRateAt(int pullNumber) {
    if (pullNumber >= hardPity) {
      return 1;
    }
    if (pullNumber >= softPityStart) {
      float rate = baseRate + softPityIncrease * (pullNumber - softPityStart + 1);
      return Math.min(rate, 1);
    }
    return baseRate;
  }

  /**
   * Returns the chance of getting at least one 5 star in n_pulls pulls.
   * @param currentPity pulls since the last 5 star
   * @param n_pulls number of planned pulls
   * @return the probability
   */
  public float probabilityWithin(int currentPity, int n_pulls) {
    // 1 - P(no 5 star in any pull)
    double noFiveStar = 1.0;
    for (int i = 1; i <= n_pulls; i++) {
      int pullNumber = currentPity + i;
      noFiveStar *= 1.0 - getRateAt(pullNumber);
      if (pullNumber >= hardPity) {
        break;
      }
    }
    return (float) (1.0 - noFiveStar);
  }
}
