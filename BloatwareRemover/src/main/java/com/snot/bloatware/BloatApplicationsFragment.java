package com.snot.bloatware;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snot.bloatware.loader.AppEntry;
import com.snot.bloatware.loader.BloatAppListLoader;

public class BloatApplicationsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<AppEntry>> {

	private AppListAdapter mAdapter;
	private static final int LOADER_ID = 1;

	private int position;

	public BloatApplicationsFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		ListView lv = getListView();
		registerForContextMenu(lv);

		mAdapter = new AppListAdapter(getActivity());
		setEmptyText(getString(R.string.no_system_applications));
		setListAdapter(mAdapter);
		setListShown(false);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);
		AppEntry mAppEntry = (AppEntry)mAdapter.getItem(position);
		//Toast.makeText(getActivity(), mAppEntry.getApplicationInfo().packageName, Toast.LENGTH_SHORT).show();

		this.position = position;
		getActivity().openContextMenu(list);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, view, menuInfo);
		menu.setHeaderTitle(getString(R.string.header_title));
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu_bloat_applications, menu);
	}

	public boolean onContextItemSelected(MenuItem item)
	{
		AppEntry mAppEntry = (AppEntry)getListView().getItemAtPosition(this.position);
		Toast.makeText(getActivity(), mAppEntry.getApplicationInfo().packageName, Toast.LENGTH_SHORT).show();
		switch(item.getItemId())
		{
			case R.id.uninstall:
				uninstall(mAppEntry);
				break;
			case R.id.freeze:
				AppUtils.freezeSystemApp(getActivity(), mAppEntry.getApplicationInfo().sourceDir);
				break;
			default:
				break;
		}
		return super.onContextItemSelected(item);
	}

	private void uninstall(final AppEntry appEntry)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getString(R.string.dialog_delete_title))
		.setMessage(getActivity().getString(R.string.dialog_delete_message))
		.setPositiveButton(getActivity().getString(R.string.dialog_delete_positive_button), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					AppUtils.deleteSystemApp(getActivity(), appEntry.getApplicationInfo().sourceDir);
					//dialog.dismiss();
				}
			}
		)
		.setNegativeButton(getActivity().getString(R.string.dialog_delete_negative_button), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			}
		)
		.show();
	}


	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/
	@Override
	public Loader<List<AppEntry>> onCreateLoader(int id, Bundle args) {
		return new BloatAppListLoader(getActivity());
	}
	
	@Override
	public void onLoadFinished(Loader<List<AppEntry>> loader, List<AppEntry> data) {
		mAdapter.setData(data);

		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}
	
	@Override
	public void onLoaderReset(Loader<List<AppEntry>> loader) {
		mAdapter.setData(null);
	}


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//	inflater.inflate(R.menu.exercise_list, menu);
//    }
}

