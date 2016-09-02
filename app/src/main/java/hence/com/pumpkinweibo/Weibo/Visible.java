package hence.com.pumpkinweibo.Weibo;

/**
 * Created by Hence on 2016/8/29.
 */

import org.json.JSONObject;

public class Visible {
    public static final int VISIBLE_NORMAL = 0;
    public static final int VISIBLE_PRIVACY = 1;
    public static final int VISIBLE_GROUPED = 2;
    public static final int VISIBLE_FRIEND = 3;
    public int type;
    public int list_id;

    public Visible() {
    }

    public static Visible parse(JSONObject jsonObject) {
        if(null == jsonObject) {
            return null;
        } else {
            Visible visible = new Visible();
            visible.type = jsonObject.optInt("type", 0);
            visible.list_id = jsonObject.optInt("list_id", 0);
            return visible;
        }
    }
}