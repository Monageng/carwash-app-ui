package com.example.carwash.carwash.utils;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by monageng on 2017/07/11.
 */

public class HTTPHelper {

    private static final String HOST_NAME = "46.101.255.101";
    private static final String PORT = "8080";
    private static final String PATH_GET_TRANSACTIONS = "/carwash/rest/ClientService/transactions";
    private static final String EMAIL_PASSWORD = "metswalle123" ;

    public HttpURLConnection getConnection(String path) throws IOException {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            String url = "http://" + HOST_NAME + ":" + PORT + path;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //con.connect();
            return  con;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String readStream(InputStream in) {
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
}
