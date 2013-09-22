package com.snot.bloatware;

import java.io.DataOutputStream;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;


class AppUtils
{
	private static final String MOUNT_RW = "mount -o remount,rw -t rfs /dev/stl5 /system; \n";
	private static final String MOUNT_RO = "mount -o remount,ro -t rfs /dev/stl5 /system; \n";

	public static void unfreezeSystemApp(Context context, String app)
	{
		final String MV_APP = "mv " + app + ".frozen " + app + "; \n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, MV_APP, Toast.LENGTH_SHORT).show();
			os.writeBytes(MV_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void freezeSystemApp(Context context, String app)
	{
		final String MV_APP = "mv " + app + " " + app + ".frozen" + "; \n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, MV_APP, Toast.LENGTH_SHORT).show();
			os.writeBytes(MV_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void deleteSystemApp(Context context, String app)
	{
		final String RM_APP = "rm -rf " + app + "; \n";
		Process process;
		try
		{
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(MOUNT_RW);
			Toast.makeText(context, RM_APP, Toast.LENGTH_SHORT).show();
			//os.writeBytes(RM_APP);
			os.writeBytes(MOUNT_RO);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

