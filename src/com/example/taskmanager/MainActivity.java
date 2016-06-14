package com.example.taskmanager;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import com.example.taskmanager.adapter.ListAdapter;
import com.example.taskmanager.data.DataBase;
import com.example.taskmanager.data.JsonExportImport;
import com.example.taskmanager.data.TimeToEnd;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	private DataBase db;
	private volatile JSONArray array;
	private ListAdapter listAdapter;
	// private static Thread threadTime = null;
	private static String sortBy = "time_end";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DataBase(this);

		try {
			array = db.getJsonArrayDataBase(sortBy);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (array != null) {
			listAdapter = new ListAdapter(this, array);
			setListAdapter(listAdapter);
			runTimeThread();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, TaskActivity.class);
		l.getCount();
		try {
			intent.putExtra("edit", array.getJSONObject(position).getString("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		overridePendingTransition(R.animator.animation_beetwen_activity, R.animator.animation_beetwen_activity_end);
		startActivity(intent);

		finish();
		// try {
		// db.remove(array.getJSONObject(position));
		// array.remove(position);
		// ListAdapter.holder.remove(position);
		// updateList();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		super.onListItemClick(l, v, position, id);
		Log.d("ARRAY PO USUNIECIU", array.toString());
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		JsonExportImport file = new JsonExportImport(this);
		if (id == R.id.sort_date) {
			sortBy = "time_end";
			try {
				updateList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else if (id == R.id.sort_title) {
			sortBy = "title";
			try {
				updateList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else if (id == R.id.import_to) {
			try {
				updateList();

				file.createAndSaveFile("JsonFile", array.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// import to json file
			return true;
		} else if (id == R.id.export) {
			// export from json file
			try {
				file.readJsonData("JsonFile");
				updateList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void openTaskMenu(View v) throws InterruptedException {
		Intent intent = new Intent(this, TaskActivity.class);
		// overridePendingTransition(R.animator.animation_beetwen_activity,
		// R.animator.animation_beetwen_activity);

		startActivity(intent);
		finish();
		// putextra db and array ?
	}

	private void updateList() throws JSONException {

		array = db.getJsonArrayDataBase(sortBy);
		if (array != null) {
			// Log.d("JSONA after update", array.toString());
			listAdapter = new ListAdapter(this, array);
			setListAdapter(listAdapter);
		}

	}

	@Override
	protected void onPause() {
		//overridePendingTransition(R.animator.animation_beetwen_activity, R.animator.animation_beetwen_activity_end);
		super.onPause();
	}

	private void runTimeThread() {
		// if(threadTime == null){
		// threadTime = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// int time=10000;
		// Log.d("THREAD", "Uruchamiam watel");
		// while(true){
		// // TODO Auto-generated method stub
		// try {
		// Thread.sleep(time);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// TimeToEnd timend=new TimeToEnd();
		// try {
		// array=db.getJsonArrayDataBase(sortBy);
		// Log.d("THREAD", "HM:"+String.valueOf(ListAdapter.holder.size()) +
		// "JS:"+array.length());
		// for (int i = 0; i < ListAdapter.holder.size(); i++) {
		// ListAdapter.holder.get(array.getJSONObject(i).getString("id")).setText(timend.getTimeToEnd(array.getJSONObject(i).getString("time_end")));
		// Log.d("THREAD",
		// timend.getTimeToEnd(array.getJSONObject(i).getString("time_end")));
		// }
		//
		// }catch (ParseException e){
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// });
		//
		// }
		// }
		// });
		// threadTime.start();
		// }
		//
	}
}
