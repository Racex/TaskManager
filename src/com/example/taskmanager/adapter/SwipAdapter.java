package com.example.taskmanager.adapter;


import org.json.JSONArray;

import com.example.taskmanager.R;
import com.example.taskmanager.R.drawable;
import com.example.taskmanager.R.id;
import com.example.taskmanager.R.layout;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SwipAdapter extends PagerAdapter{
	//public int [] pictureList = {R.drawable.ic_launcher , R.drawable.ic_launcher };
	private String [] pictureUrl= {"https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/6.10.png" ,"https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/9.10.png","https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/11.10.png","https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/27.10.png"};
	private Context context;
	private LayoutInflater layoutinflator;
	
	public SwipAdapter(Context context ) 
	{	
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureUrl.length;
	}




	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		
		layoutinflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item_view=layoutinflator.inflate(R.layout.swip_menu , container , false);
		ImageView imageView = (ImageView) item_view.findViewById(R.id.swipePicture);	
		Picasso.with(context).load(pictureUrl[position]).into(imageView);
		container.addView(item_view);
		return imageView;
	}


	
	@Override
	public boolean isViewFromObject(View view, Object o) {
		Boolean cos;
		cos=(view.findViewById(R.id.swipePicture) == o);
		return cos;
	}	
	public String getUri(int index)
	{
		return pictureUrl[index];
	}
	

}
