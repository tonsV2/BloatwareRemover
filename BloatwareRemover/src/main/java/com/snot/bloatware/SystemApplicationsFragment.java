package com.snot.bloatware;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class SystemApplicationsFragment extends ListFragment {


    public SystemApplicationsFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//	final Context context = getActivity();
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
//            android.R.layout.simple_list_item_2,
//            null,
//            new String[] { History.COL_PHONE_NUMBER, History.COL_TIMESTAMP },
//            new int[] { android.R.id.text1, android.R.id.text2 },
//            0) {
//		@Override
//		public View getView(int position, View view, ViewGroup parent)
//		{
//			View row = super.getView(position, view, parent);
//			TextView text1 = (TextView)row.findViewById(android.R.id.text1);
//			TextView text2 = (TextView)row.findViewById(android.R.id.text2);
//
//			Cursor c = getCursor();
//			c.moveToPosition(position);
//			String phoneNumber = c.getString(c.getColumnIndex(History.COL_PHONE_NUMBER));
//			long timeStamp = c.getLong(c.getColumnIndex(History.COL_TIMESTAMP));
//			String name = MainActivity.getContactName(context, phoneNumber);
//
//			text1.setText(name);
//			Date date = new java.util.Date((long)timeStamp*1000);
//			text2.setText(date.toString());
//
//			return row;
//		}
//	    };
//
//	setListAdapter(adapter);
//
//        // Load the content
//        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
//            @Override
//            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		String sortOrder = History.COL_TIMESTAMP + " DESC";
//                return new CursorLoader(getActivity(), Provider.URI_HISTORY, History.FIELDS, null, null, sortOrder);
//            }
//
//            @Override
//            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
//                ((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
//            }
//
//            @Override
//            public void onLoaderReset(Loader<Cursor> arg0) {
//                ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
//            }
//        });
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
	// get cursor
	Cursor c = ((SimpleCursorAdapter)list.getAdapter()).getCursor();
	// move to the desired position
	c.moveToPosition(position);
	// pass it to our history object
//	History history = new History(c);
//	// create geo uri
//	Uri uri = Uri.parse("geo:0,0?q=" + history.latitude + "," + history.longitude + "&z=10");
//	// create intent
//	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//	// launch intent
//	startActivity(intent);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//	inflater.inflate(R.menu.exercise_list, menu);
//    }
}

