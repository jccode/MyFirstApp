package com.example.myfirstapp.swipedemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

	public DemoCollectionPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new DemoObjectFragment();
		Bundle args = new Bundle();
		args.putInt(DemoObjectFragment.ARG_OBJECT, i+1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// swipe view count
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "OBJECT " + (position + 1); 
	}
	
}
