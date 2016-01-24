package net.fakult.triviatour;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class StoreScreen extends AppCompatActivity
{
	private ViewPager viewPager;
	private StoreTabsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_screen);
		Toolbar toolbar = (Toolbar) findViewById(R.id.storeToolbar);
		setSupportActionBar(toolbar);

		toolbar.setTitle("Package Browser");

		mAdapter = new StoreTabsAdapter(getSupportFragmentManager(), StoreScreen.this);

		viewPager = (ViewPager) findViewById(R.id.storeViewPager);
		viewPager.setAdapter(mAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.store_sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d("Query", query);
			//onSearchRequested
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_store_screen, menu);

		getMenuInflater().inflate(R.menu.menu_store_screen, menu);
		// Retrieve the SearchView and plug it into SearchManager
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
		SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				//Query server and whatnot

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				return false;
			}
		};

		searchView.setOnQueryTextListener(listener);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_search)
		{
			onSearchRequested();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
