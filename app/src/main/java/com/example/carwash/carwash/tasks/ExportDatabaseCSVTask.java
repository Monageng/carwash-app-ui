package com.example.carwash.carwash.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.carwash.carwash.constants.CommonConstants;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.db.Customer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * Created by monageng on 2017/06/01.
 */

public class ExportDatabaseCSVTask extends AsyncTask<String, String, String> {

    private Context context;
    public ExportDatabaseCSVTask(Context contex) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        File exportDir = new File(Environment.getExternalStorageDirectory(), "/backup/");

        if (!exportDir.exists()) { exportDir.mkdirs(); }

        File file = new File(exportDir, "clientv1.csv");
        try {
            file.createNewFile();
            CarwashDAO dao = new CarwashDAO(context);
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = dao.getSqLiteDatabase(CommonConstants.DB_CLIENTS).rawQuery("select * from " + Customer.Entry.TABLE_NAME, null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext()) {
                String arrStr[]=null;
                String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                for(int i=0;i<curCSV.getColumnNames().length;i++)
                {
                    mySecondStringArray[i] =curCSV.getString(i);
                }
                csvWrite.writeNext(mySecondStringArray);
            }
            csvWrite.close();
            curCSV.close();
            return "";
        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage(), e);
            return "";
        }

        //return  null;
    }
}
