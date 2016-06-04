package com.example.taskmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class TaskActivity extends Activity {
	DataBase db;
	JSONObject json;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		json=new JSONObject();
		db=new DataBase(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
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
	
	public void addTask(View view) throws JSONException
	
	{		EditText text;
				text =(EditText) findViewById(R.id.tittle);				
				json.put("title", text.getText());
				text =(EditText) findViewById(R.id.Description);
				json.put("description", text.getText());
				text =(EditText) findViewById(R.id.Calendary);
				json.put("date",text.getText());
			//	json.put("url", maciek);
				db.addToDataBase(json);
				Log.d("json", json.toString());
				Toast.makeText(TaskActivity.this, json.toString(),Toast.LENGTH_LONG).show();
	}
	public void onStart(){
     	super.onStart();
     	EditText txtdate;
     		txtdate=(EditText) findViewById(R.id.Calendary);
  			   txtdate.setOnFocusChangeListener(new OnFocusChangeListener(){
  			   public void onFocusChange(View view, boolean hasfocus){
  				      if(hasfocus){
  					         DateDialog dialog=new DateDialog(view);
  					        FragmentTransaction ft =getFragmentManager().beginTransaction();
  					        dialog.show(ft, "DatePicker");
  				}
  			}
  			
  		});
	}
}
