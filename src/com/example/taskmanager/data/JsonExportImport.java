package com.example.taskmanager.data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class JsonExportImport {
	Context context;
	DataBase db;
	public JsonExportImport(Context context) {
		this.context=context;
		db=new DataBase(context);
	}
	public void createAndSaveFile(String params, String mJsonResponse) {
	    try {
	        FileWriter file = new FileWriter("/data/data/" + context.getApplicationContext().getPackageName() + "/" + params);
	        file.write(mJsonResponse);
	        file.flush();
	        file.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void readJsonData(String params) throws JSONException {
		//int i=10;
		//showMessage(i);
		String sortBy="";
	    try {
	        File f = new File("/data/data/" + context.getPackageName() + "/" + params);
	        FileInputStream is = new FileInputStream(f);
	        int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	       // String mResponse = new String(buffer);
	        JSONArray arrayFromExport = new JSONArray(new String(buffer));
	        for(int i=0 ; i<arrayFromExport.length(); i++)
	        {
	        	if(db.isIdInDatabase(Integer.valueOf(arrayFromExport.getJSONObject(i).getString("id").toString())))
	        	showMessage(arrayFromExport.getJSONObject(i));
	        	else
	        	{
	        		db.addToDataBase(arrayFromExport.getJSONObject(i));
	        	}
	        }
	        
	        Log.d("JSONE Z EXPORTU" , arrayFromExport.toString());//check and put to DB 
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	public void showMessage(final JSONObject json) throws JSONException{
		
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(context);//getActivity()
	        builder.setMessage("W bazie istnieje ju¿ id z numerem " +json.getString("id") +"\nczy chcesz je nadpisac ?")
	               .setPositiveButton("NIE", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       //do nothing
	                   }
	               })
	               .setNegativeButton("TAK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      try {
							db.updateDataBaseRow(json);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                   }
	               });
	        // Create the AlertDialog object and return it
	        builder.create().show();
	    }
	
}
