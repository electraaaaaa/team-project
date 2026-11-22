package interface_adapter.calculate_probability;
import use_case.calculate_probability.ProbabilityOutputBoundary;
import use_case.calculate_probability.ProbabilityOutputData;

public class ProbabilityPresenter implements ProbabilityOutputBoundary {
    private final ProbabilityViewModel probabilityViewModel;

    public ProbabilityPresenter(ProbabilityViewModel probabilityViewModel) {
        this.probabilityViewModel = probabilityViewModel;
    }

    @Override
    public void prepareSuccessView(ProbabilityOutputData outputData) {
        final ProbabilityState state = probabilityViewModel.getState();
        state.setProbabilityText(String.format("%.2f%%", outputData.getProbability() * 100));
        // TODO (use case extension 4a): show a message when a 5-star is guaranteed.
        state.setErrorMessage("");
        probabilityViewModel.fireStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ProbabilityState state = probabilityViewModel.getState();
        state.setProbabilityText("");
        state.setErrorMessage(errorMessage);
        probabilityViewModel.fireStateChanged();
    }
}
