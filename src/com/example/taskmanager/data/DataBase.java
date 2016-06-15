package com.example.taskmanager.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
	// SQLiteDatabase mydatabase
	public final static String dataBaseName = "Tasks";

	public DataBase(Context context) {
		super(context, dataBaseName, null, 1);
		// mydatabase = openOrCreateDatabase("your database
		// name",MODE_PRIVATE,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE if not exists " + dataBaseName
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TITLE TEXT NOT NULL , CREATED TEXT NULL, DESCRIPTION TEXT NULL, TIME_END TEXT NULL, URL TEXT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public void addToDataBaseWithoutId(JSONObject json) throws JSONException {

		addToDataBase(json, null, "Autoincrement");
	}

	public void addToDataBase(JSONObject json, ContentValues creat, String addIdorAutoIncrement) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (creat == null)
			creat = new ContentValues();
		try {
			if (addIdorAutoIncrement.equals("addId"))// else creat with
														// autoincrement ID
			creat.put("ID", json.getString("id").toString());
			creat.put("TITLE", json.getString("title"));
			creat.put("CREATED", json.getString("created"));
			creat.put("DESCRIPTION", json.getString("description"));
			creat.put("TIME_END", json.getString("time_end"));
			creat.put("URL", json.getString("url"));
			if (db.insert(dataBaseName, null, creat) != -1) {
				Log.d("DataBase", "Dodano do bazy" + json.toString());
			} else {
				Log.d("DataBase", "nie dodano do bazy" + json.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}

	}

	public void addToDataBaseWithId(JSONObject json) throws JSONException {

		ContentValues creat = new ContentValues();
		creat.put("ID", json.getString("id").toString());
		addToDataBase(json, creat, "addId");
	}

	public void updateDataBaseRow(JSONObject json) throws JSONException {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues creat = new ContentValues();
		creat.put("TITLE", json.getString("title"));
		creat.put("CREATED", json.getString("created"));
		creat.put("DESCRIPTION", json.getString("description"));
		creat.put("TIME_END", json.getString("time_end"));
		creat.put("URL", json.getString("url"));
		db.update(dataBaseName, creat, "id = " + json.getString("id"), null);
		db.close();

	}

	public SQLiteDatabase getDataBase() {
		return this.getWritableDatabase();
	}

	public JSONArray getJsonArrayDataBase(String sortBy) throws JSONException {
		JSONArray array = new JSONArray();
		JSONArray array2 = new JSONArray();
		SQLiteDatabase db = getReadableDatabase();
		if (sortBy.equals("time_end")) {
			try {

				Cursor cursor = db.rawQuery("select *  from " + dataBaseName
						+ " WHERE time_end != \" \" order  by datetime(time_end) ASC  ;", null);
				array = takeAllRow(cursor);
				cursor = db.rawQuery(
						"select *  from " + dataBaseName + " WHERE time_end = \" \"order  by datetime(created) ASC;",
						null);
				array2 = takeAllRow(cursor);
				for (int i = 0; i < array2.length(); i++) {
					array.put(array2.getJSONObject(i));
				}
				db.close();
				return array;
			} catch (CursorIndexOutOfBoundsException e) {
				db.close();
				Log.e("KURSOR", "KURSOR PUSTY");
				return null;
			}
		} else {
			Cursor cursor = db.rawQuery("select *  from " + dataBaseName + "  order  by title ASC ;", null);
			array = takeAllRow(cursor);
			db.close();
			return array;
		}
	}

	private JSONArray takeAllRow(Cursor cursor) throws CursorIndexOutOfBoundsException {

		JSONArray array = new JSONArray();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject jsonRead = new JSONObject();
			try {
				jsonRead.put("id", cursor.getString(cursor.getColumnIndex("ID")));
				jsonRead.put("title", cursor.getString(cursor.getColumnIndex("TITLE")));
				jsonRead.put("created", cursor.getString(cursor.getColumnIndex("CREATED")));
				jsonRead.put("description", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
				jsonRead.put("time_end", cursor.getString(cursor.getColumnIndex("TIME_END")));
				jsonRead.put("url", cursor.getString(cursor.getColumnIndex("URL")));
				array.put(jsonRead);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cursor.moveToNext();
		}
		return array;
	}

	public void remove(JSONObject jsonObject) throws JSONException {
		SQLiteDatabase db = getReadableDatabase();
		if (db.delete(dataBaseName, "ID" + "=" + "'" + jsonObject.getString("id") + "'", null) > 0)
		{}
			db.close();
	}

	public boolean isIdInDatabase(int id) {
		if (getReadableDatabase().rawQuery("Select * from " + dataBaseName + " where id = " + id + " ", null)
				.getCount() > 0)
			return true;
		return false;
	}

	public JSONObject getRow(String i) {
		JSONArray array = new JSONArray();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *  from " + dataBaseName + "  where id = " + i + " Limit 1;", null);
		try {
			return takeAllRow(cursor).getJSONObject(0);
		} catch (CursorIndexOutOfBoundsException | JSONException e) {
			e.printStackTrace();
		}finally {
			db.close();
		}
		return null;
	}

}