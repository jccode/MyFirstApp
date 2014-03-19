package com.example.myfirstapp.listviewtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListViewTutorialFragment extends ListFragment {
	
	final String[] values = new String[] {"ListView with ArrayAdapter", "ListView with custom Adapter", 
			"Multiple choice List", "Single choice List", "Contextual action mode for ListView", 
			"Undo Action", "Two items in ListView", "Model and rows interaction", "Expandable List", 
			"List with header and footer", "simple cursor adapter"};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String className = "com.example.myfirstapp.listviewtutorial.ex.";
		switch (position) {
		case 0:
			className += "ListWithArrayAdapter1";
			break;
		case 1:
			className += "ListWithCustomAdapter";
			break;
		case 2:
			className += "MultipleChoiceListActivity";
			break;
		case 3:
			className += "SingleChoiceListActivity";
			break;
		case 4:
			className += "ContextualActionListActivity";
			break;
		case 5:
			className += "UndoDemoActivity";
			break;
		case 6:
			className += "TwoItemsListActivity";
			break;
		case 7:
			className += "ModelAndRowInteractionActivity";
			break;
		case 8:
			className += "ExpandableListActivity";
			break;
		case 9:
			className += "ListViewWithHeaderAndFooterActivity";
			break;
		case 10:
			className += "SimpleCursorAdapterActivity";
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

/*
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
*/

