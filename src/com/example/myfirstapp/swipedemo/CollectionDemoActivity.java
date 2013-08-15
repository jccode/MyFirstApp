package com.example.myfirstapp.swipedemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.myfirstapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CollectionDemoActivity extends FragmentActivity {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_demo);
		
		final ActionBar actionBar = getActionBar();
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		
		/*
		 */
		mViewPager.setOnPageChangeListener(
			new ViewPager.SimpleOnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					//super.onPageSelected(position);
					actionBar.setSelectedNavigationItem(position);
				}
				
			});
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(false);
		
		/**/
		ActionBar.TabListener tabListener = new TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		for(int i = 0; i < mDemoCollectionPagerAdapter.getCount(); i++) {
			actionBar.addTab(
					actionBar.newTab()
						.setText("Tab " + (i + 1))
						.setTabListener(tabListener));
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
