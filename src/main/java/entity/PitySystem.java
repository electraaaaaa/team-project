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

  /**
   * Returns the 5 star rate for a pull, counted from the last 5 star.
   * The rate stays at the base rate until soft pity, then increases by softPityIncrease
   * every pull, and is 100% at hard pity.
   * @param pullNumber the pull number since the last 5 star (the first pull is 1)
   * @return the 5 star rate for this pull, between 0 and 1
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
   * Returns the probability of getting at least one 5 star within the next n_pulls pulls.
   * This is 1 minus the probability of getting no 5 star in every one of those pulls.
   * @param currentPity the number of pulls since the last 5 star
   * @param n_pulls the number of planned pulls
   * @return the probability, between 0 and 1
   */
  public float probabilityWithin(int currentPity, int n_pulls) {
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
