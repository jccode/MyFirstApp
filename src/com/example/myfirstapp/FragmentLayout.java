package com.example.myfirstapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentLayout extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_layout, container, false);
		
		getFragmentManager().beginTransaction()
			.replace(R.id.titles, new TitlesFragment())
			.commit();
		
		return view;
	}

	
	public static class TitlesFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;
		private String[] mTitles;
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			// populate list with our static array titles
			mTitles = getResources().getStringArray(R.array.planets_array);
			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, mTitles));
			
			// check to see if we have a frame in which to embed the details
			// fragment directly in the containing UI.
			View detailFrame = getActivity().findViewById(R.id.details);
			
			mDualPane = detailFrame != null && detailFrame.getVisibility() == View.VISIBLE;
			
			if(savedInstanceState != null) {
				// Restore last state for checked position.
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			}
			
			if(mDualPane) {
				// In dual-pane mode, the list view highlights the selected item.
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				showDetails(mCurCheckPosition);
			}
		}
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("curChoice", mCurCheckPosition);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showDetails(position);
		}
		
		private void showDetails(int index) {
			mCurCheckPosition = index;
			if(mDualPane) {
				// we can display everything in-place with fragments, so update
				// the list to highlight the selected item and show the data
				getListView().setItemChecked(index, true);
				
				// Check what fragment is currently shown, replace if needed.
				DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
				if(details == null || details.getShownIndex() != index) {
					details = DetailsFragment.newInstance(index);
					
					// Execute a transaction, replacing any existing fragment
					// with this one inside the frame.
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.replace(R.id.details, details);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			} else {
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailsActivity.class);
				intent.putExtra("index", index);
				startActivity(intent);
			}
		}
	}
	

	public static class DetailsActivity extends FragmentActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.fragment_layout_details);
			
			if(getResources().getConfiguration().orientation == 
					Configuration.ORIENTATION_LANDSCAPE) {
				// If the screen is now in landscape mode, we can show the
				// dialog in-line with the list so we don't need this activity.
				finish();
				return;
			}
			
			if(savedInstanceState == null) {
				// During initial setup, plug in the details fragment.
				Fragment details = new DetailsFragment();
				details.setArguments(getIntent().getExtras());
				getSupportFragmentManager().beginTransaction()
//					.add(android.R.id.content, details).commit();
					.add(R.id.content, details).commit();
			}
		}
		
	}

	public static class DetailsFragment extends Fragment {
		
		/**
		 * Create a new instance of DetailsFragment, initialized to show the
		 * text at 'index'.
		 */
		public static DetailsFragment newInstance(int index) {
			DetailsFragment f = new DetailsFragment();
			
			// Supply index input as an argument
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);
			
			return f;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if(container == null) {
				// We have different layouts, and in one of them this
				// fragment's containing frame doesn't exist. The fragment
				// may still be created from its saved state, but there is
				// no reason to try to create its view hierarchy because it
				// won't be displayed. Note this is not needed -- we could
				// just run the code below, where we would create and return
				// the view hierarchy; it would just never be used.
				return null;
			}
			
			ScrollView scroller = new ScrollView(getActivity());
			TextView text = new TextView(getActivity());
			int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
					getActivity().getResources().getDisplayMetrics());
			text.setPadding(padding, padding, padding, padding);
			scroller.addView(text);
			text.setText("Content: "+getShownIndex());
			return scroller;
		}
		
		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}
	}
}

