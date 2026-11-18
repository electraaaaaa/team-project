package use_case.cost_conversion;

import entity.Game;

/**
 * Provides game information needed for cost conversion.
 */
public interface ConversionGameDataAccessInterface {

    /**
     * Retrieves the selected game.
     *
     * @param gameName the name of the selected game
     * @return the game, or null if it is not found
     */
    Game getGame(String gameName);
}