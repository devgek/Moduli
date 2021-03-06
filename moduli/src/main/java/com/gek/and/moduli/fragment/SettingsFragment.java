package com.gek.and.moduli.fragment;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.gek.and.moduli.R;
import com.gek.and.moduli.settings.DefaultSharedPreferenceChangeListener;

public class SettingsFragment extends PreferenceFragment {
	OnSharedPreferenceChangeListener prefListener = new DefaultSharedPreferenceChangeListener(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
		((DefaultSharedPreferenceChangeListener) prefListener).initSummaries(getPreferenceScreen());
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(prefListener);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(prefListener);
	}

}
