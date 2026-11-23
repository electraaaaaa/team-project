package entity;

public class Collectable {
  private final String name;
  private final String type;
  private final int rarity;
  /**
   * Creates a new user with the given non-empty name and non-empty password.
   * @param name the Game name
   * @param type the type of Collectable (Character, Weapon)
   * @param rarity the rarity of this collectable
   * @throws IllegalArgumentException if the name is empty
   */
  public Collectable( String name, String type, int rarity) {
      this.name = name;
    this.type = type;
    this.rarity = rarity;
  }

  public String getName() {
    return name;
  }

  public boolean isFiveStar() {
    return rarity == 5;
  }

  public int getRarity() {
    return rarity;
  }

  public String getCollectableType() {
    return type;
  }
}
