package com.example.carwash.carwash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by monageng on 2017/05/28.
 */

public class ViewHelper {

    public static TextView buildTableHeaderTextView(Context context, String label) {
        TextView textView = new TextView(context);
        textView.setText(label);
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        textView.setPadding(5,5,5,0);
        return textView;
    }
    public static TextView buildTableValuesTextView(Context context, String value, int size) {
        TextView textView = new TextView(context);
        textView.setText(value);
        textView.setWidth(size);
        //textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        //textView.setPadding(5,5,5,0);
        return textView;
    }
}
