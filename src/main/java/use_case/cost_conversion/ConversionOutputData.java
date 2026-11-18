package use_case.cost_conversion;

/**
 * The Output Data for the Conversion Use Case.
 */
public class ConversionOutputData {

    private final double cost;
    private final String currency;
    private final boolean conversionUnavailable;

    public ConversionOutputData(double cost, String currency,
                                boolean conversionUnavailable) {
        this.cost = cost;
        this.currency = currency;
        this.conversionUnavailable = conversionUnavailable;
    }

    public double getCost() {
        return cost;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isConversionUnavailable() {
        return conversionUnavailable;
    }
}
