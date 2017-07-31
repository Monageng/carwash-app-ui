package com.example.carwash.carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.dto.CustomerDto;

import java.util.ArrayList;


/**
 * Created by monageng on 2017/05/22.
 */

public class CustomerAdapter extends ArrayAdapter<CustomerDto> {

    public CustomerAdapter(Context context, ArrayList<CustomerDto> list) {
        super(context, 0, list);
    }

    public View getView(int position, View view, ViewGroup parent) {
        CustomerDto dto = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.clients_item, parent, false);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.txtName);
        TextView textView2 = (TextView) view.findViewById(R.id.txtSurname);
        TextView textView3 = (TextView) view.findViewById(R.id.txtRegNo);
        TextView textView4 = (TextView) view.findViewById((R.id.txtCell));
        //TextView textView5 = (TextView) view.findViewById(R.id.txtDOB) ;

        textView1.setText(dto.name);
        textView2.setText(dto.surname);
        textView3.setText(dto.regNo);
        textView4.setText(dto.cellNumber);
        //textView5.setText(dto.dateOfBirth);


        return view;
    }
}

