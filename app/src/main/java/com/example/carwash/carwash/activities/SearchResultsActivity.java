package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.dto.CarWashTxnDto;
import com.example.carwash.carwash.impl.CarwashImpl;
import com.example.carwash.carwash.utils.DateUtils;

import java.util.Date;

public class SearchResultsActivity extends AppCompatActivity {

    private String registrationNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra(SearchClientsActivity.VAR_NAME);
        String surname = intent.getStringExtra(SearchClientsActivity.VAR_SURNAME);
        registrationNo = intent.getStringExtra(SearchClientsActivity.VAR_REG_NO);
        String cellNumber = intent.getStringExtra(SearchClientsActivity.VAR_CELL_NUMBER);
        String dateOfbirth = intent.getStringExtra(SearchClientsActivity.VAR_DATE_OF_BIRTH);

        // Capture the layout's TextView and set the string as its text
        TextView resNameTxt = (TextView) findViewById(R.id.resName);
        resNameTxt.setText(name);

        TextView resSurnameTxt = (TextView) findViewById(R.id.resSurname);
        resSurnameTxt.setText(surname);

        TextView resDateOfbithTxt = (TextView) findViewById(R.id.resDateOfBirth);
        resDateOfbithTxt.setText(dateOfbirth);

        TextView resCellNumberTxt = (TextView) findViewById(R.id.resCellNumber);
        resCellNumberTxt.setText(cellNumber);

        TextView resRegNumberTxt = (TextView) findViewById(R.id.resRegNumber);
        resRegNumberTxt.setText(registrationNo);

        CarwashDAO dao = new CarwashDAO(this);
        int count = dao.getWashCount(registrationNo, DateUtils.getMonthFromDate(new Date()));
        TextView resWashCountTxt = (TextView) findViewById(R.id.resWashCount);
        resWashCountTxt.setText(count + "");

        TextView resModifiedTxt = (TextView) findViewById(R.id.resModified);
        resModifiedTxt.setText(intent.getStringExtra(SearchClientsActivity.VAR_MODIEFIED_DATE));
    }

    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void washCar(View view) {
        CarwashDAO dao = new CarwashDAO(this);
        CarWashTxnDto dto = new CarWashTxnDto();
        dto.setRegNo(registrationNo);
        dto.setCount(1);
        dto.setMonth(DateUtils.getMonthFromDate(new Date()));
        try {
            CarwashImpl carwash = new CarwashImpl();
            carwash.insertWashRecord(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }
}
