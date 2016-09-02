package hence.com.pumpkinweibo.Weibo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hence on 2016/8/29.
 */

public class User {
    public String id;
    public String idstr;
    public String screen_name;
    public String name;
    public int province;
    public int city;
    public String location;
    public String description;
    public String url;
    public String profile_image_url;
    public String profile_url;
    public String domain;
    public String weihao;
    public String gender;
    public int followers_count;
    public int friends_count;
    public int statuses_count;
    public int favourites_count;
    public String created_at;
    public boolean following;
    public boolean allow_all_act_msg;
    public boolean geo_enabled;
    public boolean verified;
    public int verified_type;
    public String remark;
    public WeiboStatus status;
    public boolean allow_all_comment;
    public String avatar_large;
    public String avatar_hd;
    public String verified_reason;
    public boolean follow_me;
    public int online_status;
    public int bi_followers_count;
    public String lang;
    public String star;
    public String mbtype;
    public String mbrank;
    public String block_word;

    public User() {
    }

    public static User parse(String jsonString) {
        try {
            JSONObject e = new JSONObject(jsonString);
            return parse(e);
        } catch (JSONException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static User parse(JSONObject jsonObject) {
        if(null == jsonObject) {
            return null;
        } else {
            User user = new User();
            user.id = jsonObject.optString("id", "");
            user.idstr = jsonObject.optString("idstr", "");
            user.screen_name = jsonObject.optString("screen_name", "");
            user.name = jsonObject.optString("name", "");
            user.province = jsonObject.optInt("province", -1);
            user.city = jsonObject.optInt("city", -1);
            user.location = jsonObject.optString("location", "");
            user.description = jsonObject.optString("description", "");
            user.url = jsonObject.optString("url", "");
            user.profile_image_url = jsonObject.optString("profile_image_url", "");
            user.profile_url = jsonObject.optString("profile_url", "");
            user.domain = jsonObject.optString("domain", "");
            user.weihao = jsonObject.optString("weihao", "");
            user.gender = jsonObject.optString("gender", "");
            user.followers_count = jsonObject.optInt("followers_count", 0);
            user.friends_count = jsonObject.optInt("friends_count", 0);
            user.statuses_count = jsonObject.optInt("statuses_count", 0);
            user.favourites_count = jsonObject.optInt("favourites_count", 0);
            user.created_at = jsonObject.optString("created_at", "");
            user.following = jsonObject.optBoolean("following", false);
            user.allow_all_act_msg = jsonObject.optBoolean("allow_all_act_msg", false);
            user.geo_enabled = jsonObject.optBoolean("geo_enabled", false);
            user.verified = jsonObject.optBoolean("verified", false);
            user.verified_type = jsonObject.optInt("verified_type", -1);
            user.remark = jsonObject.optString("remark", "");
            user.allow_all_comment = jsonObject.optBoolean("allow_all_comment", true);
            user.avatar_large = jsonObject.optString("avatar_large", "");
            user.avatar_hd = jsonObject.optString("avatar_hd", "");
            user.verified_reason = jsonObject.optString("verified_reason", "");
            user.follow_me = jsonObject.optBoolean("follow_me", false);
            user.online_status = jsonObject.optInt("online_status", 0);
            user.bi_followers_count = jsonObject.optInt("bi_followers_count", 0);
            user.lang = jsonObject.optString("lang", "");
            user.star = jsonObject.optString("star", "");
            user.mbtype = jsonObject.optString("mbtype", "");
            user.mbrank = jsonObject.optString("mbrank", "");
            user.block_word = jsonObject.optString("block_word", "");
            return user;
        }
    }
}
