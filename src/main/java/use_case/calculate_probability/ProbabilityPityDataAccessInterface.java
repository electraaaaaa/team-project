package use_case.calculate_probability;
import entity.PitySystem;

public interface ProbabilityPityDataAccessInterface {

    /**
     * Retrieves the pity rules for a banner type in a game.
     * @param gameName the name of the selected game
     * @param bannerType the selected banner type (e.g., Character, Weapon, Standard)
     * @return the pity rules, or null if they are not found
     */
    PitySystem getPitySystem(String gameName, String bannerType);
}
