package use_case.calculate_probability;

import data_access.InMemoryGameDataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProbabilityInteractorTest {

    private final InMemoryGameDataAccessObject gameDataAccessObject = new InMemoryGameDataAccessObject();

    @Test
    void successTest() {
        ProbabilityInputData inputData = new ProbabilityInputData(
                InMemoryGameDataAccessObject.GENSHIN, InMemoryGameDataAccessObject.CHARACTER, "0", "10");

        ProbabilityOutputBoundary successPresenter = new ProbabilityOutputBoundary() {
            @Override
            public void prepareSuccessView(ProbabilityOutputData outputData) {
                assertEquals(1 - Math.pow(1 - 0.006, 10), outputData.getProbability(), 1e-5);
                assertFalse(outputData.isGuaranteed());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        new ProbabilityInteractor(gameDataAccessObject, successPresenter).execute(inputData);
    }

    @Test
    void successReachesHardPityTest() {
        // genshin hard pity = 90, so 85 + 10 pulls hits it
        ProbabilityInputData inputData = new ProbabilityInputData(
                InMemoryGameDataAccessObject.GENSHIN, InMemoryGameDataAccessObject.CHARACTER, "85", "10");

        ProbabilityOutputBoundary successPresenter = new ProbabilityOutputBoundary() {
            @Override
            public void prepareSuccessView(ProbabilityOutputData outputData) {
                assertEquals(1.0, outputData.getProbability(), 1e-9);
                assertTrue(outputData.isGuaranteed());
                assertEquals(5, outputData.getPullsToHardPity());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        new ProbabilityInteractor(gameDataAccessObject, successPresenter).execute(inputData);
    }

    @Test
    void failurePityNotANumberTest() {
        assertFailMessage("abc", "10", "Current pity must be a whole number.");
    }

    @Test
    void failureNegativePityTest() {
        assertFailMessage("-1", "10", "Current pity cannot be negative.");
    }

    @Test
    void failurePityAtHardPityTest() {
        assertFailMessage("90", "10", "Current pity must be less than the hard pity (90).");
    }

    @Test
    void failurePullsNotANumberTest() {
        assertFailMessage("0", "ten", "Number of pulls must be a whole number.");
    }

    @Test
    void failureZeroPullsTest() {
        assertFailMessage("0", "0", "Number of pulls must be at least 1.");
    }

    private void assertFailMessage(String currentPity, String plannedPulls, String expectedMessage) {
        ProbabilityInputData inputData = new ProbabilityInputData(
                InMemoryGameDataAccessObject.GENSHIN, InMemoryGameDataAccessObject.CHARACTER,
                currentPity, plannedPulls);

        ProbabilityOutputBoundary failurePresenter = new ProbabilityOutputBoundary() {
            @Override
            public void prepareSuccessView(ProbabilityOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(expectedMessage, errorMessage);
            }
        };

        new ProbabilityInteractor(gameDataAccessObject, failurePresenter).execute(inputData);
    }
}
