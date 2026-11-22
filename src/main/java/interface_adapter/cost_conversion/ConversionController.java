package interface_adapter.cost_conversion;

import use_case.cost_conversion.ConversionInputBoundary;
import use_case.cost_conversion.ConversionInputData;

/**
 * Passes the player's input to the conversion use case.
 */
public class ConversionController {

    private final ConversionInputBoundary conversionInteractor;

    public ConversionController(ConversionInputBoundary conversionInteractor) {
        this.conversionInteractor = conversionInteractor;
    }

    public void execute(String gameName, String bannerName, int numberOfPulls, String targetCurrency) {
        ConversionInputData inputData = new ConversionInputData(
                gameName, bannerName, numberOfPulls, targetCurrency);

        conversionInteractor.execute(inputData);
    }
}