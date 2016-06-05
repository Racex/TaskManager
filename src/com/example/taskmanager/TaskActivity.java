package com.example.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TaskActivity extends Activity {
	DataBase db;
	JSONObject json;
	JSONArray jsonArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		json=new JSONObject();
		jsonArray = new JSONArray();
		db=new DataBase(this);
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		Picasso.with(this).load("https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/6.10.png").resize(512,512).into(image);
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
				text =(EditText) findViewById(R.id.Description);
				json.put("description", text.getText());
				text =(EditText) findViewById(R.id.Calendary);
				json.put("date",text.getText());
				ImageView maciek = (ImageView) findViewById(R.id.imageView1);
				if(jsonIsCorrect(json)){
				jsonArray.put(json);
				//	json.put("url", maciek);
				db.addToDataBase(json);
				Log.d("json", json.toString());
				Toast.makeText(TaskActivity.this, jsonArray.toString(),Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				}
	}
	private boolean jsonIsCorrect(JSONObject json2) throws JSONException {
		if(json2.get("title").toString().equals(""))
		{
			Toast("Tytul jest pusty");
			return false;
		}
		if(!json2.get("date").toString().equals(""))
		{
			Date date = null;
			try {
			    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
				Toast("Nie poprawny format Daty DD-MM-ROK");
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
