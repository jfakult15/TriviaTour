package net.fakult.triviatour;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jfakult on 1/23/16.
 */
public class TriviaGlobal extends Application
{
	public TriviaGlobal()
	{

	}

	public TriviaPackage readPackage(String packageName)
	{
		return null;
	}

	public void savePackage(TriviaPackage triviaPackage)
	{

	}

	private String encodePackage(TriviaPackage triviaPackage)
	{
		return "encoded-package";
	}

	private TriviaPackage decodePackage(String encodedPackage)
	{
		return null;
	}

	public ArrayList<TriviaPackage> readRecentPackages(int numPackages, Context c)
	{
		ArrayList<TriviaPackage> packages = null;//new ArrayList<TriviaPackage>();

		File dir = c.getDir("recent", MODE_PRIVATE);
		if (!dir.exists())
		{
			Log.e("Dir not found", "Couldn't locate recent packages directory. Package loader signing out, peace.");
			return null;
		}

		//Log.e("ID1", "hi");

		Shared_Preference_Manager sm = new Shared_Preference_Manager();
		ArrayList<String> ids = sm.getRecentEvents(numPackages, c);

		//Log.e("ID", (ids == null) + ": " + ids.size());
		//
		if (ids == null || ids.size() == 0)
		{
			return null;
		}

		File file = null;
		for (String id : ids)
		{
			try
			{
				file = new File(dir.getAbsolutePath() + "/" + id + ".tpkg");

				FileInputStream fin = new FileInputStream(file);
				String data = convertStreamToString(fin);

				TriviaPackage p = decodeFileData(data);

				if (p == null)
				{
					throw new Exception("Unable to parse data for file '" + file.getAbsolutePath() + "'");
				}

				packages.add(p);

				fin.close();
			}
			catch (Exception e)
			{
				Log.e("Package loader error", e.getMessage());
			}
		}

		return packages;
	}

	public ArrayList<TriviaPackage> readCompletedPackages(int numPackages, Context c)
	{
		ArrayList<TriviaPackage> packages = null;

		File dir = c.getDir("complete", MODE_PRIVATE);
		if (!dir.exists())
		{
			Log.e("Dir not found", "Couldn't locate completed packages directory. Package loader signing out, peace.");
			return null;
		}

		Shared_Preference_Manager sm = new Shared_Preference_Manager();

		for (File file : dir.listFiles())
		{
			try
			{
				FileInputStream fin = new FileInputStream(file);
				String data = convertStreamToString(fin);

				TriviaPackage p = decodeFileData(data);

				if (p == null)
				{
					throw new Exception("Unable to parse data for file '" + file.getAbsolutePath() + "'");
				}

				packages.add(p);

				fin.close();
			} catch (Exception e)
			{
				Log.e("Package loader error", e.getMessage());
			}
		}

		return packages;
	}

	public static String convertStreamToString(InputStream is) throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	public TriviaPackage decodeFileData(String data) throws JSONException
	{
		TriviaPackage p = new TriviaPackage();

		JSONObject json = new JSONObject(data);

		//Add stuff here, make sure you have good json formatting

		return p;
	}
}
