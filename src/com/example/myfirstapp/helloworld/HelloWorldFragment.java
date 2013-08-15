package com.example.myfirstapp.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myfirstapp.R;

public class HelloWorldFragment extends Fragment {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.activity_helloworld, container, false);
		
		Button btnSend = (Button) view.findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMessage(v);
			}
		});
		
		return view;
	}

	public void sendMessage(View view) {
		Activity activity = getActivity();
		Intent intent = new Intent();
		intent.setClass(activity, DisplayMessageActivity.class);
		
		EditText editText = (EditText) activity.findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
}
