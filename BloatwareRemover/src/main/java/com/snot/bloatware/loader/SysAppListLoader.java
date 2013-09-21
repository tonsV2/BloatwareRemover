package com.snot.bloatware.loader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.snot.bloatware.observer.InstalledAppsObserver;
import com.snot.bloatware.observer.SystemLocaleObserver;

/**
 * An implementation of AsyncTaskLoader which loads a {@code List<AppEntry>}
 * containing all installed applications on the device.
 */
public class SysAppListLoader extends AppListLoader {

	public SysAppListLoader(Context ctx) {
		super(ctx);
	}

  @Override
  public List<AppEntry> loadInBackground() {
    if (DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");

    // Retrieve all installed applications.
    List<ApplicationInfo> allApps = mPm.getInstalledApplications(0);

// Filter away user apps
    List<ApplicationInfo> apps = new ArrayList<ApplicationInfo>();
    for(ApplicationInfo applicationInfo : allApps)
    {
        if(isSystemPackage(applicationInfo))
	{
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

	private boolean isSystemPackage(ApplicationInfo applicationInfo) {
		return((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
	}

}

