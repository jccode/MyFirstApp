package com.example.myfirstapp.listviewtutorial.ex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class UndoDemoActivity extends Activity {
	
	private View undobarContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_undo_demo);
		setupActionBar();
		String[] values = new String[] { "Ubuntu", "Android", "iPhone",
		        "Windows", "Ubuntu", "Android", "iPhone", "Windows" };
		ListView l = (ListView) findViewById(R.id.undo_demo_listview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		l.setAdapter(adapter);
		
		undobarContainer = findViewById(R.id.undo_demo_undobar);
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
		getMenuInflater().inflate(R.menu.undo_demo, menu);
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
			
		case R.id.action_undo_demo_delete:
			showUndoBar();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showUndoBar() {
		undobarContainer.setVisibility(View.VISIBLE);
		undobarContainer.setAlpha(1);
		undobarContainer.animate().alpha(0.4f).setDuration(5000)
			.withEndAction(new Runnable() {
				
				@Override
				public void run() {
					undobarContainer.setVisibility(View.GONE);
				}
			});
	}

	public void onClick(View view) {
		Toast.makeText(this, "Deletion undone", Toast.LENGTH_SHORT).show();
		undobarContainer.setVisibility(View.GONE);
	}
}
