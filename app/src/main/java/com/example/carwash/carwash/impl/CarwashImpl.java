package com.example.carwash.carwash.impl;

import android.util.Log;

import com.example.carwash.carwash.dto.CarWashTxnDto;
import com.example.carwash.carwash.dto.CustomerDto;
import com.example.carwash.carwash.utils.DateUtils;
import com.example.carwash.carwash.utils.HTTPHelper;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by monageng on 2017/07/11.
 */

public class CarwashImpl {
    private HttpURLConnection con;
    private HTTPHelper httpHelper = new HTTPHelper();

    public void createClient(CustomerDto dto)  {
        try {
            con = httpHelper.getConnection("/carwash/rest/ClientService/client/register");
            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            JSONObject j = new JSONObject();
            j.put("regNo", dto.regNo);
            j.put("name", dto.name);
            j.put("surname", dto.surname);
            j.put("cellNumber", dto.cellNumber);
            j.put("dateOfBirth", dto.dateOfBirth);

            OutputStreamWriter streamWriter = new OutputStreamWriter(con.getOutputStream());
            streamWriter.write(j.toString());
            streamWriter.flush();
            streamWriter.close();

            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = httpHelper.readStream(con.getInputStream());
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
    }

    public CustomerDto searchClientByRegNo(String searchRegNo) {
        CustomerDto dto = new CustomerDto();
        try {
            HttpURLConnection con = httpHelper.getConnection("/carwash/rest/ClientService/searchClientByRegNo?regNo="+searchRegNo);

            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = httpHelper.readStream(con.getInputStream());
                JSONObject json = new JSONObject(server_response);
                //objectMapper.write


                dto = new CustomerDto();
                dto.regNo = json.getString("regNo");
                dto.name = json.getString("name");
                dto.surname = json.getString("surname");
                dto.cellNumber = json.getString("cellNumber");
                dto.dateOfBirth = json.getString("dateOfBirth");
                // /dto.modifiedDate =
                //dto.setRegNo(j.getString("regNo"));
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
        return dto;
    }

    public ArrayList<CustomerDto> getAllClients() {
        ArrayList<CustomerDto> list = new ArrayList<CustomerDto>();
        try {
            HttpURLConnection con = httpHelper.getConnection("/carwash/rest/ClientService/clients");

            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = httpHelper.readStream(con.getInputStream());

                JSONArray jsonArray = new JSONArray(server_response);

                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject j = (JSONObject) jsonArray.get(i);
                    CustomerDto dto = new CustomerDto();
                    dto.regNo = j.getString("regNo");
                    dto.dateOfBirth = j.getString("dateOfBirth");
                    dto.cellNumber = j.getString("cellNumber");
                    dto.name = j.getString("name");
                    dto.surname = j.getString("surname");
                    list.add(dto);
                }
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
        return list;
    }


    public ArrayList<CustomerDto> searchClientByBirthMonth(String month)  {

        ArrayList<CustomerDto> list = new ArrayList<CustomerDto>();
        try {
            HttpURLConnection con = httpHelper.getConnection("/carwash/rest/ClientService/searchClientByBirthMonth?month" + month);

            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = httpHelper.readStream(con.getInputStream());

                JSONArray jsonArray = new JSONArray(server_response);

                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject j = (JSONObject) jsonArray.get(i);
                    CustomerDto dto = new CustomerDto();
                    dto.regNo = j.getString("regNo");
                    dto.dateOfBirth = j.getString("dateOfBirth");
                    dto.cellNumber = j.getString("cellNumber");
                    dto.name = j.getString("name");
                    dto.surname = j.getString("surname");
                    list.add(dto);
                }
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
        return list;
    }


    public void insertWashRecord(CarWashTxnDto dto) {
        try {
            con = httpHelper.getConnection("/carwash/rest/ClientService/client/logTransaction");
            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            JSONObject j = new JSONObject();
            j.put("regNo", dto.getRegNo());
            //j.put("month", dto.name);
            j.put("date", DateUtils.formatToDateString(new Date(), DateUtils.PATTERN_YYYY_MM_DD));
            //j.put("cellNumber", dto.cellNumber);
            //j.put("dateOfBirth", dto.dateOfBirth);

            OutputStreamWriter streamWriter = new OutputStreamWriter(con.getOutputStream());
            streamWriter.write(j.toString());
            streamWriter.flush();
            streamWriter.close();

            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String server_response = httpHelper.readStream(con.getInputStream());
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

    }
}
