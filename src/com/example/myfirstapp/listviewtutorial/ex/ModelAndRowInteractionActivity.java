package com.example.myfirstapp.listviewtutorial.ex;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class ModelAndRowInteractionActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		
		List<Model> data = getListModels();
		MyInteractiveAdapter adapter = new MyInteractiveAdapter(this, data);
		setListAdapter(adapter);
	}

	private List<Model> getListModels() {
		List<Model> list = new ArrayList<Model>();
		list.add(new Model("Linux"));
		list.add(new Model("Windows7"));
		list.add(new Model("Suse"));
		list.add(new Model("Eclipse"));
		list.add(new Model("Ubuntu"));
		list.add(new Model("Solaris"));
		list.add(new Model("Android"));
		list.add(new Model("iPhone"));
		
		// Initially select one of the items
	    list.get(1).setSelected(true);
	    
		return list;
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
		getMenuInflater().inflate(R.menu.model_and_row_interaction, menu);
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

	static class ViewHolder {
		TextView text;
		CheckBox checkbox;
	}
	
	
	static class MyInteractiveAdapter extends ArrayAdapter<Model> {
		
		private final Activity context;
		private final List<Model> list;

		public MyInteractiveAdapter(Activity context, List<Model> list) {
			super(context, R.layout.activity_model_and_row_interaction_row, list);
			this.context = context;
			this.list = list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			Model model = list.get(position);
			ViewHolder holder = null;
			if(convertView == null) {
				view = context.getLayoutInflater().inflate(R.layout.activity_model_and_row_interaction_row, null);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.model_row_interact_label);
				holder.checkbox = (CheckBox) view.findViewById(R.id.model_row_interact_check);
				
				view.setTag(holder);
				holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						Model model = (Model) buttonView.getTag();
						model.setSelected(buttonView.isChecked());
					}
				});
			} else {
				view = convertView;
			}
			
			holder = (ViewHolder) view.getTag();
			holder.text.setText(model.getName());
			holder.checkbox.setSelected(model.isSelected());
			holder.checkbox.setTag(model);
			return view;
		}

	}
}

class Model {
	private String name;
	private boolean isSelected;
	
	public Model(String name) {
		this.name = name;
		this.isSelected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}


