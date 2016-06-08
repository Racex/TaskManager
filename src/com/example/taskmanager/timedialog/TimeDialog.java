package com.example.taskmanager.timedialog;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TimePicker;

public class TimeDialog implements OnFocusChangeListener, OnTimeSetListener {   

       private EditText editText;
       private Context context;
       private Calendar myCalendar;

       public TimeDialog(Context context , EditText editText){
           this.editText = editText;
           this.context=context;
           this.editText.setOnFocusChangeListener(this);
           this.myCalendar = Calendar.getInstance();

       }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if(hasFocus){
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                Log.d("focus", "Zfokusowany");
                new TimePickerDialog(context, this, hour, minute, true).show();
                }

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
        	String hours=String.valueOf(hourOfDay);
  			String minutes=String.valueOf(minute);
  			if(minute < 10 )
  			{
  				minutes="0"+String.valueOf(minute);
  			}
  			if(hourOfDay <10)
  			{
  				hours="0"+String.valueOf(hourOfDay);
  			}
            this.editText.setText( hours + ":" + minutes);
        }

		

    }