package com.gek.and.moduli.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;

import com.gek.and.moduli.AppConstants;
import com.gek.and.moduli.ModuliApp;
import com.gek.and.moduli.R;
import com.gek.and.moduli.adapter.ModuleCardAdapter;
import com.gek.and.moduli.db.DBModuleAdapter;
import com.gek.and.moduli.util.SemesterUtil;

public class ModuleSelectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String categoryKey = intent.getStringExtra(AppConstants.INTENT_EXTRA_CATEGORY_KEY);
		String categoryName = intent.getStringExtra(AppConstants.INTENT_EXTRA_CATEGORY_NAME);
		
		setContentView(R.layout.module_list);
		ListView moduleListView = (ListView) findViewById(R.id.module_list_view);
		
		ModuleCardAdapter moduleCardAdapter = new ModuleCardAdapter(getApplicationContext(), R.layout.module, this);
		DBModuleAdapter dbModule = new DBModuleAdapter(this);
		moduleCardAdapter.addAll(dbModule.getModules(categoryKey, ModuliApp.getApp(this).isWinter()));
		
		moduleListView.setAdapter(moduleCardAdapter);
		setTitle(categoryName);
		
//		setting background color of title and list view	
		View titleView = getWindow().findViewById(android.R.id.title);
		titleView.setBackgroundColor(SemesterUtil.getSemesterColor(this));
		moduleListView.setBackgroundColor(SemesterUtil.getSemesterColor(this));
	}

}
