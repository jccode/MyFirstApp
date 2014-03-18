package com.example.myfirstapp.password;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class PwFragment extends Fragment {
	
	private PwUtil pwu = new PwUtil();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_pw, container, false);
		
		Button btnEncode = (Button) view.findViewById(R.id.btn_pw_encode);
		btnEncode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doEncode(v);
			}
		});
		
		Button btnDecode = (Button) view.findViewById(R.id.btn_pw_decode);
		btnDecode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doDecode(v);
			}
		});
		
		return view;
	}
	
	public void doEncode(View view) {
		String txt = this.getTextFromPwField(view);
		String ret = isEmpty(txt)? "": encode(txt);
		displayResult(view, ret);
	}
	
	public void doDecode(View view) {
		String txt = this.getTextFromPwField(view);
		String ret = isEmpty(txt)? "": decode(txt);
		displayResult(view, ret);
	}
	
	private String getTextFromPwField(View view) {
		Activity activity = getActivity();
		EditText pwField = (EditText)activity.findViewById(R.id.pw_field);
		if(pwField.getText() != null)
			return pwField.getText().toString();
		return null;
	}
	
	private void displayResult(View view, String ret) {
		Activity activity = getActivity();
		TextView retView = (TextView) activity.findViewById(R.id.pw_result_txt);
		retView.setText(ret);
	}
	
	private String encode(String txt) {
		return pwu.encode(txt);
	}
	
	private String decode(String txt) {
		return pwu.decode(txt);
	}
	
	private boolean isEmpty(String s) {
		return s == null || s.trim().equals("");
	}
	
}
