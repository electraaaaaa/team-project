package data_access;
import entity.PitySystem;
import entity.PitySystemFactory;
import use_case.calculate_probability.ProbabilityPityDataAccessInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class InMemoryGameDataAccessObject implements ProbabilityPityDataAccessInterface {
    public static final String GENSHIN = "Genshin Impact";
    public static final String STAR_RAIL = "Honkai: Star Rail";
    public static final String CHARACTER = "Character";
    public static final String WEAPON = "Weapon";
    public static final String STANDARD = "Standard";

    private final Map<String, Map<String, PitySystem>> pitySystems = new LinkedHashMap<>();

    public InMemoryGameDataAccessObject() {
        final PitySystemFactory pitySystemFactory = new PitySystemFactory();

        final Map<String, PitySystem> genshin = new LinkedHashMap<>();
        genshin.put(CHARACTER, pitySystemFactory.create(0.006f, 74, 0.06f, 90));
        genshin.put(WEAPON, pitySystemFactory.create(0.007f, 63, 0.07f, 80));
        genshin.put(STANDARD, pitySystemFactory.create(0.006f, 74, 0.06f, 90));
        pitySystems.put(GENSHIN, genshin);

        final Map<String, PitySystem> starRail = new LinkedHashMap<>();
        starRail.put(CHARACTER, pitySystemFactory.create(0.006f, 74, 0.06f, 90));
        // No exact soft pity increase was found for the Light Cone banner, so this uses the Genshin
        // rule (the rate increases by base rate x 10 every pull): 0.8% x 10 = 8%.
        starRail.put(WEAPON, pitySystemFactory.create(0.008f, 66, 0.08f, 80));
        starRail.put(STANDARD, pitySystemFactory.create(0.006f, 74, 0.06f, 90));
        pitySystems.put(STAR_RAIL, starRail);
    }

    @Override
    public PitySystem getPitySystem(String gameName, String bannerType) {
        final Map<String, PitySystem> gamePitySystems = pitySystems.get(gameName);
        if (gamePitySystems == null) {
            return null;
        }
        return gamePitySystems.get(bannerType);
    }

    public List<String> getGameNames() {

        return new ArrayList<>(pitySystems.keySet());
    }
    public List<String> getBannerTypes() {

        return List.of(CHARACTER, WEAPON, STANDARD);
    }
}
