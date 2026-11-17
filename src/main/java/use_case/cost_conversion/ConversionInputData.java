package use_case.cost_conversion;

/**
 * The Input Data for the Conversion Use Case.
 */
public class ConversionInputData {

    private final String gameName;
    private final String pullType;
    private final int numberOfPulls;
    private final String targetCurrency;

    public ConversionInputData(String gameName, String pullType,
                               int numberOfPulls, String targetCurrency) {
        this.gameName = gameName;
        this.pullType = pullType;
        this.numberOfPulls = numberOfPulls;
        this.targetCurrency = targetCurrency;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPullType() {
        return pullType;
    }

    public int getNumberOfPulls() {
        return numberOfPulls;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
