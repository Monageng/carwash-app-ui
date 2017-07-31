package com.example.carwash.carwash.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by monageng on 2017/06/01.
 */

public class SMSHelper {
    public void sendSMS(Context context, String cellNumber, String ccNumber, String msg) {
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        else
        {
            //do send or read sms
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(cellNumber, null, msg, null, null);
        Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();
    }

}
