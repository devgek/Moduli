package com.gek.and.moduli.util;

import android.app.Activity;

import com.gek.and.moduli.ModuliApp;
import com.gek.and.moduli.R;

public class SemesterUtil {

	public static int getSemesterColor(Activity activity) {
		boolean winter = ModuliApp.getApp(activity).isWinter();
		return winter ? activity.getResources().getColor(R.color.background_winter) : activity.getResources().getColor(R.color.background_summer);
	}

}
