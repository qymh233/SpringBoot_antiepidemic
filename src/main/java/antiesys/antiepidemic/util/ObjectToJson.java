package antiesys.antiepidemic.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author 秦海祺
 */
public class ObjectToJson {
    public static List<?> listSub(List<?> list, Integer page, Integer limit){

        List<?> subList;
        if(((page - 1) * limit + limit) <= list.size()) {
            subList = list.subList((page - 1) * limit, (page - 1) * limit + limit);
        }else{
            subList = list.subList((page - 1) * limit, list.size());
        }
        return  subList;
    }

    public static JSONObject filterObject(JSONObject jsonObj, List<?> subList, Integer count){
        JSONArray jsonArray = JSONArray.fromObject(subList);
        NullToEmpty.filterNull(jsonArray);
        jsonObj.put("msg", "");
        jsonObj.put("count", count);
        jsonObj.put("data", jsonArray);
        return jsonObj;
    }
}
