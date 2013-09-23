package com.snot.bloatware;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.InterruptedException;
import java.util.concurrent.TimeoutException;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import android.net.Uri;
import android.content.Intent;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;
import com.stericson.RootTools.exceptions.RootDeniedException;

import com.snot.bloatware.loader.AppEntry;


class AppUtils
{
	private static final String TAG = "AppUtils";
	private static final String MOUNT_RW = "mount -o remount,rw -t rfs /dev/stl5 /system;\n";
	private static final String MOUNT_RO = "mount -o remount,ro -t rfs /dev/stl5 /system;\n";


	public static void unmarkAsBloat(Context context, AppEntry appEntry)
	{
		String to = context.getString(R.string.mark_bloat_email_to);
		String subject = context.getString(R.string.unmark_bloat_email_subject);
		String message = appEntry.getLabel() + "\n";
		message += appEntry.getApplicationInfo().packageName + "\n";
		message += appEntry.getApplicationInfo().sourceDir;
		composeMail(context, to, subject, message);
	}

	public static void markAsBloat(Context context, AppEntry appEntry)
	{
		String to = context.getString(R.string.mark_bloat_email_to);
		String subject = context.getString(R.string.mark_bloat_email_subject);
		String message = appEntry.getLabel() + "\n";
		message += appEntry.getApplicationInfo().packageName + "\n";
		message += appEntry.getApplicationInfo().sourceDir;
		composeMail(context, to, subject, message);
	}

	private static void composeMail(Context context, String to, String subject, String message)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, message);
		intent.setType("message/rfc822");
		context.startActivity(intent);
	}

	public static void info(Context context, final AppEntry appEntry)
	{
		String packageName = appEntry.getApplicationInfo().packageName;
		try
		{
			Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setData(Uri.parse("package:" + packageName));
			context.startActivity(intent);
		}
		catch(ActivityNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void execRoot(String cmd)
	{
		Command command = new Command(0, MOUNT_RW, cmd, MOUNT_RO)
		{
			@Override
			public void output(int id, String line)
			{
				Log.v(TAG, id + ":" + line);
			}
		};
		try
		{
			RootTools.getShell(true).add(command).waitForFinish();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(TimeoutException e)
		{
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		catch(RootDeniedException e)
		{
			e.printStackTrace();
		}
	}

	public static void defrostSystemApp(Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String MV_APP = "mv " + app + " " + app.replace(".frozen", "") + ";\n";
		execRoot(MV_APP);
	}

	public static void freezeSystemApp(final Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String MV_APP = "mv " + app + " " + app + ".frozen" + ";\n";
		execRoot(MV_APP);
	}

	public static void deleteSystemApp(final Context context, final AppEntry appEntry)
	{
		final String app = appEntry.getApplicationInfo().sourceDir;
		final String RM_APP = "rm -rf " + app + ";\n";
		execRoot(RM_APP);
	}
}

