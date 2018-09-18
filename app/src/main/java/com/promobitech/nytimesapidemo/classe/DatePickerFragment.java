package com.promobitech.nytimesapidemo.classe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.DatePicker;

import com.promobitech.nytimesapidemo.TimesMovieReviewActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DatePickerFragment";
    private int requestCode;

    public static DatePickerFragment newInstance(int id) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Log.d(TAG, "newInstance Called  Request code "+id);
        Bundle args = new Bundle();
        args.putInt("id", id);
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCode=getArguments().getInt("id");

        Log.d(TAG, "onCreate Called  Request code "+requestCode);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                ((TimesMovieReviewActivity) getActivity()).setDate(null, requestCode);
                return true;
            }
            return false;
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        requestCode=getArguments().getInt("id");
        Log.d(TAG, "onCreateDialog Called  Request code "+requestCode);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat(AppConstant.DATE_FORMATER_ONLY_DATE);
        String strDate = format.format(calendar.getTime());
        Log.d(TAG, "onDateSet Called " + strDate+" Request code "+requestCode);
        ((TimesMovieReviewActivity) getActivity()).setDate(strDate, requestCode);
    }

}
