package net.fakult.triviatour;

/**
 * Created by jfakult on 12/7/15.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
	private String[] tabs = {"Recent", "Trending", "Completed"};
	private Context context;

	public TabsPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	public TabsPagerAdapter(FragmentManager fm, Context context)
	{
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int index)
	{
		switch (index)
		{
			case 0:
				// Top Rated fragment activity
				return new RecentToursFragment();
			case 1:
				return new PopularToursFragment();
			case 2:
				return new CompletedToursFragment();
		}

		return null;
	}

	@Override
	public int getCount()
	{
		// get item count - equal to number of tabs
		return tabs.length;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		// Generate title based on item position
		return tabs[position];
	}
}
