package entity;

public class CollectableFactory {
  /**
   * Factory for creating Collectable objects.
   */
  public Collectable create(String name, String type, int rarity) {
    return new Collectable(name, type, rarity);
  }
}
