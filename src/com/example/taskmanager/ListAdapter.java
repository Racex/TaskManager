package com.example.taskmanager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String>{
	LayoutInflater inflater;
	Context context;
	String [] title;
	
	public ListAdapter(Context context, String [] title) {
		super(context, R.layout.single_list_model, title);
		this.context=context;
		this.title=title;
		// TODO Auto-generated constructor stub
	}
	public class ViewHolder
	{
		TextView tit;
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
		holder.tit=(TextView) convertView.findViewById(R.id.tittle);
		holder.tit.setText(title[position]);
		return convertView;
	}
	

}
