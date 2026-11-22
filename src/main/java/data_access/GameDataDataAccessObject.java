package data_access;

import entity.Game;
import use_case.game_data.GameDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class GameDataDataAccessObject implements GameDataAccessInterface {

    private final StarRailGameDataAccessObject starRailDAO;
    private final GenshinGameDataAccessObject genshinDAO;

    public GameDataDataAccessObject(
            StarRailGameDataAccessObject starRailDAO,
            GenshinGameDataAccessObject genshinDAO) {
        this.starRailDAO = starRailDAO;
        this.genshinDAO = genshinDAO;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();

        games.add(starRailDAO.getGame());
        games.add(genshinDAO.getGame());

        return games;
    }
}