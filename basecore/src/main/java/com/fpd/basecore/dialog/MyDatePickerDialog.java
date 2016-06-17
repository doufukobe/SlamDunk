package com.fpd.basecore.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/**
 * Created by solo on 2016/6/13.
 */
public class MyDatePickerDialog extends DatePickerDialog
{
    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
    {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    public MyDatePickerDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
    {
        super(context, theme, listener, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day)
    {
        super.onDateChanged(view, year, month, day);
        this.setTitle(year + "年" + (month+1) + "月" + day + "号");
    }
}
