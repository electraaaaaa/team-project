package use_case.calculate_probability;
import entity.PitySystem;

public class ProbabilityInteractor implements ProbabilityInputBoundary {
    private final ProbabilityPityDataAccessInterface pityDataAccessObject;
    private final ProbabilityOutputBoundary probabilityPresenter;

    public ProbabilityInteractor(ProbabilityPityDataAccessInterface pityDataAccessObject,
                                 ProbabilityOutputBoundary probabilityPresenter) {
        this.pityDataAccessObject = pityDataAccessObject;
        this.probabilityPresenter = probabilityPresenter;
    }

    @Override
    public void execute(ProbabilityInputData probabilityInputData) {
        final PitySystem pitySystem = pityDataAccessObject.getPitySystem(
                probabilityInputData.getGameName(), probabilityInputData.getBannerType());

        final Integer currentPity = parseWholeNumber(probabilityInputData.getCurrentPity());
        final Integer plannedPulls = parseWholeNumber(probabilityInputData.getPlannedPulls());
        if (currentPity == null || plannedPulls == null) {
            probabilityPresenter.prepareFailView("Please enter whole numbers for the pity and the number of pulls.");
            return;
        }
        // TODO (use case extension 3a)
        // TODO: fail if there are no pity rules for the selected game and banner type.

        final double probability = pitySystem.probabilityWithin(currentPity, plannedPulls);
        // TODO (use case extension 4a)

        probabilityPresenter.prepareSuccessView(new ProbabilityOutputData(probability));
    }

    private static Integer parseWholeNumber(String text) {
        try {
            return Integer.parseInt(text.trim());
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
