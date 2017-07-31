package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.dto.CustomerDto;
import com.example.carwash.carwash.impl.CarwashImpl;
import com.example.carwash.carwash.utils.DateUtils;
import com.example.carwash.carwash.utils.StyleColorHelper;
import com.example.carwash.carwash.utils.ViewHelper;

import java.util.ArrayList;
import java.util.Date;

public class BirthDaysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_days);

        CarwashDAO dao = new CarwashDAO(this);
        String month = DateUtils.getMonthFromDate(new Date());
        //ArrayList<CustomerDto> list = dao.searchClientByBirthMonth(month);
        CarwashImpl carwashImpl = new CarwashImpl();
        ArrayList<CustomerDto> list = carwashImpl.searchClientByBirthMonth(month);

        TableLayout birthDayList = (TableLayout) findViewById(R.id.birthDayList);

        TableRow trHead = new TableRow(this);
        trHead.setBackgroundColor(StyleColorHelper.getHeaderBlueColor());
        //trHead.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView regNoTextView = new TextView(this);

        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Name"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Surname"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Cell No"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "DOB"));

        birthDayList.addView(trHead);

        boolean darkRow = true;
        for (int i = 0; i < list.size(); i++) {
            TableRow tr = new TableRow(this);
            if (darkRow) {
                tr.setBackgroundColor(StyleColorHelper.getDarkBlueColor());
                darkRow = !darkRow;
            } else {
                tr.setBackgroundColor(StyleColorHelper.getLightBlueColor());
                darkRow = !darkRow;
            }

            CustomerDto dto = list.get(i);

            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.name, 300));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.surname, 250));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.cellNumber, 320));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.dateOfBirth, 300));
            birthDayList.addView(tr);
        }
        Button button = new Button(this);
        button.setWidth(200);
        button.setText("Home");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home(v);
            }
        });

        TableRow tr = new TableRow(this);
        tr.addView(button);
        birthDayList.addView(tr);

    }
    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
