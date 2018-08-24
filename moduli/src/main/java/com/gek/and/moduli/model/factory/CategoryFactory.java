package com.gek.and.moduli.model.factory;

import org.json.JSONException;
import org.json.JSONObject;

import com.gek.and.moduli.model.Category;

public class CategoryFactory {
	public static Category fromJSON(JSONObject json) throws JSONException {
		Category category = new Category();
		category.setKey(json.getString("key"));
		category.setName(json.getString("name"));
		category.setWinter(json.getBoolean("winter"));
		category.setSummer(json.getBoolean("summer"));

		return category;
	}
}
