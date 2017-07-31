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
import com.example.carwash.carwash.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private  String errorMessage = "";

    /** Called when the user taps the Send button */

    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SearchClientsActivity.class);
        errorMessage = "";
        validateView(view);

        EditText editName = (EditText) findViewById(R.id.editName);
        String name = editName.getText().toString();

        EditText editSurname = (EditText) findViewById(R.id.editSurname);
        String surname = editSurname.getText().toString();

        EditText editCellNo = (EditText) findViewById(R.id.editCellNo);
        String cellNo = editCellNo.getText().toString();

        EditText editRegNo = (EditText) findViewById(R.id.editRegNo);
        String regNo = editRegNo.getText().toString();

        EditText editDateOfBirth = (EditText) findViewById(R.id.editDateOfBirth);
        String dateOfBirth = editDateOfBirth.getText().toString();

        if (errorMessage != null && errorMessage.length() > 0) {
            TextView errTxt = (TextView) findViewById(R.id.errorMessageTxt);
            errTxt.setText(errorMessage);
        } else {
            Date currentDate = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyy-MM-dd");
            //CarwashDAO dao = new CarwashDAO(this);
            CustomerDto dto = new CustomerDto(name,surname,regNo,cellNo,dateOfBirth, dateFormatter.format(currentDate));
            //dao.insertCustomer(dto);

            CarwashImpl carwashImpl = new CarwashImpl();
            carwashImpl.createClient(dto);
            startActivity(intent);
        }
    }

    private void validateView(View view) {
        EditText editName = (EditText) findViewById(R.id.editName);
        if (editName.getText().length() < 1) {
            errorMessage = errorMessage + " Name is mandatory, \r\n";
        }

        EditText editSurname = (EditText) findViewById(R.id.editSurname);
        if (editSurname.getText().length() < 1) {
            errorMessage = errorMessage + " Surname is mandatory, \r\n";
        }

        EditText editRegNo = (EditText) findViewById(R.id.editRegNo);
        if (editRegNo.getText().length() < 1) {
            errorMessage = errorMessage + " Registration no is mandatory, \r\n";
        }
        EditText editDateOfBirth = (EditText) findViewById(R.id.editDateOfBirth);
        if (editDateOfBirth.getText().length() <  1) {
            errorMessage = errorMessage + " DateOfBirth is mandatory, \r\n";
        }
        String dateInput = editDateOfBirth.getText().toString();
        try {
            Date date = DateUtils.parseToDate(dateInput, DateUtils.PATTERN_YYYY_MM_DD );
        } catch (Exception e) {
            errorMessage = errorMessage + " , " + e.getMessage() + ", \r\n";
        }
    }
}
