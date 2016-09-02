package hence.com.pumpkinweibo.Weibo;

/**
 * Created by Hence on 2016/8/29.
 */

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Geo {
    public String longitude;
    public String latitude;
    public String city;
    public String province;
    public String city_name;
    public String province_name;
    public String address;
    public String pinyin;
    public String more;

    public Geo() {
    }

    public static Geo parse(String jsonString) {
        if(TextUtils.isEmpty(jsonString)) {
            return null;
        } else {
            Geo geo = null;

            try {
                JSONObject e = new JSONObject(jsonString);
                geo = parse(e);
            } catch (JSONException var3) {
                var3.printStackTrace();
            }

            return geo;
        }
    }

    public static Geo parse(JSONObject jsonObject) {
        if(null == jsonObject) {
            return null;
        } else {
            Geo geo = new Geo();
            geo.longitude = jsonObject.optString("longitude");
            geo.latitude = jsonObject.optString("latitude");
            geo.city = jsonObject.optString("city");
            geo.province = jsonObject.optString("province");
            geo.city_name = jsonObject.optString("city_name");
            geo.province_name = jsonObject.optString("province_name");
            geo.address = jsonObject.optString("address");
            geo.pinyin = jsonObject.optString("pinyin");
            geo.more = jsonObject.optString("more");
            return geo;
        }
    }
}