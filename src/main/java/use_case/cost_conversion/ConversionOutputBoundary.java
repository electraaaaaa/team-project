package use_case.cost_conversion;

/**
 * The output boundary for the Conversion Use Case.
 */
public interface ConversionOutputBoundary {
    /**
     * Prepares the success view for the Conversion Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ConversionOutputData outputData);

    /**
     * Prepares the failure view for the Conversion Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}

