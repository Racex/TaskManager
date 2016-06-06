package com.example.taskmanager.timedialog;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker; 

@SuppressLint("ValidFragment") 
public class DateDialog  implements OnFocusChangeListener, OnDateSetListener {
	 private EditText editText;
     private Context context;
     private Calendar myCalendar;
  
	public DateDialog(Context context , EditText editText) {
  		 this.editText = editText;
         this.context=context;
         this.editText.setOnFocusChangeListener(this);
         this.myCalendar = Calendar.getInstance();
  		}

	

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		  if(hasFocus){
              int year =  myCalendar.get(Calendar.YEAR);
              int month =  myCalendar.get(Calendar.MONTH);
              int day =  myCalendar.get(Calendar.DAY_OF_MONTH);
              new DatePickerDialog(context, this,year,month,day).show();
          }
		
	}



	@Override
	public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
  			String day=String.valueOf(dayOfMonth);
			String month=String.valueOf(monthOfYear);
			
			if(monthOfYear < 10 )
			{
				month="0"+String.valueOf(month);
			}
			if(dayOfMonth <10)
			{
				day="0"+String.valueOf(dayOfMonth);
			}
			
        this.editText.setText(years + "-" + month + "-"+day);
      
		
	}
  	

  	
  
  }