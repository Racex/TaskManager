package com.example.taskmanager;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.adapter.ListAdapter;
import com.example.taskmanager.adapter.ListAdapter.ViewHolder;
import com.example.taskmanager.adapter.TimeToEnd;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity {

	private DataBase db;
	private volatile JSONArray array;
	private ListAdapter listAdapter;
	private static Thread threadTime = null;
	private String sortBy ="date";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListAdapter.holder=new ArrayList<TextView>();
		setContentView(R.layout.activity_main);
		db = new DataBase(this);   	
				try 
				{
					array =db.getJsonArrayDataBase(sortBy);
				}catch (JSONException e) {
					e.printStackTrace();
				}
				
				if(array != null){
					listAdapter = new ListAdapter(this, array);
				
					setListAdapter(listAdapter);
					runTimeThread();
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
		if (id == R.id.sort_date) {
			sortBy="date";
			try {
				updateList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else if (id == R.id.sort_title) {
			sortBy="title";
			try {
				updateList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void openTaskMenu(View v) throws InterruptedException{
		Intent intent = new Intent(this, TaskActivity.class);
		startActivity(intent);
		finish();
		//putextra db and array ?
	}

	

	@Override
	protected void onResume() {
//		try {
//			updateList();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(time != null)
//		time.notify();
		super.onResume();
	}

	private void updateList() throws JSONException {
	
		array =db.getJsonArrayDataBase(sortBy);
		if(array != null){
			Log.d("JSONA MAM", array.toString());
		listAdapter = new ListAdapter(this, array);
		setListAdapter(listAdapter);
		}
		
	}
	private void runTimeThread() {
		if(threadTime == null){
		threadTime = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
		
		
				Log.d("THREAD", "Uruchamiam watel");
				while(true){
				// TODO Auto-generated method stub
				try {
					Thread.sleep(70000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
		            	TimeToEnd timend=new TimeToEnd();
		            	try {
							array=db.getJsonArrayDataBase(sortBy);
						
		            	for(int i=0; i <ListAdapter.holder.size() ; i++){
		            	if(array !=null){
		            		Log.d("THREAD", timend.getTimeToEnd(array.getJSONObject(i).getString("date")));
							ListAdapter.holder.get(i).setText(timend.getTimeToEnd(array.getJSONObject(i).getString("date")));}
		            	}
		            	}catch (ParseException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	
		            }
		            
				});
				
				}
			}
			});
		threadTime.start();
		}

	}
}
