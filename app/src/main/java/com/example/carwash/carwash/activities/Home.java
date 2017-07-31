package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.carwash.carwash.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void newClient(View view) {
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }

    public void searchClient(View view) {
        Intent intent = new Intent(this, SearchClientsActivity.class);
        startActivity(intent);
    }

    public void listAllRegisteredClients(View view) {
        Intent intent = new Intent(this, ListOfRegisteredClientsActivity.class);
        startActivity(intent);
    }

    public  void gelAllCarWashRecords(View view) {
        Intent intent = new Intent(this, AllTransactionsActivity.class);
        startActivity(intent);
    }

    public void getUpComingBirthdays(View view) {
        Intent intent = new Intent(this, BirthDaysActivity.class);
        startActivity(intent);
    }

    public void emailReport(View view) {
        Intent intent = new Intent(this, ClientsReportActivity.class);
        startActivity(intent);
    }
}
