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
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        new ProbabilityInteractor(gameDataAccessObject, successPresenter).execute(inputData);
    }

    @Test
    void failureNotANumberTest() {
        ProbabilityInputData inputData = new ProbabilityInputData(
                InMemoryGameDataAccessObject.GENSHIN, InMemoryGameDataAccessObject.CHARACTER, "abc", "10");

        ProbabilityOutputBoundary failurePresenter = new ProbabilityOutputBoundary() {
            @Override
            public void prepareSuccessView(ProbabilityOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please enter whole numbers for the pity and the number of pulls.", errorMessage);
            }
        };

        new ProbabilityInteractor(gameDataAccessObject, failurePresenter).execute(inputData);
    }

    // TODO: test the other use case extensions once they are implemented (3a, 4a).
}
