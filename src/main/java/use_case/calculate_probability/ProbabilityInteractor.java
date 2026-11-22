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
        if (pitySystem == null) {
            probabilityPresenter.prepareFailView("No pity rules found for this banner.");
            return;
        }
        final int hardPity = pitySystem.getHardPity();

        // 3a
        final Integer currentPity = parseWholeNumber(probabilityInputData.getCurrentPity());
        if (currentPity == null) {
            probabilityPresenter.prepareFailView("Current pity must be a whole number.");
            return;
        }
        if (currentPity < 0) {
            probabilityPresenter.prepareFailView("Current pity cannot be negative.");
            return;
        }
        if (currentPity >= hardPity) {
            probabilityPresenter.prepareFailView(
                    "Current pity must be less than the hard pity (" + hardPity + ").");
            return;
        }

        final Integer plannedPulls = parseWholeNumber(probabilityInputData.getPlannedPulls());
        if (plannedPulls == null) {
            probabilityPresenter.prepareFailView("Number of pulls must be a whole number.");
            return;
        }
        if (plannedPulls < 1) {
            probabilityPresenter.prepareFailView("Number of pulls must be at least 1.");
            return;
        }

        // 4a: hits hard pity -> guaranteed
        final int pullsToHardPity = hardPity - currentPity;
        if (plannedPulls >= pullsToHardPity) {
            probabilityPresenter.prepareSuccessView(new ProbabilityOutputData(1.0, true, pullsToHardPity));
            return;
        }

        final double probability = pitySystem.probabilityWithin(currentPity, plannedPulls);
        probabilityPresenter.prepareSuccessView(new ProbabilityOutputData(probability, false, pullsToHardPity));
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
