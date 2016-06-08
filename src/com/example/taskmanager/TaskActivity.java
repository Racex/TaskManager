package com.example.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.adapter.SwipAdapter;
import com.example.taskmanager.adapter.TimeToEnd;
import com.example.taskmanager.data.DataBase;
import com.example.taskmanager.timedialog.DateDialog;
import com.example.taskmanager.timedialog.TimeDialog;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class TaskActivity extends Activity {
	DataBase db;
	JSONObject json;
	JSONArray jsonArray;
	public ViewPager viewpager;
	SwipAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		viewpager = (ViewPager) findViewById(R.id.menu_pager);
		json=new JSONObject();
		jsonArray = new JSONArray();
		db=new DataBase(this);
		adapter = new SwipAdapter(this);	
		viewpager.setAdapter(adapter);
		focusAbleOff((EditText) findViewById(R.id.tittle));		
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
	{				
		
		
		JSONObject json = new JSONObject();
				EditText text;
				text =(EditText) findViewById(R.id.tittle);				
				json.put("title", text.getText());
				json.put("created", new TimeToEnd().getActuallyTime());
				text =(EditText) findViewById(R.id.description);
				json.put("description", text.getText());
				text =(EditText) findViewById(R.id.day);
				json.put("time_end",text.getText());
				text =(EditText) findViewById(R.id.time);
				json.put("time_end",json.getString("time_end").toString()+" "+text.getText());
				json.put("url" , adapter.getUri(viewpager.getCurrentItem()));
				
				//json.put("")
				//if(jsonIsCorrect(json)){
				Log.d("JSONDate", json.getString("time_end").toString());
				jsonArray.put(json);
				//	json.put("url", maciek);
				db.addToDataBase(json);
				Log.d("json", json.toString());
				Toast.makeText(TaskActivity.this, jsonArray.toString(),Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
				//}
				//focusAbleOff(text);
	}
	


	private void focusAbleOff(final EditText edittext) {
		Log.d("OD", edittext.toString());
		View v1 = getWindow().getDecorView().getRootView();
		final View v2 = getWindow().getDecorView().getRootView().findViewById(edittext.getId());
		v1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
            	 if(v == v2)
                	Log.d("OD", "Byly rowne");
                     if (!hasFocus) {
                         // Close keyboard
                         ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                                 .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                     }
                 
             }
         });
		
	}


	private boolean jsonIsCorrect(JSONObject json2) throws JSONException { // need to correct because adding time
		if(json2.get("title").toString().equals(""))
		{
			Toast("Tytul jest pusty");
			return false;
		}
		if(!json2.get("time_end").toString().equals(""))
		{
			Date date = null;
			try {
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    try {
					date = (Date) sdf.parse(json2.get("time_end").toString());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					return false;
				}
			    if (!json2.get("time_end").toString().equals(sdf.format(date))) {
			        date = null;
			    }
			} catch (ParseException ex) {
			    ex.printStackTrace();
			}
			if (date == null) {
				Toast("Nie poprawny format Daty dd-MM-yyyy");
			    return false;
			} else {
			    return true;
				}
			}	
		return true;
	}


	private void Toast(String string) {
		Toast toast =Toast.makeText(this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER ,0, 0);
		toast.show();
	}


	public void onStart(){
     	super.onStart();
     	
     	EditText txtDate=(EditText) findViewById(R.id.day);
     	EditText txtTime = (EditText) findViewById(R.id.time);
     	new DateDialog (this, txtDate);
		new TimeDialog(this, txtTime);	   
		
		focusAbleOff((EditText) findViewById(R.id.description));
  	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		super.onBackPressed();
	}
	

}
