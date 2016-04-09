import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yunpian.sdk.util.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingone on 16/1/19.
 */
public class JsonTest {
    String old = "";

    @Test public void test() {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(old);
        JsonObject ret = new JsonObject();
        List<String> proList = new ArrayList<String>();
        List<String> cityList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            proList.add(jsonObject.get("lable").getAsString());
            cityList.clear();
            JsonObject pro = new JsonObject();
            JsonObject city = new JsonObject();
            for (int j = 0; j < jsonObject.get("cities").getAsJsonArray().size(); j++) {
                JsonObject country = new JsonObject();
                JsonObject jsonObject1 =
                    jsonObject.get("cities").getAsJsonArray().get(j).getAsJsonObject();
                cityList.add(jsonObject1.get("lable").getAsString());
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2
                    .addProperty("countryList", JsonUtil.toJson(jsonObject1.get("counties")));
                pro.addProperty(jsonObject1.get("lable").getAsString(),
                    JsonUtil.toJson(jsonObject1.get("counties")));

            }

            pro.addProperty("cityList", JsonUtil.toJson(cityList));
            ret.addProperty(jsonObject.get("lable").getAsString(), JsonUtil.toJson(pro));
        }
        ret.addProperty("proList", JsonUtil.toJson(proList));
        String x = ret.toString();
        System.out.println(x);
        System.out.println(x.replace("\\", "").replace("\"{\"", "{\"").replace("\"}\"", "\"}")
            .replace("\"[\"", "[\"").replace("\"]\"", "\"]"));
    }
}
