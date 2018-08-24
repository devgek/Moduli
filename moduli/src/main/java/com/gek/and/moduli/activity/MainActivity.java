package com.gek.and.moduli.activity;

import java.io.File;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gek.and.moduli.AppConstants;
import com.gek.and.moduli.CategorySelectionFragment;
import com.gek.and.moduli.ModuliApp;
import com.gek.and.moduli.R;
import com.gek.and.moduli.WeeklyPlanFragment;
import com.gek.and.moduli.async.PDFGeneratorAsyncTask;
import com.gek.and.moduli.db.service.GenerationResult;
import com.gek.and.moduli.db.service.ModuleService;
import com.gek.and.moduli.fragment.SettingsFragment;
import com.gek.and.moduli.type.FragmentType;
import com.gek.and.moduli.type.GenerationResultType;
import com.gek.and.moduli.util.SemesterUtil;

public class MainActivity extends FragmentActivity implements
		android.app.ActionBar.TabListener {
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tabs);

		//Set default values of application settings
		PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.settings, false);
		
		// Set up the action bar to show tabs.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// for each of the sections in the app, add a tab to the action bar.
		ActionBar.Tab tab1 = actionBar.newTab();
		LinearLayout ll1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
		TextView tv1 = (TextView) ll1.findViewById(R.id.tabView);
		tv1.setText(R.string.title_tab1);
		tab1.setCustomView(tv1);
		tab1.setText(R.string.title_tab1);
		tab1.setTabListener(this);
		// View cv1 = new View(this);
		// cv1.setBackgroundColor(getResources().getColor(R.color.background_winter));
		// tab1.setCustomView(cv1);
		// tab1.setCustomView(R.layout.colored_tab);
		// tab1.getCustomView().setBackgroundColor(getResources().getColor(R.color.background_winter));
		// TextView tv1 = (TextView) tab1.getCustomView();
		// tv1.setText(R.string.title_tab1);
		// tv1.setBackgroundColor(getResources().getColor(R.color.background_winter));

		ActionBar.Tab tab2 = actionBar.newTab();
		LinearLayout ll2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
		TextView tv2 = (TextView) ll2.findViewById(R.id.tabView);
		tv2.setText(R.string.title_tab2);
		tab2.setCustomView(tv2);
		tab2.setText(R.string.title_tab2);
		tab2.setTabListener(this);
		// View cv2 = new View(this);
		// cv2.setBackgroundColor(getResources().getColor(R.color.background_summer));
		// tab2.setCustomView(cv2);
		// tab2.setCustomView(R.layout.colored_tab);
		// tab2.getCustomView().setBackgroundColor(getResources().getColor(R.color.background_summer));
		// TextView tv2 = (TextView) tab2.getCustomView();
		// tv2.setText(R.string.title_tab2);
		// tv2.setBackgroundColor(getResources().getColor(R.color.background_summer));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);

		getActionBar().setTitle(R.string.title_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();

		if (ModuliApp.getApp(this).isSelection()) {
			getMenuInflater().inflate(R.menu.main_selection, menu);
		} else {
			getMenuInflater().inflate(R.menu.main_plan, menu);
		}

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_switch_to_plan:
			switch_to_weeklyPlan();
			return true;
		case R.id.action_switch_to_selection:
			switch_to_selection();
			return true;
		case R.id.action_export:
			generateRegistration();
			return true;
		case R.id.action_about:
			showAbout();
			return true;
		case R.id.action_settings:
			editSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void editSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	private void generateRegistration() {
		ModuleService service = ModuleService.getInstance(getApplicationContext());
		GenerationResult genRes = service.generateRegistration();
		
		if (genRes.getResult().equals(GenerationResultType.R_OK)) {
			try {
				createAndSavePdf(genRes, AppConstants.PDF_NAME);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Anmeldung konnte nicht generiert und versendet werden", Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(getApplicationContext(), genRes.getResult().getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private void createAndSavePdf(GenerationResult genRes, String pdfName) {
		Toast.makeText(getApplicationContext(), "Anmeldung wird als PDF-Datei generiert...", Toast.LENGTH_LONG).show();
	
		AsyncTask generatePdf = new PDFGeneratorAsyncTask();
		generatePdf.execute(new Object[]{genRes, this});
	}
	
	public void onPdfGenerationOk() {
//		Intent sendEmail= new Intent(Intent.ACTION_SEND);
//		sendEmail.setType("application/pdf");
//		sendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getExternalFilesDir(null), PDF_NAME))); 
//		
//		startActivity(Intent.createChooser(sendEmail, "Anmeldung senden:"));	
		
		Intent showPdf = new Intent(Intent.ACTION_VIEW);
		showPdf.setDataAndType(Uri.fromFile(new File(getExternalFilesDir(null), AppConstants.PDF_NAME)), "application/pdf");
		showPdf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(showPdf);
	}
	
	public void onPdfGenerationNotOk() {
		Toast.makeText(getApplicationContext(), "Anmeldung konnte nicht generiert werden", Toast.LENGTH_SHORT).show();
	}


	private void showAbout() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void switch_to_selection() {
		ModuliApp.getApp(this).setSelection(true);
		switchToFragment(FragmentType.SELECTION);
	}

	private void switch_to_weeklyPlan() {
		ModuliApp.getApp(this).setSelection(false);
		switchToFragment(FragmentType.WEEKLY_PLAN);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// When the given tab is selected, show the tab contents in the
		// container view.
		if (tab.getPosition() == 0) {
			ModuliApp.getApp(this).setWinter(true);
		} else {
			ModuliApp.getApp(this).setWinter(false);
		}
		tab.getCustomView().setBackgroundColor(
				SemesterUtil.getSemesterColor(this));

		if (ModuliApp.getApp(this).isSelection()) {
			switchToFragment(FragmentType.SELECTION);
		} else {
			switchToFragment(FragmentType.WEEKLY_PLAN);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		tab.getCustomView().setBackgroundColor(getResources().getColor(R.color.my_light_grey1));
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	private void switchToFragment(FragmentType fragmentType) {
		Fragment fragment;
		if (FragmentType.SELECTION.equals(fragmentType)) {
			fragment = new CategorySelectionFragment();
		} else {
			fragment = new WeeklyPlanFragment();
		}

		invalidateOptionsMenu();
		
		getFragmentManager().beginTransaction()
				.replace(R.id.tabs_container, fragment).commit();
	}

}
