package com.example.umte_app.ui.maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    public HashMap<String,String> parseJsonObject(JSONObject object){
        HashMap<String,String> datalist = new HashMap<>();
        try {

            String name = object.getString("name");
            String latitude = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String longitude = object.getJSONObject("geometry").getJSONObject("location").getString("lng");
            datalist.put("name",name);
            datalist.put("lat",latitude);
            datalist.put("lng",longitude);


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return datalist;
    }
    public List<HashMap<String,String>> parseJsonArray(JSONArray array){

        List<HashMap<String,String>> datalist = new ArrayList<>();
        for(int i = 0; i<array.length();i++){
            try {
                HashMap<String,String> data = parseJsonObject((JSONObject )array.get(i));
                datalist.add(data);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datalist;
    }

    public List<HashMap<String,String>> parseResult(JSONObject object){
        JSONArray array = null;
        try {
            array = object.getJSONArray("results");
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return parseJsonArray(array);
    }

}
