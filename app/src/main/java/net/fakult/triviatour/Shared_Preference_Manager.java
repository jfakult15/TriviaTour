package net.fakult.triviatour;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jfakult on 1/24/16.
 */
public class Shared_Preference_Manager
{
	public void saveKeyValuePairString(String key, String value, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public String readSavedKeyValueString(String key, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		return settings.getString(key, "");
	}

	public void saveKeyValuePairBoolean(String key, Boolean value, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public Boolean readSavedKeyValueBoolean(String key, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		return settings.getBoolean(key, false);
	}

	public void saveKeyValuePairInt(String key, int value, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public int readSavedKeyValueInt(String key, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		return settings.getInt(key, -1);
	}

	public void saveKeyValuePairLong(String key, long value, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public long readSavedKeyValueLong(String key, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences(key, 0);
		return settings.getLong(key, -1);
	}

	public ArrayList<String> getRecentEvents(int numPackages, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences("recent_packages", 0);
		String allIds = settings.getString("recent_packages", "");

		if (allIds.length() == 0)
		{
			return null;
		}

		ArrayList<String> ids = (ArrayList<String>) Arrays.asList(allIds.split(":"));
		return (ArrayList<String>) ids.subList(0, numPackages);
	}

	//Use this to set id as the most recently used
	public void updateRecentEventsList(int id, Context c)
	{
		SharedPreferences settings = c.getSharedPreferences("recentPackages", 0);
		SharedPreferences.Editor editor = settings.edit();

		if (settings.contains("recent_packages"))
		{
			String recents = settings.getString("recentPackages", "");
			String[] ids = recents.split(":");
			ArrayList<String> newIds = new ArrayList<String>();

			newIds.add(id+"");

			for (int i=0; i<ids.length; i++)
			{
				if (!(id + "").equals(ids[i]))
				{
					newIds.add(ids[i]);
				}
			}

			String idString = TextUtils.join(":", newIds);
			Log.d("Id String", idString);
			editor.putString("recent_packages", idString);
		}
		else
		{
			editor.putString("recent_packages", id+"");
			editor.apply();
		}
	}
}
