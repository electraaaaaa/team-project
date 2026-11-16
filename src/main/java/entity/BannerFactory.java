package entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating Banner objects.
 */
public class BannerFactory {

  public Banner create(String name, Date startDate, Date endDate, Map<String, Float> rates, int pity,
                       String type, List<Collectable> featuredItems)
  {
    return new Banner(name, startDate, endDate, rates, pity, type, featuredItems);
  }
}
