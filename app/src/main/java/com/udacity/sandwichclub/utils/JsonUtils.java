package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);

                JSONObject name = jsonObj.getJSONObject("name");
                String mainName = name.getString("mainName");
                JSONArray alias = name.getJSONArray("alsoKnownAs");

                List<String> alsoKnownAs = new ArrayList<>();

                for (int i = 0; i < alias.length(); i++) {
                    alsoKnownAs.add(i, alias.getString(i));
                }

                String origin = jsonObj.getString("placeOfOrigin");

                String description = jsonObj.getString("description");

                String imgUrl = jsonObj.getString("image");

                JSONArray ingredients = jsonObj.getJSONArray("ingredients");

                List<String> ingredientsList = new ArrayList<>();

                for (int i = 0; i < ingredients.length(); i++) {
                    ingredientsList.add(i, ingredients.getString(i));
                }

                sandwich = new Sandwich(mainName, alsoKnownAs, origin, description, imgUrl, ingredientsList);

            } catch (Exception e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

        return sandwich;

    }
}
