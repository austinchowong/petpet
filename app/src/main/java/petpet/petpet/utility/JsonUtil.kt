package petpet.petpet.utility
import org.json.*
/**
 * Created by user on 2018-06-06.
 */
public class JsonUtil {
    /*
    //needs to be updated whenever a class is modified
    companion object {
        fun ToJson (event: PetEvent): String?
        {
            try {
                // Here we convert Java Object to JSON
                var jsonObj = JSONObject();
                jsonObj.put("name", event.name);
                jsonObj.put("description", event.description);
                jsonObj.put("startTime", event.startTime); // Set the first name/pair
                jsonObj.put("endTime", event.endTime);
                jsonObj.put("completedTime", event.completedTime);
                jsonObj.put("isComplete", event.isComplete);

                var jsonArr = JSONArray();
                for (criteria in event.criteriaList) {
                    var criteriaObj = JSONObject(ToJson(criteria));
                    jsonArr.put(criteriaObj);
                }
                jsonObj.put("criteriaList", jsonArr);

                return jsonObj.toString();
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }

        fun PetEventFromJson(string: String): PetEvent?
        {
            try {
                var petevent = PetEvent();
                var obj = JSONObject(string);
                petevent.name = obj.getString("name");
                petevent.description = obj.getString("description");
                petevent.startTime = obj.getLong("startTime");
                petevent.endTime = obj.getLong("endTime");
                petevent.completedTime = obj.getLong("completedTime");
                petevent.isComplete = obj.getBoolean("isComplete");

                var jarr = obj.getJSONArray("criteriaList");
                for (i in 0..jarr.length() - 1) {
                    var criteriaObj = jarr.getJSONObject(i);
                    CriteriaFromJson(criteriaObj.toString())?.let { petevent.criteriaList.add(it) };
                }
                return petevent;
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }

        fun ToJson(timeline: Timeline): String?
        {
            try {
                // Here we convert Java Object to JSON
                var jsonObj = JSONObject();
                jsonObj.put("startDate", timeline.startDate); // Set the first name/pair

                var jsonArr = JSONArray();
                for (petevent in timeline.timeline) {
                    var criteriaObj = JSONObject(ToJson(petevent));
                    jsonArr.put(criteriaObj);
                }
                jsonObj.put("timeline", jsonArr);

                return jsonObj.toString();
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }

        fun TimelineFromJson(string: String): Timeline?
        {
            try {
                var timeline = Timeline();
                var obj = JSONObject(string);
                var startDate =  obj.getLong("startDate");
                if(startDate == 0L)
                {
                    //timeline will set the startdate on its own when it gets created,
                    //since we're attempting to copy in a template timeline, there was no startdate
                    //so we leave it as is
                }
                else
                {
                    //otherwise, we're trying to get a timeline that was created earlier
                    //and has already begun counting down, need to get startdate
                    timeline.startDate = startDate;
                }

                var jarr = obj.getJSONArray("timeline");
                for (i in 0..jarr.length() - 1) {
                    var peteventObj = jarr.getJSONObject(i);
                    PetEventFromJson(peteventObj.toString())?.let { timeline.timeline.add(it) };
                }
                return timeline;
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }

        fun ToJson(criteria: Criteria): String?
        {
            try {
                // Here we convert Java Object to JSON
                var jsonObj = JSONObject();
                jsonObj.put("var1", criteria.var1); // Set the first name/pair
                jsonObj.put("op", criteria.op);
                jsonObj.put("var2", criteria.var2); // Set the first name/pair

                return jsonObj.toString();
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }

        fun CriteriaFromJson(string: String): Criteria?
        {
            try {
                var criteria = Criteria();
                var obj = JSONObject(string);
                criteria.var1 = obj.getString("var1");
                criteria.op = obj.getString("op");
                criteria.var2 = obj.getString("var2");

                return criteria;
            }
            catch (ex: JSONException)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
    */
}