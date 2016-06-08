package com.example.taskmanager.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeToEnd {
	
	// need to add changing colors  background
	public String getTimeToEnd(String timeToEnd) throws ParseException
	{	
		 Calendar calendar = Calendar.getInstance();
		 String datummm = String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(calendar.get(Calendar.MINUTE)));
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
         Date date = formatter.parse(timeToEnd); // You will need try/catch around this 
         long timeToEndInMilis = date.getTime();
         Date date2 = formatter.parse(datummm);
         long actualyTimeInMilis = date2.getTime();
         if(actualyTimeInMilis > timeToEndInMilis)
         {
        	 return "Koniec";
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
        	 return minute+" Minuta";
        	 else if(minute>1 || minute <= 4)
            	 return minute+" Minuty";
        	 else 
        		 return minute+" Minut";
         }
         if(hours >= 24)
         {		
        	 days=hours/24;
        	 hours=hours%24;
         }else
         {
        	 return hours+" godzin "+minute+" minut";
         }
         
         if( days > 30)
         {
        	 return "Ponad 30 Dni";
         }else
         {
        	 return days+" Dni";
         }
         
	
	}
	
	public  String getActuallyTime()
	{
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(calendar.get(Calendar.MINUTE)));
	}
}
