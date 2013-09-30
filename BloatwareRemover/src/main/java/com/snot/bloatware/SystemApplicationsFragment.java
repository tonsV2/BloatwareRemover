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
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.snot.bloatware.loader.AppEntry;
import com.snot.bloatware.loader.SysAppListLoader;


public class SystemApplicationsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<AppEntry>> {

	private AppListAdapter mAdapter;
	private static final int LOADER_ID = 2;

	private int position;
	private AppEntry appEntry;

	public SystemApplicationsFragment() {
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
	public void onListItemClick(ListView listView, View view, int position, long id) {
		this.position = position;
		this.appEntry = (AppEntry)getListView().getItemAtPosition(position);
		Toast.makeText(getActivity(), position + ":" + appEntry.toString(), Toast.LENGTH_SHORT).show();

		View childView = listView.getChildAt(position);
		if(childView != null) {
			listView.showContextMenuForChild(childView);
		}
		else
		{
			Toast.makeText(getActivity(), "childView == null", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, view, menuInfo);
		menu.setHeaderTitle(getString(R.string.header_title));
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu_system_applications, menu);
	}

	public boolean onContextItemSelected(MenuItem item)
	{
	if (getUserVisibleHint()) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		AppEntry appEntry_should_be_working = (AppEntry)getListView().getItemAtPosition(info.position);
		Toast.makeText(getActivity(), info.position + ":" + appEntry_should_be_working.toString(), Toast.LENGTH_SHORT).show();

		switch(item.getItemId())
		{
			case R.id.info:
				AppUtils.info(getActivity(), appEntry);
				return true;
			case R.id.mark_bloat:
				AppUtils.markAsBloat(getActivity(), appEntry);
				return true;
			case R.id.uninstall:
				uninstall(appEntry);
				return true;
			case R.id.freeze:
				AppUtils.freezeSystemApp(getActivity(), appEntry);
				return true;
		}
//		return super.onContextItemSelected(item);
		return true;
	} else {
		return false;
	}
	}

	private void uninstall(final AppEntry appEntry)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getString(R.string.dialog_delete_title))
		.setMessage(getActivity().getString(R.string.dialog_delete_message))
		.setPositiveButton(getActivity().getString(R.string.dialog_delete_positive_button), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					AppUtils.deleteSystemApp(getActivity(), appEntry);
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
		return new SysAppListLoader(getActivity());
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

