package use_case.calculate_probability;

// pity and pulls are strings so the interactor can check them
public class ProbabilityInputData {
    private final String gameName;
    private final String bannerType;
    private final String currentPity;
    private final String plannedPulls;

    public ProbabilityInputData(String gameName, String bannerType,
                                String currentPity, String plannedPulls) {
        this.gameName = gameName;
        this.bannerType = bannerType;
        this.currentPity = currentPity;
        this.plannedPulls = plannedPulls;
    }

    public String getGameName() {
        return gameName;
    }

    public String getBannerType() {
        return bannerType;
    }

    public String getCurrentPity() {
        return currentPity;
    }

    public String getPlannedPulls() {
        return plannedPulls;
    }
}
