package antiesys.antiepidemic.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.Iterator;

/**
 * @author 秦海祺
 */
public class NullToEmpty {
    public static JSONArray filterNull(JSONArray jsonObj) {
        Object obj = null;
        for(int i = 0; i < jsonObj.size(); i++) {
            obj = jsonObj.get(i);
            if (obj instanceof JSONObject) {
                JSONObject jObj = (JSONObject) obj;
                Iterator<String> it = jObj.keys();
                String key = null;
                while (it.hasNext()) {
                    key = it.next();
                    obj = jObj.get(key);
                    if (obj instanceof JSONArray) {
                        JSONArray objArr = (JSONArray) obj;
                        filterNull(objArr);
                    }
                    if (obj == null || obj instanceof JSONNull) {
                        jObj.put(key, "");
                    }
                    if (obj.equals(null)) {
                        jObj.put(key, "");
                    }
                }

            }
            if (obj == null || obj instanceof net.sf.json.JSONNull) {
                jsonObj.set(i, "");
            }
        }
        return jsonObj;
    }

}
