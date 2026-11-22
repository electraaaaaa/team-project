package interface_adapter.calculate_probability;
import use_case.calculate_probability.ProbabilityInputBoundary;
import use_case.calculate_probability.ProbabilityInputData;

public class ProbabilityController {
    private final ProbabilityInputBoundary probabilityUseCaseInteractor;

    public ProbabilityController(ProbabilityInputBoundary probabilityUseCaseInteractor) {
        this.probabilityUseCaseInteractor = probabilityUseCaseInteractor;
    }

    /**
     * Executes the Calculate Probability Use Case.
     * @param gameName the selected game
     * @param bannerType the selected banner type
     * @param currentPity the current pity the player entered
     * @param plannedPulls the number of planned pulls the player entered
     */
    public void execute(String gameName, String bannerType, String currentPity, String plannedPulls) {
        final ProbabilityInputData probabilityInputData =
                new ProbabilityInputData(gameName, bannerType, currentPity, plannedPulls);
        probabilityUseCaseInteractor.execute(probabilityInputData);
    }
}
