package data_access;

import entity.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;


import java.time.Instant;

import java.util.*;


public class GenshinGameDataAccessObject extends AbstractGameDataAccessObject{
    private static final String CHARACTER = "Character";
    private static final String WEAPON = "Weapon";
    private static final String CONFIG_PATH = "/genshin_config.json";

    private static final String BANNER_API =
            "https://stardb.gg/api/gi/banners";

//    when we find an API, we can use these parameters!
//    private static final String CHARACTER_API =
//            "https://stardb.gg/api/characters";
//
//    private static final String WEAPON_API =
//            "https://stardb.gg/api/light-cones";
    public GenshinGameDataAccessObject(GameFactory gameFactory) {
        super(gameFactory, CONFIG_PATH);
    }
    public Game getGame() {
        List<JSONObject> characterJson = loadJsonFiles(
                "/genshin/characters/",
                "/genshin/character-index.json"
        );

        List<JSONObject> weaponJson = loadJsonFiles(
                "/genshin/weapons/",
                "/genshin/weapon-index.json"
        );

        Map<Integer, Collectable> characters = parseCharacters(characterJson);
        Map<Integer, Collectable> weapons = parseWeapons(weaponJson);

        JSONArray bannerJson = fetchJson("https://stardb.gg/api/gi/banners");

        List<Banner> banners = parseBanners(bannerJson, characters, weapons);

        JSONObject config = fetchConfig();

        String name = config.getString("name");
        String gameCurrency = config.getString("gameCurrency");

        Map<Float, Float> moneyPackages = parseMoneyPackages(config);

        List<Collectable> collectables = new ArrayList<>();
        collectables.addAll(characters.values());
        collectables.addAll(weapons.values());

        return gameFactory.create(
                name,
                gameCurrency,
                moneyPackages,
                collectables,
                banners
        );
    }

    private Map<Integer, Collectable> parseCharacters(List<JSONObject> characterJson) {
        return getIntegerCollectableMap(characterJson, CHARACTER);
    }

    private Map<Integer, Collectable> parseWeapons(List<JSONObject> weaponJson) {
        return getIntegerCollectableMap(weaponJson, WEAPON);
    }

    @NotNull
    private Map<Integer, Collectable> getIntegerCollectableMap(List<JSONObject> characterJson, String character2) {
        Map<Integer, Collectable> characters = new HashMap<>();

        for (JSONObject character : characterJson) {
            int id = character.getInt("_id");
            String name = character.getString("name");
            int rarity = character.getInt("rarity");

            Collectable collectable = new Collectable(name, character2, rarity);

            characters.put(id, collectable);
        }

        return characters;
    }

    private List<Banner> parseBanners(
            JSONArray bannerJson,
            Map<Integer, Collectable> characters,
            Map<Integer, Collectable> weapons) {

        List<Banner> banners = new ArrayList<>();

        for (int i = 0; i < bannerJson.length(); i++) {
            JSONObject banner = bannerJson.getJSONObject(i);

            int id = banner.getInt("id");
            String name = banner.getString("name");

            Instant startDate = Instant.parse(banner.getString("start"));
            Instant endDate = Instant.parse(banner.getString("end"));

            List<Collectable> featuredItems = new ArrayList<>();
            String type;

            if (!banner.isNull("character")) {
                int characterId = banner.getInt("character");
                Collectable character = characters.get(characterId);

                if (character == null) {
                    throw new IllegalStateException(
                            "Could not find character with ID: " + characterId
                    );
                }

                featuredItems.add(character);
                type = CHARACTER;

            } else if (!banner.isNull("weapon")) {
                int weaponId = banner.getInt("weapon");
                Collectable weapon = weapons.get(weaponId);

                if (weapon == null) {
                    throw new IllegalStateException(
                            "Could not find weapon with ID: " + weaponId
                    );
                }

                featuredItems.add(weapon);
                type = WEAPON;

            } else {
                continue;
            }

            if (name.isBlank()) {
                name = featuredItems.get(0).getName();
            }

            Banner parsedBanner = new Banner(
                    id,
                    name,
                    startDate,
                    endDate,
                    new HashMap<>(),
                    0,
                    type,
                    featuredItems
            );

            banners.add(parsedBanner);
        }

        return banners;
    }

}
