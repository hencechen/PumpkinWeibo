package hence.com.pumpkinweibo.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hence on 2016/8/29.
 */

public class WeiboStatus {
    public String created_at;
    public String id;
    public String mid;
    public String idstr;
    public String text;
    public String source;
    public boolean favorited;
    public boolean truncated;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;
    public String thumbnail_pic;
    public String bmiddle_pic;
    public String original_pic;
    public Geo geo;
    public User user;
    public WeiboStatus retweeted_status;
    public int reposts_count;
    public int comments_count;
    public int attitudes_count;
    public int mlevel;
    public Visible visible;
    public ArrayList<String> pic_urls;

    public WeiboStatus() {
    }

    public static WeiboStatus parse(String jsonString) {
        try {
            JSONObject e = new JSONObject(jsonString);
            return parse(e);
        } catch (JSONException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static WeiboStatus parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        } else {
            WeiboStatus status = new WeiboStatus();
            status.created_at = jsonObject.optString("created_at");
            status.id = jsonObject.optString("id");
            status.mid = jsonObject.optString("mid");
            status.idstr = jsonObject.optString("idstr");
            status.text = jsonObject.optString("text");
            status.source = jsonObject.optString("source");
            status.favorited = jsonObject.optBoolean("favorited", false);
            status.truncated = jsonObject.optBoolean("truncated", false);
            status.in_reply_to_status_id = jsonObject.optString("in_reply_to_status_id");
            status.in_reply_to_user_id = jsonObject.optString("in_reply_to_user_id");
            status.in_reply_to_screen_name = jsonObject.optString("in_reply_to_screen_name");
            status.thumbnail_pic = jsonObject.optString("thumbnail_pic");
            status.bmiddle_pic = jsonObject.optString("bmiddle_pic");
            status.original_pic = jsonObject.optString("original_pic");
            status.geo = Geo.parse(jsonObject.optJSONObject("geo"));
            status.user = User.parse(jsonObject.optJSONObject("user"));
            status.retweeted_status = parse(jsonObject.optJSONObject("retweeted_status"));
            status.reposts_count = jsonObject.optInt("reposts_count");
            status.comments_count = jsonObject.optInt("comments_count");
            status.attitudes_count = jsonObject.optInt("attitudes_count");
            status.mlevel = jsonObject.optInt("mlevel", -1);
            status.visible = Visible.parse(jsonObject.optJSONObject("visible"));
            JSONArray picUrlsArray = jsonObject.optJSONArray("pic_urls");
            if (picUrlsArray != null && picUrlsArray.length() > 0) {
                int length = picUrlsArray.length();
                status.pic_urls = new ArrayList(length);
                JSONObject tmpObject = null;

                for (int ix = 0; ix < length; ++ix) {
                    tmpObject = picUrlsArray.optJSONObject(ix);
                    if (tmpObject != null) {
                        status.pic_urls.add(tmpObject.optString("thumbnail_pic"));
                    }
                }
            }

            return status;
        }
    }
}
