package com.example.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.adapter.SwipAdapter;
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
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
		//txt= (EditText) findViewById(R.id.time);
		//fromTime = new TimeDialog(this, txt);
		// EditText editTextFromTime = (EditText) findViewById(R.id.time);
		 
		// TimeDialog fromTime = new TimeDialog(this, editTextFromTime);
		
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
				text =(EditText) findViewById(R.id.description);
				json.put("description", text.getText());
				text =(EditText) findViewById(R.id.day);
				json.put("date",text.getText());
				text =(EditText) findViewById(R.id.time);
				json.put("date",json.getString("date").toString()+" "+text.getText());
				json.put("url" , adapter.getUri(viewpager.getCurrentItem()));
				//if(jsonIsCorrect(json)){
				Log.d("JSONDate", json.getString("date").toString());
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
            	 Log.d("OD", "root sie sfokusowal xd");
            	 if(v == v2)
                	Log.d("OD", "Byly rowne");
                     if (!hasFocus) {
                         // Close keyboard
                    	 Log.d("OD", "odfokusowana");
                         ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                                 .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                     }
                 
             }
         });
//		edittext.setOnFocusChangeListener(  new View.OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				  if (!hasFocus) {
//                      // Close keyboard
//					 Log.d("OD", "odfokusowana");
//                      ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
//                              .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
//                  }
//				
//			}
//		});
			
//		myEditField.setOnEditorActionListener(new TextView.OnEditorActionListener() { 
//		    @Override 
//		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { 
//		        if (actionId == EditorInfo.IME_ACTION_DONE) { 
//
//		            mySubroutine(); 
//		        } 
//		        return false; 
//		    } 
//		}); 
		
	}


	private boolean jsonIsCorrect(JSONObject json2) throws JSONException { // need to correct because adding time
		if(json2.get("title").toString().equals(""))
		{
			Toast("Tytul jest pusty");
			return false;
		}
		if(!json2.get("date").toString().equals(""))
		{
			Date date = null;
			try {
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    try {
					date = (Date) sdf.parse(json2.get("date").toString());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					return false;
				}
			    if (!json2.get("date").toString().equals(sdf.format(date))) {
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

}
