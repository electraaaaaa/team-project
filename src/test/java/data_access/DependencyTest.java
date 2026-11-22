package data_access;

import okhttp3.OkHttpClient;
import org.json.JSONObject;

public class DependencyTest {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        JSONObject object = new JSONObject();
        object.put("game", "Honkai: Star Rail");

        System.out.println(object);
        System.out.println("Dependencies working!");
    }
}