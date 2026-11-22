package entity;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Banner {
    private final int id;
    private final String name;
    private final Instant startDate;
    private final Instant endDate;
    private final Map<String, Float> rates;
    private int pity;
    private final String type;
    private final List<Collectable> featuredItems;

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     * @param name the Game name
     * @param startDate the start date of the Banner
     * @param endDate the end date of the Banner
     * @param rates a mapping of the rates of a rarity (3, 4, 5 star etc.) to its rates (e.g. 5 star -> 0.006)
     * @param pity the pity of this banner
     * @param type the type of banner (Character, Weapon, Standard, Beginner)
     * @param featuredItems the featured collectables of the banner
     * @throws IllegalArgumentException if the name is empty
     */
    public Banner(int id, String name, Instant startDate, Instant endDate, Map<String, Float> rates, int pity,
                  String type, List<Collectable> featuredItems) {
        this.id = id;
        if ("".equals(name)) {
        throw new IllegalArgumentException("Name cannot be empty");
      }
      this.name = name;
      this.startDate = startDate;
      this.rates = rates;
      this.type = type;
      this.pity = pity;
      this.featuredItems = featuredItems;
      this.endDate = endDate;
    }

    public String getName() {
      return name;
    }

    public int getId() {
      return id;
    }
    public Instant getStartDate() {
      return startDate;
    }

    public boolean isActive(Instant date) {
      // TODO: implement this function
      return false;
    }

    public boolean isFeatured(Collectable collectable) {
      // TODO: implement this function
      return false;
    }
}
