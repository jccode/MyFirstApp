package com.example.myfirstapp.killps;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.util.ShellUtils;

public class KillPsFragment extends Fragment {

	private ListView listView;
	private String[] pkgNames;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater
				.inflate(R.layout.fragment_killps, container, false);

		String[] appNames = this.getResources().getStringArray(
				R.array.kill_ps_app_names);
		pkgNames = this.getResources().getStringArray(
				R.array.kill_ps_app_packages);

		listView = (ListView) view.findViewById(R.id.killps_apps);
		listView.setAdapter(new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_multiple_choice, appNames));
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		Button killBtn = (Button) view.findViewById(R.id.btn_kill);
		KillPsListener listener = new KillPsListener(this);
		killBtn.setOnClickListener(listener);

		return view;
	}


	private class KillPsListener implements View.OnClickListener {
		
		private KillPsFragment owner;
		
		public KillPsListener(KillPsFragment owner) {
			this.owner = owner;
		}
		
		@Override
		public void onClick(View v) {
			ListView listView = owner.listView;
			
			int len = listView.getCount();
			SparseBooleanArray checked = listView.getCheckedItemPositions();

			List<String> commands = new ArrayList<String>();
			for (int i = 0; i < len; i++) {
				if (checked.get(i)) { // checked
					commands.add("am force-stop " + owner.pkgNames[i]);
				}
			}
			
//			Toast.makeText(getActivity(), sbResult.toString(), Toast.LENGTH_SHORT).show();
			ShellUtils.execCommand(commands, true);
		}

	}
}
