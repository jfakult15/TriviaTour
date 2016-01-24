package net.fakult.triviatour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

public class HomeScreen extends AppCompatActivity
{
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

		initializeInternalStorage(HomeScreen.this);

		toolbar.setTitle("Trivia Tour");
		toolbar.setBackground(new ColorDrawable(Color.DKGRAY));

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), HomeScreen.this);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(mAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
			{
                Intent i = new Intent(HomeScreen.this, StoreScreen.class);
				startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
        if (id == R.id.action_settings)
		{
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	private void initializeInternalStorage(Context c)
	{
		File recent = c.getDir("recent", Context.MODE_PRIVATE);
		if (!recent.exists())
		{
			recent.mkdirs();
		}

		File completed = c.getDir("recent", Context.MODE_PRIVATE);
		if (!recent.exists())
		{
			recent.mkdirs();
		}
	}
}
