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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snot.bloatware.loader.AppEntry;
import com.snot.bloatware.loader.FrozenAppListLoader;

public class FrozenApplicationsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<AppEntry>> {

	private AppListAdapter mAdapter;
	private static final int LOADER_ID = 3;

	private int position;

	public FrozenApplicationsFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		ListView lv = getListView();
		registerForContextMenu(lv);

		mAdapter = new AppListAdapter(getActivity());
		setListAdapter(mAdapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);

// TODO: Store the position before opening the context menu.
// This is needed since menuitem we access in onContextItemSelected isn't populated with this value.
// This probably isnt a really nice solution
		this.position = position;
		getActivity().openContextMenu(list);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, view, menuInfo);
		menu.setHeaderTitle(getString(R.string.header_title));
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu_frozen_applications, menu);
	}

	public boolean onContextItemSelected(MenuItem item)
	{
		AppEntry mAppEntry = (AppEntry)getListView().getItemAtPosition(this.position);
		switch(item.getItemId())
		{
			case R.id.uninstall:
				uninstall(mAppEntry);
				break;
			case R.id.defrost:
				AppUtils.defrostSystemApp(getActivity(), mAppEntry.getApplicationInfo().sourceDir);
				break;
			default:
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
		return new FrozenAppListLoader(getActivity());
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
	inflater.inflate(R.menu.main, menu);
    }
}

