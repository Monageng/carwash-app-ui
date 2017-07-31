package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.dto.CustomerDto;
import com.example.carwash.carwash.impl.CarwashImpl;

public class SearchClientsActivity extends AppCompatActivity {

    public static final String VAR_NAME = "com.example.carwash.VAR_NAME";
    public static final String VAR_SURNAME = "com.example.carwash.VAR_SURNAME";
    public static final String VAR_REG_NO = "com.example.carwash.VAR_REG_NO";
    public static final String VAR_DATE_OF_BIRTH = "com.example.carwash.VAR_DATE_OF_BIRTH";
    public static final String VAR_CELL_NUMBER = "com.example.carwash.VAR_CELL_NUMBER";
    public static final String VAR_MODIEFIED_DATE = "com.example.carwash.VAR_MODIEFIED_DATE";

    private CarwashImpl carwashImpl = new CarwashImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void searchCar(View view) {
        Intent intent = new Intent(this, SearchResultsActivity.class);

        EditText searchRegNoEdit = (EditText) findViewById(R.id.searchRegNo);
        String searchRegNo = searchRegNoEdit.getText().toString();

        //CarwashDAO dao = new CarwashDAO(this);
        //CustomerDto dto= dao.searchClientByRegNo(searchRegNo);
        CustomerDto dto = carwashImpl.searchClientByRegNo(searchRegNo);
       if (dto == null) {
            TextView errMessageTxt = (TextView) findViewById(R.id.errMessage);
            errMessageTxt.setText(searchRegNo + " , Not found.");
        } else {
            intent.putExtra(VAR_NAME, dto.name);
            intent.putExtra(VAR_SURNAME, dto.surname);
            intent.putExtra(VAR_REG_NO, dto.regNo);
            intent.putExtra(VAR_DATE_OF_BIRTH, dto.dateOfBirth);
            intent.putExtra(VAR_CELL_NUMBER, dto.cellNumber);
            intent.putExtra(VAR_MODIEFIED_DATE,  dto.modifiedDate);

            startActivity(intent);
        }


    }
}
