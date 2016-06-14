package com.example.taskmanager.view;

import com.example.taskmanager.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TextView;

public class SetView {
	private Context context;
	public SetView(Context con) {
	context=con;
		// TODO Auto-generated constructor stub
	}
	
	public void setBackground(View view , int set)
	{	
		Drawable draw = ResourcesCompat.getDrawable(view.getResources(), R.drawable.shape_with_color_over_time, null);
		
		
		
		if(set == 1)
		{
			draw = ResourcesCompat.getDrawable(view.getResources(), R.drawable.shape_with_color_hours, null);
			view.setBackground(draw);
		}
		if(set == 2)
		{
			draw = ResourcesCompat.getDrawable(view.getResources(), R.drawable.shape_with_color_minuts, null);
			view.setBackground(draw);
		}
		if(set == 3)
		{
			TextView textview = (TextView) view;
			textview.setTextColor(ContextCompat.getColor(context,R.color.white));
			draw = ResourcesCompat.getDrawable(view.getResources(), R.drawable.shape_with_color_over_time, null);
			view.setBackground(draw);
		}
		if(set != -1){
			{
				
				//color for no date elements
			}
		}
	}
	
}
