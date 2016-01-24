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

public class PopularToursFragment extends Fragment
{
	private TriviaGlobal triviaGlobal;
	private TableLayout table;

    public PopularToursFragment()
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
		View view = inflater.inflate(R.layout.fragment_popular_tours, container, false);

		triviaGlobal = new TriviaGlobal();
		table = (TableLayout) (view.findViewById(R.id.popularToursTable));

		//loadPopularTours(3, inflater);
		loadPackagesAsync(3, inflater, view);

		return view;
    }

	private void loadPopularTours(int numTours, ArrayList<TriviaPackage> packages, LayoutInflater inflater)
	{
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

	private void loadPackagesAsync(int numPackages, LayoutInflater inflater, View view)
	{
		ArrayList<TriviaPackage> packages = triviaGlobal.readRecentPackages(numPackages, getActivity());

		if (packages == null || packages.size() == 0)
		{
			TextView noneLoaded = (TextView) inflater.inflate(R.layout.no_packages_found, null);
			table.addView(noneLoaded);

			return;
		}

		loadPopularTours(numPackages, packages, inflater); //on asynch exec complete
		view.findViewById(R.id.loadingTours).setVisibility(View.GONE);
		view.findViewById(R.id.popularToursTable).setVisibility(View.VISIBLE);
	}
}
