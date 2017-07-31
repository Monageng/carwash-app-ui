package com.example.carwash.carwash.dto;

import android.text.SpannableString;

import java.util.Date;

/**
 * Created by monageng on 2017/05/20.
 */

public class CustomerDto {
    public String name = null;
    public String surname = null;
    public String regNo =null;
    public String cellNumber = null;
    public String dateOfBirth = null;
    public String modifiedDate = null;

    public  CustomerDto() {

    }
    public  CustomerDto(String name, String surname, String regNo, String cellNumber, String dateOfBirth, String modifiedDate) {
        this.name = name;
        this.surname =surname;
        this.cellNumber = cellNumber;
        this.dateOfBirth = dateOfBirth;
        this.regNo = regNo;
        this.modifiedDate = modifiedDate;

    }

}
