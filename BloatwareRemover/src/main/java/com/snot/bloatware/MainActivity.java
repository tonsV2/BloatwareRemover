package com.snot.bloatware;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends TabsFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.addTab("main", getString(R.string.tab_bloatware_title), BloatApplicationsFragment.class);
		this.addTab("hist", getString(R.string.tab_system_apps_title), SystemApplicationsFragment.class);

		restoreFromSavedInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

