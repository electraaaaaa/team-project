package interface_adapter.cost_conversion;

import use_case.cost_conversion.ConversionInputBoundary;
import use_case.cost_conversion.ConversionInputData;

/**
 * The controller for the Conversion Use Case.
 */
public class ConversionController {

    private final ConversionInputBoundary interactor;

    public ConversionController(ConversionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String gameName, int numberOfPulls, String targetCurrency) {
        ConversionInputData inputData = new ConversionInputData(gameName, numberOfPulls, targetCurrency);
        interactor.execute(inputData);
    }
}