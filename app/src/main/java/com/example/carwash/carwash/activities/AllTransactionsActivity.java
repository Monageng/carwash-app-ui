package com.example.carwash.carwash.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.carwash.carwash.R;
import com.example.carwash.carwash.adapters.CarWashTxnAdapter;
import com.example.carwash.carwash.adapters.CustomerAdapter;
import com.example.carwash.carwash.dao.CarwashDAO;
import com.example.carwash.carwash.dto.CarWashTxnDto;
import com.example.carwash.carwash.utils.HTTPHelper;
import com.example.carwash.carwash.utils.StyleColorHelper;
import com.example.carwash.carwash.utils.ViewHelper;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllTransactionsActivity extends AppCompatActivity {

    private HTTPHelper httpHelper = new HTTPHelper();
    private HttpURLConnection con;

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        CarwashDAO dao = new CarwashDAO(this);
        ArrayList<CarWashTxnDto> list= new ArrayList<CarWashTxnDto>();// dao.getAllWashRecords();

        //String url = "http://192.168.0.175:8080/carwash/rest/ClientService/transactions";
        try {
           HttpURLConnection con = httpHelper.getConnection("/carwash/rest/ClientService/transactions");

            //String content = (String) con.getContent();
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = readStream(con.getInputStream());
               // ObjectMapper objectMapper = new ObjectMapper();
                JSONArray jsonArray = new JSONArray(server_response);
                //objectMapper.write

                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject j = (JSONObject) jsonArray.get(i);
                    CarWashTxnDto dto = new CarWashTxnDto();
                    dto.setCount(j.getInt("count"));
                    dto.setMonth(j.getString("month"));
                    dto.setRegNo(j.getString("regNo"));
                    list.add(dto);
                }

                Log.v("CatalogClient", server_response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
               con.disconnect();
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
        TableLayout tableLayout = (TableLayout)  findViewById(R.id.txnTableset);
        TableRow trHead = new TableRow(this);

        trHead.setBackgroundColor(StyleColorHelper.getHeaderBlueColor());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);


        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Registration No"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Month"));
        trHead.addView(ViewHelper.buildTableHeaderTextView(this, "Count"));
        tableLayout.addView(trHead);
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

            CarWashTxnDto dto = list.get(i);

            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.getRegNo(),300));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.getMonth(), 300));
            tr.addView(ViewHelper.buildTableValuesTextView(this, dto.getCount() + "", 300));
            tableLayout.addView(tr);
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
        tableLayout.addView(tr);;

    }
    public void home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    /*

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getContent());
        }

    }*/
}
