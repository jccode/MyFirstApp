package com.example.myfirstapp.listviewtutorial.ex;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class ContextualActionListActivity extends ListActivity {
	
	private ActionMode mActionMode;
	private int selectedItem = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(mActionMode != null) {
					return false;
				}
				selectedItem = position;
				
				mActionMode = ContextualActionListActivity.this.startActionMode(mActionModeCallback);
				view.setSelected(true);
				return true;
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contextual_action_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.row_select_menu_item1:
				Toast.makeText(ContextualActionListActivity.this, String.valueOf(selectedItem), Toast.LENGTH_SHORT).show();
				mode.finish();
				return true;

			default:
				return false;
			}
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.rowselection, menu);
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			selectedItem = -1;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
	};
}
