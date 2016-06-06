package com.example.taskmanager.adapter;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.taskmanager.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String>{
	LayoutInflater inflater;
	Context context;
	JSONArray array;
	TimeToEnd timend;
	
	public volatile static ArrayList<TextView> holder;
	public ListAdapter(Context context, JSONArray array) {
		super(context, R.layout.single_list_model,new String[array.length()]);
		this.context=context;
		this.array=array;
		this.timend = new TimeToEnd();
		// TODO Auto-generated constructor stub
	}
	public class ViewHolder
	{
		TextView title;
		TextView description;
		public TextView days;
		ImageView image;
		//need to add description image from url;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.single_list_model, null);
		}
		
		ViewHolder holder= new ViewHolder();
		
		//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000, 1000);
		holder.title=(TextView) convertView.findViewById(R.id.title);
		holder.description=(TextView) convertView.findViewById(R.id.description);
		holder.days=(TextView) convertView.findViewById(R.id.how_many_days);
		this.holder.add(holder.days);
		holder.image=(ImageView) convertView.findViewById(R.id.picture_from_signal_list_model);
		try {
			
			holder.title.setText(array.getJSONObject(position).getString("title"));
			holder.description.setText(array.getJSONObject(position).getString("description"));
			holder.days.setText(timend.getTimeToEnd(array.getJSONObject(position).getString("date")));
			try{
				
			Picasso.with(context).load(array.getJSONObject(position).getString("url")).into(holder.image);
			}catch(Exception e)
			{
				Log.d("wysypal sie ", "nie ma zdjiecia");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return convertView;
	}

	public  ArrayList<TextView> getHolder()
	{
		return holder;
	}

}
