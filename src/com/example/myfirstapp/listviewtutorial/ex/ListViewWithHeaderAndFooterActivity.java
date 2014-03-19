package com.example.myfirstapp.listviewtutorial.ex;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class ListViewWithHeaderAndFooterActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		
		String[] names = new String[] { "Linux", "Windows7", "Eclipse", "Suse",
		        "Ubuntu", "Solaris", "Android", "iPhone", "Linux", "Windows7",
		        "Eclipse", "Suse", "Ubuntu", "Solaris", "Android", "iPhone" };
		
		TextView header = new TextView(this);
		header.setText("header");
		TextView footer = new TextView(this);
		footer.setText("footer");
		
		ListView listView = getListView();
		listView.addHeaderView(header);
		listView.addFooterView(footer);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
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
		getMenuInflater()
				.inflate(R.menu.list_view_with_header_and_footer, menu);
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

}
