package com.snot.bloatware;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;;

public class MainActivity extends TabsFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: don't show each time the phone is rotated... how?
		if(!RootTools.isRootAvailable())
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.dialog_noroot_title))
			.setMessage(getString(R.string.dialog_noroot_message))
			.setCancelable(false)
			.setPositiveButton(getString(R.string.dialog_noroot_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					//do things
					}
				}
			).show();
		}

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

