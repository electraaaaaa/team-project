package interface_adapter.cost_conversion;

import use_case.cost_conversion.ConversionOutputBoundary;
import use_case.cost_conversion.ConversionOutputData;

import java.util.Locale;

/**
 * Prepares conversion results and errors for display.
 */
public class ConversionPresenter implements ConversionOutputBoundary {

    private final ConversionViewModel conversionViewModel;

    public ConversionPresenter(ConversionViewModel conversionViewModel) {
        this.conversionViewModel = conversionViewModel;
    }

    @Override
    public void prepareSuccessView(ConversionOutputData outputData) {
        ConversionState state = conversionViewModel.getState();

        String costText = String.format(Locale.ROOT, "%s %.2f",
                outputData.getCurrency(), outputData.getCost());

        state.setCostText(costText);
        state.setConversionError("");

        if (outputData.isConversionUnavailable()) {
            state.setWarningMessage("Currency conversion failed. Showing the original cost.");
        }
        else {
            state.setWarningMessage("");
        }

        state.setShowingResult(true);
        conversionViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ConversionState state = conversionViewModel.getState();

        state.setConversionError(errorMessage);
        state.setCostText("");
        state.setWarningMessage("");
        state.setShowingResult(false);

        conversionViewModel.notifyStateChanged();
    }
}