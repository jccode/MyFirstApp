package com.example.myfirstapp.helloworld;

import com.example.myfirstapp.DrawerDemoActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.R.id;
import com.example.myfirstapp.R.layout;
import com.example.myfirstapp.swipedemo.CollectionDemoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class HelloWorldActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_helloworld);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	public void swipeViewDemo(View view) {
		Intent intent = new Intent(this, CollectionDemoActivity.class);
		startActivity(intent);
	}
	
	public void drawerDemo(View view) {
		Intent intent = new Intent(this, DrawerDemoActivity.class);
		startActivity(intent);
	}
}
