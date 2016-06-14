package com.example.taskmanager.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.taskmanager.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class TimeToEnd {
	private Context context;
	public TimeToEnd() {
		// TODO Auto-generated constructor stub
	}
	public TimeToEnd(Context context)
	{
		this.context=context;
	}
	// need to add changing colors  background
	public String getTimeToEnd(String timeToEnd) throws ParseException
	{	
		 if(timeToEnd.equals(" "))// when timeToend is not set
		 return "-";
		 Calendar calendar = Calendar.getInstance();
		 String datummm = String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(calendar.get(Calendar.MINUTE)));
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
         Date date = formatter.parse(timeToEnd); // You will need try/catch around this 
         long timeToEndInMilis = date.getTime();
         Date date2 = formatter.parse(datummm);
         long actualyTimeInMilis = date2.getTime();
         if(actualyTimeInMilis > timeToEndInMilis)
         {
        	 return "koniec";
         }
         long minute =((timeToEndInMilis-actualyTimeInMilis)/60000);
         long days=0;
         long hours=0;
         if(minute > 60)
         {
        	 hours=minute/60;
        	 minute=minute%60;
         }else{
        	 if(minute==0)
        		 return "kilka sekund";
        	else if(minute==1)
        	 return minute+" minuta";
        	 else if(minute>1 || minute <= 4)
            	 return minute+" minuty";
        	 else 
        		 return minute+" minut";
         }
         if(hours >= 24)
         {		
        	 days=hours/24;
        	 hours=hours%24;
         }else
         {
        	 return hours+" h "+minute+" min";
         }
         
         if( days > 30)
         {
        	 return "Ponad 30 dni";
         }else
         {
        	 return days+" dni";
         }
         
	
	}
	public int getColorToEnd(String  timeToEnd) throws ParseException
	{	
		 if(timeToEnd.equals(" "))// when timeToend is not set
		 return	-1;
		 Calendar calendar = Calendar.getInstance();
		 String datummm = String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(calendar.get(Calendar.MINUTE)));
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
         Date date = formatter.parse(timeToEnd); // You will need try/catch around this 
         long timeToEndInMilis = date.getTime();
         Date date2 = formatter.parse(datummm);
         long actualyTimeInMilis = date2.getTime();
         if(actualyTimeInMilis > timeToEndInMilis)
         {
        	 return	3;
         }
         long minute =((timeToEndInMilis-actualyTimeInMilis)/60000);
         long days=0;
         long hours=0;
         if(minute > 60)
         {
        	 hours=minute/60;
        	 minute=minute%60;
         }else{
        	 return	2;
//        	 if(minute==0)
//        		 return "kilka sekund";
//        	else if(minute==1)
//        	 return minute+" minuta";
//        	 else if(minute>1 || minute <= 4)
//            	 return minute+" minuty";
//        	 else 
//        		 return minute+" minut";
         }
         if(hours >= 24)
         {		
        	 days=hours/24;
        	 hours=hours%24;
         }else
         {
        	 return	1;
         }
         return -1;
        		 
         //return	ContextCompat.getColorStateList(con,R.color.color_for_hours); // nic nie zwracaj
//         if( days > 30)
//         {
//        	 return "Ponad 30 dni";
//         }else
//         {
//        	 return days+" dni";
//         }
         
	
	}
	
	public  String getActuallyTime()
	{
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(calendar.get(Calendar.MINUTE)));
	}
}
