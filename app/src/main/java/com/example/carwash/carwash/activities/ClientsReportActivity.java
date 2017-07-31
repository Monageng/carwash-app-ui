package com.example.carwash.carwash.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.constants.CommonConstants;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.db.Customer;
import com.example.carwash.carwash.db.CustomerDbHelper;
import com.example.carwash.carwash.tasks.ExportDatabaseCSVTask;
import com.example.carwash.carwash.utils.ExportDatabaseHelper;
import com.example.carwash.carwash.utils.SMSHelper;
import com.example.carwash.carwash.utils.SendEmail;

import java.io.File;
import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class ClientsReportActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_report);

       try {


           int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

           if (permission != PackageManager.PERMISSION_GRANTED) {
               // We don't have permission so prompt the user
               ActivityCompat.requestPermissions(
                       this,
                       PERMISSIONS_STORAGE,
                       REQUEST_EXTERNAL_STORAGE
               );
           }

           //ExportDatabaseHelper databaseHelper = new ExportDatabaseHelper(this);


       } catch (Exception e) {
           Toast.makeText(this, "SMS Failed, please try again later", Toast.LENGTH_LONG).show();
       }
    }

    public void sendSMS(View view) {
        EditText editText = (EditText) findViewById(R.id.sendSmsText);
        String cellNumber = editText.getText().toString();

        SMSHelper smsHelper = new SMSHelper();
        String smsMessage = "Happy birth day to you, Always persue your dreams at all costs, Have a FANTASTIC DAY";
        smsHelper.sendSMS(this, cellNumber, null, smsMessage);
    }

    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        EditText editText = (EditText) findViewById(R.id.sendEmailText);
        String emailAddress = editText.getText().toString();


        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        ExportDatabaseHelper exportDatabaseHelper = new ExportDatabaseHelper(this);
        exportDatabaseHelper.exportDatabase(CommonConstants.DB_CLIENTS, getDatabasePath(CommonConstants.DB_CLIENTS), "clients", "monageng2010@gmail.com");

        //exportDatabaseHelper.exportDatabase(CommonConstants.DB_CARWASH, getDatabasePath(CommonConstants.DB_CARWASH), "clients", "monageng2010@gmail.com");

    }

}
