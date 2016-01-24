package net.fakult.triviatour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StorePopularFragment extends Fragment
{
	private TableLayout table;
	private LayoutInflater in;

    public StorePopularFragment()
	{
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		in = inflater;
		View view = inflater.inflate(R.layout.fragment_store_popular, container, false);

		(view.findViewById(R.id.loadingPopularStore)).setVisibility(View.GONE);
		table = (TableLayout) (view.findViewById(R.id.storePopularTable));

		table.setVisibility(View.VISIBLE);

		loadPackages();

		return view;
    }

	private void loadPackages()
	{
		String requestUrl = "http://users.wpi.edu/~jtfakult/public/TriviaTour/packages/test2.tpkg";//store/search.php";
		String urlParameters = "requestType=popular";
		new JSONAsyncTask().execute(requestUrl, urlParameters);
	}

	private class JSONAsyncTask extends AsyncTask<String, Void, Boolean>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... urls)
		{
			try
			{
				String requestUrl = urls[0];
				String urlParameters = urls[1];
				//Log.d("Params", requestUrl + ":" + urlParameters);

				byte[] postData = urlParameters.getBytes();
				int    postDataLength = postData.length;

				URL url = new URL(requestUrl);
				HttpURLConnection conn= (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				//conn.setInstanceFollowRedirects(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("charset", "utf-8");
				conn.setRequestProperty("Content-Length", Integer.toString( postDataLength));
				conn.setUseCaches(false);

				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
				writer.write(urlParameters);
				writer.close();
				wr.close();

				BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				String response = "";
				String line = "";

				while ((line = r.readLine()) != null)
				{
					response += line;
				}

				parseResponse(response);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return false;
		}

		protected void onPostExecute(Boolean result)
		{
		}
	}

	private void parseResponse(final String response) //Should be put in TriviaGlobal
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					JSONObject data = new JSONObject(response);

					String name = data.getString("name");
					String desc = data.getString("description");
					String author = data.getString("author");
					String image = data.getString("image");
					long creationDate = data.getLong("uploaded");
					int difficulty = data.getInt("difficulty");
					int numStages = data.getInt("numStages");
					int id = data.getInt("id");
					int numDownloads = data.getInt("downloads");
					boolean hideLocations = data.getBoolean("hideLocations");

					JSONObject stages = data.getJSONObject("stages");

					ArrayList<String> stageNames = new ArrayList<String>();
					ArrayList<String> stageDesc = new ArrayList<String>();
					ArrayList<Location> locs = new ArrayList<Location>();

					for (int i=0; i<numStages; i++)
					{
						JSONObject stageData = stages.getJSONObject(i+"");
						stageNames.add(stageData.getString("name"));
						stageDesc.add(stageData.getString("description"));
						Location l = new Location("");
						l.setLatitude(stageData.getDouble("latitude"));
						l.setLongitude(stageData.getDouble("longitude"));
						locs.add(l);
					}

					//We do this for when we click and need to pass this on to a more detailed page
					final TriviaPackage tPackage = new TriviaPackage(name, desc, author, creationDate, difficulty, numStages, id, numDownloads, hideLocations);
					tPackage.setImage(image);
					tPackage.setStages(locs, stageNames, stageDesc);

					TableRow row = (TableRow) in.inflate(R.layout.package_row, null);

					TextView n = (TextView) row.findViewById(R.id.packageTitle);
					TextView d = (TextView) row.findViewById(R.id.packageDescription);
					ImageView t = (ImageView) row.findViewById(R.id.thumbnail);

					n.setText(name);
					d.setText(desc);

					byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					t.setImageBitmap(decodedByte);

					row.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							Intent i = new Intent(getActivity(), PackageDisplayScreen.class);
							i.putExtra("tpkg", tPackage);
							startActivity(i);
						}
					});

					table.addView(row);
				}
				catch (JSONException e)
				{
					Log.d("Data error", e.getMessage());
				}
			}
		});
	}
}
