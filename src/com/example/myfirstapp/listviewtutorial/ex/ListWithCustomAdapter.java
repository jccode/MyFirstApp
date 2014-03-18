package com.example.myfirstapp.listviewtutorial.ex;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class ListWithCustomAdapter extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_list_tutorial);
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2" };
		
		getListView().setBackgroundResource(android.R.color.background_dark);
		
//		ArrayAdapter<String> adapter = new MySimpleArrayAdapter(this, values);
		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
	}
	
	static class MySimpleArrayAdapter extends ArrayAdapter<String> {
		
		private final Context context;
		private final String[] values;

		public MySimpleArrayAdapter(Context context, String[] values) {
			super(context, R.layout.rowlayout, values);
			this.context = context;
			this.values = values;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.list_tutorial_row_label);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.list_tutorial_row_icon);
			
			String s = values[position];
			textView.setText(s);
			
			// change the icon for windows and iphone
			if(s.startsWith("Windows7") || s.startsWith("iPhone") || s.startsWith("Solaris")) {
				imageView.setImageResource(R.drawable.ic_cancel);
			} else {
				imageView.setImageResource(R.drawable.ic_accept);
			}
			
			return rowView;
		}

	}
	
	
	static class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
		
		private final Context context;
		private final String[] names;
		
		static class ViewHolder {
			public ImageView image;
			public TextView text;
		}

		public MyPerformanceArrayAdapter(Context context, String[] names) {
			super(context, R.layout.rowlayout, names);
			this.context = context;
			this.names = names;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if(rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.rowlayout, null);
				ViewHolder holder = new ViewHolder();
				holder.text = (TextView) rowView.findViewById(R.id.list_tutorial_row_label);
				holder.image = (ImageView) rowView.findViewById(R.id.list_tutorial_row_icon);
				rowView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();
			String s = names[position];
			holder.text.setText(s);
			if(s.startsWith("Windows7") || s.startsWith("iPhone") || s.startsWith("Solaris")) {
				holder.image.setImageResource(R.drawable.ic_cancel);
			} else {
				holder.image.setImageResource(R.drawable.ic_accept);
			}
			return rowView;
		}
	}
}
