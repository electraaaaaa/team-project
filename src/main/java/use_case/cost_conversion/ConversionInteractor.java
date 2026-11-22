package use_case.cost_conversion;

import entity.Game;

import java.util.Locale;
import java.util.Map;

/**
 * Calculates the estimated cost of pulls in the chosen currency.
 */
public class ConversionInteractor implements ConversionInputBoundary {

    private final ConversionGameDataAccessInterface gameDataAccess;
    private final ConversionExchangeRateDataAccessInterface exchangeRateDataAccess;
    private final ConversionOutputBoundary presenter;

    public ConversionInteractor(
            ConversionGameDataAccessInterface gameDataAccess,
            ConversionExchangeRateDataAccessInterface exchangeRateDataAccess,
            ConversionOutputBoundary presenter) {
        this.gameDataAccess = gameDataAccess;
        this.exchangeRateDataAccess = exchangeRateDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ConversionInputData inputData) {
        int numberOfPulls = inputData.getNumberOfPulls();

        if (numberOfPulls < 0) {
            presenter.prepareFailView("Number of pulls cannot be negative.");
            return;
        }

        String targetCurrency = normalizeCurrency(inputData.getTargetCurrency());

        if (!targetCurrency.matches("[A-Z]{3}")) {
            presenter.prepareFailView("Select a currency.");
            return;
        }

        Game game = gameDataAccess.getGame(inputData.getGameName());

        if (game == null) {
            presenter.prepareFailView("The selected game was not found.");
            return;
        }

        double packageAmount = 0;
        double packagePrice = 0;

        for (Map.Entry<Float, Float> entry : game.getMoneyPackages().entrySet()) {
            double amount = entry.getKey();
            double price = entry.getValue();

            if (packageAmount == 0 || price / amount < packagePrice / packageAmount) {
                packageAmount = amount;
                packagePrice = price;
            }
        }

        String originalCurrency = normalizeCurrency(game.getPriceCurrency());

        if (packageAmount <= 0 || packagePrice <= 0) {
            presenter.prepareFailView("Pricing information is missing or invalid.");
            return;
        }

        double requiredCurrency = game.getCostOfPulls(numberOfPulls);

        if (requiredCurrency < 0) {
            presenter.prepareFailView("Pull pricing is unavailable.");
            return;
        }

        double originalCost = requiredCurrency / packageAmount * packagePrice;

        if (!Double.isFinite(originalCost)) {
            presenter.prepareFailView("The calculated cost is too large.");
            return;
        }

        if (originalCurrency.equals(targetCurrency) || numberOfPulls == 0) {
            presenter.prepareSuccessView(new ConversionOutputData(
                    originalCost, targetCurrency, false));
            return;
        }

        Double exchangeRate = exchangeRateDataAccess.getExchangeRate(originalCurrency, targetCurrency);

        // The DAO returns a positive, finite rate or null.
        if (exchangeRate == null) {
            showOriginalCost(originalCost, originalCurrency);
            return;
        }

        double convertedCost = originalCost * exchangeRate;

        // Multiplication can overflow even when both values are finite.
        if (!Double.isFinite(convertedCost)) {
            showOriginalCost(originalCost, originalCurrency);
            return;
        }

        presenter.prepareSuccessView(new ConversionOutputData(
                convertedCost, targetCurrency, false));
    }

    private void showOriginalCost(double cost, String currency) {
        presenter.prepareSuccessView(
                new ConversionOutputData(cost, currency, true));
    }

    private String normalizeCurrency(String currency) {
        if (currency == null) {
            return "";
        }

        return currency.trim().toUpperCase(Locale.ROOT);
    }
}