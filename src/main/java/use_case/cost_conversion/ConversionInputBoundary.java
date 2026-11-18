package use_case.cost_conversion;

/**
 * Input boundary for the cost conversion use case.
 */
public interface ConversionInputBoundary {

    /**
     * Executes the cost conversion use case.
     *
     * @param conversionInputData the input data
     */
    void execute(ConversionInputData conversionInputData);
}
