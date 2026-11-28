package use_case.compare_games;

/**
 * Input data for comparing the pity systems of two selected games.
 */
public class CompareGamesInputData {

    private final String game1;
    private final String game2;
    private final int numberOfPulls;
    private final int pity1;
    private final int pity2;

    public CompareGamesInputData(String game1, String game2, int numberOfPulls,
                                 int pity1, int pity2) {
        this.game1 = game1;
        this.game2 = game2;
        this.numberOfPulls = numberOfPulls;
        this.pity1 = pity1;
        this.pity2 = pity2;
    }

    public String getGame1() {
        return game1;
    }

    public String getGame2() {
        return game2;
    }

    public int getNumberOfPulls() {
        return numberOfPulls;
    }

    public int getPity1() {
        return pity1;
    }

    public int getPity2() {
        return pity2;
    }
}
