package com.example.taskmanager.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taskmanager.R;
import com.example.taskmanager.R.id;
import com.example.taskmanager.R.layout;
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
	
	public ListAdapter(Context context, JSONArray array) {
		super(context, R.layout.single_list_model,new String[array.length()]);
		this.context=context;
		this.array=array;
		// TODO Auto-generated constructor stub
	}
	public class ViewHolder
	{
		TextView title;
		TextView description;
		TextView days;
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
		holder.title=(TextView) convertView.findViewById(R.id.title);
		holder.description=(TextView) convertView.findViewById(R.id.description);
		holder.image=(ImageView) convertView.findViewById(R.id.picture_from_signal_list_model);
		try {
			
			holder.title.setText(array.getJSONObject(position).getString("title"));
			holder.description.setText(array.getJSONObject(position).getString("description"));
			String timeToEnd = getTimetoEnd(position);
			try{
				
			Picasso.with(context).load(array.getJSONObject(position).getString("url")).into(holder.image);
			}catch(Exception e)
			{
				Log.d("wysypal sie ", "nie ma zdjiecia");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	private String getTimetoEnd(int position) throws JSONException {
		String dateend=array.getJSONObject(position).getString("date").toString();
		return null;
	}
	

}
