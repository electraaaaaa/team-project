package entity;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating Banner objects.
 */
public class BannerFactory {

  public Banner create(int id, String name, Instant startDate, Instant endDate, Map<String, Float> rates, int pity,
                       String type, List<Collectable> featuredItems)
  {
    return new Banner(id, name, startDate, endDate, rates, pity, type, featuredItems);
  }
}
