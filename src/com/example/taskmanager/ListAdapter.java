package com.example.taskmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		try {if(array.getJSONObject(position).getString("title") != null)
			holder.title.setText(array.getJSONObject(position).getString("title"));
			holder.title.setBackgroundColor(0);
			holder.description.setText(array.getJSONObject(position).getString("description"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	

}
