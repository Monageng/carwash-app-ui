package com.example.carwash.carwash.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;;

import com.example.carwash.carwash.constants.CommonConstants;
import com.example.carwash.carwash.db.Customer;
import com.example.carwash.carwash.db.CustomerDbHelper;
import com.example.carwash.carwash.db.WashTxn;
import com.example.carwash.carwash.db.WashTxnDbHelper;
import com.example.carwash.carwash.dto.CarWashTxnDto;
import com.example.carwash.carwash.dto.CustomerDto;
import com.example.carwash.carwash.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by monageng on 2017/05/25.
 */

public class CarwashDAO {

    private Context context;

    public CarwashDAO(Context context) {
        this.context = context;
    }

    public boolean insertWashRecord(CarWashTxnDto dto) throws Exception {
        if (dto != null) {
            SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CARWASH);
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            values.put(WashTxn.Entry.COLUMN_NAME_DATE, "datetime()");
            values.put(WashTxn.Entry.COLUMN_NAME_REG_NO, dto.getRegNo());
            values.put(WashTxn.Entry.COLUMN_NAME_COUNT, dto.getCount());
            values.put(WashTxn.Entry.COLUMN_NAME_MONTH, dto.getMonth());

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(WashTxn.Entry.TABLE_NAME, null, values);
            return  newRowId > 0;
        } else {
            throw  new Exception("CarWashTxnDto is null");
        }
    }

    public List<CarWashTxnDto> getWashRecordByRegNo(String regNo) {
        List<CarWashTxnDto> list = new ArrayList<CarWashTxnDto>();
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CARWASH);

        String[] projection = getProjection();

        String selection = WashTxn.Entry.COLUMN_NAME_REG_NO + " = ?";
        String[] selectionArgs = { regNo };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =  WashTxn.Entry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(WashTxn.Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        buildCarWashTxnDtoList(list, cursor);
        cursor.close();

        return  list;
    }

    public int getWashCount(String regNo, String month) {
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CARWASH);
        int count  = 0;

        String[] projection = getProjection();
        String[] selectionArgs = { regNo , month};

        // Filter results WHERE "title" = 'My Title'
        String selection = WashTxn.Entry.COLUMN_NAME_REG_NO + " = ? AND " + WashTxn.Entry.COLUMN_NAME_MONTH + " = ?";

        // How you want the results sorted in the resulting Cursor
        String sortOrder =  WashTxn.Entry.COLUMN_NAME_REG_NO + ", " + WashTxn.Entry.COLUMN_NAME_MONTH + " DESC";

        Cursor cursor = db.query(WashTxn.Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        while(cursor.moveToNext()) {
            count ++;
        }
        cursor.close();
        return  count;
    }

    public SQLiteDatabase getSqLiteDatabase(String database) {
        if (database.equalsIgnoreCase(CommonConstants.DB_CLIENTS)) {
            CustomerDbHelper mDbHelper = new CustomerDbHelper(context);
            return mDbHelper.getReadableDatabase();
        } else if (database.equalsIgnoreCase(CommonConstants.DB_CARWASH)) {
            WashTxnDbHelper mDbHelper = new WashTxnDbHelper(context);
            return mDbHelper.getReadableDatabase();
        }
        return null;
    }

    public ArrayList<CarWashTxnDto> getAllWashRecords() {
        ArrayList<CarWashTxnDto> list = new ArrayList<CarWashTxnDto>();
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CARWASH);


        String[] projection = getProjection();

        // Filter results WHERE "title" = 'My Title'
        String selection = WashTxn.Entry.COLUMN_NAME_REG_NO + " = ?";

        // How you want the results sorted in the resulting Cursor
        String sortOrder =  WashTxn.Entry.COLUMN_NAME_REG_NO + ", " + WashTxn.Entry.COLUMN_NAME_MONTH + " DESC";

        Cursor cursor = db.query(WashTxn.Entry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        buildCarWashTxnDtoList(list, cursor);
        cursor.close();

        return  list;
    }

    private void buildCarWashTxnDtoList(List<CarWashTxnDto> list, Cursor cursor) {
        String regNo = "";
        String month = "";
        boolean recordAdded = false;
       ;
        int count = 0;
        CarWashTxnDto dto = null;
        while(cursor.moveToNext()) {
            String dbReg = cursor.getString(cursor.getColumnIndexOrThrow(WashTxn.Entry.COLUMN_NAME_REG_NO));
            String dbMonth = cursor.getString(cursor.getColumnIndexOrThrow(WashTxn.Entry.COLUMN_NAME_MONTH));
            count = cursor.getInt(cursor.getColumnIndexOrThrow(WashTxn.Entry.COLUMN_NAME_COUNT));

            if (!regNo.equalsIgnoreCase(dbReg)) {
                regNo = dbReg;
                month = dbMonth;
                dto = new CarWashTxnDto();
                dto.setMonth(month);
                dto.setRegNo(regNo);
                dto.setCount(count);
                list.add(dto);
            } else {
                if (month.equalsIgnoreCase(dbMonth)) {
                    dto.setCount(count + dto.getCount());
                } else {
                    month = dbMonth;
                    dto = new CarWashTxnDto();
                    dto.setRegNo(regNo);
                    dto.setMonth(month);
                    dto.setCount(count);
                    list.add(dto);
                }

            }

        }
    }



    public void insertCustomer(CustomerDto dto) {
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CLIENTS);
        ContentValues values = new ContentValues();
        values.put(Customer.Entry.COLUMN_NAME_NAME, dto.name);
        values.put(Customer.Entry.COLUMN_NAME_SURNAME, dto.surname);
        values.put(Customer.Entry.COLUMN_NAME_CELL_NO, dto.cellNumber);
        values.put(Customer.Entry.COLUMN_NAME_REG_NO, dto.regNo);
        values.put(Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH, dto.dateOfBirth);
        values.put(Customer.Entry.COLUMN_MODIFIED_DATE, dto.modifiedDate);
        long newRowId = db.insert(Customer.Entry.TABLE_NAME, null, values);
        // Create a new map of values, where column names are the keys


        // Insert the new row, returning the primary key value of the new row
        //long newRowId = db.insert(Customer.Entry.TABLE_NAME, null, values);

    }

    public CustomerDto searchClientByRegNo(String regNo) {
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CLIENTS);

        CustomerDto dto = null;
        String[] projection = getClientProjection();
        String selection = Customer.Entry.COLUMN_NAME_REG_NO + " = ?";
        String[] selectionArgs = { regNo };

        String sortOrder =  Customer.Entry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(Customer.Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        while(cursor.moveToNext()) {
            String resName = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_NAME));
            String resSurname = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_SURNAME));
            String resRegNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_REG_NO));
            String resCellNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_CELL_NO));
            String resDateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH));
            String resModifiedDate = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_MODIFIED_DATE));
            dto = new CustomerDto(resName,resSurname, resRegNo, resCellNo, resDateOfBirth, resModifiedDate);
        }
        cursor.close();
        return dto;
    }

    public ArrayList<CustomerDto> searchClientByBirthMonth(String month)  {

        ArrayList<CustomerDto> list  = new ArrayList<CustomerDto>();
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CLIENTS);
        String[] projection =  getClientProjection();
        String sortOrder =  Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH + " ASC";
        Cursor cursor = db.query(Customer.Entry.TABLE_NAME, projection, null, null, null, null, sortOrder);




        while(cursor.moveToNext()) {

            String resDateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH));
            Date date = null;
            try {
                date = DateUtils.parseToDate(resDateOfBirth, DateUtils.PATTERN_YYYY_MM_DD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dbMonth = DateUtils.getMonthFromDate(date);

            if (month.contains(dbMonth)) {
                String resName = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_NAME));
                String resSurname = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_SURNAME));
                String resRegNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_REG_NO));
                String resCellNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_CELL_NO));
                String resModiedDate = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_MODIFIED_DATE));
                CustomerDto dto = new CustomerDto(resName, resSurname, resRegNo, resCellNo, resDateOfBirth, resModiedDate);
                list.add(dto);
            }
        }
        return list;
    }
    public ArrayList<CustomerDto> getAllClients() {
        SQLiteDatabase db = getSqLiteDatabase(CommonConstants.DB_CLIENTS);

        String[] projection =  getClientProjection();
        String sortOrder =  Customer.Entry.COLUMN_NAME_NAME + " DESC";
        Cursor cursor = db.query(Customer.Entry.TABLE_NAME, projection, null, null, null, null, sortOrder);
        ArrayList<CustomerDto> customerList = new ArrayList<CustomerDto>();
        buildClientDto(cursor, customerList);
        cursor.close();
        return customerList;
    }

    private void buildClientDto(Cursor cursor, ArrayList<CustomerDto> customerList) {
        while(cursor.moveToNext()) {
            String resName = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_NAME));
            String resSurname = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_SURNAME));
            String resRegNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_REG_NO));
            String resCellNo = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_CELL_NO));
            String resDateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH));
            String resModiedDate = cursor.getString(cursor.getColumnIndexOrThrow(Customer.Entry.COLUMN_MODIFIED_DATE));
            CustomerDto dto = new CustomerDto(resName, resSurname, resRegNo, resCellNo, resDateOfBirth, resModiedDate);
            customerList.add(dto);
        }
    }

    private String[] getClientProjection() {
        return new String[]{
                    Customer.Entry.COLUMN_NAME_CELL_NO,
                    Customer.Entry.COLUMN_NAME_DATE_OF_BIRTH,
                    Customer.Entry.COLUMN_NAME_NAME,
                    Customer.Entry.COLUMN_NAME_REG_NO,
                    Customer.Entry.COLUMN_NAME_SURNAME,
                    Customer.Entry.COLUMN_MODIFIED_DATE
            };
    }

    private String[] getProjection() {
        return new String[]{
                WashTxn.Entry.COLUMN_NAME_DATE,
                WashTxn.Entry.COLUMN_NAME_COUNT,
                WashTxn.Entry.COLUMN_NAME_MONTH,
                WashTxn.Entry.COLUMN_NAME_REG_NO
        };
    }


}
