package data_access;

import entity.GameFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

public abstract class AbstractGameDataAccessObject {

    protected final GameFactory gameFactory;
    protected final OkHttpClient httpClient;

    private final String configPath;

    public AbstractGameDataAccessObject(
            GameFactory gameFactory,
            String configPath) {

        this.gameFactory = gameFactory;
        this.configPath = configPath;

        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    protected JSONArray fetchJson(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IllegalStateException(
                        "API request failed with status: " + response.code()
                );
            }

            if (response.body() == null) {
                throw new IllegalStateException(
                        "API returned an empty response."
                );
            }

            return new JSONArray(response.body().string());

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Failed to retrieve game data.", exception
            );
        }
    }

    protected JSONObject fetchConfig() {
        try (InputStream inputStream =
                     getClass().getResourceAsStream(configPath)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Could not find game configuration: " + configPath
                );
            }

            String json = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            return new JSONObject(json);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Failed to load game configuration.", exception
            );
        }
    }

    protected Map<Float, Float> parseMoneyPackages(JSONObject config) {
        JSONObject packagesJson = config.getJSONObject("moneyPackages");

        Map<Float, Float> moneyPackages = new LinkedHashMap<>();

        for (String currencyAmount : packagesJson.keySet()) {
            float amount = Float.parseFloat(currencyAmount);
            float price = packagesJson.getFloat(currencyAmount);

            moneyPackages.put(amount, price);
        }

        return moneyPackages;
    }
}
