
package data_access;

import entity.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.pull_history.PullHistoryDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StarRailPullHistoryDataAccessObject
        implements PullHistoryDataAccessInterface {

    private static final String API_URL =
            "https://public-operation-hkrpg-sg.hoyoverse.com"
                    + "/common/gacha_record/api/getGachaLog";

    private static final int PAGE_SIZE = 20;
    private static final int CHARACTER_EVENT = 11;
    private static final int LIGHT_CONE = 12;
    private static final int STANDARD = 1;
    private static final int DEPARTURE = 2;

    private final OkHttpClient client;

    private final CollectableFactory collectableFactory;
    private final GamePullHistoriesFactory gamePullHistoriesFactory;

    public StarRailPullHistoryDataAccessObject(CollectableFactory collectableFactory,
                                               GamePullHistoriesFactory gamePullHistoriesFactory) {
        this.collectableFactory = collectableFactory;
        this.gamePullHistoriesFactory = gamePullHistoriesFactory;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }


    @Override
    public GamePullHistories getGamePullHistory(Game game, String warpHistoryUrl) {

        HttpUrl originalUrl = HttpUrl.parse(warpHistoryUrl);

        if (originalUrl == null || !originalUrl.isHttps()) {
            throw new IllegalArgumentException(
                    "Invalid warp history URL."
            );
        }

        String authkey =
                originalUrl.queryParameter("authkey");

        String authkeyVer =
                originalUrl.queryParameter("authkey_ver");

        String signType =
                originalUrl.queryParameter("sign_type");

        if (authkey == null || authkeyVer == null
                || signType == null) {
            throw new IllegalArgumentException(
                    "Missing authorization parameters."
            );
        }

        GamePullHistories histories =
                gamePullHistoriesFactory.create(game);

        histories.addHistory(getPullHistory(
                authkey, authkeyVer, signType, CHARACTER_EVENT, "Character"
        ));

        histories.addHistory(getPullHistory(
                authkey, authkeyVer, signType, LIGHT_CONE, "Light Cone"
        ));

        histories.addHistory(getPullHistory(
                authkey, authkeyVer, signType, STANDARD, "Standard"
        ));

        histories.addHistory(getPullHistory(
                authkey, authkeyVer, signType, DEPARTURE, "Departure"
        ));

        return histories;
    }



    private PullHistory getPullHistory(
            String authkey,
            String authkeyVer,
            String signType,
            int bannerId,
            String bannerName
    ) {

        List<Collectable> collectables = new ArrayList<>();

        String endId = "0";
        int currentPity = 0;
        boolean foundFiveStar = false;

        Set<String> visitedEndIds = new HashSet<>();

        while (true) {

            HttpUrl requestUrl = createRequestUrl(
                    authkey,
                    authkeyVer,
                    signType,
                    endId,
                    bannerId
            );

            JSONArray records = fetchPage(requestUrl);

            if (records.length() == 0) {
                break;
            }

            for (int i = 0; i < records.length(); i++) {

                JSONObject record = records.getJSONObject(i);

                String name = record.getString("name");
                String type = record.getString("item_type");

                int rarity = Integer.parseInt(
                        record.getString("rank_type")
                );

                if ("Light Cone".equalsIgnoreCase(type)) {
                    type = "Weapon";
                }

                collectables.add(
                        collectableFactory.create(name, type, rarity)
                );

                if (rarity == 5 && !foundFiveStar) {
                    foundFiveStar = true;
                } else if (!foundFiveStar){
                    currentPity++;
                }
            }

            String nextEndId = records
                    .getJSONObject(records.length() - 1)
                    .getString("id");

            if (!visitedEndIds.add(nextEndId)) {
                break;
            }

            endId = nextEndId;
        }

        return new PullHistory(
                bannerName,
                collectables.size(),
                currentPity,
                collectables
        );
    }



    private HttpUrl createRequestUrl(
            String authkey,
            String authkeyVer,
            String signType,
            String endId,
            int bannerId
    ) {

        return HttpUrl.parse(API_URL)
                .newBuilder()
                .addQueryParameter("authkey", authkey)
                .addQueryParameter("authkey_ver", authkeyVer)
                .addQueryParameter("sign_type", signType)
                .addQueryParameter("lang", "en")
                .addQueryParameter("game_biz", "hkrpg_global")
                .addQueryParameter("size", String.valueOf(PAGE_SIZE))
                .addQueryParameter("gacha_type", String.valueOf(bannerId))
                .addQueryParameter("end_id", endId)
                .build();
    }

    private JSONArray fetchPage(HttpUrl url) {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();

        try (Response response =
                     client.newCall(request).execute()) {

            if (!response.isSuccessful()
                    || response.body() == null) {
                throw new IOException(
                        "HTTP error: " + response.code()
                );
            }

            JSONObject json = new JSONObject(
                    response.body().string()
            );

            int retcode = json.optInt("retcode", -999);

            if (retcode != 0) {
                throw new IOException(
                        "HoYoverse API error: " + retcode
                );
            }

            JSONObject data = json.optJSONObject("data");

            if (data == null) {
                return new JSONArray();
            }

            JSONArray records = data.optJSONArray("list");

            return records == null
                    ? new JSONArray()
                    : records;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to retrieve HSR warp records.", e
            );
        }
    }

}
