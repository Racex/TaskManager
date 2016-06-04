package com.example.taskmanager;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabaseLockedException;
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
   
}