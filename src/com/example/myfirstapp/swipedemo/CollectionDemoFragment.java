package com.example.myfirstapp.swipedemo;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapp.R;

public class CollectionDemoFragment extends Fragment {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.activity_collection_demo,
				container, false);
		FragmentActivity activity = getActivity();
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getFragmentManager());
		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		

		final ActionBar actionBar = activity.getActionBar();
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// super.onPageSelected(position);
						// actionBar.setSelectedNavigationItem(position);
					}

				});

		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setDisplayHomeAsUpEnabled(false);

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

		/*
		for (int i = 0; i < mDemoCollectionPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText("Tab " + (i + 1))
					.setTabListener(tabListener));
		}
		*/
		

		return view;
	}
	

}
