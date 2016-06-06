package com.example.taskmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.adapter.ListAdapter;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity {
	//String [] title = {"TEST 1", "TEST 2","TEST 3"};
	//Json json;
	DataBase db;
	JSONArray array;
	ListAdapter listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		    	db = new DataBase(this);   	
				try {
					array =db.getJsonArrayDataBase();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(array != null){
				listAdapter = new ListAdapter(this, array);
				setListAdapter(listAdapter);
				}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//need to add removing from database after long pressed
		
		try {
			db.remove(array.getJSONObject(position));
			array.remove(position);
			updateList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onListItemClick(l, v, position, id);
		Log.d("ARRAY PO USUNIECIU", array.toString());
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openTaskMenu(View v){
		Intent intent = new Intent(this, TaskActivity.class);
		startActivity(intent);
		finish();
		//putextra db and array ?
	}

	

	@Override
	protected void onPostResume() {
		try {
			updateList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onPostResume();
	}

	private void updateList() throws JSONException {
	
		array =db.getJsonArrayDataBase();
		if(array != null){
		listAdapter = new ListAdapter(this, array);
		setListAdapter(listAdapter);
		}
	}
	
}
