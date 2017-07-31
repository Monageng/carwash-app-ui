package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.adapters.CustomerAdapter;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.db.Customer;
import com.example.carwash.carwash.db.CustomerDbHelper;
import com.example.carwash.carwash.dto.CarWashTxnDto;
import com.example.carwash.carwash.dto.CustomerDto;
import com.example.carwash.carwash.impl.CarwashImpl;
import com.example.carwash.carwash.utils.StyleColorHelper;
import com.example.carwash.carwash.utils.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ListOfRegisteredClientsActivity extends AppCompatActivity {

    private CarwashImpl carwashImpl = new CarwashImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_registered_clients);

        CarwashDAO dao = new CarwashDAO(this);
        ArrayList<CustomerDto> customerList = carwashImpl.getAllClients();

        TableLayout clientListTable = (TableLayout) findViewById(R.id.clientListTable);
        //clientListTable.setHorizontalScrollBarEnabled(true);
        //clientListTable.setVerticalScrollBarEnabled(true);
        TableRow trHead = new TableRow(this);
        trHead.setBackgroundColor(StyleColorHelper.getHeaderBlueColor());
        //trHead.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView regNoTextView = new TextView(this);

        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Name"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Surname"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Reg No"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Cell No"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "DOB"));

        clientListTable.addView(trHead);
        boolean darkRow = true;
        for (int i = 0; i < customerList.size(); i++) {
            TableRow tr = new TableRow(this);
            if (darkRow) {
                tr.setBackgroundColor(StyleColorHelper.getDarkBlueColor());
                darkRow = !darkRow;
            } else {
                tr.setBackgroundColor(StyleColorHelper.getLightBlueColor());
                darkRow = !darkRow;
            }

            CustomerDto dto = customerList.get(i);

            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.name, 300));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.surname, 250));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.regNo, 300));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.cellNumber, 320));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.dateOfBirth, 300));
            clientListTable.addView(tr);
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
        clientListTable.addView(tr);
    }
    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
