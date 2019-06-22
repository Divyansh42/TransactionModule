package com.divyanshu.transactionmodule.Activity;

import android.Manifest;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.divyanshu.transactionmodule.Adapter.ListAdapter;
import com.divyanshu.transactionmodule.Models.SMSData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.divyanshu.transactionmodule.Utils.Parsers.parseAmount;
import static com.divyanshu.transactionmodule.Utils.Parsers.parseTransactionDate;
import static com.divyanshu.transactionmodule.Utils.Parsers.parseTransactionType;

public class MainActivity extends ListActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<SMSData> smsList;
    ArrayList<SMSData> parsedSmsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsList = new ArrayList<SMSData>();
        parsedSmsList = new ArrayList<SMSData>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
           getMessages();
        }




    }

    public void getMessages(){
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, null ,null,null);
        if(cursor != null){
            try{
                if(cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        SMSData sms = new SMSData();
                        sms.setBody(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());
                        sms.setNumber(cursor.getString(cursor.getColumnIndexOrThrow("address")).toString());
                        smsList.add(sms);

                        cursor.moveToNext();
                    }
                }

                parsevalues(smsList);
            }
            catch (IllegalArgumentException e) {
                Log.e(TAG,"Error While getting path", e);
            } finally {
                try {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                    cursor = null;
                } catch (Exception e) {
                    Log.e(TAG,"Error While closing cursor", e);
                }
            }
        }

    }

    private void parsevalues(ArrayList<SMSData> body_val) {
        ArrayList<SMSData> resSms = new ArrayList<>();
        for (int i = 0; i < body_val.size(); i++) {
            SMSData smsData = body_val.get(i);

            smsData.setAmount(parseAmount(smsData));
            smsData.setTransactionType(parseTransactionType(smsData));
            smsData.setTransactionDate(parseTransactionDate(smsData));

            if (!Character.isDigit(smsData.getNumber().charAt(0)))
                if (smsData.getTransactionType() != null && smsData.getAmount() != null
                && smsData.getTransactionDate() != null)
                    resSms.add(smsData);
        }
        setListAdapter(new ListAdapter(this, resSms));
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData)getListAdapter().getItem(position);

        Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
