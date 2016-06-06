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
		creat.put("URL", json.getString("url"));
		if(db.insert(dataBaseName, null, creat) != -1){
			Log.d("DataBase", "Dodano do bazy" + json.toString());
		}
		else
		{
			Log.d("DataBase", "nie dodano do bazy" + json.toString());
		}
		db.close();
	}
	public SQLiteDatabase getDataBase()
	{
		return this.getWritableDatabase();
	}
	
	public JSONArray getJsonArrayDataBase(String sortBy) throws JSONException
	{
		JSONArray array = new JSONArray();
		SQLiteDatabase db= getReadableDatabase();	
		if(sortBy.equals("date")){
		 	try{
			    Cursor cursor = db.rawQuery("select *  from "+dataBaseName+" WHERE date != \" \" order  by datetime(date) ASC ;",null);  
			    takeAllRow(cursor,array);
			    cursor = db.rawQuery("select *  from "+dataBaseName+" WHERE date = \" \";",null);  
			    takeAllRow(cursor,array);	 
			    return array;
		 	}catch(CursorIndexOutOfBoundsException e)
		 	{	db.close();
		 		Log.e("KURSOR", "KURSOR PUSTY");
		 		return null;
		 	}
		}
		else{
		 		Cursor cursor = db.rawQuery("select *  from "+dataBaseName+"  order  by title ASC ;",null);
		 		takeAllRow(cursor,array);	
		 		return array;
		 	}
	}

	private void takeAllRow(Cursor cursor, JSONArray array) throws CursorIndexOutOfBoundsException{
		cursor.moveToFirst();
	    while(!cursor.isAfterLast())
	    {		JSONObject jsonRead = new JSONObject();
	            try {
					jsonRead.put("title",cursor.getString(cursor.getColumnIndex("TITLE")));
				
	            jsonRead.put("description",cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
	            jsonRead.put("date",cursor.getString(cursor.getColumnIndex("DATE")));
	            jsonRead.put("url",cursor.getString(cursor.getColumnIndex("URL")));
	            array.put(jsonRead);
	            } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            cursor.moveToNext();
	    }
		
	}

	public void remove(JSONObject jsonObject) throws JSONException {
		SQLiteDatabase db= getReadableDatabase();
	    if(db.delete(dataBaseName, "TITLE" + "="+ "'"+jsonObject.getString("title")+"'",null) > 0);
	    db.close();
	    Log.d("USUNIETE", jsonObject.toString());
	    
	    //  return array;
	}
   
}