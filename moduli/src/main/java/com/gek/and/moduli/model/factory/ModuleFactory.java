package com.gek.and.moduli.model.factory;

import org.json.JSONException;
import org.json.JSONObject;

import com.gek.and.moduli.model.Category;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.type.BlockType;
import com.gek.and.moduli.type.RatingType;
import com.gek.and.moduli.type.SeasonType;
import com.gek.and.moduli.type.WeekDayType;

public class ModuleFactory {
	public static Module fromJSON(JSONObject json) throws JSONException {
		Module module = new Module();

		module.setCategoryKey(json.getString("catKey"));
		if (!"99".equals(module.getCategoryKey())) {
			module.setTitle(json.getString("title"));
			module.setNumber(json.getString("number"));
			module.setSeason(SeasonType.parse(json.getString("season")));
			module.setLeader(json.getString("leader"));
			module.setWeekDay(WeekDayType.parse(json.getString("day")));
			module.setBlock(BlockType.parse(json.getString("block")));
			module.setRating(RatingType.valueOf(json.getString("rating")));
			module.setDescription(json.getString("desc"));
			module.setAnnotation(json.getString("anno"));
			module.setPrecondition(json.getString("pre"));
			module.setGrading(json.getString("grad"));
		}

		return module;
	}
}
