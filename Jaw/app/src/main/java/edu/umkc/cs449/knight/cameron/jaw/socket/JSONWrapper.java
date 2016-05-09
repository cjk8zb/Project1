package edu.umkc.cs449.knight.cameron.jaw.socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by camjknight on 4/10/16.
 */
class JSONWrapper {
    public static String wrap(String root, Object obj) {
        Gson gson = new Gson();
        JsonElement je = gson.toJsonTree(obj);
        JsonObject jo = new JsonObject();
        jo.add(root, je);
        return jo.toString();
    }

}
