package com.example.taskmanager.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
	public static final String PREFS_NAME = "UserInfo";
	Context context;

	public UserPreferences(Context context) {
		this.context = context;
	}
	public String getSettings()
	{
		SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
		if(settings.getString("Username", "").toString() !=null)
		return settings.getString("sortBy", "").toString();
		return "time_end";
		
	}
	public void save(String save) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("sortBy", save);
		editor.commit();
	}
}
