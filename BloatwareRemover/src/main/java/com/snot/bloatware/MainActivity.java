package com.snot.bloatware;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;;

public class MainActivity extends TabsFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String msg = "";
		if (RootTools.isRootAvailable())
		{
			msg = "Root!";
		}
		else
		{
			msg = "No root!";
		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

		this.addTab("bloat", getString(R.string.tab_bloatware_title), BloatApplicationsFragment.class);
		this.addTab("sys", getString(R.string.tab_system_apps_title), SystemApplicationsFragment.class);
		this.addTab("frozen", getString(R.string.tab_frozen_title), FrozenApplicationsFragment.class);

		restoreFromSavedInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

