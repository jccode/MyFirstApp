package com.example.myfirstapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myfirstapp.actionbar.ActionBarFragment;
import com.example.myfirstapp.bitmap.BitmapFragment;
import com.example.myfirstapp.helloworld.HelloWorldFragment;
import com.example.myfirstapp.killps.KillPsFragment;
import com.example.myfirstapp.listviewtutorial.ListViewTutorialFragment;
import com.example.myfirstapp.password.PwFragment;
import com.example.myfirstapp.swipedemo.CollectionDemoFragment;

public class MainActivity extends ActionBarActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mActionBar = getSupportActionBar();
		
		mTitle = mDrawerTitle = getTitle();

		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				
				mActionBar.setTitle(mTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				mActionBar.setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		if (savedInstanceState == null) {
            selectItem(0);
        }
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//setupActionBar();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// pass the event to ActionBarDrawerToggle, if it return true.
		// then it has handle the app icon touch event
		if(mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				
				// NavUtils.navigateUpFromSameTask(this);
				return true;
				
			default: 
				return super.onOptionsItemSelected(item);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}

	}

	public void selectItem(int position) {
		resetActionBar();
		
		Fragment fragment = getReplaceFragment(position);
		if(fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
		}
		
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	private Fragment getReplaceFragment(int position) {
		Fragment fragment = null;
		switch(position) {
			case 0:
				fragment = new HelloWorldFragment();
				break;
			case 1:
				fragment = new CollectionDemoFragment();
				break;
			case 2:
				fragment = new ActionBarFragment();
				break;
			case 3:
				fragment = new FragmentLayout();
				break;
			case 4:
				fragment = new BitmapFragment();
				break;
			case 5:
				fragment = new PwFragment();
				break;
			case 6:
				fragment = new KillPsFragment();
				break;
			case 7:
				fragment = new ListViewTutorialFragment();
				break;
			default:
				;
		}
		
		return fragment;
	}
	
	private void resetActionBar() {
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		mActionBar.setTitle(mTitle);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// sync the toggle state after onRestoreInstanceState has occurred
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
}
