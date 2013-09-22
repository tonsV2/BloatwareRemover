package com.snot.bloatware;

import java.io.DataOutputStream;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;
import android.util.Log;

import com.snot.bloatware.loader.AppEntry;

class AppUtils
{
	private static final String TAG = "AppUtils";
	private static final String MOUNT_RW = "mount -o remount,rw -t rfs /dev/stl5 /system;\n";
	private static final String MOUNT_RO = "mount -o remount,ro -t rfs /dev/stl5 /system;\n";

	public static void defrostSystemApp(Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String MV_APP = "mv " + app + " " + app.replace(".frozen", "") + ";\n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, MV_APP, Toast.LENGTH_SHORT).show();
			Log.v(TAG, MV_APP);
			os.writeBytes(MV_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void freezeSystemApp(final Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String MV_APP = "mv " + app + " " + app + ".frozen" + ";\n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, MV_APP, Toast.LENGTH_SHORT).show();
			Log.v(TAG, MV_APP);
			os.writeBytes(MV_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void deleteSystemApp(final Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String RM_APP = "rm -rf " + app + ";\n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, RM_APP, Toast.LENGTH_SHORT).show();
			Log.v(TAG, RM_APP);
			//os.writeBytes(RM_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

