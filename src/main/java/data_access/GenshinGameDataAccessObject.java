package data_access;

import entity.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Instant;
import java.util.*;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

public class GenshinGameDataAccessObject extends AbstractGameDataAccessObject{
    private static final String CHARACTER = "Character";
    private static final String WEAPON = "Weapon";
    private static final String CONFIG_PATH = "/hsr_config.json";

    private static final String BANNER_API =
            "https://stardb.gg/api/gi/banners";

    private static final String CHARACTER_API =
            "https://stardb.gg/api/characters";

    private static final String WEAPON_API =
            "https://stardb.gg/api/light-cones";

    public GenshinGameDataAccessObject(GameFactory gameFactory) {
        super(gameFactory, CONFIG_PATH);
    }

    public Game getGame() {
        JSONArray bannerJson = fetchJson(BANNER_API);
        JSONArray characterJson = fetchJson(CHARACTER_API);
        JSONArray weaponJson = fetchJson(WEAPON_API);

        Map<String, Collectable> characters =
                parseCharacters(characterJson);

        Map<String, Collectable> lightCones =
                parseWeapons(weaponJson);

        List<Banner> banners = parseBanners(
                bannerJson,
                characters,
                lightCones
        );

        JSONObject config = fetchConfig();

        String name = config.getString("name");
        String gameCurrency = config.getString("gameCurrency");

        Map<Float, Float> moneyPackages =
                parseMoneyPackages(config);

        List<Collectable> collectables = new ArrayList<>();
        collectables.addAll(characters.values());
        collectables.addAll(lightCones.values());

        return gameFactory.create(
                name,
                gameCurrency,
                moneyPackages,
                collectables,
                banners
        );
    }

    private Map<String, Collectable> parseCharacters(JSONArray jsonArray) {
        Map<String, Collectable> characters = new LinkedHashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject characterData = jsonArray.getJSONObject(i);

            String id = String.valueOf(characterData.getInt("id"));
            String name = characterData.getString("name");
            int rarity = characterData.getInt("rarity");


            Collectable character = new Collectable(
                    name,
                    CHARACTER,
                    rarity
            );

            characters.put(id, character);
        }

        return characters;
    }

    private Map<String, Collectable> parseWeapons(JSONArray jsonArray) {
        Map<String, Collectable> lightCones = new LinkedHashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject lightConeData = jsonArray.getJSONObject(i);

            String id = String.valueOf(lightConeData.getInt("id"));
            String name = lightConeData.getString("name");
            int rarity = lightConeData.getInt("rarity");

            Collectable lightCone = new Collectable(
                    name,
                    WEAPON,
                    rarity
            );

            lightCones.put(id, lightCone);
        }

        return lightCones;
    }

    private List<Banner> parseBanners(
            JSONArray jsonArray,
            Map<String, Collectable> characters,
            Map<String, Collectable> lightCones) {

        List<Banner> banners = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bannerData = jsonArray.getJSONObject(i);

            int id = bannerData.getInt("id");
            String name = bannerData.getString("name");

            Instant startDate = Instant.parse(
                    bannerData.getString("start")
            );

            Instant endDate = Instant.parse(
                    bannerData.getString("end")
            );

            List<Collectable> featuredItems = new ArrayList<>();
            String type;

            if (!bannerData.isNull("character")) {
                String characterId =
                        String.valueOf(bannerData.getInt("character"));

                Collectable character = characters.get(characterId);

                if (character == null) {
                    throw new IllegalStateException(
                            "Character not found: " + characterId
                    );
                }

                featuredItems.add(character);
                type = "Character";
            }
            else if (!bannerData.isNull("light_cone")) {
                String lightConeId =
                        String.valueOf(bannerData.getInt("light_cone"));

                Collectable lightCone = lightCones.get(lightConeId);

                if (lightCone == null) {
                    throw new IllegalStateException(
                            "Light Cone not found: " + lightConeId
                    );
                }

                featuredItems.add(lightCone);
                type = "Weapon";
            }
            else {
                continue;
            }

            if (name.isBlank() && !featuredItems.isEmpty()) {
                name = featuredItems.get(0).getName();
            }

            Banner banner = new Banner(
                    id,
                    name,
                    startDate,
                    endDate,
                    new HashMap<>(),
                    0,
                    type,
                    featuredItems
            );

            banners.add(banner);
        }

        return banners;
    }

}
