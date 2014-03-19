package com.example.myfirstapp.listviewtutorial.ex;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.example.myfirstapp.R;

public class SimpleCursorAdapterActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, 
				new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME}, 
				new int[] {android.R.id.text1, android.R.id.text2}, 0);
		setListAdapter(mAdapter);
		
		// Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
		getLoaderManager().initLoader(0, null, this);
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
		getMenuInflater().inflate(R.menu.simple_cursor_adapter, menu);
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.Contacts._ID, 
				ContactsContract.Contacts.DISPLAY_NAME
		};
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ ("1") + "'";
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		
		return new CursorLoader(this, uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
