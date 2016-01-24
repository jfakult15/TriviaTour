package net.fakult.triviatour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentToursFragment extends Fragment
{
	private TriviaGlobal triviaGlobal;
	private TableLayout table;

    public RecentToursFragment()
	{
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

		//LayoutInflater inflater = getLayoutInflater(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_recent_tours, container, false);

		triviaGlobal = new TriviaGlobal();
		table = (TableLayout) view.findViewById(R.id.recentToursTable);

		loadRecentTours(3, inflater);

        return view;
    }

	private void loadRecentTours(int numTours, LayoutInflater inflater)
	{
		ArrayList<TriviaPackage> packages;// = new TriviaPackage[3];
		packages = triviaGlobal.readRecentPackages(3, getActivity());

		if (packages == null || packages.size() == 0)
		{
			TextView noneLoaded = (TextView) inflater.inflate(R.layout.no_packages_found, null);
			table.addView(noneLoaded);

			return;
		}

		for (int i=0; i<numTours; i++)
		{
			TableRow row = (TableRow) inflater.inflate(R.layout.package_row, null);
			TextView name = (TextView) row.findViewById(R.id.packageTitle);
			TextView description = (TextView) row.findViewById(R.id.packageDescription);
			ImageView image = (ImageView) row.findViewById(R.id.thumbnail);

			name.setText(packages.get(i).getName());
			description.setText(packages.get(i).getDescription());

			table.addView(row);
		}
	}
}
