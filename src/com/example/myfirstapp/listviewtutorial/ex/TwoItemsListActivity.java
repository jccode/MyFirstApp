package com.example.myfirstapp.listviewtutorial.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.example.myfirstapp.R;

public class TwoItemsListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		
		ArrayList<Map<String, String>> list = buildData();
		String[] from = {"name", "purpose"};
		int[] to = {android.R.id.text1, android.R.id.text2};
		SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
		setListAdapter(adapter);
	}

	private ArrayList<Map<String, String>> buildData() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list.add(putData("Android", "Mobile"));
		list.add(putData("Windows7", "Windows7"));
		list.add(putData("iPhone", "iPhone"));
		return list;
	}

	private Map<String, String> putData(String name, String purpose) {
		Map<String, String> item = new HashMap<String, String>();
		item.put("name", name);
		item.put("purpose", purpose);
		return item;
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
		getMenuInflater().inflate(R.menu.two_items_list, menu);
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
