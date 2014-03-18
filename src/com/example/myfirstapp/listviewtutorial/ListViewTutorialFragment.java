package com.example.myfirstapp.listviewtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myfirstapp.R;

public class ListViewTutorialFragment extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_listview_tutorial, container, false);
		
//		((Button)view.findViewById(R.id.btn_listview_with_array_adapter)).setOnClickListener(this);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_listview_tutorial);
		for(int i=0, len=layout.getChildCount(); i<len; i++) {
			View v = layout.getChildAt(i);
			if(v instanceof Button) {
				((Button) v).setOnClickListener(this);
			}
		}
		
		return view;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		String className = "com.example.myfirstapp.listviewtutorial.ex.";
		
		switch (id) {
		case R.id.btn_listview_with_array_adapter:
			className += "ListWithArrayAdapter1";
			break;

		case	R.id.btn_listview_with_custom_adapter:
			className += "ListWithCustomAdapter";
			break;
			
		default:
			className = null;
			break;
		}
		
		
		if (className != null) {
			try {
				Intent intent = new Intent(this.getActivity(),
						Class.forName(className));
				startActivity(intent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}
