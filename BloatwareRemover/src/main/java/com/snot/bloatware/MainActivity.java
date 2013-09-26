package com.snot.bloatware;

import android.content.DialogInterface;
import android.content.Context;
import android.app.AlertDialog;
import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.content.SharedPreferences;


import com.stericson.RootTools.RootTools;;

public class MainActivity extends TabsFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		checkRoot();

		this.addTab(getString(R.string.tab_bloatware_title), new BloatApplicationsFragment());
		this.addTab(getString(R.string.tab_system_apps_title), new SystemApplicationsFragment());
		this.addTab(getString(R.string.tab_frozen_title), new FrozenApplicationsFragment());

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void checkRoot()
	{
		final SharedPreferences settings = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		final String ROOT_DIALOG_SHOW = "dont_show";
		if(!RootTools.isRootAvailable() && settings.getBoolean(ROOT_DIALOG_SHOW, true))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			View view = inflater.inflate(R.layout.dialog_root_check_checkbox, null);
			builder.setView(view);
			final CheckBox dontShowAgain = (CheckBox)view.findViewById(R.id.dont_show_again);

			builder.setTitle(getString(R.string.dialog_noroot_title))
			.setMessage(getString(R.string.dialog_noroot_message))
			.setCancelable(false)
			.setPositiveButton(getString(R.string.dialog_noroot_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if(dontShowAgain.isChecked())
						{
							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean(ROOT_DIALOG_SHOW, false);
							editor.commit();
						}
					}
				}
			).show();
		}
	}
}

