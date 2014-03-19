package com.example.myfirstapp.listviewtutorial.ex;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class ExpandableListActivity extends Activity {
	
	SparseArray<Group> groups = new SparseArray<Group>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list);
		setupActionBar();
		
		createData();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandable_listview);
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
		listView.setAdapter(adapter);
	}

	private void createData() {
		for(int i = 0; i < 5; i++) {
			Group group = new Group("Test"+i);
			for(int j = 0; j < 5; j++) {
				group.children.add("sub item "+j);
			}
			groups.append(i, group);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.expandable_list, menu);
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
	
	
	static class Group {
		String name;
		final List<String> children = new ArrayList<String>();
		public Group(String name) {
			super();
			this.name = name;
		}
	}

	
	static class MyExpandableListAdapter extends BaseExpandableListAdapter {
		
		Activity mContext;
		SparseArray<Group> mGroups;

		public MyExpandableListAdapter(Activity context, SparseArray<Group> groups) {
			this.mContext = context;
			this.mGroups = groups;
		}

		@Override
		public int getGroupCount() {
			return mGroups.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mGroups.get(groupPosition).children.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mGroups.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return mGroups.get(groupPosition).children.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
//			return 0;
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
//			return 0;
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = null;
			if(convertView == null) {
				view = mContext.getLayoutInflater().inflate(R.layout.activity_expandable_list_group, null);
			} else {
				view = convertView;
			}
			Group group = (Group) getGroup(groupPosition);
			((CheckedTextView)view).setText(group.name);
			((CheckedTextView)view).setChecked(isExpanded);
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = null;
			final String children = (String) getChild(groupPosition, childPosition);
			if(convertView == null) {
				view = mContext.getLayoutInflater().inflate(R.layout.activity_expandable_list_group_detail, null);
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(mContext, children, Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				view = convertView;
			}
			TextView textView = (TextView) view.findViewById(R.id.expandable_list_detail_text);
			textView.setText(children);
			
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
		
	}
}
