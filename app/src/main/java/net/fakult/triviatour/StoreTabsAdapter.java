package net.fakult.triviatour;

/**
 * Created by jfakult on 12/7/15.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//Remember: Needs to get location to sort by what you can get to
public class StoreTabsAdapter extends FragmentPagerAdapter
{
	private String[] tabs = {"Popular", "Categories", "New"};
	private Context context;

	public StoreTabsAdapter(FragmentManager fm)
	{
		super(fm);
	}

	public StoreTabsAdapter(FragmentManager fm, Context context)
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
				return new StorePopularFragment();
			case 1:
				return new StoreCategoriesFragment();
			case 2:
				return new StoreNewFragment();
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
		return tabs[position];
	}
}

