package com.example.carwash.carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.dto.CarWashTxnDto;

import java.util.ArrayList;


/**
 * Created by monageng on 2017/05/22.
 */

public class CarWashTxnAdapter extends ArrayAdapter<CarWashTxnDto> {

    public CarWashTxnAdapter(Context context, ArrayList<CarWashTxnDto> list) {
        super(context, 0, list);
    }

    public View getView(int position, View view, ViewGroup parent) {
        CarWashTxnDto dto = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.wash_item, parent, false);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.txnRegNo);
        TextView textView2 = (TextView) view.findViewById(R.id.txnMonth);
        TextView textView3 = (TextView) view.findViewById(R.id.txnCount);
        //TextView textView4 = (TextView) view.findViewById((R.id.txtCell));
        //TextView textView5 = (TextView) view.findViewById(R.id.txtDOB) ;

        textView1.setText(dto.getRegNo());
        textView2.setText(dto.getMonth());
        textView3.setText(dto.getCount() + "");
        //textView4.setText(dto.getDate());
        //textView5.setText(dto.dateOfBirth);


        return view;
    }
}

