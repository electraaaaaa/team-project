package use_case.cost_conversion;

/**
 * The Input Data for the Conversion Use Case.
 */
public class ConversionInputData {

    private final String gameName;
    private final String bannerName;
    private final int numberOfPulls;
    private final String targetCurrency;

    public ConversionInputData(String gameName, String bannerName, int numberOfPulls, String targetCurrency) {
        this.gameName = gameName;
        this.bannerName = bannerName;
        this.numberOfPulls = numberOfPulls;
        this.targetCurrency = targetCurrency;
    }

    public String getGameName() {
        return gameName;
    }

    public String getBannerName() {
        return bannerName;
    }

    public int getNumberOfPulls() {
        return numberOfPulls;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
