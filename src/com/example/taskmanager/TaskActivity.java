package com.example.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.adapter.SwipAdapter;
import com.example.taskmanager.data.DataBase;
import com.example.taskmanager.data.TimeToEnd;
import com.example.taskmanager.timedialog.DateDialog;
import com.example.taskmanager.timedialog.TimeDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskActivity extends Activity {
	DataBase db;
	JSONObject json;
	public ViewPager viewpager;
	SwipAdapter adapter;
	String extrasFromMain = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		db = new DataBase(this);
		adapter = new SwipAdapter(getApplicationContext());
		viewpager = (ViewPager) findViewById(R.id.menu_pager);
		viewpager.setAdapter(adapter);
		json = new JSONObject();
		if (checkExtras(savedInstanceState)) {
			Button button = (Button) findViewById(R.id.add_task);
			Button buttonRemove = (Button) findViewById(R.id.remove_button);
			button.setText("Edytuj");
			buttonRemove.setVisibility(android.view.View.VISIBLE);
			overridePendingTransition(R.animator.animation_beetwen_activity, R.animator.animation_beetwen_activity_end);
		}
	}

	public void addTask(View view) throws JSONException {
		JSONObject json = initJSON();

		if (jsonIsCorrect(json)) {
			if (extrasFromMain != null) {
				json.put("id", extrasFromMain);
				db.updateDataBaseRow(json);
				onBackPressed();
			} else {
				db.addToDataBase(json);
				Log.d("json", json.toString());
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}

	}

	private boolean jsonIsCorrect(JSONObject json2) throws JSONException { // need
																			// to
																			// correct
																			// because
																			// adding
																			// time
		if (json2.get("title").toString().equals("")) {
			Toast("Tytul jest pusty");
			return false;
		}
		if (!json2.get("time_end").toString().equals("")) {
			Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				// sdf.setTimeZone(TimeZone.getTimeZone("Europe/Copenhagen"));
				try {
					date = sdf.parse(json2.get("time_end").toString());

				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block

					Toast("Proszê wpisaæ poprawn¹ datê");
					return false;
				}
				if (!json2.get("time_end").toString().equals(sdf.format(date))) {
					Log.d("ISCORRECT", json2.get("time_end").toString() + ":" + sdf.format(date));
					date = null;
				}
			} catch (ParseException ex) {
				ex.printStackTrace();
				Toast("2");
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
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onStart() {
		super.onStart();

		EditText txtDate = (EditText) findViewById(R.id.day);
		EditText txtTime = (EditText) findViewById(R.id.time);
		new DateDialog(this, txtDate);
		new TimeDialog(this, txtTime);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	private boolean checkExtras(Bundle savedInstanceState) {

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null)
				extrasFromMain = null;
			else
				extrasFromMain = extras.getString("edit");
		} else {
			extrasFromMain = (String) savedInstanceState.getSerializable("edit");
		}

		if (extrasFromMain != null) {
			prepareEditView(extrasFromMain);
			return true;
		}
		return false;
	}

	private void prepareEditView(String id) {
		JSONObject json = db.getRow(id);
		try {
			if (json != null) {
				EditText text;
				text = (EditText) findViewById(R.id.tittle);
				text.setText(json.getString("title").toString());
				text = (EditText) findViewById(R.id.description);
				text.setText(json.getString("description"));
				Log.d("Szukam", json.getString("time_end").toString());
				Log.d("ERROR", "+" + json.getString("time_end").toString().equals(" ") + "+");
				if (!json.getString("time_end").toString().equals(" ")) {
					text = (EditText) findViewById(R.id.day);
					text.setText(json.getString("time_end").toString().substring(0, 10));
					text = (EditText) findViewById(R.id.time);
					text.setText(
							json.getString("time_end").toString().substring(11, json.getString("time_end").length()));
				}
				viewpager.setCurrentItem(adapter.getPositionUri(json.getString("url").toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void removeTask(View view) {
		JSONObject json = initJSON();
		try {
			json.put("id", extrasFromMain);
			db.remove(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		onBackPressed();
	}

	public JSONObject initJSON() {
		JSONObject json = new JSONObject();
		try {
			EditText text;
			text = (EditText) findViewById(R.id.tittle);
			json.put("title", text.getText());
			json.put("created", new TimeToEnd().getActuallyTime());
			text = (EditText) findViewById(R.id.description);
			json.put("description", text.getText());
			text = (EditText) findViewById(R.id.day);
			json.put("time_end", text.getText());
			text = (EditText) findViewById(R.id.time);
			json.put("time_end", json.getString("time_end").toString() + " " + text.getText());
			json.put("url", adapter.getUri(viewpager.getCurrentItem()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}

}
