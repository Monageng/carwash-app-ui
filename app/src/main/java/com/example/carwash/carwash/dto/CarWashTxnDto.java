package com.example.carwash.carwash.dto;

import java.util.Date;

/**
 * Created by monageng on 2017/05/25.
 */

public class CarWashTxnDto {
    private String regNo;
    private String month;
    private int count;
    private Date date;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
