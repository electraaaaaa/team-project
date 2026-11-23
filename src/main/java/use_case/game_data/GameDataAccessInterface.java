package use_case.game_data;

import entity.Game;
import java.util.List;

/**
 * Data Access Interface for retrieving game data.
 */
public interface GameDataAccessInterface {

    /**
     * Retrieves all supported games.
     * @return a list of Game entities
     */
    List<Game> getAllGames();
}