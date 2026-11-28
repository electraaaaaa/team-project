package use_case.compare_games;

/**
 * Output data with comparison results for two games.
 */
public class CompareGamesOutputData {

    private final String game1;
    private final String game2;
    private final float probability1;
    private final float probability2;

    public CompareGamesOutputData(String game1, String game2,
                                  float probability1, float probability2) {
        this.game1 = game1;
        this.game2 = game2;
        this.probability1 = probability1;
        this.probability2 = probability2;
    }

    public String getGame1() {
        return game1;
    }

    public String getGame2() {
        return game2;
    }

    public float getProbability1() {
        return probability1;
    }

    public float getProbability2() {
        return probability2;
    }
}
