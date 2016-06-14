package com.example.taskmanager.adapter;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.taskmanager.R;
import com.example.taskmanager.data.TimeToEnd;
import com.example.taskmanager.view.SetView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapter extends ArrayAdapter<String>{
	private LayoutInflater inflater;
	private Context context;
	private JSONArray array;
	private TimeToEnd timend;
	private SetView editView;
	public final static HashMap<String , TextView> holder=new HashMap<String ,TextView>();
	
	public ListAdapter(Context context, JSONArray array) {
		super(context, R.layout.single_list_model,new String[array.length()]);
		this.context=context;
		this.array=array;
		editView=new SetView(context);
		//this.holder=new HashMap<String ,TextView>();
		this.timend = new TimeToEnd(context);
		// TODO Auto-generated constructor stub
	}
	public class ViewHolder
	{	public String id;
		public TextView title;
		public TextView description;
		public TextView days;
		public ImageView image;
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
		holder.days=(TextView) convertView.findViewById(R.id.how_many_days);
		holder.image=(ImageView) convertView.findViewById(R.id.picture_from_signal_list_model);
		try {
			shouldAddtoHashMap(holder.days , array.getJSONObject(position).getString("id").toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			String jSONtime=array.getJSONObject(position).getString("time_end");
			editView.setBackground(holder.title , timend.getColorToEnd(jSONtime));
			holder.days.setText(timend.getTimeToEnd(jSONtime));
			holder.id=array.getJSONObject(position).getString("id").toString();	
			holder.title.setText(array.getJSONObject(position).getString("title"));
			holder.description.setText(array.getJSONObject(position).getString("description"));
			
			
			if(!array.getJSONObject(position).getString("url").toString().equals(""))
				Picasso.with(context).load(array.getJSONObject(position).getString("url").toString()).into(holder.image);	
			else{
				holder.image.setVisibility(android.view.View.INVISIBLE);
				//holder.title.setText(array.getJSONObject(position).getString("id")+":"+array.getJSONObject(position).getString("url")+"<--");
				}
			}catch(Exception e)
			{
				Log.d("wysypal sie ", "nie ma zdjiecia");
				e.printStackTrace();
			}
		return convertView;
		}
	
	private boolean shouldAddtoHashMap(TextView days , String key) {
		if(holder.containsKey(key))
		{	
			holder.remove(key);
			return false;
		}
		
		holder.put(key, days);
		return true;
	}
	

	public  HashMap  <String, TextView> getHolder()
	{
		return holder;
	}

	@Override
	public int getViewTypeCount() {
		if(getCount() !=0)
			return getCount();
		return 1;
	}
	
	@Override
	public int getItemViewType(int position) {
	    return Adapter.IGNORE_ITEM_VIEW_TYPE;
	}

	

}
