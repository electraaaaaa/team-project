package use_case.cost_conversion;

/**
 * The Input Data for the Conversion Use Case.
 */
public class ConversionInputData {

    private final String gameName;
    private final int numberOfPulls;
    private final String targetCurrency;

    public ConversionInputData(String gameName, int numberOfPulls, String targetCurrency) {
        this.gameName = gameName;
        this.numberOfPulls = numberOfPulls;
        this.targetCurrency = targetCurrency;
    }

    public String getGameName() {
        return gameName;
    }

    public int getNumberOfPulls() {
        return numberOfPulls;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
