package data_access;

import okhttp3.*;
import org.json.*;
import use_case.cost_conversion.ConversionExchangeRateDataAccessInterface;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ExchangeRateDataAccessObject implements ConversionExchangeRateDataAccessInterface {

    private static final String API_URL = "https://api.frankfurter.dev/v2";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .build();

    @Override
    public List<String> getSupportedCurrencies() {
        try {
            String responseBody = getResponse(API_URL + "/currencies");
            JSONArray currencies = new JSONArray(responseBody);

            List<String> currencyCodes = new ArrayList<>();

            for (int i = 0; i < currencies.length(); i++) {
                JSONObject currency = currencies.getJSONObject(i);

                if ("currency".equals(currency.getString("type"))) {
                    currencyCodes.add(currency.getString("iso_code"));
                }
            }

            Collections.sort(currencyCodes);
            return currencyCodes;
        }
        catch (IOException | JSONException exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public Double getExchangeRate(String originalCurrency, String targetCurrency) {
        try {
            String url = API_URL + "/rate/" + originalCurrency + "/" + targetCurrency;

            String responseBody = getResponse(url);
            JSONObject result = new JSONObject(responseBody);

            double rate = result.getDouble("rate");

            if (!Double.isFinite(rate) || rate <= 0) {
                return null;
            }

            return rate;
        }
        catch (IOException | JSONException exception) {
            return null;
        }
    }

    /**
     * Sends a GET request and returns the response body.
     */
    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with status " + response.code());
            }

            ResponseBody body = response.body();

            if (body == null) {
                throw new IOException("The response body was empty.");
            }

            return body.string();
        }
    }
}