package com.snot.bloatware.loader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FilenameFilter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.snot.bloatware.observer.InstalledAppsObserver;
import com.snot.bloatware.observer.SystemLocaleObserver;
import com.snot.bloatware.R;

/**
 * An implementation of AsyncTaskLoader which loads a {@code List<AppEntry>}
 * containing all installed applications on the device.
 */
public class FrozenAppListLoader extends AppListLoader {

	private static final String TAG = "FrozenAppListLoader";
	private Context context;

	public FrozenAppListLoader(Context ctx) {
		super(ctx);
		context = ctx;
		Log.v(TAG, "Called!");
	}

  @Override
  public List<AppEntry> loadInBackground() {
    if (DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");

	List<ApplicationInfo> apps = new ArrayList<ApplicationInfo>();
	// Retrive all frozen apps
	// http://stackoverflow.com/questions/15598657/how-to-use-ls-c-command-in-java
	// TODO: make prefix (.frozen) a setting
	String path = "/system/app/";
	String prefix = ".frozen";
	File dir = new File(path);
	File[] files = dir.listFiles();
	PackageManager pm = context.getPackageManager();
	for (int i = 0; i < files.length; i++) {
		String absolutePath = files[i].getAbsolutePath();
		if(absolutePath.endsWith(prefix))
		{
			PackageInfo packageInfo = pm.getPackageArchiveInfo(absolutePath, 0);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			// TODO: isn't it a bug that I manually have to assign the following?
			// Some info here as well... but no solution beside manual assignment.
			// http://stackoverflow.com/questions/5661418/get-apk-file-icon-version-name
			applicationInfo.sourceDir = absolutePath;
			applicationInfo.publicSourceDir = absolutePath;

			Log.v(TAG, "applicationInfo: " + applicationInfo.toString());
			apps.add(applicationInfo);
		}
	}

    if (apps == null) {
      apps = new ArrayList<ApplicationInfo>();
    }

    // Create corresponding array of entries and load their labels.
    List<AppEntry> entries = new ArrayList<AppEntry>(apps.size());
    for (int i = 0; i < apps.size(); i++) {
      AppEntry entry = new AppEntry(this, apps.get(i));
      entry.loadLabel(getContext());
      entries.add(entry);
    }

    // Sort the list.
    Collections.sort(entries, ALPHA_COMPARATOR);

    return entries;
  }
}

