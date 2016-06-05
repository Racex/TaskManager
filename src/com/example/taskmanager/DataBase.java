package com.example.taskmanager;


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


public class DataBase extends SQLiteOpenHelper 
{
		//SQLiteDatabase mydatabase
	public final static String dataBaseName="Tasks";
		
	public DataBase(Context context) {
		super(context, dataBaseName, null, 1);
		//mydatabase = openOrCreateDatabase("your database name",MODE_PRIVATE,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	//db.execSQL("create table " +data table);	
		db.execSQL("CREATE TABLE if not exists "+dataBaseName+"( TITLE TEXT , DESCRIPTION TEXT , DATE TEXT , URL, TEXT )");
		//mydatabase=db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public void addToDataBase(JSONObject json) throws JSONException
	{
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues creat = new ContentValues();
		creat.put("TITLE", json.getString("title"));
		creat.put("DESCRIPTION", json.getString("description"));
		creat.put("DATE", json.getString("date"));
		creat.put("URL", "nie ma jeszcze");
		if(db.insert(dataBaseName, null, creat) != -1){
			Log.d("DataBase", "Dodano do bazy" + json.toString());
		}
		else
		{
			Log.d("DataBase", "nie dodano do bazy" + json.toString());
		}
	}
	public SQLiteDatabase getDataBase()
	{
		return this.getWritableDatabase();
	}
	
	public JSONArray getJsonArrayDataBase() throws JSONException
	{
		JSONArray array = new JSONArray();
		
		 	SQLiteDatabase db= getReadableDatabase();
		 	try{
		    Cursor cursor = db.rawQuery("SELECT * FROM " +dataBaseName,null);
		    
		    cursor.moveToFirst();
		    while(!cursor.isAfterLast())
		    {		JSONObject jsonRead = new JSONObject();
		            jsonRead.put("title",cursor.getString(cursor.getColumnIndex("TITLE")));
		            jsonRead.put("description",cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
		            jsonRead.put("date",cursor.getString(cursor.getColumnIndex("DATE")));
		            jsonRead.put("url",cursor.getString(cursor.getColumnIndex("URL")));
		            array.put(jsonRead);
		            cursor.moveToNext();
		    }	
		            return array;
		 	}catch(CursorIndexOutOfBoundsException e)
		 	{
		 		Log.e("KURSOR", "KURSOR PUSTY");
		 		return null;
		 	}
	}

	public void remove(JSONObject jsonObject) throws JSONException {
		SQLiteDatabase db= getReadableDatabase();
	    if(db.delete(dataBaseName, "TITLE" + "="+ "'"+jsonObject.getString("title")+"'",null) > 0);
	    Log.d("USUNIETE", jsonObject.toString());
	    
	    //  return array;
	}
   
}