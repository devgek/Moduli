package com.gek.and.moduli;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gek.and.moduli.db.DBCategoryAdapter;
import com.gek.and.moduli.db.DBModuleAdapter;
import com.gek.and.moduli.db.ModuliDBOpenHelper;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.model.factory.CategoryFactory;
import com.gek.and.moduli.model.factory.ModuleFactory;
import com.gek.and.moduli.util.FileUtil;

public class ModuliApp extends Application {
	private static final String TAG = "Moduli::";
	private boolean winter = true;
	private boolean selection = true;
	private String activeBook;
	
	public boolean isWinter() {
		return winter;
	}
	public void setWinter(boolean winter) {
		this.winter = winter;
	}
	public boolean isSelection() {
		return selection;
	}
	public void setSelection(boolean selection) {
		this.selection = selection;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences settings = getSettings();
		String activeBook = settings.getString(AppConstants.SETTING_ACTIVE_BOOK, "");
		int versionCode = settings.getInt(AppConstants.SETTING_VERSION_CODE, 0);
		
		String configuredActiveBook = getResources().getString(R.string.active_book);
		int versionCodeManifest = getVersionCode();
		
		if (!activeBook.equals(configuredActiveBook) || versionCode != versionCodeManifest) {
			loadActiveBookToDatabase(configuredActiveBook);
			Editor editor = settings.edit();
			editor.putString(AppConstants.SETTING_ACTIVE_BOOK, configuredActiveBook);
			editor.putInt(AppConstants.SETTING_VERSION_CODE, versionCodeManifest);
			editor.commit();
		}
	}
	
	private int getVersionCode() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private boolean loadActiveBookToDatabase(String configuredActiveBook) {
		try {
			InputStream is = getResources().getAssets().open(configuredActiveBook + ".json");
			JSONObject jsonObject = new JSONObject(FileUtil.readContent(is));
			
			String title = jsonObject.getString("title");
			String school = jsonObject.getString("school");
			JSONArray categories = jsonObject.getJSONArray("categories");
			JSONArray modules = jsonObject.getJSONArray("modules");
			
			ModuliDBOpenHelper helper = new ModuliDBOpenHelper(this);
			SQLiteDatabase db = helper.getWritableDatabase();
			db.setVersion(db.getVersion() + 1);
			
			DBCategoryAdapter categoryAdapter = new DBCategoryAdapter(this);
			for (int i = 0; i < categories.length(); i++) {
				categoryAdapter.insertCategory(CategoryFactory.fromJSON(categories.getJSONObject(i)));
			}
			
			DBModuleAdapter moduleAdapter = new DBModuleAdapter(this);
			for (int i = 0; i < modules.length(); i++) {
				Module module = ModuleFactory.fromJSON(modules.getJSONObject(i));
				if (!module.isComment()) {
					moduleAdapter.insertModule(module);
				}
			}
			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void logJson(JSONObject jsonObject) {
		Log.i(TAG, "logJson");
		try {
			String title = jsonObject.getString("title");
			String school = jsonObject.getString("school");
			JSONArray categories = jsonObject.getJSONArray("categories");
			JSONArray modules = jsonObject.getJSONArray("modules");
			
			Log.i(TAG, "title: " + title);
			Log.i(TAG, "school: " + school);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public SharedPreferences getSettings() {
		return getSharedPreferences(AppConstants.SETTINGS_NAME, Activity.MODE_PRIVATE);
	}
	
	public static ModuliApp getApp(Activity activity) {
		ModuliApp app = (ModuliApp) activity.getApplication();
		return app;
	}
}
