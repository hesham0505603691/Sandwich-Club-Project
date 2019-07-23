package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mRoot = new JSONObject(json);
            JSONObject mName = mRoot.getJSONObject("name");
            String mainName = mName.getString("mainName");
            JSONArray mAlsoKnownAs = mName.getJSONArray("alsoKnownAs");
            ArrayList<String> mAlsoKnownAsList = new ArrayList<>();
            for(int i=0; i<mAlsoKnownAs.length(); i++){
                mAlsoKnownAsList.add(mAlsoKnownAs.getString(i));
            }

            // get attributes
            String placeOfOrigin = mRoot.getString("placeOfOrigin");
            String description = mRoot.getString("description");
            String image = mRoot.getString("image");
            JSONArray ingredients = mRoot.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for(int i=0; i<ingredients.length(); i++){
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, mAlsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
