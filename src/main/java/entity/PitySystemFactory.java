package entity;

/**
 * Factory for creating PitySystem objects.
 */
public class PitySystemFactory {

  public PitySystem create(float baseRate, int softPityStart, float softPityIncrease, int hardPity) {
    return new PitySystem(baseRate, softPityStart, softPityIncrease, hardPity);
  }
}
