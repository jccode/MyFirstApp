package com.example.myfirstapp.actionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class ActionBarFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// explicitly tell to create optionmenu.
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.fragment_actionbar_actions, menu);
		
		/*
		MenuItem item = menu.findItem(R.id.action_search);
		View searchView = SearchViewCompat.newSearchView(getActivity());
		MenuItemCompat.setActionView(item, searchView);
		*/
		
		/*
		MenuItem item = menu.add("Search");
		item.setIcon(android.R.drawable.ic_menu_search);
		MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS 
				| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		final View searchView = SearchViewCompat.newSearchView(getActivity());
		if(searchView != null) {
			SearchViewCompat.setOnQueryTextListener(searchView, new OnQueryTextListenerCompat() {
				
			});
			SearchViewCompat.setOnCloseListener(searchView, new OnCloseListenerCompat() {
			});
			MenuItemCompat.setActionView(item, searchView);
		}
		*/
		
		// sprinner
		initDropDownList();
	}
	
	/**
	 * sprinner
	 */
	private void initDropDownList() {
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.nav_spinner, 
				R.layout.simple_spinner_dropdown_item); 
//		android.R.layout.simple_spinner_dropdown_item); 
		ActionBar mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
		OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		// enable action bar's drop-down list
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mActionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
		mActionBar.setDisplayShowTitleEnabled(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_actionbar_test, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.action_search:
				// do nothing
				
				return super.onOptionsItemSelected(item);
				
			case R.id.action_mail: 
				Toast.makeText(getActivity(), "send mail.", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
