package use_case.cost_conversion;

import java.util.List;

/**
 * Provides exchange rates needed for cost conversion.
 */
public interface ConversionExchangeRateDataAccessInterface {

    /**
     * Retrieves the rate for converting between two currencies.
     * Multiply the original amount by this rate to convert it.
     *
     * @param originalCurrency the original currency code
     * @param targetCurrency the chosen currency code
     * @return a positive exchange rate, or null if unavailable
     */
    Double getExchangeRate(String originalCurrency, String targetCurrency);

    /**
     * Returns supported currency codes, such as CAD and USD.
     * Returns an empty list if they cannot be retrieved.
     */
    List<String> getSupportedCurrencies();
}