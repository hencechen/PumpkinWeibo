package hence.com.pumpkinweibo.Weibo;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hence on 2016/8/29.
 */

public class StatusList {
    public ArrayList<WeiboStatus> statusList;
    public WeiboStatus statuses;
    public boolean hasvisible;
    public String previous_cursor;
    public String next_cursor;
    public int total_number;
    public Object[] advertises;

    public StatusList() {
    }

    public static StatusList parse(String jsonString) {
        if(TextUtils.isEmpty(jsonString)) {
            return null;
        } else {
            StatusList statuses = new StatusList();

            try {
                JSONObject e = new JSONObject(jsonString);
                statuses.hasvisible = e.optBoolean("hasvisible", false);
                statuses.previous_cursor = e.optString("previous_cursor", "0");
                statuses.next_cursor = e.optString("next_cursor", "0");
                statuses.total_number = e.optInt("total_number", 0);
                JSONArray jsonArray = e.optJSONArray("statuses");
                if(jsonArray != null && jsonArray.length() > 0) {
                    int length = jsonArray.length();
                    statuses.statusList = new ArrayList(length);

                    for(int ix = 0; ix < length; ++ix) {
                        statuses.statusList.add(WeiboStatus.parse(jsonArray.getJSONObject(ix)));
                    }
                }
            } catch (JSONException var6) {
                var6.printStackTrace();
            }

            return statuses;
        }
    }
}